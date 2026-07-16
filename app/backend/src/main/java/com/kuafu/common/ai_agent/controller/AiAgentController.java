package com.kuafu.common.ai_agent.controller;


import com.kuafu.common.ai_agent.contant.KeyConstant;
import com.kuafu.common.ai_agent.entity.AiAgentRequest;
import com.kuafu.common.ai_agent.handler.AgentHandler;
import com.kuafu.common.cache.Cache;
import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/aiAgent")
public class AiAgentController {

    @Resource
    private Cache cache;

    @Resource
    private AgentHandler agentHandler;


    @PostMapping("isEnd")
    public BaseResponse<Boolean> isEnd(@Validated  @RequestBody AiAgentRequest aiAgentRequest) {
        Boolean b=agentHandler.taskIsEnd(aiAgentRequest);



        return ResultUtils.success(b);

    }
}
