<template>
  <view v-if="visible" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
    <view class="bg-white rounded-2xl w-4/5 max-w-md p-4 shadow-lg">
      <view class="text-xl font-bold text-center mb-4 text-gray-800">
        支付金额：￥{{ price }}
      </view>

      <radio-group @change="onRadioChange">
        <view class="space-y-2 mb-4">
          <label
              v-for="method in payMethods"
              :key="method.value"
              class="flex items-center justify-between p-3 rounded-lg border"
              :class="selectedMethod === method.value ? 'border-primary-500 bg-primary-50' : 'border-gray-300'"
          >
            <view v-if="method.enable" class="flex items-center">
              <text class="text-gray-700">{{ method.label }}</text>
              <radio
                  class="text-primary-500"
                  :value="method.value"
                  :checked="selectedMethod === method.value"
              />
            </view>
          </label>
        </view>
      </radio-group>

      <button
          class="w-full py-3 bg-primary-500 text-white text-center text-lg rounded-xl mb-2 hover:bg-primary-600 transition-colors"
          @click="handlePay"
      >
        支付
      </button>

      <button
          class="w-full py-2 text-gray-500 text-center text-base hover:text-gray-700 transition-colors"
          @click="hide"
      >
        取消
      </button>
    </view>
  </view>
  </template>

<script setup>
import {ref, watch, defineExpose} from 'vue'
import {onMounted} from 'vue'

const {proxy} = getCurrentInstance();
// Props
const props = defineProps({
  price: {
    type: Number,
    required: true
  },
  defaultChannel: {
    type: String,
    default: 'mock'
  },
  productId: {
    type: [String, Number],
    required: true
  },
  userId: {
    type: [String, Number],
    required: true
  },
  payChanel: {
    type: String,
    default: ''
  },
  quality: {
    type: Number,
    default: 1
  },
  priceSingle: {
    type: Number,
    default: 0
  },
  remark: {
    type: String,
    default: ''
  },
  productSubject: {
    type: String,
    default: ''
  },
  tel: {
    type: String,
    default: ''
  },
  recipient: {
    type: String,
    default: ''
  },
  shippingAddress: {
    type: String,
    default: ''
  },
  orderTableName: {
    type: String,

  },
  orderTypeFiled: {
    type: String,
  },
  orderNo: {
    type: String,
  },
  appId:{
    type: String,
    default: import.meta.env.VITE_APP_ID,
  }
})

import service from "@/utils/request";

// 状态
const visible = ref(false)
const selectedMethod = ref(props.defaultChannel)
const payMethods = ref([
  {label: '虚拟支付(发布上线后可以连接到真实的在线支付系统)', value: 'mock', enable: true},
  {label: '微信支付', value: 'wechat', enable: false},
  {label: '支付宝', value: 'alipay', enable: false}
])

onLoad(async () => {
  let res = await proxy.$cf.payment.payMethod()
  if (!res.success) {
    return
  }
  if (res.data.includes('wechat')) {
    payMethods.value[1].enable = true;
  } else {
    payMethods.value[1].enable = false;
  }

  if (res.data.includes('mock')) {
    payMethods.value[0].enable = true;
  } else {
    payMethods.value[0].enable = false;
  }

})

// 控制弹窗显示/隐藏
function show() {
  visible.value = true
  selectedMethod.value = props.defaultChannel
}

function hide() {
  visible.value = false
}

// 单选变化
function onRadioChange(e) {
  selectedMethod.value = e.detail.value
}

function onBridgeReady(param) {
  WeixinJSBridge.invoke(
      'getBrandWCPayRequest',
      {
        ...param
      },
      function (res) {
        console.log(res);
        if (res.err_msg === "get_brand_wcpay_request:ok") {
          // 支付成功
          emit('paySuccess', {
            paymentOrderId: param.paymentOrderId
          });
        } else {
          // 支付失败或取消
          alert("支付失败" + res.err_msg)
          console.log("支付失败：" + res);
        }
      }
  );
}

// 唤起支付前调用这个函数
function callWeChatPay(param) {
  if (typeof WeixinJSBridge === "undefined") {
    console.log("WeixinJSBridge is not defined")
    if (document.addEventListener) {
      console.log("document.addEventListener")
      document.addEventListener('WeixinJSBridgeReady', function () {
        console.log("WeixinJSBridge is defined 1")
        onBridgeReady(param);
      }, false);
    } else if (document.attachEvent) {
      console.log("document.attachEvent")
      document.attachEvent('WeixinJSBridgeReady', function () {
        onBridgeReady(param);
      });
      document.attachEvent('onWeixinJSBridgeReady', function () {
        onBridgeReady(param);
      });
    }
  } else {
    console.log("WeixinJSBridge is defined")
    onBridgeReady(param);
  }
}


/**
 * 唤起支付
 * @param payParam
 */
async function callPay(payParam) {

  if (selectedMethod.value == 'mock') {
    uni.showLoading({
      title: '支付中',
      mask: true
    })
    try {
      let  url=`/generalOrder/callback/${selectedMethod.value}`
      if (props.appId!=null && props.appId!=''){
        url=`/generalOrder/callback/${props.appId}/${selectedMethod.value}`
      }
      // 调用支付回调的接口，返回订单id
      await service({
        url: url,
        method: "post",
        data: payParam.paymentOrderId // 支付系统中的订单id
      })
      uni.hideLoading()
      emit('paySuccess', {
        // amount: props.price,
        // method: selectedMethod.value
        'paymentOrderId': payParam.paymentOrderId
      })
    } catch (e) {
      console.error(e)
      uni.hideLoading()
    }

  } else if (selectedMethod.value == 'wechat') {
    console.log("执行")
    //#ifdef MP-WEIXIN
    console.log("执行1")
    uni.requestPayment({
      provider: 'wxpay',
      ...payParam,
      success: (res) => {
        console.log("pay success", res)
        emit('paySuccess', {
          // amount: props.price,
          // method: selectedMethod.value
          'paymentOrderId': payParam.paymentOrderId
        })
      },
      fail: (err) => {
        console.error('pay error', err)
      }
    })
    //#endif
    // #ifdef H5
    console.log("执行2")
    if (isWeixinBrowser()) {
      console.log("微信环境", payParam)

      callWeChatPay(payParam)
    } else {
      console.log("非微信环境")
    }
    //#endif

  }
}

function isWeixinBrowser() {
  return typeof window !== 'undefined' &&
      window.navigator.userAgent.toLowerCase().toString().includes('micromessenger')
}

// 点击支付
async function handlePay() {
  let payChanel = selectedMethod.value;
  //#ifdef MP-WEIXIN
  if (selectedMethod.value == 'wechat') {
    payChanel = 'wx_jsapi'
  }
  // #endIf

  //#ifdef H5
  if (selectedMethod.value == 'wechat') {
    payChanel = 'wx_jsapi_mp'
  }
  // #endIf


  let request_body={
    totalPrice:props.price,
    productId: props.productId,
    userId: props.userId,
    payChanel: payChanel,
    quality: props.quality,
    priceSingle: props.priceSingle,
    remark: props.remark,
    productSubject: props.productSubject,
    tel: props.tel,
    recipient: props.recipient,
    shippingAddress: props.shippingAddress,
    tableName: props.orderTableName,
    fieldName: props.orderTypeFiled,
    orderNo: props.orderNo
  }
  console.log("执行",request_body)
  let res = await proxy.$cf.payment.createOrders(request_body)
  console.log(res,"res====")
  if (!res.success) {
    console.log(res.message,"打印")
    proxy.$cf.toast({
      message: res.message ? res.message:'支付失败,请重试',
      icon: 'none'
    })
    hide();
    return
  }

  let payParam = res.data // 返回支付所需要的参数

  await callPay(payParam)


  hide();
}

// 向父组件暴露 show/hide 方法
defineExpose({
  show,
  hide
})

// 发射事件
const emit = defineEmits(['paySuccess'])
</script>

<style scoped>
/* 可以添加自定义动画或样式 */
</style>
