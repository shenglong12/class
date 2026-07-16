import home_page_name from "../config/home_condfig";

/**
 * 处理分享路径（保持与路由跳转一致的路径处理逻辑）
 * @param {string} path 原始路径
 * @returns {string} 处理后的路径
 */
function processSharePath(path) {
    if (!path.includes(`/${home_page_name}/`)) {
        let urlParts = path.split('/');

        // 1. 替换pages为pagesA（与路由跳转逻辑一致）
        for (let i = 0; i < urlParts.length; i++) {
            if (urlParts[i] === 'pages') {
                urlParts[i] = 'pagesA';
            }
        }

        // 2. 处理多余的index（与路由跳转逻辑一致）
        let lastIndex = urlParts.lastIndexOf('index');
        if (lastIndex !== -1) {
            while (lastIndex > 1 && urlParts[lastIndex] === 'index' && urlParts[lastIndex - 1] === 'index') {
                urlParts.splice(lastIndex, 1);
                lastIndex--;
            }
        }

        path = urlParts.join('/');
    }

    // 确保路径以/开头
    if (!path.startsWith('/')) {
        path = '/' + path;
    }

    return path;
}

/**
 * 封装微信分享功能（好友+朋友圈）
 * @param {Object} options 分享配置
 * @param {string} options.title 分享标题
 * @param {string} options.path 页面路径（用于好友分享）
 * @param {string} options.query 查询参数（用于朋友圈分享）
 * @param {string} options.imageUrl 分享图片（可选）
 */
const useShare = (options) => {
    // 处理路径（应用与路由跳转相同的逻辑）
    const processedPath = processSharePath(options.path || '/pages/index/index');

    // 好友分享
    const onShareAppMessage = () => ({
        title: options.title || '',
        path: processedPath,
        imageUrl: options.imageUrl || ''
    });

    // 朋友圈分享（仅微信小程序）
    const onShareTimeline = () => ({
        title: options.title || '',
        query: options.query || '',
        imageUrl: options.imageUrl || ''
    });

    return {
        onShareAppMessage,
        onShareTimeline
    };
};

export default useShare