import request from '@/utils/request';
import {ElLoading, ElMessage} from "element-plus";
import {blobValidate} from "@/utils/ruoyi.js";
import errorCode from "@/utils/errorCode.js";
let downloadLoadingInstance;

const BASE_API = "/login_manger";
export default {
    page(query) {
        return request({
            url: BASE_API + "/page",
            method: "post",
            data: query
        });
    },
    get(id) {
        return request({
            url: BASE_API + "/get/" + id,
            method: "get"
        });
    },
    add(data) {
        return request({
            url: BASE_API + "/add",
            method: "post",
            data: data
        });
    }, update(data) {
        return request({
            url: BASE_API + "/update",
            method: "put",
            data: data
        });
    },
    delete(id) {
        return request({
            url: BASE_API + "/delete/" + id,
            method: "delete"
        });
    },
    relevance_all() {
        return request({
            url: BASE_API + "/relevance/all",
            method: "get"
        });
    },
    relevance_get_value(data) {
        return request({
            url: BASE_API + "/relevance/get_value",
            method: "get",
            params: data
        });
    },
    update_password(data){
        return request({
            url: BASE_API + "/update_password",
            method: "post",
            data: data
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
    import(formData) {
        return request({
            url: BASE_API + '/import',
            method: 'post',
            data: formData, // 确保这里传递的是 formData
            headers: {
                'Content-Type': 'multipart/form-data' // 明确设置请求头
            }
        })
    },
};