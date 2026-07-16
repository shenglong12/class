drop table if exists `building_enum`;
create table `building_enum` (
	building_enum_id	INT	 not null auto_increment,
	building_name	varchar(512)	not null,
	primary key (building_enum_id)
);insert into	building_enum	(building_name,building_enum_id)	values	("第一教学楼","1");
insert into	building_enum	(building_name,building_enum_id)	values	("第二教学楼","2");
insert into	building_enum	(building_name,building_enum_id)	values	("第三教学楼","3");
insert into	building_enum	(building_name,building_enum_id)	values	("第四教学楼","4");
insert into	building_enum	(building_name,building_enum_id)	values	("第五教学楼","5");
insert into	building_enum	(building_name,building_enum_id)	values	("综合楼","6");
