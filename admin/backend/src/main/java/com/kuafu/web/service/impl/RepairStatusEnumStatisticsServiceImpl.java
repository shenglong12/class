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

import com.kuafu.web.mapper.RepairStatusEnumStatisticsMapper;
import com.kuafu.web.entity.RepairStatusEnum;
import com.kuafu.web.service.IRepairStatusEnumStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> repair_status_enum 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("RepairStatusEnumStatistics")
public class RepairStatusEnumStatisticsServiceImpl extends ServiceImpl<RepairStatusEnumStatisticsMapper, RepairStatusEnum> implements IRepairStatusEnumStatisticsService {
    @Resource
    private RepairStatusEnumStatisticsMapper repairStatusEnumStaticMapper;

                        @Override
                        public List<Object> repair_status_enum_repair_status_enum_id_status_statistic_3331a20c_count(LambdaQueryWrapper queryWrapper) {
                                return repairStatusEnumStaticMapper.repair_status_enum_repair_status_enum_id_status_statistic_3331a20c_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_status_enum_repair_status_enum_id_status_statistic_f7fab3d8_count(LambdaQueryWrapper queryWrapper) {
                                return repairStatusEnumStaticMapper.repair_status_enum_repair_status_enum_id_status_statistic_f7fab3d8_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_status_enum_repair_status_enum_id_status_statistic_20ddc808_count(LambdaQueryWrapper queryWrapper) {
                                return repairStatusEnumStaticMapper.repair_status_enum_repair_status_enum_id_status_statistic_20ddc808_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_status_enum_status_name_status_statistic_e2385888_count(LambdaQueryWrapper queryWrapper) {
                                return repairStatusEnumStaticMapper.repair_status_enum_status_name_status_statistic_e2385888_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_status_enum_status_name_status_statistic_b8607948_count(LambdaQueryWrapper queryWrapper) {
                                return repairStatusEnumStaticMapper.repair_status_enum_status_name_status_statistic_b8607948_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_status_enum_status_name_status_statistic_5735d9aa_count(LambdaQueryWrapper queryWrapper) {
                                return repairStatusEnumStaticMapper.repair_status_enum_status_name_status_statistic_5735d9aa_count(queryWrapper);
                                }


}
