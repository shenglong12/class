import service from "@/utils/request";

export default {
    logout(params = {}) {
        return service({
            url: "/logout",
            method: "get",
        }).then(res => {
            // 接口调用成功后，再清除token
            uni.removeStorageSync("h5_token");
            return res; // 将结果返回给调用者
        }).catch(err => {
            return Promise.reject(err); // 保持错误状态
        });
    }
}