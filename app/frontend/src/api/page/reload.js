/**
 * 刷新当前页面，没有返回值
 * @param param
 */

export default () => {
    // 获取当前页面栈
    const pages = getCurrentPages()
    const curPage = pages[pages.length - 1]

// 获取当前页面的路径和参数
    const route = curPage.route
    const options = curPage.options || {} // 页面参数

// 构建 query 参数字符串
    const query = Object.keys(options)
        .map(key => `${key}=${encodeURIComponent(options[key])}`)
        .join('&')

    console.log('refresh', route, query)
    return new Promise((resolve, reject) => {
        uni.redirectTo({
            url: `/${route}${query ? '?' + query : ''}`
            , success: () => {
                resolve({
                    success: true
                })
            },
            fail: () => {
                reject(new Error('刷新失败'))
            }
        })
    })
}