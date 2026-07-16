package com.kuafu.common.ai_agent.llm;

import lombok.Data;

@Data
public class LlmRepose {
    private int code;

    private BlockData data;

    private String message;


    @Data
    public static class BlockData{
        private String answer;
    }
}
