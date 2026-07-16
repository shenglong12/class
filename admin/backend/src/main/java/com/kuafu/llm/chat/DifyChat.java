package com.kuafu.llm.chat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kuafu.llm.model.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
@ConditionalOnExpression("${llm.enable:false} and '${llm.chat.provider}'.equalsIgnoreCase('dify')")
public class DifyChat implements Chat {

    @Value("${llm.chat.dify_api}")
    private String difyApi;

    @Value("${llm.chat.dify_key}")
    private String difyKey;

    private final Gson gson = new Gson();

    @Override
    public void callApiStream(String query, String conversationId, String userId, SseEmitter sseEmitter) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .build();
        // Create the request body as a Map
        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put("inputs", new HashMap<>()); // Empty map for "inputs"
        jsonPayload.put("query", query);
        jsonPayload.put("conversation_id", conversationId);
        jsonPayload.put("user", userId);
        jsonPayload.put("response_mode", "streaming");


        // Convert the Map to a JSON string
        String jsonString = gson.toJson(jsonPayload);

        // Create the request body
        RequestBody body = RequestBody.create(
                MediaType.get("application/json; charset=utf-8"),
                jsonString
        );

        // Build the request
        Request request = new Request.Builder()
                .url(difyApi + "/chat-messages")
                .post(body)
                .addHeader("Authorization", "Bearer " + difyKey) // Replace with actual method to get the API key
                .addHeader("Content-Type", "application/json")
                .build();

        EventSourceListener listener = new EventSourceListener() {
            String convsersionId;

            @Override
            public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
                log.info("Connection opened");
            }

            @Override
            public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {

                JsonObject jsonObject = gson.fromJson(data, JsonObject.class);

                String event = jsonObject.get("event").getAsString();

                convsersionId = jsonObject.get("conversation_id").getAsString();

                HashMap<String, String> messageMap = new HashMap<>();
                messageMap.put("event", event);
                messageMap.put("conversation_id", convsersionId);

                if ("message".equals(event)) {

                    String answer = jsonObject.get("answer").getAsString();

                    messageMap.put("answer", answer);
                }

                try {
                    sseEmitter.send(SseEmitter.event().name("message").data(gson.toJson(messageMap)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                log.info("Connection closed,convserionId is " + convsersionId);
                sseEmitter.complete();
            }

            @Override
            public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                try {
                    log.info("error: {}", response.body().string());
                    sseEmitter.complete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        EventSource.Factory factory = EventSources.createFactory(client);
        EventSource eventSource = factory.newEventSource(request, listener);

    }

    @Override
    public ChatResponse callApiBlock(String query, String conversationId, String userId) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(50, TimeUnit.SECONDS)
                .build();

        // Create the request body as a Map
        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put("inputs", new HashMap<>()); // Empty map for "inputs"
        jsonPayload.put("query", query);
        jsonPayload.put("response_mode", "blocking");
        jsonPayload.put("conversation_id", conversationId);
        jsonPayload.put("user", userId);

        // Convert the Map to a JSON string
        String jsonString = gson.toJson(jsonPayload);

        // Create the request body
        RequestBody body = RequestBody.create(
                MediaType.get("application/json; charset=utf-8"),
                jsonString
        );

        // Build the request
        Request request = new Request.Builder()
                .url(difyApi + "/chat-messages")
                .post(body)
                .addHeader("Authorization", "Bearer " + difyKey) // Replace with actual method to get the API key
                .addHeader("Content-Type", "application/json")
                .build();

        Response execute = null;
        try {
            execute = client.newCall(request).execute();
            String string = execute.body().string();

            log.info("======{}", string);

            JsonObject jsonObject = gson.fromJson(string, JsonObject.class);

            String event = jsonObject.get("event").getAsString();
            if (event.equals("message")) {
                String conversation_id = jsonObject.get("conversation_id").getAsString();
                String answer = jsonObject.get("answer").getAsString();
                ChatResponse chatResponse = new ChatResponse();
                chatResponse.setAnswer(answer);
                chatResponse.setConversionId(conversation_id);
                return chatResponse;
            } else {
                System.out.println(jsonObject);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
