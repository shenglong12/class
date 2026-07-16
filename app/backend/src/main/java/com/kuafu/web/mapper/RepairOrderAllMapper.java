package com.kuafu.web.mapper;

import java.util.List;
import com.kuafu.web.entity.RepairOrderAll;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * <p> 报修单 Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface RepairOrderAllMapper extends BaseMapper<RepairOrderAll> {
    public List<RepairOrderAll> selectListNew(@Param("ew") Wrapper<RepairOrderAll> queryWrapper);
}
