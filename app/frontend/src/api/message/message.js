import service from "@/utils/request";

const BASE_API = "/api";

export default {
    feishuRobot(params = {}) {
        if (params.content) {
            params.content = params.content.replace(/\r?\n/g, ' ');
        }
        return service({
            url: BASE_API + `/feishuRobotText`,
            method: "post",
            data: params
        }).then((res) => {
            return {success: true, code: 0, message: "ok"};
        });
    },
    wechatRobot(params = {}) {
        return service({
            url: BASE_API + `/wechatRobotText`,
            method: "post",
            data: params
        }).then((res) => {
            return {success: true, code: 0, message: "ok"};
        });
    }
}