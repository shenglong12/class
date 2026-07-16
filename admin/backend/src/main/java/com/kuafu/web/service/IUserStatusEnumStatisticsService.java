package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.UserStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  user_status_enum 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IUserStatusEnumStatisticsService extends IService<UserStatusEnum> {
        List<Object> user_status_enum_user_status_enum_id_status_statistic_250bfd65_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_status_enum_user_status_enum_id_status_statistic_75457957_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_status_enum_user_status_enum_id_status_statistic_1ef97a93_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_status_enum_status_name_status_statistic_d1621cea_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_status_enum_status_name_status_statistic_0ee48f21_count(LambdaQueryWrapper queryWrapper);

        List<Object> user_status_enum_status_name_status_statistic_6a2582b2_count(LambdaQueryWrapper queryWrapper);

}
