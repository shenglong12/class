// import request from '@/utils/request';

import service from "@/utils/request";
const BASE_API = "/courseInfoStatistics";

export default {
course_info_statistic_11f06534_count(data){
  return service({
    url: BASE_API + "/course_info_statistic_11f06534_count",
    method: "post",
    data:data
  });
},
course_info_statistic_43e99b84_count(data){
  return service({
    url: BASE_API + "/course_info_statistic_43e99b84_count",
    method: "post",
    data:data
  });
},
course_info_statistic_6ba45514_count(data){
  return service({
    url: BASE_API + "/course_info_statistic_6ba45514_count",
    method: "post",
    data:data
  });
},
course_info_start_date_datetime_statistic_799f21ac_count(data){
  return service({
    url: BASE_API + "/course_info_start_date_datetime_statistic_799f21ac_count",
    method: "post",
    data:data
  });
},
course_info_start_date_datetime_statistic_40a54bd0_count(data){
  return service({
    url: BASE_API + "/course_info_start_date_datetime_statistic_40a54bd0_count",
    method: "post",
    data:data
  });
},
course_info_start_date_datetime_statistic_470d5ddb_count(data){
  return service({
    url: BASE_API + "/course_info_start_date_datetime_statistic_470d5ddb_count",
    method: "post",
    data:data
  });
},
course_info_end_date_datetime_statistic_9bbd1d33_count(data){
  return service({
    url: BASE_API + "/course_info_end_date_datetime_statistic_9bbd1d33_count",
    method: "post",
    data:data
  });
},
course_info_end_date_datetime_statistic_f4a5cdc7_count(data){
  return service({
    url: BASE_API + "/course_info_end_date_datetime_statistic_f4a5cdc7_count",
    method: "post",
    data:data
  });
},
course_info_end_date_datetime_statistic_9844a0b8_count(data){
  return service({
    url: BASE_API + "/course_info_end_date_datetime_statistic_9844a0b8_count",
    method: "post",
    data:data
  });
},
};
