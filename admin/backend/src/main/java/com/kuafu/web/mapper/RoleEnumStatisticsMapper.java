package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.RoleEnum;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> role_enum Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface RoleEnumStatisticsMapper extends BaseMapper<RoleEnum> {

                        List<Object> role_enum_role_enum_id_status_statistic_d3cd406e_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> role_enum_role_enum_id_status_statistic_dec1eb62_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> role_enum_role_enum_id_status_statistic_b83c5d21_count(@Param("ew") Wrapper queryWrapper);

}
