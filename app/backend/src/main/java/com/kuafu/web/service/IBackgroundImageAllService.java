package com.kuafu.web.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.domin.PageRequest;
import com.kuafu.web.entity.BackgroundImageAll;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * <p>  背景图片 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IBackgroundImageAllService extends IService<BackgroundImageAll> {
        List<BackgroundImageAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<BackgroundImageAll> queryWrapper);
        List<BackgroundImageAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<BackgroundImageAll> queryWrapper,boolean isPage);

        long selectCount(PageRequest pageRequest, LambdaQueryWrapper<BackgroundImageAll> queryWrapper);

        IPage pageNew(IPage<BackgroundImageAll> page, PageRequest pageRequest, LambdaQueryWrapper<BackgroundImageAll> queryWrapper);
        }
