// #ifdef MP-WEIXIN || APP-PLUS || APP-HARMONY
import home_page_name from "../api/config/home_condfig";

let baseurl = import.meta.env.VITE_APP_SERVICE_API;
// #endif

// #ifdef H5
import home_page_name from "../api/config/home_condfig";
let baseurl = import.meta.env.VITE_APP_BASE_API;
// #endif
// #ifdef H5
import axios from 'axios';


// 创建 axios 实例
const service = axios.create({
    baseURL: baseurl,
    timeout: 50000,
    headers: {
        'Content-Type': 'application/json;charset=utf-8',
        APP_ID: import.meta.env.VITE_APP_ID,
        APP_TYPE: import.meta.env.VITE_APP_TYPE
    },
});

const useLogin = import.meta.env.VITE_USE_LOGIN === 'true'
// 请求拦截器
service.interceptors.request.use(
    (config) => {
        if (!config.headers) {
            throw new Error(`Expected 'config' and 'config.headers' not to be undefined`);
        }

        try {
            // 可选：定义 useLogin 函数或移除相关代码
            if (uni.getStorageSync("h5_token")) {
                config.headers.Authorization = 'Bearer ' + uni.getStorageSync("h5_token");
            }
        } catch (e) {
            console.log(e)
        }


        config.headers.APP_ID = import.meta.env.VITE_APP_ID;
        config.headers.APP_TYPE = import.meta.env.VITE_APP_TYPE;
        config.headers.X_Env = import.meta.env.VITE_APP_ENV;

        return config;
    },
    (error) => {
        return Promise.reject(error);
    },
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        const res = response.data;
        const {code, message} = res;
        if (code == null || code == undefined) {
            return res;
        }
        if (code === 0) {
            return res;
        } else {
            if (code === 401 || code === 403) {
                uni.removeStorageSync("h5_token");
                uni.navigateTo({
                    url: `/pages/${home_page_name}/index`
                })
            } else {
                return res;
            }
        }
    },
    (error) => {
        console.log('请求异常：', error);
        if (error.response.status === 401) {
            // localStorage.removeItem("h5_token");
            uni.removeStorageSync("h5_token");
            location.href =
                import.meta.env.VITE_BASE;
        } else {
            uni.showToast({
                title: '网络异常，请稍后再试!',
                icon: "none"
            })
            // ElMessage({
            //     message: '网络异常，请稍后再试!',
            //     type: 'error',
            //     duration: 5 * 1000,
            // });
            return Promise.reject(new Error('Error'));
        }
    },
);
export default service
// #endif
// #ifdef MP-WEIXIN || APP-PLUS || APP-HARMONY


let service = (res) => {
    console.log("加载中", res)
    uni.showLoading({
        title: '加载中',
    })

    let {url, data, method, token} = res
    token = uni.getStorageSync("h5_token")

    // 使用标志位确保 hideLoading 只调用一次
    let loadingHidden = false
    const hideLoadingOnce = () => {
        if (!loadingHidden) {
            loadingHidden = true
            uni.hideLoading()
        }
    }

    return new Promise((resolve, reject) => {
        try {
            uni.request({
                url: baseurl + url,
                // url: url,
                data: data,
                method: method,
                timeout: 18000,
                header: {
                    'content-type': 'application/json',
                    "Authorization": 'Bearer ' + token,
                    "APP_ID": import.meta.env.VITE_APP_ID,
                    "APP_TYPE": import.meta.env.VITE_APP_TYPE,
                    "X_Env": import.meta.env.VITE_APP_ENV,
                },
                success(res) {
                    if (res.data.code == 401) {
                        // uni.showToast({
                        //     title: "请先去绑定手机号吧～",
                        //     icon: "none"
                        // })
                        setTimeout(() => {
                            // uni.navigateTo({
                            //     url: ''
                            // })
                            uni.navigateTo({
                                url: `/pages/${home_page_name}/index`
                            })
                            uni.removeStorage({
                                key: 'h5_token',
                            })
                        }, 800)
                        // 401 也需要 resolve，让前端可以处理
                        resolve(res.data)
                    } else if (res.data.code != 0 && res.data.code != undefined) {
                        uni.showToast({
                            title: res.data.message || '系统出错',
                            icon: "none"
                        })
                        // 即使有错误也要 resolve，让前端可以处理错误响应
                        resolve(res.data)
                    } else {
                        resolve(res.data)
                    }
                },
                fail(err) {
                    console.log(err)
                    if (err.errMsg == "request:fail timeout") {
                        uni.showToast({
                            title: "当前网络状态不佳请刷新重试",
                            icon: "none"
                        })
                    }
                    reject(err)
                },
                complete() {
                    hideLoadingOnce()
                }
            })
        } catch (error) {
            // 如果 uni.request 调用前就抛出异常，确保隐藏 loading
            hideLoadingOnce()
            reject(error)
        }
    }).then(
        result => {
            // 成功时确保隐藏 loading（虽然 complete 已经处理，但双重保险）
            hideLoadingOnce()
            return result
        },
        error => {
            // 失败时确保隐藏 loading（虽然 complete 已经处理，但双重保险）
            hideLoadingOnce()
            return Promise.reject(error)
        }
    )
}
export default service
// #endif
