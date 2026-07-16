package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.BookingInfo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> booking_info Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface BookingInfoStatisticsMapper extends BaseMapper<BookingInfo> {

                        List<Object> booking_info_statistic_1126e6b6_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_4043b0cb_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_1853f55c_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_d40776f0_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_548dd610_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_44542338_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_aff5634d_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_17a6caf6_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_statistic_c980a12e_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_booking_date_datetime_statistic_eae3f8e9_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_booking_date_datetime_statistic_e87a89ae_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_booking_date_datetime_statistic_674e8a71_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_e8bf0f4a_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_3920cb9d_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_booking_status_enum_booking_status_enum_id_1_status_statistic_abe486bb_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_create_time_datetime_statistic_deef36d9_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_create_time_datetime_statistic_198072fd_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_create_time_datetime_statistic_25bff35e_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_start_time_datetime_statistic_f9865a97_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_start_time_datetime_statistic_8d1d71fe_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_start_time_datetime_statistic_937c1379_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_end_time_datetime_statistic_2492f29a_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_end_time_datetime_statistic_e6adae60_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> booking_info_end_time_datetime_statistic_d22436f5_count(@Param("ew") Wrapper queryWrapper);

}
