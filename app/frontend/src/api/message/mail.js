import service from "@/utils/request";

export default {
    send(params = {}) {
        const {title, content, to, param = {}} = params

        return service({
            url: '/common/mail/send',
            method: "post",
            data: {
                title: title,
                content: content,
                mail: to,
                params: param
            }
        }).then((res) => {
            return {success: true, code: 0, message: "ok"};
        });
    }
}