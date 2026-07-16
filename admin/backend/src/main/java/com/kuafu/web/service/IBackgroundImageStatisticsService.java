package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.BackgroundImage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  background_image 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IBackgroundImageStatisticsService extends IService<BackgroundImage> {
        List<Object> background_image_statistic_2cec2f0a_count(LambdaQueryWrapper queryWrapper);

        List<Object> background_image_statistic_6d9fd8e7_count(LambdaQueryWrapper queryWrapper);

        List<Object> background_image_statistic_db8eea91_count(LambdaQueryWrapper queryWrapper);

        List<Object> background_image_upload_time_datetime_statistic_775bad3a_count(LambdaQueryWrapper queryWrapper);

        List<Object> background_image_upload_time_datetime_statistic_401f3864_count(LambdaQueryWrapper queryWrapper);

        List<Object> background_image_upload_time_datetime_statistic_f7bdec73_count(LambdaQueryWrapper queryWrapper);

}
