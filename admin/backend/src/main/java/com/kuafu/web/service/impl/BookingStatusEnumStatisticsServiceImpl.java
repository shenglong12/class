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

import com.kuafu.web.mapper.BookingStatusEnumStatisticsMapper;
import com.kuafu.web.entity.BookingStatusEnum;
import com.kuafu.web.service.IBookingStatusEnumStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> booking_status_enum 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("BookingStatusEnumStatistics")
public class BookingStatusEnumStatisticsServiceImpl extends ServiceImpl<BookingStatusEnumStatisticsMapper, BookingStatusEnum> implements IBookingStatusEnumStatisticsService {
    @Resource
    private BookingStatusEnumStatisticsMapper bookingStatusEnumStaticMapper;

                        @Override
                        public List<Object> booking_status_enum_booking_status_enum_id_status_statistic_adcce722_count(LambdaQueryWrapper queryWrapper) {
                                return bookingStatusEnumStaticMapper.booking_status_enum_booking_status_enum_id_status_statistic_adcce722_count(queryWrapper);
                                }


                        @Override
                        public List<Object> booking_status_enum_booking_status_enum_id_status_statistic_e5e0d2ef_count(LambdaQueryWrapper queryWrapper) {
                                return bookingStatusEnumStaticMapper.booking_status_enum_booking_status_enum_id_status_statistic_e5e0d2ef_count(queryWrapper);
                                }


                        @Override
                        public List<Object> booking_status_enum_booking_status_enum_id_status_statistic_b6fdee33_count(LambdaQueryWrapper queryWrapper) {
                                return bookingStatusEnumStaticMapper.booking_status_enum_booking_status_enum_id_status_statistic_b6fdee33_count(queryWrapper);
                                }


                        @Override
                        public List<Object> booking_status_enum_status_name_status_statistic_90b34348_count(LambdaQueryWrapper queryWrapper) {
                                return bookingStatusEnumStaticMapper.booking_status_enum_status_name_status_statistic_90b34348_count(queryWrapper);
                                }


                        @Override
                        public List<Object> booking_status_enum_status_name_status_statistic_6038f1fd_count(LambdaQueryWrapper queryWrapper) {
                                return bookingStatusEnumStaticMapper.booking_status_enum_status_name_status_statistic_6038f1fd_count(queryWrapper);
                                }


                        @Override
                        public List<Object> booking_status_enum_status_name_status_statistic_a52c379f_count(LambdaQueryWrapper queryWrapper) {
                                return bookingStatusEnumStaticMapper.booking_status_enum_status_name_status_statistic_a52c379f_count(queryWrapper);
                                }


}
