package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.ClassroomInfo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> classroom_info Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface ClassroomInfoStatisticsMapper extends BaseMapper<ClassroomInfo> {

                        List<Object> classroom_info_statistic_b88cfabc_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> classroom_info_statistic_ca7982e5_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> classroom_info_statistic_b73aeebe_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count(@Param("ew") Wrapper queryWrapper);

}
