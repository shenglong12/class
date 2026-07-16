package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.BookingStatusEnum;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> booking_status_enum Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface BookingStatusEnumStatisticsMapper extends BaseMapper<BookingStatusEnum> {

                        List<Object> booking_status_enum_booking_status_enum_id_status_statistic_adcce722_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_status_enum_booking_status_enum_id_status_statistic_e5e0d2ef_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_status_enum_booking_status_enum_id_status_statistic_b6fdee33_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_status_enum_status_name_status_statistic_90b34348_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_status_enum_status_name_status_statistic_6038f1fd_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_status_enum_status_name_status_statistic_a52c379f_count(@Param("ew") Wrapper queryWrapper);

}
