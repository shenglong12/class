package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.RepairOrder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  repair_order 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IRepairOrderStatisticsService extends IService<RepairOrder> {
        List<Object> repair_order_statistic_6e1b2cda_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_706e6dad_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_dbd88f7e_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_f027c1c8_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_6a0412be_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_ab5cf58a_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_e0ad78fb_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_def7eca8_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_statistic_e9136ad8_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_ae2d02e5_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_create_time_datetime_statistic_426c9cba_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_create_time_datetime_statistic_13a3f358_count(LambdaQueryWrapper queryWrapper);

        List<Object> repair_order_create_time_datetime_statistic_b1da4bdf_count(LambdaQueryWrapper queryWrapper);

}
