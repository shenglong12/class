package com.kuafu.web.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.domin.PageRequest;
import com.kuafu.web.entity.CourseInfoAll;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * <p>  课程信息 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface ICourseInfoAllService extends IService<CourseInfoAll> {
        List<CourseInfoAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<CourseInfoAll> queryWrapper);
        List<CourseInfoAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<CourseInfoAll> queryWrapper,boolean isPage);

        long selectCount(PageRequest pageRequest, LambdaQueryWrapper<CourseInfoAll> queryWrapper);

        IPage pageNew(IPage<CourseInfoAll> page, PageRequest pageRequest, LambdaQueryWrapper<CourseInfoAll> queryWrapper);
        }
