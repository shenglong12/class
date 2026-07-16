package com.kuafu.web.dynamic;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import com.kuafu.api.ApiBusinessService;
import com.kuafu.common.domin.ErrorCode;
import com.kuafu.common.domin.ResultUtils;
import com.kuafu.common.entity.DynamicApiSetting;
import com.kuafu.common.exception.BusinessException;
import com.kuafu.common.util.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UnifiedApiController {

    @Autowired
    private ApiBusinessService apiBusinessService;
    private final Gson gson = new Gson();

    private final Type return_value_type = new TypeToken<Map<String, Object>>() {
    }.getType();

    @PostMapping("/{key}")
    public Object handle(
            @PathVariable(value = "key") String apiKey,
            @RequestBody Map<String, Object> data
    ) {

        DynamicApiSetting setting = apiBusinessService.getByApiKey(apiKey);
        if (setting == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (StringUtils.equalsIgnoreCase(setting.getProtocol(), "http")) {
            // 添加环境变量参数
            data.put("ip", ServletUtils.getClientIp());

            // 请求接口地址
            String result = apiBusinessService.callHttpApi(setting, data);

            String dataPath = setting.getDataPath();
            String dataType = setting.getDataType();

            if (StringUtils.isNotEmpty(dataPath)) {
                String content = JsonPath.read(result, dataPath);

                if (StringUtils.isNotEmpty(dataType) && StringUtils.equalsIgnoreCase(dataType, "json")) {
                    return ResultUtils.success(gson.fromJson(content, return_value_type));
                } else {
                    return ResultUtils.success(content);
                }
            } else {
                return gson.fromJson(result, return_value_type);
            }
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

    }
}
