package com.kuafu.common.ai_agent.llm;

import com.kuafu.common.http.AbstractClient;
import com.kuafu.common.http.AbstractModel;

public class LlmHttpClient extends AbstractClient {
    private String baseUrl;

    public LlmHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String sendPostRequest(AbstractModel request) {
        return internalRequest(baseUrl, "POST", request);
    }

    public String sendGetRequest(AbstractModel request) {
        return internalRequest(baseUrl, "GET", request);
    }
}
