package com.kuafu.web.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomTenantHandler implements TenantLineHandler {

    private final TenantContextHolder tenantContextHolder;

    @Autowired
    public CustomTenantHandler(TenantContextHolder tenantContextHolder) {
        this.tenantContextHolder = tenantContextHolder;
    }

    public static ThreadLocal<Set<String>> threadLocalSet = ThreadLocal.withInitial(() -> {
        Set<String> set = new HashSet<>();
        set.add("xxxxx");
        return set;
    });

    @Override
    public Expression getTenantId() {
        // 假设有一个租户上下文，能够从中获取当前用户的租户
        Integer tenantId = tenantContextHolder.getCurrentTenantId();
        if (tenantId == null) {
            return null;
        }
        // 返回租户ID的表达式，LongValue 是 JSQLParser 中表示 bigint 类型的 class
        return new LongValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return "xxxxx";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 根据需要返回是否忽略该表
        return threadLocalSet.get().contains(tableName);
    }

}