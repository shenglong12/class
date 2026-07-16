package com.kuafu.common.llm;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kuafu.common.util.IdUtils;
import com.kuafu.common.util.JSON;
import com.kuafu.common.util.StringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * dify 调用方法
 */
@Slf4j
public class LlmDifyApi implements LlmApi {

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(50, TimeUnit.SECONDS)
            .build();

    private final Gson gson = new Gson();

    @Setter
    private String baseUrl;

    @Setter
    private String difyKey;


    @Override
    public Response chat(String query, Map<String, Object> inputs, String conversationId, String user) {

        return chat(query, inputs, conversationId, user, "dify");
    }

    @Override
    public Response chat(String query, Map<String, Object> inputs, String conversationId, String user, String type) {
        if (StringUtils.isEmpty(baseUrl)) {
            throw new RuntimeException("请先配置dify地址");
        }
        if (StringUtils.isEmpty(difyKey)) {
            throw new RuntimeException("请先配置API_KEY");
        }

        Map<String, Object> payLoad = Maps.newHashMap();
        payLoad.put("query", Objects.nonNull(query) ? query : "");
        payLoad.put("inputs", Objects.nonNull(inputs) ? inputs : Maps.newHashMap());
        payLoad.put("response_mode", "blocking");
        payLoad.put("conversation_id", Objects.nonNull(conversationId) ? conversationId : "");
        payLoad.put("user", StringUtils.isNotEmpty(user) ? user : IdUtils.simpleUUID());

        RequestBody body = RequestBody.create(
                MediaType.get("application/json; charset=utf-8"),
                gson.toJson(payLoad)
        );

        String url = baseUrl;
        if (StringUtils.equalsIgnoreCase(type, "dify")) {
            url = url + "/chat-messages";
        }

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + difyKey) // Replace with actual method to get the API key
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            okhttp3.Response execute = client.newCall(request).execute();
            String string = execute.body().string();
            if (StringUtils.equalsIgnoreCase(type, "dify")) {
                return JSON.parseObject(string, MessageResponse.class);
            } else {
                return JSON.parseObject(string, LLmResponse.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
