package com.kuafu.web.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.common.domin.PageRequest;
import com.kuafu.web.entity.BookingInfoAll;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * <p>  预约信息 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IBookingInfoAllService extends IService<BookingInfoAll> {
        List<BookingInfoAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<BookingInfoAll> queryWrapper);
        List<BookingInfoAll> selectListNew(PageRequest pageRequest, LambdaQueryWrapper<BookingInfoAll> queryWrapper,boolean isPage);

        long selectCount(PageRequest pageRequest, LambdaQueryWrapper<BookingInfoAll> queryWrapper);

        IPage pageNew(IPage<BookingInfoAll> page, PageRequest pageRequest, LambdaQueryWrapper<BookingInfoAll> queryWrapper);
        }
