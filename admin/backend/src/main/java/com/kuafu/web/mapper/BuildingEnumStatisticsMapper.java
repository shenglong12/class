package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.BuildingEnum;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> building_enum Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface BuildingEnumStatisticsMapper extends BaseMapper<BuildingEnum> {

                        List<Object> building_enum_building_enum_id_status_statistic_146915fe_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> building_enum_building_enum_id_status_statistic_060920a4_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> building_enum_building_enum_id_status_statistic_f0e4acc5_count(@Param("ew") Wrapper queryWrapper);

}
