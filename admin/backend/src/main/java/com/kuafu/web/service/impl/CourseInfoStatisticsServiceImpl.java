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

import com.kuafu.web.mapper.CourseInfoStatisticsMapper;
import com.kuafu.web.entity.CourseInfo;
import com.kuafu.web.service.ICourseInfoStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> course_info 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("CourseInfoStatistics")
public class CourseInfoStatisticsServiceImpl extends ServiceImpl<CourseInfoStatisticsMapper, CourseInfo> implements ICourseInfoStatisticsService {
    @Resource
    private CourseInfoStatisticsMapper courseInfoStaticMapper;

                        @Override
                        public List<Object> course_info_statistic_11f06534_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_statistic_11f06534_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_statistic_43e99b84_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_statistic_43e99b84_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_statistic_6ba45514_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_statistic_6ba45514_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_start_date_datetime_statistic_799f21ac_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_start_date_datetime_statistic_799f21ac_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_start_date_datetime_statistic_40a54bd0_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_start_date_datetime_statistic_40a54bd0_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_start_date_datetime_statistic_470d5ddb_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_start_date_datetime_statistic_470d5ddb_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_end_date_datetime_statistic_9bbd1d33_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_end_date_datetime_statistic_9bbd1d33_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_end_date_datetime_statistic_f4a5cdc7_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_end_date_datetime_statistic_f4a5cdc7_count(queryWrapper);
                                }


                        @Override
                        public List<Object> course_info_end_date_datetime_statistic_9844a0b8_count(LambdaQueryWrapper queryWrapper) {
                                return courseInfoStaticMapper.course_info_end_date_datetime_statistic_9844a0b8_count(queryWrapper);
                                }


}
