import home_page_name from "../config/home_condfig";

const defaultType = "page";

/**
 * 打开外部链接
 * @param url
 */
function openExternalUrl(url) {
    if (!url) {
        uni.showToast({title: '无效的链接', icon: 'none'});
        return;
    }

    // #ifdef H5
    window.location.href = url;
    // #endif

    // #ifdef APP-PLUS
    plus.runtime.openURL(url);
    // #endif

    // #ifdef MP
    uni.showModal({
        title: '提示',
        content: '当前平台不支持打开外部链接',
        showCancel: false
    });
    // #endif
}


export default {

    /**
     * 可取值
     *  url:要打开的链接
     *  type：打开链接的类型 page，应用内页面跳转,external 打开外部的url链接，(例如，https://www.baidu.com等)
     * @param param
     * @returns {Promise<void>}
     */
    to(param) {
        console.log(home_page_name, "1`111111");
        let {
            url = '',
            type = defaultType , // 默认跳转类型
            mode='navigate'
        } = param;

        return new Promise((resolve, reject) => {
            if (!url) {
                uni.showToast({title: '请提供链接地址', icon: 'none'});
                reject(new Error('链接地址为空'));
                return;
            }

            switch (type) {
                case 'page':
                    if (!url.includes("/" + home_page_name + "/")) {
                        let urlParts = url.split("/");
                        for (let i = 0; i < urlParts.length; i++) {
                            if (urlParts[i] === 'pages') {
                                urlParts[i] = 'pagesA'
                            }
                        }
                        // 2. 处理多余的 index
                        const newParts = [];
                        for (let i = 0; i < urlParts.length; i++) {
                            const part = urlParts[i];

                            if (part === "index") {
                                // 计算从 i 开始连续 index 的长度
                                let j = i;
                                while (j < urlParts.length && urlParts[j] === "index") j++;
                                const runLen = j - i;

                                // 保留数量：最多保留 2 个（根据需要也可以改成 1 或者保持全部）
                                const keep = Math.min(runLen, 2);
                                for (let k = 0; k < keep; k++) newParts.push("index");

                                // 跳过已处理的 index
                                i = j - 1;
                            } else {
                                newParts.push(part);
                            }
                        }
                        // 只获取一个index
                        url = urlParts.join("/");
                    }
                    if (mode === 'redirect'){
                        uni.redirectTo({
                            url,
                            success: () => resolve({
                                success: true
                            }),
                            fail:(err) => reject(err)
                        })
                    }else {
                        uni.navigateTo({
                            url,
                            success: () => resolve({
                                success: true
                            }),
                            fail: (err) => reject(err)
                        });
                    }

                    break;
                case 'external':
                    try {
                        openExternalUrl(url);
                        resolve({
                            success: true
                        });
                    } catch (e) {
                        console.error(e)
                        reject(e);
                    }
                    break;
                default:
                    uni.showModal({
                        title: '提示',
                        content: '无效的跳转类型',
                        showCancel: false
                    });
                    reject(new Error('无效的跳转类型'));
            }
        });
    },
    /**
     * 返回上一页面
     * @returns {Promise<void>}
     */
    back() {
        return new Promise((resolve, reject) => {
            uni.navigateBack({
                success: () => resolve(),
                fail: (err) => reject(err)
            });
        })
    },

}
