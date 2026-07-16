import service from "@/utils/request";

const BASE_API = "/api";
const appId = import.meta.env.VITE_APP_ID

export default {
    word2pic(params = {}) {
        const {text} = params
        return service({
            url: BASE_API + "/pic/word2pic",
            method: "post",
            data: {text: text}
        });
    },
    pic2word(params = {}) {
        const {url} = params
        return service({
            url: BASE_API + "/pic/pic2word",
            method: "post",
            data: {file: url}
        });
    },
    questionRecognize(params = {}) {
        const {query, url} = params
        if (!params.conversation_id) {
            params.conversation_id = ""
        }
        const files = [{
            type: "image",
            transfer_method: "remote_url",
            url: url
        }]
        return service({
            url: BASE_API + "/questionRecognize",
            method: "post",
            data: {
                query: query,
                conversation_id: params.conversation_id,
                user: appId,
                files: url ? files : []
            }
        });
    },
    ocr(params = {}) {
        const {url} = params
        return service({
            url: BASE_API + "/ocr",
            method: "post",
            data: {file: url}
        });
    }
}