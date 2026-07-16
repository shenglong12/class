package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.UserInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  user_info 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IUserInfoStatisticsService extends IService<UserInfo> {
        List<Object> user_info_statistic_26db9414_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_statistic_d748e97d_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_statistic_fd86a11f_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_statistic_cd4888d0_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_statistic_97b06621_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_statistic_b82e02ef_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_role_enum_role_enum_id_1_status_statistic_17efc900_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_role_enum_role_enum_id_1_status_statistic_c074d54d_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_role_enum_role_enum_id_1_status_statistic_ab566b0f_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_b868696b_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_811edae4_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_52937bd0_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_registration_date_datetime_statistic_c6d4320e_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_registration_date_datetime_statistic_5c524202_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_info_registration_date_datetime_statistic_f89dba19_count(LambdaQueryWrapper queryWrapper);

}
