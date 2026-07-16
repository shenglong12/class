package com.kuafu.common.mcp.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuafu.common.mcp.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class OkHttpSseClientTransport implements McpTransport {

    private static final String MESSAGE_EVENT_TYPE = "message";
    private static final String ENDPOINT_EVENT_TYPE = "endpoint";
    private static final String DEFAULT_SSE_ENDPOINT = "/sse";

    protected ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUri;

    private final String sseEndpoint;

    private final SseClient sseClient;

    private final OkHttpClient httpClient;

    private final AtomicReference<String> messageEndpoint = new AtomicReference<>();
    private CompletableFuture<Void> connectFuture = new CompletableFuture<>();


    public OkHttpSseClientTransport(String baseUri, String sseEndPoint) {
        this(new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(50, TimeUnit.SECONDS)
                .build(), baseUri, sseEndPoint);
    }

    public OkHttpSseClientTransport(OkHttpClient okHttpClient, String baseUri, String sseEndPoint) {
        this.httpClient = okHttpClient;
        this.baseUri = baseUri;
        this.sseClient = new SseClient(httpClient);
        this.sseEndpoint = sseEndPoint;
    }

    @Override
    public void connect(McpClientEventHandler handler) {
        sseClient.subscribe(this.sseEndpoint, new SseClient.SseEventHandler() {
            @Override
            public void onEvent(SseClient.SseEvent event) {
                try {
                    if (ENDPOINT_EVENT_TYPE.equals(event.getType())) {
                        String endpoint = event.getData();
                        messageEndpoint.set(endpoint);
                        //链接成功
                        connectFuture.complete(null);
                    } else if (MESSAGE_EVENT_TYPE.equals(event.getType())) {
                        if (handler != null) {
                            McpSchema.JSONRPCMessage message = McpSchema.deserializeJsonRpcMessage(objectMapper, event.getData());
                            handler.event(message);
                        }
                    }
                } catch (Exception e) {
                    log.error("Error processing SSE event", e);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });

        try {
            connectFuture.get();
        } catch (Exception e) {
            throw new RuntimeException("链接失败" + e.getMessage());
        }
    }

    @Override
    public boolean sendMessage(McpSchema.JSONRPCMessage message) {
        String endpoint = messageEndpoint.get();
        try {
            String jsonText = this.objectMapper.writeValueAsString(message);
            String url = this.baseUri + endpoint;

            log.info("send message {} === {}", url, jsonText);

            RequestBody body = RequestBody.create(
                    MediaType.get("application/json; charset=utf-8"),
                    jsonText
            );
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response resp = this.httpClient.newCall(request).execute();
            return true;
        } catch (Exception e) {
            log.info("send error {}", e);
            return false;
        }
    }

    @Override
    public <T> T unmarshalFrom(Object data, TypeReference<T> typeRef) {
        return this.objectMapper.convertValue(data, typeRef);
    }


}

