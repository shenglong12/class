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

import com.kuafu.web.mapper.ClassroomInfoStatisticsMapper;
import com.kuafu.web.entity.ClassroomInfo;
import com.kuafu.web.service.IClassroomInfoStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> classroom_info 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("ClassroomInfoStatistics")
public class ClassroomInfoStatisticsServiceImpl extends ServiceImpl<ClassroomInfoStatisticsMapper, ClassroomInfo> implements IClassroomInfoStatisticsService {
    @Resource
    private ClassroomInfoStatisticsMapper classroomInfoStaticMapper;

                        @Override
                        public List<Object> classroom_info_statistic_b88cfabc_count(LambdaQueryWrapper queryWrapper) {
                                return classroomInfoStaticMapper.classroom_info_statistic_b88cfabc_count(queryWrapper);
                                }


                        @Override
                        public List<Object> classroom_info_statistic_ca7982e5_count(LambdaQueryWrapper queryWrapper) {
                                return classroomInfoStaticMapper.classroom_info_statistic_ca7982e5_count(queryWrapper);
                                }


                        @Override
                        public List<Object> classroom_info_statistic_b73aeebe_count(LambdaQueryWrapper queryWrapper) {
                                return classroomInfoStaticMapper.classroom_info_statistic_b73aeebe_count(queryWrapper);
                                }


                        @Override
                        public List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count(LambdaQueryWrapper queryWrapper) {
                                return classroomInfoStaticMapper.classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count(queryWrapper);
                                }


                        @Override
                        public List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count(LambdaQueryWrapper queryWrapper) {
                                return classroomInfoStaticMapper.classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count(queryWrapper);
                                }


                        @Override
                        public List<Object> classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count(LambdaQueryWrapper queryWrapper) {
                                return classroomInfoStaticMapper.classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count(queryWrapper);
                                }


}
