package com.kuafu.text.controller;

import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.text.service.TextBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/text")
public class TextController {

    @Autowired
    private TextBusinessService textBusinessService;

    @PostMapping("/text2text")
    public BaseResponse word2Pic(@RequestBody Map<String, Object> data) {
        if (!data.containsKey("text")) {
            return ResultUtils.error("text参数不存在，请检查参数！");
        }

        String text = String.valueOf(data.get("text"));

        String prompt = String.valueOf(data.getOrDefault("prompt", ""));

        String content = textBusinessService.text2text(text, prompt);
        return ResultUtils.success(content);
    }

    @PostMapping("/tts")
    public BaseResponse tts(@RequestBody Map<String, Object> data) {
        if (!data.containsKey("text")) {
            return ResultUtils.error("text参数不存在，请检查参数！");
        }
        String text = String.valueOf(data.get("text"));
        String content = textBusinessService.tts(text);
        return ResultUtils.success(content);
    }
}
