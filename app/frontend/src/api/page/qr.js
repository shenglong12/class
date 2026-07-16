/**
 * 扫码
 */

import {Html5Qrcode} from 'html5-qrcode';


export default {
    /**
     * 扫描二维码，仅在小程序环境下支持
     *
     *
     * @returns {Promise<unknown>}
     */
    scanCode(param) {
        if (!param) {
            param = {}
        }
        const {onlyFromCamera = false, scanType = ['qrCode']} = param
        return new Promise((resolve, reject) => {
            // #ifdef MP-WEIXIN || APP-PLUS
            uni.scanCode({
                onlyFromCamera,
                scanType,
                success: (res) => resolve({
                    success:true,
                    result:res.result
                }),
                fail: (err) => reject(err)
            });
            // #endif

            // #ifdef H5
            // uni.showLoading()
            // 创建隐藏的容器（即使不用摄像头扫描也需要）
            const containerId = 'html5qr-container-hidden';
            let container = document.getElementById(containerId);
            if (!container) {
                container = document.createElement('view');
                container.id = containerId;
                container.style.display = 'none'; // 隐藏容器
                document.body.appendChild(container);
            }

            const input = document.createElement('input');
            input.type = 'file';
            input.accept = 'image/*';


            // 处理文件选择
            input.onchange = async (e) => {
                uni.showLoading()
                const file = e.target.files[0];
                if (!file) {
                    reject(new Error('未选择文件'));
                    return;
                }

                try {
                    // 使用 html5-qrcode 解析二维码
                    const html5QrCode = new Html5Qrcode(containerId);
                    const result = await html5QrCode.scanFile(file, true);
                    resolve({
                        success:true,
                        result:result
                    });
                } catch (err) {
                    reject(new Error(`二维码解析失败: ${err}`));
                } finally {
                    uni.hideLoading()
                }
            };

            // 主动触发文件选择
            input.click();
            // #endif
        });
    }
}