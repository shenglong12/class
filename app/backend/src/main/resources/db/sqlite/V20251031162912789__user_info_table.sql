drop table if exists `user_info`;
create table `user_info` (
	user_info_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	name	varchar(512)	not null,
	student_id	varchar(512)	not null,
	phone_number	VARCHAR(64)	not null,
	college	varchar(512)	not null,
	major	varchar(512)	not null,
	password	VARCHAR(64)	not null,
	role_enum_role_enum_id_1	INTEGER	not null,
	user_status_enum_user_status_enum_id_1	INTEGER	not null,
	registration_date	DATETIME	
);insert into	user_info	(college,password,registration_date,major,name,role_enum_role_enum_id_1,student_id,user_status_enum_user_status_enum_id_1,phone_number,user_info_id)	values	("计算机学院","pass1234","2024-01-10 09:00:00.0 00:00:00.0","软件工程","李晓明","1","20231001","2","13800000001","1");
insert into	user_info	(college,password,registration_date,major,name,role_enum_role_enum_id_1,student_id,user_status_enum_user_status_enum_id_1,phone_number,user_info_id)	values	("电子学院","pass2345","2024-01-11 10:15:00.0 00:00:00.0","电子信息","王磊","1","20231002","1","13800000002","2");
insert into	user_info	(college,password,registration_date,major,name,role_enum_role_enum_id_1,student_id,user_status_enum_user_status_enum_id_1,phone_number,user_info_id)	values	("外语学院","pass3456","2024-01-12 11:30:00.0 00:00:00.0","英语","赵娜","1","20231003","2","13800000003","3");
insert into	user_info	(college,password,registration_date,major,name,role_enum_role_enum_id_1,student_id,user_status_enum_user_status_enum_id_1,phone_number,user_info_id)	values	("管理学院","adminpass","2024-01-05 08:45:00.0 00:00:00.0","行政管理","陈强","2","admin001","2","13800000004","4");
insert into	user_info	(college,password,registration_date,major,name,role_enum_role_enum_id_1,student_id,user_status_enum_user_status_enum_id_1,phone_number,user_info_id)	values	("艺术学院","pass5678","2024-01-13 14:00:00.0 00:00:00.0","视觉传达","孙婷","1","20231005","3","13800000005","5");
insert into	user_info	(college,password,registration_date,major,name,role_enum_role_enum_id_1,student_id,user_status_enum_user_status_enum_id_1,phone_number,user_info_id)	values	("理学院","pass6789","2024-01-14 15:20:00.0 00:00:00.0","数学与应用数学","刘洋","1","20231006","2","13800000006","6");
