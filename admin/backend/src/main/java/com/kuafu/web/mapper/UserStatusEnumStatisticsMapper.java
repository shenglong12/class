package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.UserStatusEnum;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> user_status_enum Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface UserStatusEnumStatisticsMapper extends BaseMapper<UserStatusEnum> {

                        List<Object> user_status_enum_user_status_enum_id_status_statistic_250bfd65_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_status_enum_user_status_enum_id_status_statistic_75457957_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_status_enum_user_status_enum_id_status_statistic_1ef97a93_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_status_enum_status_name_status_statistic_d1621cea_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_status_enum_status_name_status_statistic_0ee48f21_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_status_enum_status_name_status_statistic_6a2582b2_count(@Param("ew") Wrapper queryWrapper);

}
