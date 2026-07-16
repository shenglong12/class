import service from "@/utils/request";

export default {
    loginPasswd(data) {
        return service({
            url: "/login/passwd",
            method: "post",
            data: data
        });
    },
    getLoginUser() {
        return service({
            url: "/getUserInfo",
            method: "get",
        });
    },
    getCodeByPhone(phone) {
        return service({
            url: "/login/sms/code?phone=" + phone,
            method: "get",

        });
    },
    loginBySms(data) {
        return service({
            url: "/login/sms",
            method: "post",
            data: data
        });
    },
    getOpenIdByCode(code) {
        return service({
            url: "/login/openid?code=" + code,
            method: "get",
        });
    },
    getMpUrl() {
        return service({
            url: "/get_mp_url",
            method: "post",
        });
    },
    sendEmailCode(params) {
        const {email} = params;
        return service({
            url: "/login/mail/sendCode",
            method: "post",
            data: {
                phone: email
            }
        });
    },
    loginByMail(params) {
        const {email} = params;
        return service({
            url: "/login/mail",
            method: "post",
            data: {
                phone: email,
                ...params
            }
        });
    },
    loginWechatAuthUri() {
        return service({
            url: "/login/wechat",
            method: "get",
        });
    },
    loginByWechat(params) {
        return service({
            url: "/login/wechat",
            method: "post",
            data: params
        });
    },
    loginByWeApp(params) {
        return service({
            url: "/login/weapp",
            method: "post",
            data: params
        });
    },
};