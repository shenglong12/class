package com.kuafu.web.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AIAgentConfig {
    @Value("${aiagent.baseUrl:xxxxxxx}")
    private    String baseUrl;

    @Value("${aiagent.apiKey:xxxxxxxxx}")
    private    String apiKey ;

    @Value("${aiagent.apiKey:xxxxxxx")
    private String userId;

    @Value("${aiagent.systemPrompt:用户与你的对话只会进行一次,之后也不会和你再次进行对话,你需要尽可能的使用用户提供的信息,完成用户的需求,并且使用合理的语言表述出来,step by step}")
    private   String systemPrompt;




}
