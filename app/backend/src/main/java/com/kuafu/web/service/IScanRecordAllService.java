package com.kuafu.web.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.domin.PageRequest;
import com.kuafu.web.entity.ScanRecordAll;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * <p>  扫码记录 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IScanRecordAllService extends IService<ScanRecordAll> {
        List<ScanRecordAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<ScanRecordAll> queryWrapper);
        List<ScanRecordAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<ScanRecordAll> queryWrapper,boolean isPage);

        long selectCount(PageRequest pageRequest, LambdaQueryWrapper<ScanRecordAll> queryWrapper);

        IPage pageNew(IPage<ScanRecordAll> page, PageRequest pageRequest, LambdaQueryWrapper<ScanRecordAll> queryWrapper);
        }
