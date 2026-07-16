package com.kuafu.web.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class EventVo<T> {
    /**
     * add/update
     */
    private String model;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 数据
     */

    private T data;

}
