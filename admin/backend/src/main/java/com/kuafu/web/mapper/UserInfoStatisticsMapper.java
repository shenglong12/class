package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.UserInfo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> user_info Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface UserInfoStatisticsMapper extends BaseMapper<UserInfo> {

                        List<Object> user_info_statistic_26db9414_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_statistic_d748e97d_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_statistic_fd86a11f_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_statistic_cd4888d0_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_statistic_97b06621_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_statistic_b82e02ef_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_role_enum_role_enum_id_1_status_statistic_17efc900_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_role_enum_role_enum_id_1_status_statistic_c074d54d_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_role_enum_role_enum_id_1_status_statistic_ab566b0f_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_b868696b_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_811edae4_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_52937bd0_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_registration_date_datetime_statistic_c6d4320e_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_registration_date_datetime_statistic_5c524202_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> user_info_registration_date_datetime_statistic_f89dba19_count(@Param("ew") Wrapper queryWrapper);

}
