package com.kuafu.web.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.domin.PageRequest;
import com.kuafu.web.entity.ClassroomInfoAll;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * <p>  教室信息 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IClassroomInfoAllService extends IService<ClassroomInfoAll> {
        List<ClassroomInfoAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<ClassroomInfoAll> queryWrapper);
        List<ClassroomInfoAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<ClassroomInfoAll> queryWrapper,boolean isPage);

        long selectCount(PageRequest pageRequest, LambdaQueryWrapper<ClassroomInfoAll> queryWrapper);

        IPage pageNew(IPage<ClassroomInfoAll> page, PageRequest pageRequest, LambdaQueryWrapper<ClassroomInfoAll> queryWrapper);
        }
