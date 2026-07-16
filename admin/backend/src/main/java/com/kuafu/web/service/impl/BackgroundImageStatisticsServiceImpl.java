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

import com.kuafu.web.mapper.BackgroundImageStatisticsMapper;
import com.kuafu.web.entity.BackgroundImage;
import com.kuafu.web.service.IBackgroundImageStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> background_image 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("BackgroundImageStatistics")
public class BackgroundImageStatisticsServiceImpl extends ServiceImpl<BackgroundImageStatisticsMapper, BackgroundImage> implements IBackgroundImageStatisticsService {
    @Resource
    private BackgroundImageStatisticsMapper backgroundImageStaticMapper;

                        @Override
                        public List<Object> background_image_statistic_2cec2f0a_count(LambdaQueryWrapper queryWrapper) {
                                return backgroundImageStaticMapper.background_image_statistic_2cec2f0a_count(queryWrapper);
                                }


                        @Override
                        public List<Object> background_image_statistic_6d9fd8e7_count(LambdaQueryWrapper queryWrapper) {
                                return backgroundImageStaticMapper.background_image_statistic_6d9fd8e7_count(queryWrapper);
                                }


                        @Override
                        public List<Object> background_image_statistic_db8eea91_count(LambdaQueryWrapper queryWrapper) {
                                return backgroundImageStaticMapper.background_image_statistic_db8eea91_count(queryWrapper);
                                }


                        @Override
                        public List<Object> background_image_upload_time_datetime_statistic_775bad3a_count(LambdaQueryWrapper queryWrapper) {
                                return backgroundImageStaticMapper.background_image_upload_time_datetime_statistic_775bad3a_count(queryWrapper);
                                }


                        @Override
                        public List<Object> background_image_upload_time_datetime_statistic_401f3864_count(LambdaQueryWrapper queryWrapper) {
                                return backgroundImageStaticMapper.background_image_upload_time_datetime_statistic_401f3864_count(queryWrapper);
                                }


                        @Override
                        public List<Object> background_image_upload_time_datetime_statistic_f7bdec73_count(LambdaQueryWrapper queryWrapper) {
                                return backgroundImageStaticMapper.background_image_upload_time_datetime_statistic_f7bdec73_count(queryWrapper);
                                }


}
