package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.BuildingEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  building_enum 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IBuildingEnumStatisticsService extends IService<BuildingEnum> {
        List<Object> building_enum_building_enum_id_status_statistic_146915fe_count(LambdaQueryWrapper queryWrapper);

        List<Object> building_enum_building_enum_id_status_statistic_060920a4_count(LambdaQueryWrapper queryWrapper);

        List<Object> building_enum_building_enum_id_status_statistic_f0e4acc5_count(LambdaQueryWrapper queryWrapper);

}
