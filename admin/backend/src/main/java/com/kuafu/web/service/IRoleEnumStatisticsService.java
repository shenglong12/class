package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.RoleEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  role_enum 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IRoleEnumStatisticsService extends IService<RoleEnum> {
        List<Object> role_enum_role_enum_id_status_statistic_d3cd406e_count(LambdaQueryWrapper queryWrapper);

        List<Object> role_enum_role_enum_id_status_statistic_dec1eb62_count(LambdaQueryWrapper queryWrapper);

        List<Object> role_enum_role_enum_id_status_statistic_b83c5d21_count(LambdaQueryWrapper queryWrapper);

}
