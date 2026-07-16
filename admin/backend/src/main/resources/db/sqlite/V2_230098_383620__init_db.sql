drop table if exists `kf_system_config`;
create table `kf_system_config` (
 	 `id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `name` 	 varchar(512) not null  ,
	 `chinese_name` 	 varchar(512)   ,
	 `description` 	 varchar(512)   ,
	 `content` 	 TEXT   ,
	 `remark` 	 varchar(512)   ,
	 `type` 	 varchar(512)   
 );


drop table if exists `role_enum`;
create table `role_enum` (
 	 `role_enum_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `role_name` 	 varchar(512) not null  
 );
insert into `role_enum` (`role_name` ) values ("普通用户" );
insert into `role_enum` (`role_name` ) values ("管理员" );

drop table if exists `user_status_enum`;
create table `user_status_enum` (
 	 `user_status_enum_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `status_name` 	 varchar(512) not null  
 );
insert into `user_status_enum` (`status_name` ) values ("待审核" );
insert into `user_status_enum` (`status_name` ) values ("审核通过" );
insert into `user_status_enum` (`status_name` ) values ("禁用" );

drop table if exists `building_enum`;
create table `building_enum` (
 	 `building_enum_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `building_name` 	 varchar(512) not null  
 );
insert into `building_enum` (`building_name` ) values ("第一教学楼" );
insert into `building_enum` (`building_name` ) values ("第二教学楼" );
insert into `building_enum` (`building_name` ) values ("第三教学楼" );
insert into `building_enum` (`building_name` ) values ("第四教学楼" );
insert into `building_enum` (`building_name` ) values ("第五教学楼" );
insert into `building_enum` (`building_name` ) values ("综合楼" );

drop table if exists `user_info`;
create table `user_info` (
 	 `user_info_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `name` 	 varchar(512) not null  ,
	 `student_id` 	 varchar(512) not null  ,
	 `phone_number` 	 VARCHAR(64) not null  ,
	 `college` 	 varchar(512) not null  ,
	 `major` 	 varchar(512) not null  ,
	 `password` 	 VARCHAR(64) not null  ,
	 `role_enum_role_enum_id_1` 	 INTEGER not null  ,
	 `user_status_enum_user_status_enum_id_1` 	 INTEGER not null  ,
	 `registration_date` 	 DATETIME   
 );
insert into `user_info` (`name`, `student_id`, `phone_number`, `college`, `major`, `password`, `role_enum_role_enum_id_1`, `user_status_enum_user_status_enum_id_1`, `registration_date` ) values ("李晓明","20231001","13800000001","计算机学院","软件工程","pass1234","1","2","2025-01-01 00:00:00" );
insert into `user_info` (`name`, `student_id`, `phone_number`, `college`, `major`, `password`, `role_enum_role_enum_id_1`, `user_status_enum_user_status_enum_id_1`, `registration_date` ) values ("王磊","20231002","13800000002","电子学院","电子信息","pass2345","1","1","2025-01-01 00:00:00" );
insert into `user_info` (`name`, `student_id`, `phone_number`, `college`, `major`, `password`, `role_enum_role_enum_id_1`, `user_status_enum_user_status_enum_id_1`, `registration_date` ) values ("赵娜","20231003","13800000003","外语学院","英语","pass3456","1","2","2025-01-01 00:00:00" );
insert into `user_info` (`name`, `student_id`, `phone_number`, `college`, `major`, `password`, `role_enum_role_enum_id_1`, `user_status_enum_user_status_enum_id_1`, `registration_date` ) values ("陈强","admin001","13800000004","管理学院","行政管理","adminpass","2","2","2025-01-01 00:00:00" );
insert into `user_info` (`name`, `student_id`, `phone_number`, `college`, `major`, `password`, `role_enum_role_enum_id_1`, `user_status_enum_user_status_enum_id_1`, `registration_date` ) values ("孙婷","20231005","13800000005","艺术学院","视觉传达","pass5678","1","3","2025-01-01 00:00:00" );
insert into `user_info` (`name`, `student_id`, `phone_number`, `college`, `major`, `password`, `role_enum_role_enum_id_1`, `user_status_enum_user_status_enum_id_1`, `registration_date` ) values ("刘洋","20231006","13800000006","理学院","数学与应用数学","pass6789","1","2","2025-01-01 00:00:00" );

drop table if exists `classroom_info`;
create table `classroom_info` (
 	 `classroom_info_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `building_enum_building_enum_id_1` 	 INTEGER not null  ,
	 `room_number` 	 varchar(512) not null  ,
	 `qr_code_path` 	 varchar(512)   
 );
insert into `classroom_info` (`building_enum_building_enum_id_1`, `room_number`, `qr_code_path` ) values ("1","101","" );
insert into `classroom_info` (`building_enum_building_enum_id_1`, `room_number`, `qr_code_path` ) values ("1","102","" );
insert into `classroom_info` (`building_enum_building_enum_id_1`, `room_number`, `qr_code_path` ) values ("2","201","" );
insert into `classroom_info` (`building_enum_building_enum_id_1`, `room_number`, `qr_code_path` ) values ("2","202","" );
insert into `classroom_info` (`building_enum_building_enum_id_1`, `room_number`, `qr_code_path` ) values ("3","301","" );
insert into `classroom_info` (`building_enum_building_enum_id_1`, `room_number`, `qr_code_path` ) values ("4","401","" );

drop table if exists `course_info`;
create table `course_info` (
 	 `course_info_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `course_name` 	 varchar(512) not null  ,
	 `college` 	 varchar(512) not null  ,
	 `major` 	 varchar(512) not null  ,
	 `classroom_info_classroom_info_id_1` 	 INTEGER not null  ,
	 `start_date` 	 DATETIME not null  ,
	 `end_date` 	 DATETIME not null  ,
	 `weekdays` 	 varchar(512) not null  ,
	 `time_period` 	 varchar(512) not null  ,
	 `content` 	 TEXT   
 );
insert into `course_info` (`course_name`, `college`, `major`, `classroom_info_classroom_info_id_1`, `start_date`, `end_date`, `weekdays`, `time_period`, `content` ) values ("高等数学","理学院","数学与应用数学","6","2025-01-01 00:00:00","2025-01-01 00:00:00","周一,周三,周五","08:00-09:35","微积分与线性代数综合课程" );
insert into `course_info` (`course_name`, `college`, `major`, `classroom_info_classroom_info_id_1`, `start_date`, `end_date`, `weekdays`, `time_period`, `content` ) values ("大学英语","外语学院","英语","3","2025-01-01 00:00:00","2025-01-01 00:00:00","周二,周四","10:00-11:35","英语听说读写综合训练" );
insert into `course_info` (`course_name`, `college`, `major`, `classroom_info_classroom_info_id_1`, `start_date`, `end_date`, `weekdays`, `time_period`, `content` ) values ("数据结构","计算机学院","软件工程","1","2025-01-01 00:00:00","2025-01-01 00:00:00","周一,周三","14:00-15:35","数据结构基础与算法设计" );
insert into `course_info` (`course_name`, `college`, `major`, `classroom_info_classroom_info_id_1`, `start_date`, `end_date`, `weekdays`, `time_period`, `content` ) values ("电子电路","电子学院","电子信息","4","2025-01-01 00:00:00","2025-01-01 00:00:00","周二,周四","08:00-09:35","模拟与数字电子电路原理" );
insert into `course_info` (`course_name`, `college`, `major`, `classroom_info_classroom_info_id_1`, `start_date`, `end_date`, `weekdays`, `time_period`, `content` ) values ("艺术设计基础","艺术学院","视觉传达","5","2025-01-01 00:00:00","2025-01-01 00:00:00","周三","13:00-15:35","设计思维与视觉表达" );
insert into `course_info` (`course_name`, `college`, `major`, `classroom_info_classroom_info_id_1`, `start_date`, `end_date`, `weekdays`, `time_period`, `content` ) values ("管理学原理","管理学院","行政管理","6","2025-01-01 00:00:00","2025-01-01 00:00:00","周五","10:00-11:35","管理学基础理论与应用" );

drop table if exists `repair_status_enum`;
create table `repair_status_enum` (
 	 `repair_status_enum_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `status_name` 	 varchar(512) not null  
 );
insert into `repair_status_enum` (`status_name` ) values ("待审核" );
insert into `repair_status_enum` (`status_name` ) values ("处理中" );
insert into `repair_status_enum` (`status_name` ) values ("已完成" );

drop table if exists `repair_order`;
create table `repair_order` (
 	 `repair_order_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `user_info_user_info_id_1` 	 INTEGER not null  ,
	 `classroom_info_classroom_info_id_1` 	 INTEGER not null  ,
	 `content` 	 TEXT not null  ,
	 `repair_status_enum_repair_status_enum_id_1` 	 INTEGER not null  ,
	 `feedback` 	 TEXT   ,
	 `create_time` 	 DATETIME  DEFAULT (CURRENT_TIMESTAMP) ,
	 `image_files_resource_key` 	 VARCHAR(255)   
 );
insert into `repair_order` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `content`, `repair_status_enum_repair_status_enum_id_1`, `feedback`, `create_time`, `image_files_resource_key` ) values ("1","1","教室空调不制冷","1","","2025-01-01 00:00:00","0" );
insert into `repair_order` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `content`, `repair_status_enum_repair_status_enum_id_1`, `feedback`, `create_time`, `image_files_resource_key` ) values ("2","3","投影仪无法开机","2","已联系维修人员，预计明天修复","2025-01-01 00:00:00","0" );
insert into `repair_order` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `content`, `repair_status_enum_repair_status_enum_id_1`, `feedback`, `create_time`, `image_files_resource_key` ) values ("3","5","桌椅损坏，需更换","3","已更换新桌椅，使用正常","2025-01-01 00:00:00","0" );
insert into `repair_order` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `content`, `repair_status_enum_repair_status_enum_id_1`, `feedback`, `create_time`, `image_files_resource_key` ) values ("5","2","照明灯闪烁","2","更换灯泡，问题解决","2025-01-01 00:00:00","0" );
insert into `repair_order` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `content`, `repair_status_enum_repair_status_enum_id_1`, `feedback`, `create_time`, `image_files_resource_key` ) values ("6","4","门锁损坏无法关闭","1","","2025-01-01 00:00:00","0" );
insert into `repair_order` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `content`, `repair_status_enum_repair_status_enum_id_1`, `feedback`, `create_time`, `image_files_resource_key` ) values ("1","6","地面有积水","3","已清理干净，排水正常","2025-01-01 00:00:00","0" );

drop table if exists `booking_status_enum`;
create table `booking_status_enum` (
 	 `booking_status_enum_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `status_name` 	 varchar(512) not null  
 );
insert into `booking_status_enum` (`status_name` ) values ("待审核" );
insert into `booking_status_enum` (`status_name` ) values ("审核通过" );
insert into `booking_status_enum` (`status_name` ) values ("拒绝" );

drop table if exists `booking_info`;
create table `booking_info` (
 	 `booking_info_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `user_info_user_info_id_1` 	 INTEGER not null  ,
	 `classroom_info_classroom_info_id_1` 	 INTEGER not null  ,
	 `booking_date` 	 DATETIME not null  ,
	 `time_period` 	 varchar(512) not null  ,
	 `purpose` 	 varchar(512) not null  ,
	 `booking_status_enum_booking_status_enum_id_1` 	 INTEGER not null  ,
	 `create_time` 	 DATETIME  DEFAULT (CURRENT_TIMESTAMP) ,
	 `start_time` 	 TIME not null  ,
	 `end_time` 	 TIME not null  
 );
insert into `booking_info` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `booking_date`, `time_period`, `purpose`, `booking_status_enum_booking_status_enum_id_1`, `create_time`, `start_time`, `end_time` ) values ("1","1","2025-01-01 00:00:00","08:00-09:35","学院活动","1","2025-01-01 00:00:00","0","0" );
insert into `booking_info` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `booking_date`, `time_period`, `purpose`, `booking_status_enum_booking_status_enum_id_1`, `create_time`, `start_time`, `end_time` ) values ("2","2","2025-01-01 00:00:00","10:00-11:35","课程讨论","2","2025-01-01 00:00:00","0","0" );
insert into `booking_info` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `booking_date`, `time_period`, `purpose`, `booking_status_enum_booking_status_enum_id_1`, `create_time`, `start_time`, `end_time` ) values ("3","3","2025-01-01 00:00:00","14:00-15:35","专业讲座","1","2025-01-01 00:00:00","0","0" );
insert into `booking_info` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `booking_date`, `time_period`, `purpose`, `booking_status_enum_booking_status_enum_id_1`, `create_time`, `start_time`, `end_time` ) values ("6","4","2025-01-01 00:00:00","08:00-09:35","学生活动","3","2025-01-01 00:00:00","0","0" );
insert into `booking_info` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `booking_date`, `time_period`, `purpose`, `booking_status_enum_booking_status_enum_id_1`, `create_time`, `start_time`, `end_time` ) values ("1","5","2025-01-01 00:00:00","13:00-15:35","课程辅导","2","2025-01-01 00:00:00","0","0" );
insert into `booking_info` (`user_info_user_info_id_1`, `classroom_info_classroom_info_id_1`, `booking_date`, `time_period`, `purpose`, `booking_status_enum_booking_status_enum_id_1`, `create_time`, `start_time`, `end_time` ) values ("4","6","2025-01-01 00:00:00","10:00-11:35","部门会议","1","2025-01-01 00:00:00","0","0" );

drop table if exists `background_image`;
create table `background_image` (
 	 `background_image_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `user_info_user_info_id_1` 	 INTEGER not null  ,
	 `image_files_resource_key` 	 VARCHAR(255)   ,
	 `upload_time` 	 DATETIME   
 );
insert into `background_image` (`user_info_user_info_id_1`, `image_files_resource_key`, `upload_time` ) values ("4","0","2025-01-01 00:00:00" );
insert into `static_resources` (`resource_path`, `related_table_name`, `related_table_key`, `relate_table_column_name`) values ('https://www.codeflying.net/preview/scenery-field.jpg','background_image', '1', 'image_files');
insert into `static_resources` (`resource_path`, `related_table_name`, `related_table_key`, `relate_table_column_name`) values ('https://www.codeflying.net/preview/lake-mountains.jpg','background_image', '1', 'image_files');
insert into `background_image` (`user_info_user_info_id_1`, `image_files_resource_key`, `upload_time` ) values ("4","0","2025-01-01 00:00:00" );
insert into `static_resources` (`resource_path`, `related_table_name`, `related_table_key`, `relate_table_column_name`) values ('https://www.codeflying.net/preview/sunset-sea.jpg','background_image', '2', 'image_files');
insert into `background_image` (`user_info_user_info_id_1`, `image_files_resource_key`, `upload_time` ) values ("4","0","2025-01-01 00:00:00" );
insert into `static_resources` (`resource_path`, `related_table_name`, `related_table_key`, `relate_table_column_name`) values ('https://www.codeflying.net/preview/flowers-vase.jpg','background_image', '3', 'image_files');
insert into `background_image` (`user_info_user_info_id_1`, `image_files_resource_key`, `upload_time` ) values ("4","0","2025-01-01 00:00:00" );
insert into `static_resources` (`resource_path`, `related_table_name`, `related_table_key`, `relate_table_column_name`) values ('https://www.codeflying.net/preview/retro-film.jpg','background_image', '4', 'image_files');
insert into `background_image` (`user_info_user_info_id_1`, `image_files_resource_key`, `upload_time` ) values ("4","0","2025-01-01 00:00:00" );
insert into `static_resources` (`resource_path`, `related_table_name`, `related_table_key`, `relate_table_column_name`) values ('https://www.codeflying.net/preview/hot-air-balloon-lake.jpg','background_image', '5', 'image_files');
insert into `background_image` (`user_info_user_info_id_1`, `image_files_resource_key`, `upload_time` ) values ("4","0","2025-01-01 00:00:00" );
insert into `static_resources` (`resource_path`, `related_table_name`, `related_table_key`, `relate_table_column_name`) values ('https://www.codeflying.net/preview/puppy.jpg','background_image', '6', 'image_files');

drop table if exists `scan_record`;
create table `scan_record` (
 	 `scan_id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `user_info_user_info_id_1` 	 INTEGER   ,
	 `building_name` 	 varchar(512)   ,
	 `room_number` 	 varchar(512)   ,
	 `scan_time` 	 DATETIME not null  
 );
insert into `scan_record` (`user_info_user_info_id_1`, `building_name`, `room_number`, `scan_time` ) values ("1","教学楼A","101","2025-01-01 00:00:00" );
insert into `scan_record` (`user_info_user_info_id_1`, `building_name`, `room_number`, `scan_time` ) values ("1","教学楼B","202","2025-01-01 00:00:00" );

drop table if exists `dynamic_api_setting`;
create table `dynamic_api_setting` (
 	 `id` 	 INTEGER PRIMARY KEY AUTOINCREMENT ,
	 `key_name` 	 VARCHAR(512)   ,
	 `description` 	 VARCHAR(512)   ,
	 `url` 	 VARCHAR(512)   ,
	 `token` 	 VARCHAR(512)   ,
	 `app_id` 	 VARCHAR(512)   ,
	 `api_key` 	 VARCHAR(512)   ,
	 `api_secret` 	 VARCHAR(512)   ,
	 `method` 	 VARCHAR(512)   ,
	 `body_type` 	 VARCHAR(512)   ,
	 `body_template` 	 VARCHAR(512)   ,
	 `header` 	 VARCHAR(512)   ,
	 `auth_type` 	 VARCHAR(512)   ,
	 `protocol` 	 VARCHAR(512)   ,
	 `data_path` 	 VARCHAR(512)   ,
	 `data_type` 	 VARCHAR(512)   
 );
insert into `dynamic_api_setting` (`key_name`, `description`, `url`, `token`, `app_id`, `api_key`, `api_secret`, `method`, `body_type`, `body_template`, `header`, `auth_type`, `protocol`, `data_path`, `data_type` ) values ("word2pic","AI文本生成图片","http://1.94.150.175:8087/api/word2pic","sk-SjDJYUYE0p8OepvSL8EVfbsMVWwg4ge8xfbXFyFlaRY=","","","","post","template",'{"input":"${{text}}"}','{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}',"codeflying","http","","" );
insert into `dynamic_api_setting` (`key_name`, `description`, `url`, `token`, `app_id`, `api_key`, `api_secret`, `method`, `body_type`, `body_template`, `header`, `auth_type`, `protocol`, `data_path`, `data_type` ) values ("pic2word","AI图片识别理解","http://1.94.150.175:8087/api/pic2word","sk-SjDJYUYE0p8OepvSL8EVfbsMVWwg4ge8xfbXFyFlaRY=","","","","post","template",'{"input":"请详细描述一下图中有什么？如果图片中是文本，请将文本内容原样输出。","content":"${{content}}"}','{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}',"codeflying","http","","" );
insert into `dynamic_api_setting` (`key_name`, `description`, `url`, `token`, `app_id`, `api_key`, `api_secret`, `method`, `body_type`, `body_template`, `header`, `auth_type`, `protocol`, `data_path`, `data_type` ) values ("text2text","AI文本生成文本","http://1.94.150.175:8087/llm/chat-block","sk-SjDJYUYE0p8OepvSL8EVfbsMVWwg4ge8xfbXFyFlaRY=","","","","post","","",'{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}',"codeflying","http","","" );
insert into `dynamic_api_setting` (`key_name`, `description`, `url`, `token`, `app_id`, `api_key`, `api_secret`, `method`, `body_type`, `body_template`, `header`, `auth_type`, `protocol`, `data_path`, `data_type` ) values ("tts","文本生成语音","http://1.94.150.175:8087/api/text2speech","sk-SjDJYUYE0p8OepvSL8EVfbsMVWwg4ge8xfbXFyFlaRY=","","","","post","template",'{"content":"${{text}}"}','{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}',"codeflying","http","","" );
insert into `dynamic_api_setting` (`key_name`, `description`, `url`, `token`, `app_id`, `api_key`, `api_secret`, `method`, `body_type`, `body_template`, `header`, `auth_type`, `protocol`, `data_path`, `data_type` ) values ("amap_geo","高德-将详细的结构化地址转换为经纬度坐标","http://1.94.150.175:8087/mcp/amap?method=maps_geo","sk-SjDJYUYE0p8OepvSL8EVfbsMVWwg4ge8xfbXFyFlaRY=","","","","post","template",'{"city":"${{city}}","address":"${{address}}"}','{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}',"codeflying","http","$.data.content[0].text","json" );
insert into `dynamic_api_setting` (`key_name`, `description`, `url`, `token`, `app_id`, `api_key`, `api_secret`, `method`, `body_type`, `body_template`, `header`, `auth_type`, `protocol`, `data_path`, `data_type` ) values ("amap_regeocode","高德-经纬度坐标转换为行政区划地址信息","http://1.94.150.175:8087/mcp/amap?method=maps_regeocode","sk-SjDJYUYE0p8OepvSL8EVfbsMVWwg4ge8xfbXFyFlaRY=","","","","post","template",'{"location":"${{location}}"}','{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}',"codeflying","http","$.data.content[0].text","json" );
insert into `dynamic_api_setting` (`key_name`, `description`, `url`, `token`, `app_id`, `api_key`, `api_secret`, `method`, `body_type`, `body_template`, `header`, `auth_type`, `protocol`, `data_path`, `data_type` ) values ("amap_direction_driving","高德-驾车路径规划","http://1.94.150.175:8087/mcp/amap?method=maps_direction_driving","sk-SjDJYUYE0p8OepvSL8EVfbsMVWwg4ge8xfbXFyFlaRY=","","","","post","template",'{"origin":"${{origin}}","destination":"${{destination}}"}','{"Authorization": "Bearer ${{token}}", "Content-Type": "application/json"}',"codeflying","http","$.data.content[0].text","json" );

drop table if exists `login`;

    create table `login` (
        `login_id`      INTEGER PRIMARY KEY AUTOINCREMENT,
        `relevance_id`  VARCHAR ,
        `password`      VARCHAR ,
        `user_name`     VARCHAR ,
        `relevance_table`       VARCHAR ,
        `phone_number`  VARCHAR ,
        `wx_open_id`    VARCHAR
    );
    
    insert into `login` (`relevance_id`,`password`,`user_name`,`relevance_table`,`phone_number`,`wx_open_id` ) values ("","$2a$10$bOrRjyHCMT2DUnXoVuFWNO1dSd6Zj/fyjS0TETmcD1ZXlR/BGklc2","admin","","admin","" );
    
    insert into `login` (`relevance_id`,`password`,`user_name`,`relevance_table`,`phone_number`,`wx_open_id` ) values ("1","$2a$10$bOrRjyHCMT2DUnXoVuFWNO1dSd6Zj/fyjS0TETmcD1ZXlR/BGklc2","test","userInfo","18852718858","" );
