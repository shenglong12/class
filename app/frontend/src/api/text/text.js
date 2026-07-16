import service from "@/utils/request";

const BASE_API = "/api";
const appId = import.meta.env.VITE_APP_ID

export default {
    text2text(params = {}) {
        const {text, prompt = "", conversationId=""} = params
        return service({
            url: BASE_API + "/text/text2text",
            method: "post",
            data: {
                text: text,
                prompt: prompt,
                conversationId: conversationId
            }
        });
    },
    tts(params = {}) {
        const {text} = params
        return service({
            url: BASE_API + "/text/tts",
            method: "post",
            data: {text: text}
        });
    },
    textTranslate(params = {}) {
        const {from, to, text} = params
        return service({
            url: BASE_API + "/textTransform",
            method: "post",
            data: {
                query: text,
                from: from,
                conversation_id: "",
                to: to,
                user: appId
            }
        });
    }
}