drop table if exists `booking_info`;
create table `booking_info` (
	booking_info_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	user_info_user_info_id_1	INTEGER	not null,
	classroom_info_classroom_info_id_1	INTEGER	not null,
	booking_date	DATETIME	not null,
	time_period	varchar(512)	not null,
	purpose	varchar(512)	not null,
	booking_status_enum_booking_status_enum_id_1	INTEGER	not null,
	create_time	DATETIME	,
	start_time	TIME	not null,
	end_time	TIME	not null
);
insert into	booking_info	(user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,purpose,booking_date,booking_info_id,booking_status_enum_booking_status_enum_id_1,time_period,start_time,end_time)	values	("1","2024-03-20 09:00:00.0 00:00:00.0","1","学院活动","2024-04-01 00:00:00.0","1","1","08:00-09:35","08:00","09:35");
insert into	booking_info	(user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,purpose,booking_date,booking_info_id,booking_status_enum_booking_status_enum_id_1,time_period,start_time,end_time)	values	("2","2024-03-21 10:15:00.0 00:00:00.0","2","课程讨论","2024-04-02 00:00:00.0","2","2","10:00-11:35","10:00","11:35");
insert into	booking_info	(user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,purpose,booking_date,booking_info_id,booking_status_enum_booking_status_enum_id_1,time_period,start_time,end_time)	values	("3","2024-03-22 11:30:00.0 00:00:00.0","3","专业讲座","2024-04-03 00:00:00.0","3","1","14:00-15:35","14:00","15:35");
insert into	booking_info	(user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,purpose,booking_date,booking_info_id,booking_status_enum_booking_status_enum_id_1,time_period,start_time,end_time)	values	("6","2024-03-23 14:45:00.0 00:00:00.0","4","学生活动","2024-04-04 00:00:00.0","4","3","08:00-09:35","08:00","09:35");
insert into	booking_info	(user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,purpose,booking_date,booking_info_id,booking_status_enum_booking_status_enum_id_1,time_period,start_time,end_time)	values	("1","2024-03-24 16:00:00.0 00:00:00.0","5","课程辅导","2024-04-05 00:00:00.0","5","2","13:00-15:35","13:00","15:35");
insert into	booking_info	(user_info_user_info_id_1,create_time,classroom_info_classroom_info_id_1,purpose,booking_date,booking_info_id,booking_status_enum_booking_status_enum_id_1,time_period,start_time,end_time)	values	("4","2024-03-25 08:30:00.0 00:00:00.0","6","部门会议","2024-04-06 00:00:00.0","6","1","10:00-11:35","10:00","11:35");
