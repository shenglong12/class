// import request from '@/utils/request';

import service from "@/utils/request";
const BASE_API = "/backgroundImageStatistics";

export default {
background_image_statistic_2cec2f0a_count(data){
  return service({
    url: BASE_API + "/background_image_statistic_2cec2f0a_count",
    method: "post",
    data:data
  });
},
background_image_statistic_6d9fd8e7_count(data){
  return service({
    url: BASE_API + "/background_image_statistic_6d9fd8e7_count",
    method: "post",
    data:data
  });
},
background_image_statistic_db8eea91_count(data){
  return service({
    url: BASE_API + "/background_image_statistic_db8eea91_count",
    method: "post",
    data:data
  });
},
background_image_upload_time_datetime_statistic_775bad3a_count(data){
  return service({
    url: BASE_API + "/background_image_upload_time_datetime_statistic_775bad3a_count",
    method: "post",
    data:data
  });
},
background_image_upload_time_datetime_statistic_401f3864_count(data){
  return service({
    url: BASE_API + "/background_image_upload_time_datetime_statistic_401f3864_count",
    method: "post",
    data:data
  });
},
background_image_upload_time_datetime_statistic_f7bdec73_count(data){
  return service({
    url: BASE_API + "/background_image_upload_time_datetime_statistic_f7bdec73_count",
    method: "post",
    data:data
  });
},
};
