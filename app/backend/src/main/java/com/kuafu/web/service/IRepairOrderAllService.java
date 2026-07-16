package com.kuafu.web.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.domin.PageRequest;
import com.kuafu.web.entity.RepairOrderAll;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * <p>  报修单 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IRepairOrderAllService extends IService<RepairOrderAll> {
        List<RepairOrderAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<RepairOrderAll> queryWrapper);
        List<RepairOrderAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<RepairOrderAll> queryWrapper,boolean isPage);

        long selectCount(PageRequest pageRequest, LambdaQueryWrapper<RepairOrderAll> queryWrapper);

        IPage pageNew(IPage<RepairOrderAll> page, PageRequest pageRequest, LambdaQueryWrapper<RepairOrderAll> queryWrapper);
        }
