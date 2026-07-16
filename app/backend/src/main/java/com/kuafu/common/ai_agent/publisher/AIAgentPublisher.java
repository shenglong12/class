package com.kuafu.common.ai_agent.publisher;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kuafu.common.ai_agent.contant.KeyConstant;
import com.kuafu.common.ai_agent.entity.AIAgentEvent;
import com.kuafu.common.ai_agent.handler.AgentHandler;
import com.kuafu.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
@Slf4j
public class AIAgentPublisher {

    @Resource
    private ApplicationContext applicationContext;


    @Resource
    private AgentHandler agentHandler;


    /**
     * 发布消息时，先将该字段置为空，再发布事件
     *
     * @param tableName
     * @param tableId
     * @param primaryName
     * @param mode
     * @param agentFieldName
     * @param prompt
     * @param data
     */

    public void publishEvent(String tableName, String tableId, String primaryName, String mode, String agentFieldName, String prompt, Object data) {
        log.info("publishEvent tableName:{},tableId:{},primaryName:{},mode:{},agentFieldName:{},prompt:{},data:{}", tableName, tableId, primaryName, mode,
                agentFieldName, prompt, data);
        IService service = (IService) applicationContext.getBean(tableName);
        boolean update = service.update(new UpdateWrapper<>()
                .set(agentFieldName, null).eq(primaryName, tableId));

        if (!update) {
            log.error("update ai agent error {} ", tableName);
            return;
        }
//      保存信息到缓存中
        agentHandler.saveTask(tableName, agentFieldName, tableId);

        applicationContext.publishEvent(new AIAgentEvent(this, tableName, tableId, primaryName, mode, agentFieldName, prompt, data));
    }
}



