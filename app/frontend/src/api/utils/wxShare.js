import get_page_title from "../config/get_page_title";

export default {
    data() {
        return {
            share: {
                // 转发的标题 （默认标题）
                title: '默认标题--分享标题',
                // 默认是当前页面，必须是以‘/’开头的完整路径
                path: ''
            }
        }
    },
    // 发送给朋友
    onShareAppMessage(res) {
        // 获取加载的页面
        let pages = getCurrentPages(),
            // 获取当前页面的对象
            view = pages[pages.length - 1];
        //分享的页面路径
        this.share.path = `/${view.route}`;
        this.share.title=get_page_title(view.route)
        //转发参数
        return this.share;
    },
    //分享到朋友圈
    onShareTimeline(res) {
        // 获取加载的页面
        let pages = getCurrentPages(),
            // 获取当前页面的对象
            view = pages[pages.length - 1];
        // console.log("获取加载的页面", pages);
        //console.log("当前页面的对象", view);
        this.share.path = `/${view.route}`;
        this.share.title = get_page_title(view.route)
        //转发参数
        return this.share;
    },

}
