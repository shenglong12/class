show
tables;
DROP TABLE IF EXISTS static_resources;
CREATE TABLE IF NOT EXISTS static_resources
(
    resource_id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT, -- 设置为自增
    resource_name
    VARCHAR
(
    255
),
    resource_type VARCHAR
(
    255
),
    resource_path VARCHAR
(
    500
) NOT NULL,
    related_table_name VARCHAR
(
    255
) DEFAULT NULL,
    related_table_key INT DEFAULT NULL,
    relate_table_column_name VARCHAR
(
    255
) DEFAULT NULL
    );



DROP TABLE IF EXISTS category;
CREATE TABLE IF NOT EXISTS category
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT, -- id
    name
    VARCHAR
(
    255
) NOT NULL, -- 类别名称
    parent_id INT DEFAULT NULL, -- 父级类别id
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    relevance_table VARCHAR
(
    255
) DEFAULT NULL, -- 表分类
    relevance_table_column VARCHAR
(
    255
) DEFAULT NULL -- 分类字段
    );


CREATE TABLE `system_messages`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `type`        VARCHAR(255) NOT NULL COMMENT '类型：notification(通知)、carousel(轮播图)、announcement(公告栏)',
    `title`       VARCHAR(255) NULL COMMENT '标题',
    `content`     TEXT NULL COMMENT '内容，适用于通知和公告栏',
    `image_url`   VARCHAR(255) COMMENT '适用于轮播图：图片URL',
    `url`         VARCHAR(255) COMMENT '适用于轮播图：跳转的URL',
    `create_time` DATE DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', -- 创建时间
    `update_time` DATE DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);


CREATE TABLE dify_requirement
(
    id              INT AUTO_INCREMENT PRIMARY KEY, -- 主键ID
    content         TEXT NULL,                      -- 内容
    file_url        VARCHAR(256) NULL,              -- 内容
    conversation_id INTEGER NULL,                   -- 对话ID
    dify_id         INTEGER NULL,                   -- dify_id
    type            VARCHAR(128) NULL               -- 类型：TEXT FILE
);


CREATE TABLE delayed_tasks
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_type    VARCHAR(216) NOT NULL,
    task_data    VARCHAR(1024)         NOT NULL,
    status       VARCHAR(64)  NOT NULL DEFAULT 0 COMMENT ,
    execute_time DATETIME     NOT NULL,
    create_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX        idx_execute_time_status (execute_time, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

