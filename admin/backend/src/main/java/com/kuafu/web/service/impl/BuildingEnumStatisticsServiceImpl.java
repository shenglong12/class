package com.kuafu.web.service.impl;

import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kuafu.web.mapper.BuildingEnumStatisticsMapper;
import com.kuafu.web.entity.BuildingEnum;
import com.kuafu.web.service.IBuildingEnumStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> building_enum 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("BuildingEnumStatistics")
public class BuildingEnumStatisticsServiceImpl extends ServiceImpl<BuildingEnumStatisticsMapper, BuildingEnum> implements IBuildingEnumStatisticsService {
    @Resource
    private BuildingEnumStatisticsMapper buildingEnumStaticMapper;

                        @Override
                        public List<Object> building_enum_building_enum_id_status_statistic_146915fe_count(LambdaQueryWrapper queryWrapper) {
                                return buildingEnumStaticMapper.building_enum_building_enum_id_status_statistic_146915fe_count(queryWrapper);
                                }


                        @Override
                        public List<Object> building_enum_building_enum_id_status_statistic_060920a4_count(LambdaQueryWrapper queryWrapper) {
                                return buildingEnumStaticMapper.building_enum_building_enum_id_status_statistic_060920a4_count(queryWrapper);
                                }


                        @Override
                        public List<Object> building_enum_building_enum_id_status_statistic_f0e4acc5_count(LambdaQueryWrapper queryWrapper) {
                                return buildingEnumStaticMapper.building_enum_building_enum_id_status_statistic_f0e4acc5_count(queryWrapper);
                                }


}
