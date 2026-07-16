package com.kuafu.llm.controller;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChatRequest {

    @NotNull(message = "query不能为空")
    private String query;

    private String conversionId;

    @NotNull(message = "用户ID不能为空")
    private String userId;
}
