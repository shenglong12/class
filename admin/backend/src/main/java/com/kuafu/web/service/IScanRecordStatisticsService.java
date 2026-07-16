package com.kuafu.web.service;

import java.util.List;
import com.kuafu.web.entity.ScanRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p>  scan_record 服务类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
public interface IScanRecordStatisticsService extends IService<ScanRecord> {
        List<Object> scan_record_statistic_cf42143d_count(LambdaQueryWrapper queryWrapper);

        List<Object> scan_record_statistic_53e9c652_count(LambdaQueryWrapper queryWrapper);

        List<Object> scan_record_statistic_44e4ab34_count(LambdaQueryWrapper queryWrapper);

        List<Object> scan_record_scan_time_datetime_statistic_2603ead4_count(LambdaQueryWrapper queryWrapper);

        List<Object> scan_record_scan_time_datetime_statistic_c280d87a_count(LambdaQueryWrapper queryWrapper);

        List<Object> scan_record_scan_time_datetime_statistic_e3554bf9_count(LambdaQueryWrapper queryWrapper);

}
