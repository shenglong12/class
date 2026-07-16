package com.kuafu.llm.controller;

import com.kuafu.llm.chat.Chat;
import com.kuafu.llm.config.LLMStartBusiness;
import com.kuafu.llm.config.PromptConfig;
import com.kuafu.llm.model.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("chat")
@ConditionalOnProperty(prefix = "llm", name = "enable", havingValue = "true")
public class ChatController {

    @Autowired
    private LLMStartBusiness llmStartBusiness;

    @Autowired
    private Chat chat;

    @Value("${llm.chat.default_prompt}")
    private String defaultPrompt;

    /**
     * 对话流
     *
     * @param chatRequest
     * @return
     */
    @PostMapping("")
    public SseEmitter stream(@RequestBody ChatRequest chatRequest) {
        // 用于创建一个 SSE 连接对象
        SseEmitter emitter = new SseEmitter(3600000L);
        List<String> search = llmStartBusiness.search(chatRequest.getQuery());
        log.info("embedding search: {}", search);

        String conversionId = chatRequest.getConversionId();

        String query = chatRequest.getQuery();

        if (StringUtils.isEmpty(conversionId)) {

            query = PromptConfig.PROMPT + "\n" + query;
//            ChatResponse chatResponse = chat.callApiBlock(PromptConfig.PROMPT + "\n当你接收到这段话时,你只需要回复【收到】即可，不要回复多余的内容",
//                    null, chatRequest.getUserId());
//            conversionId = chatResponse.getConversionId();
        }

        StringBuilder context = new StringBuilder("context is \n");
        for (String s : search) {
            context.append(s).append("\n");
        }
        query = query + "\n" + context;

        log.info("content : {}", query);

        chat.callApiStream(query, conversionId, chatRequest.getUserId(), emitter);

        // 在后台线程中模拟实时数据
        return emitter;
    }

}
