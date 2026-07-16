package com.kuafu.common.dynamic_config.mapper;

import com.kuafu.common.dynamic_config.domain.SystemConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


/**
 * @author www.macpe.cn
 * @description 针对表【system_config】的数据库操作Mapper
 * @createDate 2025-05-08 21:14:29
 * @Entity generator.domain.SystemConfig
 */
@Mapper
@Component("commonSystemConfigMapper")

public interface SystemConfigMapper extends BaseMapper<SystemConfig> {

}




