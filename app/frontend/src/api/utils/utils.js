/**
 * 随机工具类
 */
export default  {
    /**
     * 生成随机数
     * @param
     * @param
     * @param
     * @returns {{success: boolean, value: (number|number)}}
     */
    random_number(param={}) {
        const {min = 0, max = 100, isInteger = true} = param
        const raw = Math.random() * (max - min) + min;
        const value = isInteger ? Math.floor(raw) : raw;
        return value;

    },

    /**
     * 生成随机字符串
     * @param
     * @returns {{success: boolean, value: string}}
     */
    random_string(param={}) {
        const {length = 30} = param
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        let result = '';
        for (let i = 0; i < length; i++) {
            result += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return result;
    }



}