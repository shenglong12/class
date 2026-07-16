package com.kuafu.common.llm;

import java.util.Map;

/**
 * llm api
 */
public interface LlmApi {


    Response chat(String query, Map<String, Object> inputs, String conversationId, String user);

    Response chat(String query, Map<String, Object> inputs, String conversationId, String user, String type);

}
