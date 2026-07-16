package com.kuafu.common.ai_agent.handler;

import cn.hutool.core.bean.BeanUtil;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.google.common.base.CaseFormat;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.kuafu.common.ai_agent.contant.KeyConstant;
import com.kuafu.common.ai_agent.entity.AIAgentEvent;
import com.kuafu.common.ai_agent.entity.AiAgentRequest;
import com.kuafu.common.ai_agent.llm.LLmRequest;
import com.kuafu.common.ai_agent.llm.LlmHttpClient;
import com.kuafu.common.ai_agent.llm.LlmRepose;
import com.kuafu.common.cache.Cache;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.JSON;
import com.kuafu.common.util.StringUtils;
import com.kuafu.common.util.UUID;
import com.kuafu.web.config.AIAgentConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.text.StringSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
@Slf4j
public class AgentHandler {


    @Resource
    private ApplicationContext applicationContext;


    @Resource
    private AIAgentConfig aiAgentConfig;


    @Resource
    private Cache cache;

    public void process(AIAgentEvent event) {
        String tableName = event.getTableName();
        String tableId = event.getTableId();
        String primaryName = event.getPrimaryName();
        String agentFieldName = event.getAgentFieldName();
        String prompt = event.getPrompt();
        Object data = event.getData();


        StringBuffer llmPrompt = processLlmPrompt(prompt, data);

//      调用LLM生成结果
        String llmStr = callLlm(llmPrompt.toString());


        updateDataById(event, tableName, agentFieldName, llmStr, primaryName, tableId);


        removeTask(tableName, agentFieldName, tableId);
    }

    @NotNull
    private static StringBuffer processLlmPrompt(String prompt, Object data) {
        StringBuffer llmPrompt = new StringBuffer(prompt);
        if (StringUtils.isNotEmpty(prompt) && ObjectUtils.isNotEmpty(data)) {

            String template = "\"" + prompt + "\""; // 作为一个字符串处理
            // 编译表达式
            Expression expression = AviatorEvaluator.compile(template, true);

            String result = (String) expression.execute(pojoToMap(data));

            llmPrompt = new StringBuffer(result);
        }
        return llmPrompt;
    }

    private void updateDataById(AIAgentEvent event, String tableName, String agentFieldName, String llmStr, String primaryName, String tableId) {
        //      更新生成的结果到数据库中
        IService service = (IService) applicationContext.getBean(tableName);
        boolean update = service.update(new UpdateWrapper<>()
                .set(agentFieldName, llmStr).eq(primaryName, tableId));

        if (!update) {
            log.error("update ai agent error {} ", event);
            return;
        }
    }

    private String callLlm(String llmPrompt) {
        LlmHttpClient llmHttpClient = new LlmHttpClient
                (aiAgentConfig.getBaseUrl());
        LLmRequest request = new LLmRequest(aiAgentConfig.getApiKey());
        request.set("query", llmPrompt);
        HashMap<String, Object> inputs = new HashMap<>();
        inputs.put("prompt", aiAgentConfig.getSystemPrompt());
        request.set("inputs", inputs);
        request.set("user", aiAgentConfig.getUserId());
        String response = llmHttpClient.sendPostRequest(request);

        log.info("llm response {} ", response);


        LlmRepose llmRepose = JSON.parseObject(response, LlmRepose.class);
        String llmStr = "大模型服务异常,请重试";
        if (ObjectUtils.isNotEmpty(llmRepose)) {
            llmStr = llmRepose.getMessage();

            int code = llmRepose.getCode();
            if (code == 0) {
                llmStr = llmRepose.getData().getAnswer();
            }

        }
        return llmStr;
    }


    /**
     * 对象转map
     *
     * @param pojo
     * @return
     */

    public static Map<String, Object> pojoToMap(Object pojo) {

        final Map<String, Object> stringObjectMap = BeanUtil.beanToMap(pojo);

        // 转换 key 为下划线格式
        Map<String, Object> snakeCaseMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
            String snakeKey = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entry.getKey());
            snakeCaseMap.put(snakeKey, entry.getValue());
        }
        stringObjectMap.putAll(snakeCaseMap);

        return stringObjectMap;
    }


    /**
     * 保存任务ID
     *
     * @param tableName
     * @param agentFieldName
     * @param tableId
     */

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void saveTask(String tableName, String agentFieldName, String tableId) {
        String key = KeyConstant.AI_AGENT_TASK_LIST + ":" + tableName + ":" + agentFieldName;
        Set<String> set = cache.getCacheSet(key);
        if (set == null){
            set=new HashSet<>();
        }
        set.add(tableId);
        cache.setCacheSet(key, set);
    }


    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void removeTask(String tableName, String agentFieldName, String tableId) {
        String key = KeyConstant.AI_AGENT_TASK_LIST + ":" + tableName + ":" + agentFieldName;
        Set<String> set = cache.getCacheSet(key);
        if (set == null){
            set=new HashSet<>();
        }
        set.remove(tableId);
        cache.setCacheSet(key, set);
    }

    public Boolean taskIsEnd(AiAgentRequest aiAgentRequest) {

        final String agentFieldName = aiAgentRequest.getAgentFieldName();
        final String tableId = aiAgentRequest.getTableId();
        final String tableName = aiAgentRequest.getTableName();
        final List<String> tableIdList = aiAgentRequest.getTableIdList();


//      两个不能同时为空
        if (StringUtils.isEmpty(tableId) && ObjectUtils.isEmpty(tableIdList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        final Set<Object> cacheSet = cache.getCacheSet(KeyConstant.AI_AGENT_TASK_LIST + ":" + tableName + ":" + agentFieldName);

        if (ObjectUtils.isEmpty(cacheSet)) {
//          如果是空说明没有正在进行的任务
            return true;
        }
//
        if (StringUtils.isNotEmpty(tableId)) {
//          如果不在列表中，说明任务已经结束
            return !cacheSet.contains(tableId);

        }


//      如果是列表的情况，只要有一个在列表中就算任务在进行中
        if (ObjectUtils.isNotEmpty(tableIdList)) {
            for (String tabId : tableIdList) {

                if (!cacheSet.contains(tabId)) {
//
                    return true;
                }
            }
        }

        return false;
    }
}


