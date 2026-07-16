// import request from '@/utils/request';

import service from "@/utils/request";
const BASE_API = "/scanRecordStatistics";

export default {
scan_record_statistic_cf42143d_count(data){
  return service({
    url: BASE_API + "/scan_record_statistic_cf42143d_count",
    method: "post",
    data:data
  });
},
scan_record_statistic_53e9c652_count(data){
  return service({
    url: BASE_API + "/scan_record_statistic_53e9c652_count",
    method: "post",
    data:data
  });
},
scan_record_statistic_44e4ab34_count(data){
  return service({
    url: BASE_API + "/scan_record_statistic_44e4ab34_count",
    method: "post",
    data:data
  });
},
scan_record_scan_time_datetime_statistic_2603ead4_count(data){
  return service({
    url: BASE_API + "/scan_record_scan_time_datetime_statistic_2603ead4_count",
    method: "post",
    data:data
  });
},
scan_record_scan_time_datetime_statistic_c280d87a_count(data){
  return service({
    url: BASE_API + "/scan_record_scan_time_datetime_statistic_c280d87a_count",
    method: "post",
    data:data
  });
},
scan_record_scan_time_datetime_statistic_e3554bf9_count(data){
  return service({
    url: BASE_API + "/scan_record_scan_time_datetime_statistic_e3554bf9_count",
    method: "post",
    data:data
  });
},
};
