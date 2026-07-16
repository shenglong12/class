package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.RepairStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  repair_status_enum 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IRepairStatusEnumStatisticsService extends IService<RepairStatusEnum> {
        List<Object> repair_status_enum_repair_status_enum_id_status_statistic_3331a20c_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_status_enum_repair_status_enum_id_status_statistic_f7fab3d8_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_status_enum_repair_status_enum_id_status_statistic_20ddc808_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_status_enum_status_name_status_statistic_e2385888_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_status_enum_status_name_status_statistic_b8607948_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_status_enum_status_name_status_statistic_5735d9aa_count(LambdaQueryWrapper queryWrapper);

}
