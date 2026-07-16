package com.kuafu.common.ai_agent.entity;

import lombok.Data;
import org.springframework.context.ApplicationEvent;


public class AIAgentEvent extends ApplicationEvent {
    //  表名
    private String tableName;
    //  主键id
    private String tableId;
    //  主键名称
    private String primaryName;
    //  更新/删除
    private String mode;
    //  agent字段名
    private String agentFieldName;
    //    prompt
    private String prompt;
    //    该行的记录
    private Object data;

    public AIAgentEvent(Object source, String tableName, String tableId, String primaryName, String mode, String agentFieldName, String prompt, Object data) {
        super(source);
        this.tableName = tableName;
        this.tableId = tableId;
        this.primaryName = primaryName;
        this.mode = mode;
        this.agentFieldName = agentFieldName;
        this.prompt = prompt;
        this.data = data;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getAgentFieldName() {
        return agentFieldName;
    }

    public void setAgentFieldName(String agentFieldName) {
        this.agentFieldName = agentFieldName;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AIAgentEvent{" +
                "tableName='" + tableName + '\'' +
                ", tableId='" + tableId + '\'' +
                ", primaryName='" + primaryName + '\'' +
                ", mode='" + mode + '\'' +
                ", agentFieldName='" + agentFieldName + '\'' +
                ", prompt='" + prompt + '\'' +
                ", data=" + data +
                '}';
    }
}
