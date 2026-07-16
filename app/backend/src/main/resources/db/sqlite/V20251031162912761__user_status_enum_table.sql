drop table if exists `user_status_enum`;
create table `user_status_enum` (
	user_status_enum_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	status_name	varchar(512)	not null
);insert into	user_status_enum	(status_name,user_status_enum_id)	values	("待审核","1");
insert into	user_status_enum	(status_name,user_status_enum_id)	values	("审核通过","2");
insert into	user_status_enum	(status_name,user_status_enum_id)	values	("禁用","3");
