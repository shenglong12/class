import service from "@/utils/request";

const BASE_API = "/api";

export default {
    text2music(params = {}) {
        const {prompt} = params
        let trim_prompt = prompt
        if (trim_prompt) {
            trim_prompt = this.cleanString(trim_prompt)
        }
        return service({
            url: BASE_API + "/text2music",
            method: "post",
            data: {
                prompt: trim_prompt
            }
        });
    },
    queryMusic(params = {}) {
        const {id} = params
        return service({
            url: BASE_API + "/queryMusic",
            method: "post",
            data: {
                item_ids: id
            }
        });
    },
    cleanString(str) {
        if (!str) return str
        // 1. 先把换行、回车、制表符替换成空格或逗号
        str = str.replace(/[\n\r\t]+/g, ",")
        // 2. 也可以替换多余的空格
        str = str.replace(/\s{2,}/g, ",")
        return str.trim()
    }
}