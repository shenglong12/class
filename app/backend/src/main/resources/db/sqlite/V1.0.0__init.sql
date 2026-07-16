drop table if exists static_resources;
create table if not exists static_resources
(
    resource_id
    INTEGER
    primary
    key
    autoincrement,
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
) not null,
    related_table_name VARCHAR
(
    255
),
    related_table_key INTEGER,
    relate_table_column_name varchar
(
    255
)
    );

drop table if exists category;
create table if not exists category
(
    id
    integer
    primary
    key
    autoincrement, -- id
    name
    varchar
(
    255
) not null, -- 列别名称
    parent_id integer, -- 父级类别id
    create_time date,
    update_time date,
    relevance_table varchar
(
    255
), -- 表分类
    relevance_table_column varchar
(
    255
) -- 分类字段
    );



CREATE TABLE system_messages
(
    id        INTEGER PRIMARY KEY autoincrement, -- 主键ID，SQLite 使用 INTEGER PRIMARY KEY AUTOINCREMENT 实现自增
    type      TEXT NOT NULL,                     -- 类型：notification(通知)、carousel(轮播图)、announcement(公告栏)
    title     TEXT,                              -- 标题，NULL 默认为可为空
    content   TEXT,                              -- 内容，适用于通知和公告栏
    image_url TEXT,                              -- 适用于轮播图：图片URL
    url       TEXT                               -- 适用于轮播图：跳转的URL
);

CREATE TABLE dify_requirement
(
    id              INTEGER PRIMARY KEY autoincrement, -- 主键ID
    content         TEXT,                              -- 内容
    file_url        VARCHAR(256),                      -- 内容
    conversation_id INTEGER,                           -- 对话ID
    dify_id         INTEGER,                           -- 应用ID
    type            VARCHAR(128)                       -- 类型 TEXT FILE
);

CREATE TABLE system_config
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT, -- 主键自增
    name         varchar(512) NOT NULL UNIQUE,      -- 名称，唯一
    chinese_name varchar(512),                      -- 中文名称
    description  varchar(512),                      -- 描述
    value        varchar(512),                      -- 配置值
    remark       varchar(512),                      -- 备注
    type         varchar(512)                       -- 类型（比如 string, int, boolean 等）
);
INSERT INTO system_config (id, name, chinese_name, description, value, remark, type)
VALUES (1, 'log', '日志', null, '0/5 * * * * ?', null, null);
INSERT INTO system_config (id, name, chinese_name, description, value, remark, type)
VALUES (2, 'dbconfig.refreshInterval', '动态刷新系统配置时间间隔', null, '0/10 * * * * ?', null, null);
INSERT INTO system_config (id, name, chinese_name, description, value, remark, type)
VALUES (3, 'order.expire.time', null, null, '1', null, null);



CREATE TABLE delayed_tasks
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    task_type    varchar(255)  NOT NULL,
    task_data    varchar(1024) NOT NULL,
    status       varchar(64)   NOT NULL DEFAULT 0,
    execute_time DATETIME      NOT NULL,
    create_time  DATETIME      NOT NULL DEFAULT (datetime('now')),
    update_time  DATETIME      NOT NULL DEFAULT (datetime('now'))
);

-- 创建索引
CREATE INDEX idx_delayed_tasks_execute_time_status ON delayed_tasks (execute_time, status);


-- 订单主表
create table general_orders
(
    id               INTEGER
        primary key autoincrement,
    order_no         TEXT                                            not null,
    user_id          INTEGER                                         not null,
    user_name        TEXT,
    op_id            varchar(255),
    order_type       TEXT     default 'normal',
    order_status     TEXT                                            not null,
    pay_status       TEXT                                            not null,
    delivery_status  TEXT,
    products_id      INT                                             not null,
    quality          int      default 1                              not null,
    total_amount     REAL                                            not null,
    discount_amount  REAL     default 0.0,
    shipping_fee     REAL     default 0.0,
    actual_amount    REAL                                            not null,
    payment_channel  TEXT,
    payment_order_id TEXT,
    payment_time     DATETIME,
    payment_amount   REAL,
    shipping_method  TEXT,
    shipping_address TEXT,
    shipping_company TEXT,
    tracking_number  TEXT,
    tel              TEXT,
    recipient        TEXT,
    create_time      DATETIME default (datetime('now', 'localtime')) not null,
    update_time      DATETIME default (datetime('now', 'localtime')) not null,
    cancel_time      DATETIME,
    complete_time    DATETIME,
    deliver_time     DATETIME,
    refund_id        varchar(255),
    refund_type      TEXT,
    refund_amount    REAL,
    refund_reason    TEXT,
    refund_status    TEXT,
    remark           TEXT,
    is_deleted       INTEGER  default 0,
    table_name       varchar(255),
    field_name       varchar(255)
);

create index idx_orders_create_time
    on general_orders (create_time);

create index idx_orders_order_no
    on general_orders (order_no);

create index idx_orders_payment_order_id
    on general_orders (payment_order_id);

create index idx_orders_status_composite
    on general_orders (order_status, pay_status, delivery_status);

create index idx_orders_user_id
    on general_orders (user_id);



