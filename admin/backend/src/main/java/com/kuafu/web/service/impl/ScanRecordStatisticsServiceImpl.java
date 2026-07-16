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

import com.kuafu.web.mapper.ScanRecordStatisticsMapper;
import com.kuafu.web.entity.ScanRecord;
import com.kuafu.web.service.IScanRecordStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> scan_record 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("ScanRecordStatistics")
public class ScanRecordStatisticsServiceImpl extends ServiceImpl<ScanRecordStatisticsMapper, ScanRecord> implements IScanRecordStatisticsService {
    @Resource
    private ScanRecordStatisticsMapper scanRecordStaticMapper;

                        @Override
                        public List<Object> scan_record_statistic_cf42143d_count(LambdaQueryWrapper queryWrapper) {
                                return scanRecordStaticMapper.scan_record_statistic_cf42143d_count(queryWrapper);
                                }


                        @Override
                        public List<Object> scan_record_statistic_53e9c652_count(LambdaQueryWrapper queryWrapper) {
                                return scanRecordStaticMapper.scan_record_statistic_53e9c652_count(queryWrapper);
                                }


                        @Override
                        public List<Object> scan_record_statistic_44e4ab34_count(LambdaQueryWrapper queryWrapper) {
                                return scanRecordStaticMapper.scan_record_statistic_44e4ab34_count(queryWrapper);
                                }


                        @Override
                        public List<Object> scan_record_scan_time_datetime_statistic_2603ead4_count(LambdaQueryWrapper queryWrapper) {
                                return scanRecordStaticMapper.scan_record_scan_time_datetime_statistic_2603ead4_count(queryWrapper);
                                }


                        @Override
                        public List<Object> scan_record_scan_time_datetime_statistic_c280d87a_count(LambdaQueryWrapper queryWrapper) {
                                return scanRecordStaticMapper.scan_record_scan_time_datetime_statistic_c280d87a_count(queryWrapper);
                                }


                        @Override
                        public List<Object> scan_record_scan_time_datetime_statistic_e3554bf9_count(LambdaQueryWrapper queryWrapper) {
                                return scanRecordStaticMapper.scan_record_scan_time_datetime_statistic_e3554bf9_count(queryWrapper);
                                }


}
