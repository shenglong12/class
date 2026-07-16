drop table if exists `scan_record`;
create table `scan_record` (
	scan_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	user_info_user_info_id_1	INTEGER	,
	building_name	varchar(512)	,
	room_number	varchar(512)	,
	scan_time	DATETIME	not null
);insert into	scan_record	(building_name,user_info_user_info_id_1,room_number,scan_id,scan_time)	values	("教学楼A","1","101","1","2025-10-01 08:00:00.0 00:00:00.0");
insert into	scan_record	(building_name,user_info_user_info_id_1,room_number,scan_id,scan_time)	values	("教学楼B","1","202","2","2025-10-02 09:00:00.0 00:00:00.0");
