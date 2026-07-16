package com.kuafu.common.ai_agent.handler;

import com.kuafu.common.ai_agent.entity.AiAgentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgentHandlerTest {

    @Resource
    private AgentHandler agentHandler;
    @Test
    void taskIsEnd() {
        final AiAgentRequest aiAgentRequest = new AiAgentRequest();
        aiAgentRequest.setTableName("CommunicationRecord");
        aiAgentRequest.setTableId("1");
        aiAgentRequest.setAgentFieldName("summary");
//        aiAgentRequest.setTableIdList();


        final Boolean b = agentHandler.taskIsEnd(aiAgentRequest);
        System.out.println(b);
    }
}