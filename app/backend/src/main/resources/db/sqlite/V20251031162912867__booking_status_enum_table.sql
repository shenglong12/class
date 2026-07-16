drop table if exists `booking_status_enum`;
create table `booking_status_enum` (
	booking_status_enum_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	status_name	varchar(512)	not null
);insert into	booking_status_enum	(booking_status_enum_id,status_name)	values	("1","待审核");
insert into	booking_status_enum	(booking_status_enum_id,status_name)	values	("2","审核通过");
insert into	booking_status_enum	(booking_status_enum_id,status_name)	values	("3","拒绝");
