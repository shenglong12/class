drop table if exists `kf_system_config`;
create table `kf_system_config` (
	id	INT	 not null auto_increment,
	name	varchar(512)	not null,
	chinese_name	varchar(512)	,
	description	varchar(512)	,
	content	TEXT	,
	remark	varchar(512)	,
	type	varchar(512)	,
	primary key (id)
);