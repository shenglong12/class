package com.kuafu.common.resource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.common.entity.StaticResource;
import com.kuafu.common.resource.service.IStaticResourceService;
import org.springframework.stereotype.Service;


@Service
public class StatisticResourceServiceImpl extends ServiceImpl<com.kuafu.common.resource.service.mapper.StaticResourcesMapper,
        StaticResource> implements IStaticResourceService {
}
