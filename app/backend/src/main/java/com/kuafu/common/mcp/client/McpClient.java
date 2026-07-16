package com.kuafu.common.mcp.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kuafu.common.mcp.spec.McpSchema;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Getter
public class McpClient {

    private final String sessionPrefix = UUID.randomUUID().toString().substring(0, 8);
    private final AtomicLong requestCounter = new AtomicLong(0);

    private final Map<String, CompletableFuture<McpSchema.JSONRPCMessage>> pendingRequests = new ConcurrentHashMap<>();

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private final McpTransport sseClient;

    private McpSchema.ServerCapabilities serverCapabilities;
    private String serverInstructions;
    private McpSchema.Implementation serverInfo;

    public McpClient(String baseUrl, String sseEndpoint) {
        this.sseClient = new OkHttpSseClientTransport(baseUrl, sseEndpoint);
        this.sseClient.connect(this::eventHandler);
    }

    public McpClient(ServerParameters params) {
        this.sseClient = new StdioClientTransport(params);
        this.sseClient.connect(this::eventHandler);
    }

    public boolean isInitialized() {
        return this.initialized.get();
    }

    public void eventHandler(McpSchema.JSONRPCMessage message) {
        if (message instanceof McpSchema.JSONRPCResponse) {
            McpSchema.JSONRPCResponse response = (McpSchema.JSONRPCResponse) message;
            String id = String.valueOf(response.getId());
            if (pendingRequests.containsKey(id)) {
                CompletableFuture<McpSchema.JSONRPCMessage> future = pendingRequests.remove(id);
                future.complete(message);
            }
        }
    }

    public void initialize() {
        try {
            McpSchema.InitializeRequest initializeRequest = new McpSchema.InitializeRequest(
                    McpSchema.LATEST_PROTOCOL_VERSION,
                    new McpSchema.ClientCapabilities(null, null, null),
                    new McpSchema.Implementation("Java SDK MCP Client", "1.0.0"));

            CompletableFuture<McpSchema.JSONRPCMessage> future = sendRequest(McpSchema.METHOD_INITIALIZE, initializeRequest);

            McpSchema.JSONRPCMessage response = future.get();
            if (response instanceof McpSchema.JSONRPCResponse) {
                McpSchema.JSONRPCResponse message = (McpSchema.JSONRPCResponse) response;
                McpSchema.InitializeResult result = sseClient.unmarshalFrom(message.getResult(), new TypeReference<McpSchema.InitializeResult>() {
                });

                // 发送
                sendNotification(McpSchema.METHOD_NOTIFICATION_INITIALIZED, null);
                this.initialized.set(true);

                this.serverCapabilities = result.getCapabilities();
                this.serverInstructions = result.getInstructions();
                this.serverInfo = result.getServerInfo();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    private static final TypeReference<McpSchema.CallToolResult> CALL_TOOL_RESULT_TYPE_REF = new TypeReference<McpSchema.CallToolResult>() {
    };

    private static final TypeReference<McpSchema.ListToolsResult> LIST_TOOLS_RESULT_TYPE_REF = new TypeReference<McpSchema.ListToolsResult>() {
    };

    public McpSchema.ListToolsResult listTools() {
        return listTools(null);
    }

    public McpSchema.ListToolsResult listTools(String cursor) {
        try {
            CompletableFuture<McpSchema.JSONRPCMessage> future = sendRequest(McpSchema.METHOD_TOOLS_LIST, new McpSchema.PaginatedRequest(cursor));

            McpSchema.JSONRPCMessage response = future.get();
            if (response instanceof McpSchema.JSONRPCResponse) {
                McpSchema.JSONRPCResponse message = (McpSchema.JSONRPCResponse) response;
                return sseClient.unmarshalFrom(message.getResult(), LIST_TOOLS_RESULT_TYPE_REF);
            } else {
                return null;
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public McpSchema.CallToolResult callTool(McpSchema.CallToolRequest callToolRequest) {
        try {
            CompletableFuture<McpSchema.JSONRPCMessage> future = sendRequest(McpSchema.METHOD_TOOLS_CALL, callToolRequest);

            McpSchema.JSONRPCMessage response = future.get();
            if (response instanceof McpSchema.JSONRPCResponse) {
                McpSchema.JSONRPCResponse message = (McpSchema.JSONRPCResponse) response;
                return sseClient.unmarshalFrom(message.getResult(), CALL_TOOL_RESULT_TYPE_REF);
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<McpSchema.JSONRPCMessage> sendRequest(String method, Object requestParams) {
        String requestId = this.generateRequestId();

        McpSchema.JSONRPCRequest jsonrpcRequest = new McpSchema.JSONRPCRequest(McpSchema.JSONRPC_VERSION, method,
                requestId, requestParams);

        CompletableFuture<McpSchema.JSONRPCMessage> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);

        boolean flag = this.sseClient.sendMessage(jsonrpcRequest);
        if (flag) {
            return future;
        } else {
            pendingRequests.remove(requestId);
            return null;
        }
    }

    public void sendNotification(String method, Object params) {
        McpSchema.JSONRPCNotification jsonrpcNotification = new McpSchema.JSONRPCNotification(McpSchema.JSONRPC_VERSION,
                method, params);
        this.sseClient.sendMessage(jsonrpcNotification);
    }


    private String generateRequestId() {
        return this.sessionPrefix + "-" + this.requestCounter.getAndIncrement();
    }


}
