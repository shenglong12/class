package com.kuafu.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuafu.common.entity.BaseEntity;
import com.kuafu.common.entity.Filter;
import com.kuafu.common.entity.OrderCondition;
import com.kuafu.common.entity.SortEnum;

import java.util.List;

public class QueryUtils {
    /**
     * 获取 query utils
     *
     * @return
     */
    public static QueryWrapper getQueryWrapper(BaseEntity baseEntity) {
        final QueryWrapper wrapper = new QueryWrapper<>();
        return getQueryWrapper(wrapper, baseEntity);
    }


    /**
     * 获取 query wrappler
     *
     * @return
     */
    public static QueryWrapper getQueryWrapper(QueryWrapper wrapper, BaseEntity baseEntity) {
        final List<Filter> andFilter = baseEntity.getAndFilter();
        final List<Filter> orFilter = baseEntity.getOrFilter();
        final List<OrderCondition> orderConditions = baseEntity.getOrderConditions();


//      处理and
        if (andFilter != null && !andFilter.isEmpty()) {
            for (Filter filter : andFilter) {
                processAndFilter(wrapper, filter);
            }
        }


        // 处理 orFilter，并将它们包含在一个 and 条件中
        if (andFilter != null && !andFilter.isEmpty()) {
            if (orFilter!=null &&  !orFilter.isEmpty()){
                wrapper.and(w -> {
                    processOrFilter((QueryWrapper) w, orFilter);
                });
            }

        } else {
            if (orFilter!=null &&  !orFilter.isEmpty()){
                processOrFilter(wrapper, orFilter);
            }
        }

//      处理排序字段
        if (orderConditions != null && !orderConditions.isEmpty()) {
            for (OrderCondition orderCondition : orderConditions) {
                processSortCondition(orderCondition, wrapper);
            }
        }


        return wrapper;
    }

    private static void processSortCondition(OrderCondition orderCondition, QueryWrapper wrapper) {

        final String name = orderCondition.getName();
        if (StringUtils.isEmpty(name)){
            return;
        }
        final String sortStr = orderCondition.getSort();
        SortEnum sort = SortEnum.fromValue(sortStr);
        if (sort == null) {
            sort = SortEnum.ASC;
        }
        if (sort.equals(SortEnum.ASC)) {
            wrapper.orderByAsc(name);
        } else {
            wrapper.orderByDesc(name);
        }

    }


    /**
     * 处理or条件
     *
     * @param wrapper
     * @param orFilter
     */
    private static void processOrFilter(QueryWrapper wrapper, List<Filter> orFilter) {

        for (int i = 0; i < orFilter.size(); i++) {
            final Filter filter = orFilter.get(i);
            QueryWrapper tmpWrapper = processAndFilter(wrapper, filter); // 使用w作为and中的wrapper
            if (i != orFilter.size() - 1) {
                tmpWrapper.or();
            }
        }
    }


    /**
     * 处理查询
     *
     * @param wrapper
     * @param filter
     */
    private static QueryWrapper processAndFilter(QueryWrapper wrapper, Filter filter) {
        final String name = filter.getName();

        if (filter.getLt() != null) {
            wrapper.lt(name, filter.getLt());
        }

        if (filter.getGt() != null) {
            wrapper.gt(name, filter.getGt());
        }

        if (filter.getGe() != null) {
            wrapper.ge(name, filter.getGe());
        }

        if (filter.getLe() != null) {
            wrapper.le(name, filter.getLe());
        }

        if (filter.getEq() != null) {
            wrapper.eq(name, filter.getEq());
        }

        if (filter.getNe() != null) {
            wrapper.ne(name, filter.getNe());
        }

        if (filter.getIn() != null && !filter.getIn().isEmpty()) {
            wrapper.in(name, filter.getIn());
        }

        if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
            wrapper.notIn(name, filter.getNotIn());
        }
        if (filter.getIsNull() != null) {
            wrapper.isNull(name);
        }

        if (filter.getIsNotNull() != null) {
            wrapper.isNotNull(name);
        }

        if (StringUtils.isNotEmpty(filter.getLike())) {
            wrapper.like(name, filter.getLike());
        }

        if (StringUtils.isNotEmpty(filter.getLeftLike())) {
            wrapper.likeLeft(name, filter.getLeftLike());
        }

        if (StringUtils.isNotEmpty(filter.getRightLike())) {
            wrapper.likeRight(name, filter.getRightLike());
        }

        return wrapper;
    }
}
