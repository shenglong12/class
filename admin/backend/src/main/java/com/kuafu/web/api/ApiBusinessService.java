package com.kuafu.web.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;

import com.jayway.jsonpath.JsonPath;
import com.kuafu.common.domin.ErrorCode;

import com.kuafu.common.exception.BusinessException;

import com.kuafu.common.file.ImageUtils;
import com.kuafu.common.util.IdUtils;
import com.kuafu.web.api.client.ApiClient;
import com.kuafu.web.api.spec.ApiDefinition;
import com.kuafu.web.entity.DynamicApiSetting;
import com.kuafu.web.service.IDynamicApiSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiBusinessService {

    private final IDynamicApiSettingService dynamicApiSettingService;
    private final ApiClient apiClient = new ApiClient();

    public String callApi(String apiKey, Map<String, Object> params) {
        // 查询 api 记录
        DynamicApiSetting setting = getByApiKey(apiKey);
        if (setting == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (StringUtils.equalsIgnoreCase(setting.getProtocol(), "http")) {
            return callHttpApi(setting, params);
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    public String callHttpApi(DynamicApiSetting setting, Map<String, Object> params) {

        ApiDefinition apiDefinition = getApiBySetting(setting);
        Map<String, String> fixedParams = Maps.newHashMap();
        // 判断鉴权方式
        fixedParams.put("token", setting.getToken());

        // 合并 params
        params.putAll(fixedParams);

        return apiClient.call(apiDefinition, params);
    }

    public DynamicApiSetting getByApiKey(String apiKey) {
        LambdaQueryWrapper<DynamicApiSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DynamicApiSetting::getKeyName, apiKey);
        return dynamicApiSettingService.getOne(queryWrapper);
    }

    private ApiDefinition getApiBySetting(DynamicApiSetting setting) {
        return ApiDefinition.builder()
                .name(setting.getKeyName())
                .method(setting.getMethod())
                .url(setting.getUrl())
                .headers(setting.getHeader())
                .bodyType(setting.getBodyType())
                .bodyTemplate(setting.getBodyTemplate())
                .build();
    }

    /**
     * 识别图片中的内容
     *
     * @param image
     * @param format
     * @return
     */
    public String pic2word(BufferedImage image, String format) {
        try {
            byte[] imageBytes = imageToByte(image, format);
            String base64Content = Base64.getEncoder().encodeToString(imageBytes);

            String keyName = "pic2word";
            Map<String, Object> params = Maps.newHashMap();
            params.put("content", base64Content);

            String result = callApi(keyName, params);

            return JsonPath.read(result, "$.data.payload.choices.text[0].content");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 文本2文本
     *
     * @param text
     * @param prompt
     * @return
     */
    public String text2text(String text, String prompt) {
        try {
            String keyName = "text2text";
            Map<String, Object> params = Maps.newHashMap();
            params.put("query", text);
            Map<String, Object> inputs = Maps.newHashMap();
            inputs.put("prompt", com.kuafu.common.util.StringUtils.isNotEmpty(prompt) ? prompt : "用户与你的对话只会进行一次,之后也不会和你再次进行对话,你需要尽可能的使用用户提供的信息,完成用户的需求,并且使用合理的语言表述出来,step by step");
            params.put("inputs", inputs);
            params.put("response_mode", "blocking");
            params.put("conversation_id", "");
            params.put("user", IdUtils.simpleUUID());

            String result = callApi(keyName, params);
            return JsonPath.read(result, "$.data.answer");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private byte[] imageToByte(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

}
