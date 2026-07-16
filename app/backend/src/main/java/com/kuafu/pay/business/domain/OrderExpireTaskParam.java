package com.kuafu.pay.business.domain;

import com.kuafu.common.delay_task.domain.DelayTask;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderExpireTaskParam extends DelayTask {

    /**
     * 业务系统订单号
     */
    private String orderNo;

}
