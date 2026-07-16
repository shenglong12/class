package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.RepairOrder;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> repair_order Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface RepairOrderStatisticsMapper extends BaseMapper<RepairOrder> {

                        List<Object> repair_order_statistic_6e1b2cda_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_706e6dad_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_dbd88f7e_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_f027c1c8_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_6a0412be_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_ab5cf58a_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_e0ad78fb_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_def7eca8_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_statistic_e9136ad8_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_ae2d02e5_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_create_time_datetime_statistic_426c9cba_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_create_time_datetime_statistic_13a3f358_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_order_create_time_datetime_statistic_b1da4bdf_count(@Param("ew") Wrapper queryWrapper);

}
