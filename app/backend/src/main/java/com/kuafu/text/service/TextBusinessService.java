package com.kuafu.text.service;

import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import com.kuafu.api.ApiBusinessService;
import com.kuafu.common.config.AppConfig;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.file.FileUtils;
import com.kuafu.common.util.IdUtils;
import com.kuafu.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
public class TextBusinessService {

    @Autowired
    private ApiBusinessService apiBusinessService;

    /**
     * Ai 文本生成文本
     *
     * @param text
     * @return
     */
    public String text2text(String text, String prompt) {
        try {
            String keyName = "text2text";
            Map<String, Object> params = Maps.newHashMap();
            params.put("query", text);
            Map<String, Object> inputs = Maps.newHashMap();
            inputs.put("prompt", StringUtils.isNotEmpty(prompt) ? prompt : "用户与你的对话只会进行一次,之后也不会和你再次进行对话,你需要尽可能的使用用户提供的信息,完成用户的需求,并且使用合理的语言表述出来,step by step");
            params.put("inputs", inputs);
            params.put("response_mode", "blocking");
            params.put("conversation_id", "");
            params.put("user", IdUtils.simpleUUID());

            String result = apiBusinessService.callApi(keyName, params);
            return JsonPath.read(result, "$.data.answer");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public String tts(String text) {
        try {
            String keyName = "tts";
            Map<String, Object> params = Maps.newHashMap();
            params.put("text", Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8)));

            String result = apiBusinessService.callApi(keyName, params);

            String content = JsonPath.read(result, "$.data.data.audio");
            byte[] audio = Base64.getDecoder().decode(content);

            String mp3Dir = AppConfig.getProfile() + "/mp3";

            return FileUtils.writeBytes(audio, mp3Dir, "mp3");

        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
