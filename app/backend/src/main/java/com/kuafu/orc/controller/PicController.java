package com.kuafu.orc.controller;


import com.kuafu.common.domin.BaseResponse;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.orc.service.OcrService;
import com.kuafu.orc.service.PicBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 图片识别
 */
@RestController
@RequestMapping("/api/pic")
public class PicController {

    @Autowired
    private PicBusinessService picBusinessService;

    @Autowired
    @Qualifier("LocalOcrService")
    private OcrService ocrService;

    @PostMapping("/word2pic")
    public BaseResponse word2Pic(@RequestBody Map<String, Object> data) {
        if (!data.containsKey("text")) {
            return ResultUtils.error("text参数不存在，请检查参数！");
        }

        String text = String.valueOf(data.get("text"));

        String content = picBusinessService.text2pic(text);
        return ResultUtils.success(content);
    }

    @PostMapping("/pic2word")
    public BaseResponse pic2word(@RequestBody Map<String, Object> data) {
        if (!data.containsKey("file")) {
            return ResultUtils.error("file参数不存在，请检查参数！");
        }
        String file = data.getOrDefault("file", "").toString();
        String content = picBusinessService.pic2word(file);
        return ResultUtils.success(content);
    }

    @PostMapping("/ocr")
    public BaseResponse ocr(@RequestBody Map<String, Object> data) {

        if (!data.containsKey("file")) {
            return ResultUtils.error("ocr 识别 file 文件不存在");
        }

        String file = data.getOrDefault("file", "").toString();

        String result = ocrService.ocr(file);
        if (StringUtils.isEmpty(result)) {
            return ResultUtils.error("ocr识别失败");
        } else {
            return ResultUtils.success(result);
        }
    }

}
