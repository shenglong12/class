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

import com.kuafu.web.mapper.RepairOrderStatisticsMapper;
import com.kuafu.web.entity.RepairOrder;
import com.kuafu.web.service.IRepairOrderStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> repair_order 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("RepairOrderStatistics")
public class RepairOrderStatisticsServiceImpl extends ServiceImpl<RepairOrderStatisticsMapper, RepairOrder> implements IRepairOrderStatisticsService {
    @Resource
    private RepairOrderStatisticsMapper repairOrderStaticMapper;

                        @Override
                        public List<Object> repair_order_statistic_6e1b2cda_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_6e1b2cda_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_706e6dad_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_706e6dad_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_dbd88f7e_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_dbd88f7e_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_f027c1c8_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_f027c1c8_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_6a0412be_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_6a0412be_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_ab5cf58a_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_ab5cf58a_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_e0ad78fb_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_e0ad78fb_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_def7eca8_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_def7eca8_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_statistic_e9136ad8_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_statistic_e9136ad8_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_ae2d02e5_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_ae2d02e5_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_create_time_datetime_statistic_426c9cba_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_create_time_datetime_statistic_426c9cba_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_create_time_datetime_statistic_13a3f358_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_create_time_datetime_statistic_13a3f358_count(queryWrapper);
                                }


                        @Override
                        public List<Object> repair_order_create_time_datetime_statistic_b1da4bdf_count(LambdaQueryWrapper queryWrapper) {
                                return repairOrderStaticMapper.repair_order_create_time_datetime_statistic_b1da4bdf_count(queryWrapper);
                                }


}
