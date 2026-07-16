package com.kuafu.common.ai_agent.entity;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AiAgentRequest {
    @NotBlank(message = "表名不能为空")
    private String tableName;
    private String tableId;
    @NotBlank(message = "字段名不能为空")
    private String agentFieldName;

    private List<String> tableIdList;
}
