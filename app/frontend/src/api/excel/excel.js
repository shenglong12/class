import service from "@/utils/request";

const BASE_API = "/api/data";

export default {
    export(params) {
        const {table_name, param = {}} = params;
        // #ifdef H5
        return service({
            url: BASE_API + `/export?table=${table_name}`,
            method: 'post',
            data: param,
            responseType: 'blob'
        }).then(response => {
            console.log("response", response);
            const blob = new Blob([response], {
                type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            })
            const link = document.createElement('a')
            link.href = URL.createObjectURL(blob)
            const uuid = crypto.randomUUID().replace(/-/g, "");
            link.download = `${table_name}_${uuid}.xlsx`
            link.click()
        })
        // #endif


        // #ifdef MP-WEIXIN
        // 微信小程序环境
        return new Promise((resolve, reject) => {
            uni.downloadFile({
                url: BASE_API + `/export?table=${table_name}`,
                formData: param,
                success: (res) => {
                    if (res.statusCode === 200) {
                        // 自动打开文件
                        uni.openDocument({
                            filePath: res.tempFilePath,
                            success: () => {
                                console.log('打开文档成功')
                                resolve()
                            },
                            fail: reject
                        })
                    } else {
                        reject('下载失败')
                    }
                },
                fail: reject
            })
        })
        // #endif

        // #ifdef APP-PLUS
        // App 原生环境（安卓/IOS）
        return new Promise((resolve, reject) => {
            const downloadTask = uni.downloadFile({
                url: BASE_API + `/export?table=${table_name}`,
                formData: param,
                success: (res) => {
                    if (res.statusCode === 200) {
                        uni.openDocument({
                            filePath: res.tempFilePath,
                            success: () => {
                                console.log('打开文档成功')
                                resolve()
                            },
                            fail: reject
                        })
                    } else {
                        reject('下载失败')
                    }
                },
                fail: reject
            })
        })
        // #endif
    }
}