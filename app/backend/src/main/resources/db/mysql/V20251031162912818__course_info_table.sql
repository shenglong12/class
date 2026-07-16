drop table if exists `course_info`;
create table `course_info` (
	course_info_id	INT	 not null auto_increment,
	course_name	varchar(512)	not null,
	college	varchar(512)	not null,
	major	varchar(512)	not null,
	classroom_info_classroom_info_id_1	INT	not null,
	start_date	DATETIME	not null,
	end_date	DATETIME	not null,
	weekdays	varchar(512)	not null,
	time_period	varchar(512)	not null,
	content	TEXT	,
	primary key (course_info_id)
);insert into	course_info	(college,end_date,major,classroom_info_classroom_info_id_1,course_name,course_info_id,weekdays,content,time_period,start_date)	values	("理学院","2024-06-30","数学与应用数学","6","高等数学","1","周一,周三,周五","微积分与线性代数综合课程","08:00-09:35","2024-02-01");
insert into	course_info	(college,end_date,major,classroom_info_classroom_info_id_1,course_name,course_info_id,weekdays,content,time_period,start_date)	values	("外语学院","2024-06-30","英语","3","大学英语","2","周二,周四","英语听说读写综合训练","10:00-11:35","2024-02-01");
insert into	course_info	(college,end_date,major,classroom_info_classroom_info_id_1,course_name,course_info_id,weekdays,content,time_period,start_date)	values	("计算机学院","2024-06-30","软件工程","1","数据结构","3","周一,周三","数据结构基础与算法设计","14:00-15:35","2024-02-01");
insert into	course_info	(college,end_date,major,classroom_info_classroom_info_id_1,course_name,course_info_id,weekdays,content,time_period,start_date)	values	("电子学院","2024-06-30","电子信息","4","电子电路","4","周二,周四","模拟与数字电子电路原理","08:00-09:35","2024-02-01");
insert into	course_info	(college,end_date,major,classroom_info_classroom_info_id_1,course_name,course_info_id,weekdays,content,time_period,start_date)	values	("艺术学院","2024-06-30","视觉传达","5","艺术设计基础","5","周三","设计思维与视觉表达","13:00-15:35","2024-02-01");
insert into	course_info	(college,end_date,major,classroom_info_classroom_info_id_1,course_name,course_info_id,weekdays,content,time_period,start_date)	values	("管理学院","2024-06-30","行政管理","6","管理学原理","6","周五","管理学基础理论与应用","10:00-11:35","2024-02-01");
