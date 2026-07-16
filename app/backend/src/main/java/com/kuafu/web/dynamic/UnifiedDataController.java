package com.kuafu.web.dynamic;


import com.kuafu.common.domin.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 统一入口
 */
@RestController
@RequestMapping("/api/data")
public class UnifiedDataController {

    @Autowired
    private DynamicServiceInvoker dynamicServiceInvoker;

    /**
     * 统一入口
     *
     * @param table
     * @param method page/list/add/update/get/delete
     * @param data
     * @return
     */
    @PostMapping("/invoke")
    public Object handle(
            @RequestParam("table") String table,
            @RequestParam(value = "method", defaultValue = "save") String method,
            @RequestBody Map<String, Object> data
    ) {
        try {

            if (StringUtils.equalsAnyIgnoreCase(method, "get", "list", "page")) {
                boolean check = VoConverter.checkTableAllExist(table);
                if (check) {
                    table = table + "_all";
                }
            }

            Object vo;
            if (StringUtils.equalsIgnoreCase(method, "page")) {
                vo = VoConverter.convertPageVo(table, data);
            } else if (StringUtils.endsWith(table, "_all")) {
                vo = VoConverter.convertPageVo(table, data);
            } else {
                vo = VoConverter.convert(table, data);
            }

            return dynamicServiceInvoker.invoke(table, vo, method);
        } catch (Exception e) {

            return ResultUtils.error(e.getMessage());
        }
    }
}
