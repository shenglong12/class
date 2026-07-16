const STATIC_URL = import.meta.env.VITE_APP_SERVICE_API

const get_resource_url = (path) => {

    if (typeof path === 'object' && path !== null && !Array.isArray(path)) {
        // path 是一个对象（且不是数组，也不是 null）
        if ("url" in path) {
            path = path.url
        }
    }
    if (path) {
        if (path.startsWith("https://") || path.startsWith("http://")) {
            return path;
        }
    }

    let base_url = STATIC_URL

    // #ifdef MP-WEIXIN
    if (base_url) {
        return base_url + path
    }
    // #endif

    // #ifdef H5
    // if (base_url) {
    //     return base_url + path
    // }
    // #endif
    return path

}
export default get_resource_url