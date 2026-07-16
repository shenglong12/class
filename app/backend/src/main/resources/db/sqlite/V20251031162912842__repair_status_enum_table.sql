drop table if exists `repair_status_enum`;
create table `repair_status_enum` (
	repair_status_enum_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	status_name	varchar(512)	not null
);insert into	repair_status_enum	(status_name,repair_status_enum_id)	values	("待审核","1");
insert into	repair_status_enum	(status_name,repair_status_enum_id)	values	("处理中","2");
insert into	repair_status_enum	(status_name,repair_status_enum_id)	values	("已完成","3");
