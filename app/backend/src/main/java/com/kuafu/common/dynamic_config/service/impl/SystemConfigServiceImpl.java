package com.kuafu.common.dynamic_config.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuafu.common.dynamic_config.domain.SystemConfig;
import com.kuafu.common.dynamic_config.service.SystemConfigService;
import com.kuafu.common.dynamic_config.mapper.SystemConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author www.macpe.cn
* @description 针对表【system_config】的数据库操作Service实现
* @createDate 2025-05-08 21:14:29
*/
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig>
    implements SystemConfigService{

}




