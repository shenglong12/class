import request from '@/utils/request';

const BASE_API = "/userInfoAll";
    import {saveAs} from 'file-saver'
import {ElLoading, ElMessage} from "element-plus";
import errorCode from "@/utils/errorCode.js";
import {blobValidate} from "@/utils/ruoyi.js";
let downloadLoadingInstance;
export default {
  page(query) {
    return request({
      url: BASE_API + "/page",
      method: "post",
      data: query
    });
  },
  get(id){
    return request({
      url: BASE_API + "/get/" + id,
      method: "get"
    });
  },
  list(query) {
    return request({
      url: BASE_API + "/list",
      method: "post",
      data: query
    });
  },

get_role_enum_list(){
  return request({
    url: BASE_API + "/get/role_enum_list",
    method: "get"
  });
},

get_user_status_enum_list(){
  return request({
    url: BASE_API + "/get/user_status_enum_list",
    method: "get"
  });
},


downloadExcel(params, filename, url, method) {
    downloadLoadingInstance = ElLoading.service({text: "正在下载数据，请稍候", background: "rgba(0, 0, 0, 0.7)",})
    return request({
      url: BASE_API + "/" + url,
      method: method,
      responseType: 'blob',
      data: params
    }).then(async (data) => {
      const isBlob = blobValidate(data);
      if (isBlob) {
        const blob = new Blob([data])
        saveAs(blob, filename)
      } else {
        const resText = await data.text();
        const rspObj = JSON.parse(resText);
        const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
        ElMessage.error(errMsg);
      }
      downloadLoadingInstance.close();
    }).catch((r) => {
      console.error(r)
      ElMessage.error('下载文件出现错误，请联系管理员！')
      downloadLoadingInstance.close();
    })
  },
};
