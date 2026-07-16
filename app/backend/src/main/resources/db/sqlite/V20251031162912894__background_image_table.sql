drop table if exists `background_image`;
create table `background_image` (
	background_image_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	user_info_user_info_id_1	INTEGER	not null,
	image_files_resource_key	varchar(255)	not null	default	'image_files_resource_key',
	upload_time	DATETIME	
);insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-01 09:00:00.0 00:00:00.0","1","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-05 10:30:00.0 00:00:00.0","2","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-10 14:00:00.0 00:00:00.0","3","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-15 16:20:00.0 00:00:00.0","4","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-20 08:45:00.0 00:00:00.0","5","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-25 11:10:00.0 00:00:00.0","6","4");
