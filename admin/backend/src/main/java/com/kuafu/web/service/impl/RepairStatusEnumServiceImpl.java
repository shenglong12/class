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

import com.kuafu.web.mapper.RepairStatusEnumMapper;
import com.kuafu.web.entity.RepairStatusEnum;
import com.kuafu.web.service.IRepairStatusEnumService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> 报修状态 服务实现类 </p>
 *
 * @author kuafuai
 * @description
 * @date 2025/10/31 16:29
 */
@Slf4j
@Service("RepairStatusEnum")
public class RepairStatusEnumServiceImpl extends ServiceImpl<RepairStatusEnumMapper, RepairStatusEnum> implements IRepairStatusEnumService {


}
