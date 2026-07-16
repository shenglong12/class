// import request from '@/utils/request';

import service from "@/utils/request";
const BASE_API = "/buildingEnumStatistics";

export default {
building_enum_building_enum_id_status_statistic_146915fe_count(data){
  return service({
    url: BASE_API + "/building_enum_building_enum_id_status_statistic_146915fe_count",
    method: "post",
    data:data
  });
},
building_enum_building_enum_id_status_statistic_060920a4_count(data){
  return service({
    url: BASE_API + "/building_enum_building_enum_id_status_statistic_060920a4_count",
    method: "post",
    data:data
  });
},
building_enum_building_enum_id_status_statistic_f0e4acc5_count(data){
  return service({
    url: BASE_API + "/building_enum_building_enum_id_status_statistic_f0e4acc5_count",
    method: "post",
    data:data
  });
},
};
