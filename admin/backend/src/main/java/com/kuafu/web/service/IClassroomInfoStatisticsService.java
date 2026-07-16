package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.ClassroomInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  classroom_info 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IClassroomInfoStatisticsService extends IService<ClassroomInfo> {
        List<Object> classroom_info_statistic_b88cfabc_count(LambdaQueryWrapper queryWrapper);

        List<Object> classroom_info_statistic_ca7982e5_count(LambdaQueryWrapper queryWrapper);

        List<Object> classroom_info_statistic_b73aeebe_count(LambdaQueryWrapper queryWrapper);

        List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count(LambdaQueryWrapper queryWrapper);

        List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count(LambdaQueryWrapper queryWrapper);

        List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count(LambdaQueryWrapper queryWrapper);

}
