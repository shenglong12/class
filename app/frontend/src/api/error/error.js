import service from "@/utils/request";
export  default {
    /**
     * 上报异常信息
     * @param url
     * @param data
     */
    report(url,data={}){
        return service({
            url: "/error/report",
            method: "post",
            data: data
        }).then(res => {
            // 成功时直接返回接口数据
            return {
                success: true,
                data: res.data
            };
        }).catch(err => {
            console.error(err)
                // 拦截错误，返回统一结构
                return {
                    success: false,
                    error: err.message || '接口请求失败'
                };
            });
    }
}