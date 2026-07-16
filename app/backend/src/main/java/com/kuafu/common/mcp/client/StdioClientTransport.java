package com.kuafu.common.mcp.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuafu.common.mcp.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class StdioClientTransport implements McpTransport {

    private final BlockingQueue<McpSchema.JSONRPCMessage> outboundQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> errorQueue = new LinkedBlockingQueue<>();

    private Process process;

    protected ObjectMapper objectMapper = new ObjectMapper();

    private final ServerParameters params;

    private McpClientEventHandler handler;

    private ExecutorService inboundExecutor;
    private ExecutorService outboundExecutor;
    private ExecutorService errorExecutor;

    private volatile boolean isClosing = false;


    public StdioClientTransport(ServerParameters params) {

        this.params = params;

        this.inboundExecutor = Executors.newSingleThreadExecutor();
        this.outboundExecutor = Executors.newSingleThreadExecutor();
        this.errorExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void connect(McpClientEventHandler handler) {
        // Prepare command and environment
        List<String> fullCommand = new ArrayList<>();
        fullCommand.add(params.getCommand());
        fullCommand.addAll(params.getArgs());

        ProcessBuilder processBuilder = this.getProcessBuilder();
        processBuilder.command(fullCommand);
        processBuilder.environment().putAll(params.getEnv());

        this.handler = handler;

        // Start the process
        try {
            this.process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start process with command: " + fullCommand, e);
        }

        startInboundProcessing();
        startOutboundProcessing();
        startErrorProcessing();
    }

    protected ProcessBuilder getProcessBuilder() {

        return new ProcessBuilder();
    }


    private void startInboundProcessing() {
        inboundExecutor.submit(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while (!isClosing && (line = reader.readLine()) != null) {
                    try {
                        log.info("===={}", line);
                        McpSchema.JSONRPCMessage msg = McpSchema.deserializeJsonRpcMessage(objectMapper, line);
                        if (this.handler != null) {
                            handler.event(msg);
                        }
                    } catch (Exception e) {
                        log.error("Error in inbound", e);
                    }
                }
            } catch (IOException e) {
                log.error("Reading stdin failed", e);
            }
        });
    }

    private void startOutboundProcessing() {
        outboundExecutor.submit(() -> {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8));
                while (!isClosing) {
                    McpSchema.JSONRPCMessage msg = outboundQueue.take();
                    String json = objectMapper.writeValueAsString(msg)
                            .replace("\r\n", "\\n").replace("\n", "\\n").replace("\r", "\\n");
                    writer.write(json);
                    writer.newLine();
                    writer.flush();
                }
            } catch (Exception e) {
                log.error("Outbound write error", e);
            }
        });
    }

    private void startErrorProcessing() {
        errorExecutor.submit(() -> {
            try {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while (!isClosing && (line = errorReader.readLine()) != null) {
                    log.info("error line ===== {}", line);
                    errorQueue.offer(line);
                }
            } catch (IOException e) {
                log.error("Error reading stderr", e);
            }
        });
    }

    @Override
    public boolean sendMessage(McpSchema.JSONRPCMessage message) {
        // 队列满了返回false
        return outboundQueue.offer(message);
    }


    @Override
    public <T> T unmarshalFrom(Object data, TypeReference<T> typeRef) {
        return this.objectMapper.convertValue(data, typeRef);
    }
}
