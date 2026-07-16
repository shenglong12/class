package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.BookingStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  booking_status_enum 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IBookingStatusEnumStatisticsService extends IService<BookingStatusEnum> {
        List<Object> booking_status_enum_booking_status_enum_id_status_statistic_adcce722_count(LambdaQueryWrapper queryWrapper);

        List<Object> booking_status_enum_booking_status_enum_id_status_statistic_e5e0d2ef_count(LambdaQueryWrapper queryWrapper);

        List<Object> booking_status_enum_booking_status_enum_id_status_statistic_b6fdee33_count(LambdaQueryWrapper queryWrapper);

        List<Object> booking_status_enum_status_name_status_statistic_90b34348_count(LambdaQueryWrapper queryWrapper);

        List<Object> booking_status_enum_status_name_status_statistic_6038f1fd_count(LambdaQueryWrapper queryWrapper);

        List<Object> booking_status_enum_status_name_status_statistic_a52c379f_count(LambdaQueryWrapper queryWrapper);

}
