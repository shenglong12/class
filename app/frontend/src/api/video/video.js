import service from "@/utils/request";

const BASE_API = "/api";

export default {
    text2video(params = {}) {
        const {prompt} = params
        return service({
            url: BASE_API + "/text2video",
            method: "post",
            data: {
                prompt: prompt
            }
        });
    },
    queryVideo(params = {}) {
        const {id} = params
        return service({
            url: BASE_API + "/queryVideo",
            method: "post",
            data: {
                id: id
            }
        });
    }
}