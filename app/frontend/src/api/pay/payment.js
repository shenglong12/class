import service from "@/utils/request";

const BASE_API = "/generalOrder/";

export default {
    /**
     * 获取订单的操作id
     * @param params
     * @returns {{success: boolean, message: string}|*}
     */
    getUniqueOrderNo(params = {}) {
        // 获取订单的操作id

        const {productId, userId} = params
        if (!userId) {
            return {
                "success": false,
                "message": "productId或userId均不能为空"
            }
        }
        // 创建预支付订单
        return service({
            url: BASE_API + `getUniqueOrderNo`,
            method: "post",
            data: params
        });
    },
    /**
     * 创建预支付订单
     * @param params
     * @returns {Promise<any>}
     */
    async createOrders(params = {}) {
        // 获取订单的操作id
        let res = await this.getUniqueOrderNo({
            'productId': params.productId,
            'userId': params.userId
        })
        console.log(res,  'res')
        if (!res.success) {
            return res
        }

        params.opId = res.data
        // 创建预支付订单
        return service({
            url: BASE_API + `create`,
            method: "post",
            data: params
        });
    },

    /**
     * 获取订单参数
     * @param param
     * @returns {*}
     */
    getPaymentParam(param={}){
        return service({
            url: BASE_API + `getPaymentParam`,
            method: "post",
            data: param
        });
    },


    /**
     * 发货
     */
    deliver(param={}){
        return service({
            url: BASE_API + `deliver`,
            method: "post",
            data: param
        });
    },


    /**
     * 确认收货
     */
    confirmReceipt(param={}){
        return service({
            url: BASE_API + `confirm`,
            method: "post",
            data: param
        });
    },
    /**
     * 获取支付单的详细参数
     * @param param
     * @returns {*}
     */
    getPayOrderMessage(param={}){
        return service({
            url: BASE_API + `getPayOrderMessage`,
            method: "post",
            data: param
        });
    },
    /**
     * 取消支付
     *
     */
    cancelPay(param={}){
        return service({
            url: BASE_API + `cancelPay`,
            method: "post",
            data: param
        });
    },

    payMethod(){
        return service({
            url: BASE_API + `payMethod`,
            method: "get"
        });
    }

}