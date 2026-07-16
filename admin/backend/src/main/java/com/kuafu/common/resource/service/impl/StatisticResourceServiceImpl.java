package com.kuafu.common.resource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.common.domin.StaticResource;
import com.kuafu.common.resource.mapper.StaticResourcesMapper;
import com.kuafu.common.resource.service.IStaticResourceService;
import org.springframework.stereotype.Service;


@Service
public class StatisticResourceServiceImpl extends ServiceImpl<StaticResourcesMapper, StaticResource> implements IStaticResourceService {
}
