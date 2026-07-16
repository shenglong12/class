/**
 * 调用组件的方法
 * @param refName 组件的 ref属性的值
 * @param action 组件的方法名
 * @param parameters 组件方法的参数
 * @returns {Promise<unknown>}
 * 要求被调用的组件必须具备 ref属性，并且方法名必须和组件的方法名一致
 */
export default ({refName, action, parameters = {}}) => {
    return new Promise((resolve, reject) => {
        if (!refName || !action) {
            const msg = 'refName 和 action 是必须的'
            console.warn(msg)
            return reject(new Error(msg))
        }

        const currentPage = getCurrentPages()?.slice(-1)[0]
        const componentInstance = currentPage?.$vm?.$refs?.[refName]

        if (!componentInstance) {
            const msg = `未找到组件实例：${refName}`
            console.warn(msg)
            return reject(new Error(msg))
        }

        const method = componentInstance[action]

        if (typeof method !== 'function') {
            const msg = `组件 ${refName} 没有方法 ${action}`
            console.warn(msg)
            return reject(new Error(msg))
        }

        try {
            const result = method.call(componentInstance, parameters)
            // 如果方法返回的是 Promise，就等待它
            if (result instanceof Promise) {
                result.then(res => {
                    if (res !== null && res !== undefined) {
                        const resultPromise = {
                            success: true,
                            data: res
                        }
                        resolve(resultPromise)
                    } else {
                        resolve({
                            success: true,
                            data: null
                        })
                    }

                }).catch(reject)
            } else {
                if (result === null || result === undefined) {
                    resolve({
                        success: true,
                        data: result
                    })
                } else {
                    resolve({
                        success: true,
                        data: null
                    })
                }

            }
        } catch (err) {
            console.error(`调用组件 ${refName} 的方法 ${action} 出错`, err)
            reject(err)
        }
    })
}
