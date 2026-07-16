package com.kuafu.web.mapper;

import java.util.List;
import com.kuafu.web.entity.DynamicApiSetting;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p> API配置 Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface DynamicApiSettingMapper extends BaseMapper<DynamicApiSetting> {

}
