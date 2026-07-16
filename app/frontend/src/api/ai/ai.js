import service from "@/utils/request";

const BASE_API = "/api";
const appId = import.meta.env.VITE_APP_ID
export default {
    text2pic(params = {}) {
        const {text} = params
        return service({
            url: BASE_API + "/pic/word2pic",
            method: "post",
            data: {text: text}
        });
    },
    pic2text(params = {}) {
        const {url, prompt, text, conversation_id = ""} = params
        const files = [{
            type: "image",
            transfer_method: "remote_url",
            url: url
        }]
        return service({
            url: BASE_API + "/AiAnalysis",
            method: "post",
            data: {
                prompt: prompt,
                query: text,
                conversation_id: conversation_id,
                user: appId,
                files: url ? files : []
            }
        });
    },
    text2text(params = {}) {
        const {text, prompt = "", conversationId = ""} = params
        return service({
            url: BASE_API + "/AiAnalysis",
            method: "post",
            data: {
                prompt: prompt,
                query: text,
                conversation_id: conversationId,
                user: appId,
                files: []
            }
        });
    },
    text2tts(params = {}) {
        const {text} = params
        return service({
            url: BASE_API + "/text/tts",
            method: "post",
            data: {text: text}
        });
    },
    async text2video(params = {}) {
        try {
            const {text} = params
            // 第一步：调用 genVideoTaskId
            const res = await this.genVideoTaskId({text})
            const id = res.data

            // 第二步：轮询 queryVideo
            let status = ""
            while (status !== "succeeded") {
                const queryRes = await this.queryVideo({id})
                console.log("queryVideo 返回：", queryRes)

                status = queryRes.data?.status || ""

                if (status === "succeeded") {
                    console.log("生成成功", queryRes.data)
                    return queryRes
                }

                if (status === "failed") {
                    return queryRes
                }

                // 等待 2 秒再查一次
                await new Promise(resolve => setTimeout(resolve, 2000))
            }
        } catch (err) {
            console.error("生成视频失败", err)
        }
    },
    genVideoTaskId(params = {}) {
        const {text} = params
        return service({
            url: BASE_API + "/text2video",
            method: "post",
            data: {
                text: text
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
    },
    async text2music(params={}) {
        try {
            // 第一步：调用 genMusicTaskId
            const {text} = params
            const res = await this.genMusicTaskId({text})
            const id = res.data  // 假设后端返回 {id: "..."}

            // 第二步：轮询 queryMusic
            let status = ""
            while (status !== "succeeded") {
                const queryRes = await this.queryMusic({id})
                console.log("queryMusic 返回：", queryRes)

                status = queryRes.data?.status || ""

                if (status === "succeeded") {
                    console.log("生成成功", queryRes.data)
                    return queryRes
                }

                if (status === "failed") {
                    return queryRes
                }

                // 等待 2 秒再查一次
                await new Promise(resolve => setTimeout(resolve, 2000))
            }
        } catch (err) {
            console.error("生成音乐失败", err)
        }
    },
    genMusicTaskId(params = {}) {
        const {text} = params
        let trim_prompt = text
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
        str = str.toString()
        // 1. 先把换行、回车、制表符替换成空格或逗号
        str = str.replace(/[\n\r\t]+/g, ",")
        // 2. 也可以替换多余的空格
        str = str.replace(/\s{2,}/g, ",")
        return str.trim()
    }
}