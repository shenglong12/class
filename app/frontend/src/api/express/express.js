import service from "@/utils/request";

const BASE_API = "/api";

export default {
    inquiry(params = {}) {
        const {com, num, phone} = params
        if (com && (com === 'shunfeng' || com === 'shunfengkuaiyun')) {
            throw new Error('必须提供手机号参数');
        }
        return service({
            url: BASE_API + "/expressInquiry",
            method: "post",
            data: {
                com,
                num,
                "resultv2": 4,
                ...(phone ? {phone} : null)
            }
        });
    },
    mapTrajectory(params = {}) {
        const {com, num, form, to, phone} = params
        if (com && (com === 'shunfeng' || com === 'shunfengkuaiyun')) {
            throw new Error('必须提供手机号参数');
        }
        return service({
            url: BASE_API + "/expressMap",
            method: "post",
            data: {
                com,
                num,
                form,
                to,
                "resultv2": 5,
                ...(phone ? {phone} : null)
            }
        });
    }
}