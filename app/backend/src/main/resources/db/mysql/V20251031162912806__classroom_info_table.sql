drop table if exists `classroom_info`;
create table `classroom_info` (
	classroom_info_id	INT	 not null auto_increment,
	building_enum_building_enum_id_1	INT	not null,
	room_number	varchar(512)	not null,
	qr_code_path	varchar(512)	,
	primary key (classroom_info_id)
);insert into	classroom_info	(building_enum_building_enum_id_1,room_number,qr_code_path,classroom_info_id)	values	("1","101","","1");
insert into	classroom_info	(building_enum_building_enum_id_1,room_number,qr_code_path,classroom_info_id)	values	("1","102","","2");
insert into	classroom_info	(building_enum_building_enum_id_1,room_number,qr_code_path,classroom_info_id)	values	("2","201","","3");
insert into	classroom_info	(building_enum_building_enum_id_1,room_number,qr_code_path,classroom_info_id)	values	("2","202","","4");
insert into	classroom_info	(building_enum_building_enum_id_1,room_number,qr_code_path,classroom_info_id)	values	("3","301","","5");
insert into	classroom_info	(building_enum_building_enum_id_1,room_number,qr_code_path,classroom_info_id)	values	("4","401","","6");
