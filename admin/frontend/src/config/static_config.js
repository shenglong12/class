const STATIC_URL = import.meta.env.VITE_STATIC_URL

const get_resource_url = (path) => {
    let base_url=STATIC_URL
    if (base_url === undefined || base_url === '' || base_url == null) {
        base_url  = 'https://kuafuai.obs.cn-east-3.myhuaweicloud.com/h5_vue_template/'
    }
    if (base_url){
        return base_url + path
    }
    return path

}
export default get_resource_url