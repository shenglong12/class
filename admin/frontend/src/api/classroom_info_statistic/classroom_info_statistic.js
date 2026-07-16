// import request from '@/utils/request';

import service from "@/utils/request";
const BASE_API = "/classroomInfoStatistics";

export default {
classroom_info_statistic_b88cfabc_count(data){
  return service({
    url: BASE_API + "/classroom_info_statistic_b88cfabc_count",
    method: "post",
    data:data
  });
},
classroom_info_statistic_ca7982e5_count(data){
  return service({
    url: BASE_API + "/classroom_info_statistic_ca7982e5_count",
    method: "post",
    data:data
  });
},
classroom_info_statistic_b73aeebe_count(data){
  return service({
    url: BASE_API + "/classroom_info_statistic_b73aeebe_count",
    method: "post",
    data:data
  });
},
classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count(data){
  return service({
    url: BASE_API + "/classroom_info_building_enum_building_enum_id_1_status_statistic_85cdeae2_count",
    method: "post",
    data:data
  });
},
classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count(data){
  return service({
    url: BASE_API + "/classroom_info_building_enum_building_enum_id_1_status_statistic_69e0173b_count",
    method: "post",
    data:data
  });
},
classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count(data){
  return service({
    url: BASE_API + "/classroom_info_building_enum_building_enum_id_1_status_statistic_70a96f89_count",
    method: "post",
    data:data
  });
},
};
