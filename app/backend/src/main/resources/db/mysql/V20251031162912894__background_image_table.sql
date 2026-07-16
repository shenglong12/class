drop table if exists `background_image`;
create table `background_image` (
	background_image_id	INT	 not null auto_increment,
	user_info_user_info_id_1	INT	not null,
	image_files_resource_key	varchar(255)	not null	default	'image_files_resource_key',
	upload_time	DATETIME	,
	primary key (background_image_id)
);insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-01 09:00:00","1","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-05 10:30:00","2","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-10 14:00:00","3","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-15 16:20:00","4","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-20 08:45:00","5","4");
insert into	background_image	(upload_time,background_image_id,user_info_user_info_id_1)	values	("2024-03-25 11:10:00","6","4");
