package com.kuafu.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 删除全部数据后重置 SQLite 自增计数器，让主键重新从 1 开始。
 */
@Component
@RequiredArgsConstructor
public class SqliteSequenceReset {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 如果表已清空，重置自增序列
     * @param tableName 数据库表名（如 classroom_info）
     */
    public void resetIfEmpty(String tableName) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM " + tableName, Integer.class);
            if (count != null && count == 0) {
                jdbcTemplate.execute(
                        "DELETE FROM sqlite_sequence WHERE name = '" + tableName + "'");
            }
        } catch (Exception ignored) {
            // MySQL 等不存在 sqlite_sequence 表，忽略即可
        }
    }
}
