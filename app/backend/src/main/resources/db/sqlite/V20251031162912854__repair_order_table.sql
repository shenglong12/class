drop table if exists `repair_order`;
create table `repair_order` (
	repair_order_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	user_info_user_info_id_1	INTEGER	not null,
	classroom_info_classroom_info_id_1	INTEGER	not null,
	content	TEXT	not null,
	repair_status_enum_repair_status_enum_id_1	INTEGER	not null,
	feedback	TEXT	,
	create_time	DATETIME	,
	image_files_resource_key	varchar(255)		default	'image_files_resource_key'
);insert into	repair_order	(feedback,user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,content,repair_status_enum_repair_status_enum_id_1,repair_order_id)	values	("","1","2024-03-01 09:00:00.0 00:00:00.0","1","教室空调不制冷","1","1");
insert into	repair_order	(feedback,user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,content,repair_status_enum_repair_status_enum_id_1,repair_order_id)	values	("已联系维修人员，预计明天修复","2","2024-03-02 10:15:00.0 00:00:00.0","3","投影仪无法开机","2","2");
insert into	repair_order	(feedback,user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,content,repair_status_enum_repair_status_enum_id_1,repair_order_id)	values	("已更换新桌椅，使用正常","3","2024-03-03 11:30:00.0 00:00:00.0","5","桌椅损坏，需更换","3","3");
insert into	repair_order	(feedback,user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,content,repair_status_enum_repair_status_enum_id_1,repair_order_id)	values	("更换灯泡，问题解决","5","2024-03-04 14:45:00.0 00:00:00.0","2","照明灯闪烁","2","4");
insert into	repair_order	(feedback,user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,content,repair_status_enum_repair_status_enum_id_1,repair_order_id)	values	("","6","2024-03-05 16:00:00.0 00:00:00.0","4","门锁损坏无法关闭","1","5");
insert into	repair_order	(feedback,user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,content,repair_status_enum_repair_status_enum_id_1,repair_order_id)	values	("已清理干净，排水正常","1","2024-03-06 08:30:00.0 00:00:00.0","6","地面有积水","3","6");
