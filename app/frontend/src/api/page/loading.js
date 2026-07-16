export default {
    showLoading(param={}) {
        uni.showLoading({
            title: param.title || '加载中',
        })
    },


    hideLoading() {
        uni.hideLoading()
    }
}