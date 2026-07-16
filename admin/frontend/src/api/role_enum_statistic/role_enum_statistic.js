// import request from '@/utils/request';

import service from "@/utils/request";
const BASE_API = "/roleEnumStatistics";

export default {
role_enum_role_enum_id_status_statistic_d3cd406e_count(data){
  return service({
    url: BASE_API + "/role_enum_role_enum_id_status_statistic_d3cd406e_count",
    method: "post",
    data:data
  });
},
role_enum_role_enum_id_status_statistic_dec1eb62_count(data){
  return service({
    url: BASE_API + "/role_enum_role_enum_id_status_statistic_dec1eb62_count",
    method: "post",
    data:data
  });
},
role_enum_role_enum_id_status_statistic_b83c5d21_count(data){
  return service({
    url: BASE_API + "/role_enum_role_enum_id_status_statistic_b83c5d21_count",
    method: "post",
    data:data
  });
},
};
