SELECT name FROM sqlite_master WHERE type='table';

drop table if exists static_resources;
create table if not exists static_resources
(
    resource_id              INTEGER
    primary key autoincrement,
    resource_type            VARCHAR(255),
    resource_name            VARCHAR(255) ,
    resource_path            VARCHAR(500) not null,
    related_table_name       VARCHAR(255),
    related_table_key        INTEGER,
    relate_table_column_name varchar(255)
);

drop table if exists category;
create table if not exists category
(
    id                     integer primary key autoincrement, -- id
    name                   varchar(255) not null,             -- 列别名称
    parent_id              integer     ,             -- 父级类别id
    create_time            date,
    update_time            date,
    relevance_table        varchar(255),                      -- 表分类
    relevance_table_column varchar(255)                       -- 分类字段
);

drop table if exists dify_requirement;
CREATE TABLE dify_requirement
(
    id              INTEGER PRIMARY KEY autoincrement, -- 主键ID
    content         TEXT,                     -- 内容
    file_url         VARCHAR(256),                     -- 内容
    conversation_id INTEGER,                           -- 对话ID
    dify_id         INTEGER,                           -- 应用ID
    type            VARCHAR(128)                       -- 类型 TEXT FILE
);


drop table if exists dynamic_api_setting;
CREATE TABLE `dynamic_api_setting`
(
    `id`            INTEGER PRIMARY KEY autoincrement,
    `key_name`      varchar(255) NOT NULL ,
    `description`   varchar(255) NOT NULL ,
    `url`           varchar(255) NOT NULL ,
    `token`         varchar(255)          DEFAULT NULL,
    `app_id`        varchar(255)          DEFAULT NULL ,
    `api_key`       varchar(255)          DEFAULT NULL,
    `api_secret`    varchar(255)          DEFAULT NULL,
    `method`        varchar(255) NOT NULL DEFAULT '',
    `body_type`     varchar(255)          DEFAULT '' ,
    `body_template` varchar(1000)         DEFAULT NULL,
    `header`        varchar(255)          DEFAULT NULL ,
    `auth_type`     varchar(255)          DEFAULT '' ,
    `protocol`      varchar(255)          DEFAULT ''
);

INSERT INTO dynamic_api_setting (id, key_name, description, url, token, app_id, api_key, api_secret, method, body_type, body_template, header, auth_type, protocol) VALUES (1, 'word2pic', 'AI文本生成图片', 'http://60.204.199.245:8087/api/word2pic', 'sk-5+E0fIxMO8A90hN0O7nBMuxzKQSXdvBpv7g8dVGNlTM=', null, null, null, 'post', 'template', '{"input":"${{text}}"}', '{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}', 'codeflying', 'http');
INSERT INTO dynamic_api_setting (id, key_name, description, url, token, app_id, api_key, api_secret, method, body_type, body_template, header, auth_type, protocol) VALUES (2, 'pic2word', 'AI图片识别理解', 'http://60.204.199.245:8087/api/pic2word', 'sk-5+E0fIxMO8A90hN0O7nBMuxzKQSXdvBpv7g8dVGNlTM=', null, null, null, 'post', 'template', '{"input":"请详细描述一下图中有什么？如果图片中是文本，请将文本内容原样输出。","content":"${{content}}"}', '{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}', 'codeflying', 'http');
INSERT INTO dynamic_api_setting (id, key_name, description, url, token, app_id, api_key, api_secret, method, body_type, body_template, header, auth_type, protocol) VALUES (3, 'text2text', 'AI文本生成文本', 'http://60.204.199.245:8087/llm/chat-block', 'sk-5+E0fIxMO8A90hN0O7nBMuxzKQSXdvBpv7g8dVGNlTM=', null, null, null, 'post', '', null, '{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}', 'codeflying', 'http');
INSERT INTO dynamic_api_setting (id, key_name, description, url, token, app_id, api_key, api_secret, method, body_type, body_template, header, auth_type, protocol) VALUES (4, 'tts', '文本生成语音', 'http://60.204.199.245:8087/api/text2speech', 'sk-5+E0fIxMO8A90hN0O7nBMuxzKQSXdvBpv7g8dVGNlTM=', null, null, null, 'post', 'template', '{"content":"${{text}}"}', '{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}', 'codeflying', 'http');

