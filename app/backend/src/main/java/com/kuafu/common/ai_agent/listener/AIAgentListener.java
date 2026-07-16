package com.kuafu.common.ai_agent.listener;

import com.kuafu.common.ai_agent.entity.AIAgentEvent;
import com.kuafu.common.ai_agent.handler.AgentHandler;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AIAgentListener {

    @Resource
    private AgentHandler agentHandler;


    @EventListener
    @Async("aiAgentTaskExecutor")
    public void handleAIAgentEvent(AIAgentEvent event) {
        agentHandler.process(event);
    }
}
