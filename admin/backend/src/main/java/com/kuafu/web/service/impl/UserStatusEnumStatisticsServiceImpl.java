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

import com.kuafu.web.mapper.UserStatusEnumStatisticsMapper;
import com.kuafu.web.entity.UserStatusEnum;
import com.kuafu.web.service.IUserStatusEnumStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> user_status_enum 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("UserStatusEnumStatistics")
public class UserStatusEnumStatisticsServiceImpl extends ServiceImpl<UserStatusEnumStatisticsMapper, UserStatusEnum> implements IUserStatusEnumStatisticsService {
    @Resource
    private UserStatusEnumStatisticsMapper userStatusEnumStaticMapper;

                        @Override
                        public List<Object> user_status_enum_user_status_enum_id_status_statistic_250bfd65_count(LambdaQueryWrapper queryWrapper) {
                                return userStatusEnumStaticMapper.user_status_enum_user_status_enum_id_status_statistic_250bfd65_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_status_enum_user_status_enum_id_status_statistic_75457957_count(LambdaQueryWrapper queryWrapper) {
                                return userStatusEnumStaticMapper.user_status_enum_user_status_enum_id_status_statistic_75457957_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_status_enum_user_status_enum_id_status_statistic_1ef97a93_count(LambdaQueryWrapper queryWrapper) {
                                return userStatusEnumStaticMapper.user_status_enum_user_status_enum_id_status_statistic_1ef97a93_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_status_enum_status_name_status_statistic_d1621cea_count(LambdaQueryWrapper queryWrapper) {
                                return userStatusEnumStaticMapper.user_status_enum_status_name_status_statistic_d1621cea_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_status_enum_status_name_status_statistic_0ee48f21_count(LambdaQueryWrapper queryWrapper) {
                                return userStatusEnumStaticMapper.user_status_enum_status_name_status_statistic_0ee48f21_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_status_enum_status_name_status_statistic_6a2582b2_count(LambdaQueryWrapper queryWrapper) {
                                return userStatusEnumStaticMapper.user_status_enum_status_name_status_statistic_6a2582b2_count(queryWrapper);
                                }


}
