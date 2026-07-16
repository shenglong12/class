drop table if exists login; 
 create table if not exists login
(
    login_id        INTEGER
        primary key autoincrement,
    wx_open_id      VARCHAR(255),
    phone_number    VARCHAR(255),
    password        VARCHAR(255),
    user_name       VARCHAR(255),
    relevance_id    varchar(255),
    relevance_table varchar(255)
);
insert into login (login_id, wx_open_id, phone_number, password, user_name, relevance_id, relevance_table)
values (null, null, 'admin', '$2a$10$bOrRjyHCMT2DUnXoVuFWNO1dSd6Zj/fyjS0TETmcD1ZXlR/BGklc2', null, null,null);