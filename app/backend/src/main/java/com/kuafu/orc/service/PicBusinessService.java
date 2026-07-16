package com.kuafu.orc.service;

import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import com.kuafu.api.ApiBusinessService;
import com.kuafu.common.config.AppConfig;
import com.kuafu.common.file.FileUtils;
import com.kuafu.common.file.ImageUtils;
import com.kuafu.common.llm.LlmDifyApi;
import com.kuafu.common.llm.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
public class PicBusinessService {

    @Autowired
    private ApiBusinessService apiBusinessService;

    private final static String picDir = AppConfig.getProfile() + "/text2pic";

    public String text2pic(String text) {
        try {
            String keyName = "word2pic";
            Map<String, Object> params = Maps.newHashMap();
            params.put("text", text);

            String result = apiBusinessService.callApi(keyName, params);

            String content = JsonPath.read(result, "$.data.payload.choices.text[0].content");
            byte[] imageBytes = Base64.getDecoder().decode(content);
            return FileUtils.writeBytes(imageBytes, picDir);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String pic2word(String file) {
        try {
            byte[] imageBytes = ImageUtils.readFile(file);
            String base64Content = Base64.getEncoder().encodeToString(imageBytes);

            String keyName = "pic2word";
            Map<String, Object> params = Maps.newHashMap();
            params.put("content", base64Content);

            String result = apiBusinessService.callApi(keyName, params);

            return JsonPath.read(result, "$.data.payload.choices.text[0].content");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
