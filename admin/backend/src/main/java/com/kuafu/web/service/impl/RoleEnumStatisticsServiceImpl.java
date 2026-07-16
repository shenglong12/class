package com.kuafu.web.service.impl;

import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kuafu.web.mapper.RoleEnumStatisticsMapper;
import com.kuafu.web.entity.RoleEnum;
import com.kuafu.web.service.IRoleEnumStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> role_enum 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("RoleEnumStatistics")
public class RoleEnumStatisticsServiceImpl extends ServiceImpl<RoleEnumStatisticsMapper, RoleEnum> implements IRoleEnumStatisticsService {
    @Resource
    private RoleEnumStatisticsMapper roleEnumStaticMapper;

                        @Override
                        public List<Object> role_enum_role_enum_id_status_statistic_d3cd406e_count(LambdaQueryWrapper queryWrapper) {
                                return roleEnumStaticMapper.role_enum_role_enum_id_status_statistic_d3cd406e_count(queryWrapper);
                                }


                        @Override
                        public List<Object> role_enum_role_enum_id_status_statistic_dec1eb62_count(LambdaQueryWrapper queryWrapper) {
                                return roleEnumStaticMapper.role_enum_role_enum_id_status_statistic_dec1eb62_count(queryWrapper);
                                }


                        @Override
                        public List<Object> role_enum_role_enum_id_status_statistic_b83c5d21_count(LambdaQueryWrapper queryWrapper) {
                                return roleEnumStaticMapper.role_enum_role_enum_id_status_statistic_b83c5d21_count(queryWrapper);
                                }


}
