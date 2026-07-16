package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.RepairStatusEnum;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> repair_status_enum Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface RepairStatusEnumStatisticsMapper extends BaseMapper<RepairStatusEnum> {

                        List<Object> repair_status_enum_repair_status_enum_id_status_statistic_3331a20c_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_status_enum_repair_status_enum_id_status_statistic_f7fab3d8_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_status_enum_repair_status_enum_id_status_statistic_20ddc808_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_status_enum_status_name_status_statistic_e2385888_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_status_enum_status_name_status_statistic_b8607948_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> repair_status_enum_status_name_status_statistic_5735d9aa_count(@Param("ew") Wrapper queryWrapper);

}
