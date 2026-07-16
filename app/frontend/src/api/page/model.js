/**
 * 模态对话框 modal
 * title：对话框标题
 * message：提示信息（内容）
 * confirmText：确认按钮文本，默认“确认”
 * cancelText：取消按钮文本，默认“取消”
 * */
export default (param) => {
    if (!param) {
        param = {}
    }
    const {
        title,
        message,
        confirmText = '确认',
        cancelText = '取消'
    } = param;

    return new Promise((resolve, reject) => {
        uni.showModal({
            title,
            content: message,
            confirmText: confirmText,
            cancelText: cancelText,
            success: (res) => {
                console.log(res)
                if (res.confirm) {
                    res['success'] = true
                    resolve(res);
                } else {
                    res['success'] = false
                    resolve(res);
                }
            }, fail: (err) => reject(err)
        });
    });
}