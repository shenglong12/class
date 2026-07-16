/**
 * 全局变量
 */
export default {
    /**
     * 全局变量的读取
     * @param param,如果读取一个不存在的变量，则会执行reject方法
     */
    read(param) {
        if (!param) {
            param = {}
        }
        const {variableName} = param

        return new Promise((resolve, reject) => {
            if (variableName == null) {
                reject(new Error('variableName不能为空'))
            }
            uni.getStorage({
                key: variableName,
                success: (res) => {
                    resolve({
                        success: true,
                        value: res.data
                    })
                },
                fail: (err) => reject(err)
            })
        })

    },

    /**
     * 全局变量的写入
     * @param param
     * @param param.variableName 要保存的变量名
     * @param param.value 要保存的值
     */
    write(param) {
        if (!param) {
            param = {}
        }
        const { variableName, value } = param

        return new Promise((resolve, reject) => {
            if (variableName == null) {
                reject(new Error('variableName不能为空'))
            }
            uni.setStorage({
                key: variableName,
                data: value,
                success: () => resolve({
                    success: true
                }),
                fail: (err) => reject(err)
            })
        })
    },

    /**
     * 全局变量的删除
     * @param param
     * @param param.variableName 要删除的变量名
     */
    remove(param) {
        const { variableName } = param

        return new Promise((resolve, reject) => {
            if (variableName == null) {
                reject(new Error('variableName不能为空'))
            }
            uni.removeStorage({
                key: variableName,
                success: () => resolve({
                    success: true
                }),
                fail: (err) => reject(err)
            })
        })
    }
}