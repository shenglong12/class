/**
 * 弹窗 toast
 * message：提示消息
 * duration： 显示时长
 * level：提示级别，可选值：none,success,error
 * @param param
 * @returns {Promise<unknown>}
 */
export default (param) => {
    const {message = '完成', duration = 3000, level = 'none'} = param;
    const result = {
        success: true,
        message: "成功"
    };
    return new Promise((resolve, reject) => {
        uni.showToast({
            title: message,
            icon: level,
            duration: duration,
            success: () => resolve(result),
            fail: (err) => reject(err)
        })
    })
}