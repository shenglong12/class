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

import com.kuafu.web.mapper.UserInfoStatisticsMapper;
import com.kuafu.web.entity.UserInfo;
import com.kuafu.web.service.IUserInfoStatisticsService;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
/**
 * <p> user_info 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("UserInfoStatistics")
public class UserInfoStatisticsServiceImpl extends ServiceImpl<UserInfoStatisticsMapper, UserInfo> implements IUserInfoStatisticsService {
    @Resource
    private UserInfoStatisticsMapper userInfoStaticMapper;

                        @Override
                        public List<Object> user_info_statistic_26db9414_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_statistic_26db9414_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_statistic_d748e97d_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_statistic_d748e97d_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_statistic_fd86a11f_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_statistic_fd86a11f_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_statistic_cd4888d0_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_statistic_cd4888d0_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_statistic_97b06621_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_statistic_97b06621_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_statistic_b82e02ef_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_statistic_b82e02ef_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_role_enum_role_enum_id_1_status_statistic_17efc900_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_role_enum_role_enum_id_1_status_statistic_17efc900_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_role_enum_role_enum_id_1_status_statistic_c074d54d_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_role_enum_role_enum_id_1_status_statistic_c074d54d_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_role_enum_role_enum_id_1_status_statistic_ab566b0f_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_role_enum_role_enum_id_1_status_statistic_ab566b0f_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_b868696b_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_user_status_enum_user_status_enum_id_1_status_statistic_b868696b_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_811edae4_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_user_status_enum_user_status_enum_id_1_status_statistic_811edae4_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_user_status_enum_user_status_enum_id_1_status_statistic_52937bd0_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_user_status_enum_user_status_enum_id_1_status_statistic_52937bd0_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_registration_date_datetime_statistic_c6d4320e_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_registration_date_datetime_statistic_c6d4320e_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_registration_date_datetime_statistic_5c524202_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_registration_date_datetime_statistic_5c524202_count(queryWrapper);
                                }


                        @Override
                        public List<Object> user_info_registration_date_datetime_statistic_f89dba19_count(LambdaQueryWrapper queryWrapper) {
                                return userInfoStaticMapper.user_info_registration_date_datetime_statistic_f89dba19_count(queryWrapper);
                                }


}
