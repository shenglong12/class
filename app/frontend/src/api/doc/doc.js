import service from "@/utils/request";

const BASE_API = "/api";

export default {
    async pdf2image(params = {}) {
        try {
            // 第一步：调用 createTask
            const {url, format} = params
            const res = await this.createTask({url, format});
            console.log("TaskRes", res);
            const taskId = res.data

            // 第二步：轮询 queryTask
            let state = 0
            while (state !== 1) {
                const queryRes = await this.queryTask({taskId})
                console.log("queryTask 返回：", queryRes)

                state = queryRes.data?.state || ""

                if (state === 1) {
                    console.log("完成", queryRes.data)
                    return queryRes
                }

                if (state < 0) {
                    console.log("PDF2ImageTaskStatus：", state)
                    return queryRes
                }

                // 等待 2 秒再查一次
                await new Promise(resolve => setTimeout(resolve, 2000))
            }
        } catch (err) {
            console.error("PDF转图片失败", err)
        }
    },
    createTask(params = {}) {
        const {url, format = "pdf-to-image"} = params
        return service({
            url: BASE_API + `/pdf2image`,
            method: "post",
            data: {
                url,
                format
            }
        });
    },
    queryTask(params = {}) {
        const {taskId} = params
        return service({
            url: BASE_API + `/queryPDF2ImageTask`,
            method: "post",
            data: {
                taskId
            }
        });
    },
}