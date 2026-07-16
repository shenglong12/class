drop table if exists `role_enum`;
create table `role_enum` (
	role_enum_id	INT	 not null auto_increment,
	role_name	varchar(512)	not null,
	primary key (role_enum_id)
);insert into	role_enum	(role_name,role_enum_id)	values	("普通用户","1");
insert into	role_enum	(role_name,role_enum_id)	values	("管理员","2");
