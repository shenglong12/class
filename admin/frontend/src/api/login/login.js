import request from '@/utils/request';

export default {
    loginWxWeb(data) {
        return request({
            url: "/login/wxWeb",
            method: "post",
            data: data
        });
    },
    loginPasswd(data) {
        return request({
            url: "/login/passwd",
            method: "post",
            data: data
        });
    },
    getUserInfo() {
        return request({
            url: "/getUserInfo",
            method: "get"
        });
    },
    logout() {
        return request({
            url: "/logout",
            method: "get"
        });
    }
};