package com.kuafu.web.mapper;

import java.util.List;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kuafu.web.entity.ScanRecord;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
/**
 * <p> scan_record Mapper </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Mapper
public interface ScanRecordStatisticsMapper extends BaseMapper<ScanRecord> {

                        List<Object> scan_record_statistic_cf42143d_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> scan_record_statistic_53e9c652_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> scan_record_statistic_44e4ab34_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> scan_record_scan_time_datetime_statistic_2603ead4_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> scan_record_scan_time_datetime_statistic_c280d87a_count(@Param("ew") Wrapper queryWrapper);


                        List<Object> scan_record_scan_time_datetime_statistic_e3554bf9_count(@Param("ew") Wrapper queryWrapper);

}
