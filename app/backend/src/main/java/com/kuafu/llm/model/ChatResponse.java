package com.kuafu.llm.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private String conversionId;
    private String answer;
    private List<String> questions;
}
