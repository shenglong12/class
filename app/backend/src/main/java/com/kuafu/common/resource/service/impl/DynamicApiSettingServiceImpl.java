package com.kuafu.common.resource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.common.entity.DynamicApiSetting;
import com.kuafu.common.resource.mapper.DynamicApiSettingMapper;
import com.kuafu.common.resource.service.DynamicApiSettingService;
import org.springframework.stereotype.Service;

@Service
public class DynamicApiSettingServiceImpl extends ServiceImpl<DynamicApiSettingMapper, DynamicApiSetting>
        implements DynamicApiSettingService {
}
