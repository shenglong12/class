<template>
  <base-layout>
    <view class="px-4 py-4 space-y-4">
      <!-- 订单进度与状态 -->
      <uni-card :title="'订单状态：' + (orderDetail.status_name || '--')"
        :sub-title="orderDetail.order_date ? '下单时间：' + orderDetail.order_date : ''" :is-shadow="true"
        class="rounded-2xl border-0 bg-white shadow-lg">
        <view class="flex items-center gap-3 mb-2">
          <uni-icons type="cart" size="24" color="#4f46e5" />
          <text class="text-lg font-semibold text-primary-600">订单号：{{ orderDetail.order_info_id }}</text>
        </view>
        <view class="flex items-center gap-2 mb-1">
          <uni-badge :text="orderDetail.status_name" :type="getStatusType(orderDetail.status_name)" size="normal" />
          <text class="text-gray-500 ml-2" v-if="orderDetail.logistics_info">
            物流：{{ orderDetail.logistics_info }}
          </text>
        </view>
      </uni-card>

      <!-- 商品信息 -->
      <uni-card :title="orderDetail.product_name || '--'" :sub-title="'发布者：' + (orderDetail.seller_username || '--')"
        :is-shadow="true" class="rounded-2xl border-0 bg-white shadow-lg">
        <view class="flex gap-3">
          <image v-if="orderDetail.images && orderDetail.images.length"
            :src="get_resource_url(orderDetail.images[0].url)"
            class="w-24 h-24 rounded-xl object-cover border border-neutral-200" mode="aspectFill" />
          <image v-else src="https://www.codeflying.net/preview/scenery-climbing.jpg"
            class="w-24 h-24 rounded-xl object-cover border border-neutral-200" mode="aspectFill" />
          <view class="flex-1 flex flex-col justify-between">
            <view>
              <text class="text-base font-semibold text-text-primary">￥{{ orderDetail.price || '--' }}</text>
              <text class="text-sm text-gray-600 mt-1">
                数量: {{ orderDetail.quantity || '--' }}
              </text>
              <text class="text-base font-bold text-primary-600 mt-2">
                总价: ￥{{ totalPrice.toFixed(2) }}
              </text>
            </view>
            <view class="flex items-center gap-2 mt-2">
              <uni-tag v-if="orderDetail.hot_tags" :text="orderDetail.hot_tags" type="primary" size="small" circle />
              <uni-tag v-if="orderDetail.category_name" :text="orderDetail.category_name" type="secondary" size="small"
                circle />
            </view>
            <text class="text-xs text-gray-400 mt-2" v-if="orderDetail.description">
              {{ orderDetail.description }}
            </text>
          </view>
        </view>
      </uni-card>

      <!-- 买家卖家信息 -->
      <uni-card title="买卖双方" :is-shadow="true" class="rounded-2xl border-0 bg-white shadow-lg">
        <view class="flex flex-col gap-3">
          <view class="flex items-center gap-2">
            <uni-icons type="person" size="18" color="#6366f1" />
            <text class="font-medium text-text-primary">买家：</text>
            <text class="text-gray-700">{{ orderDetail.buyer_username || '--' }}</text>
          </view>
          <view class="flex items-center gap-2">
            <uni-icons type="person-filled" size="18" color="#4f46e5" />
            <text class="font-medium text-text-primary">卖家：</text>
            <text class="text-gray-700">{{ orderDetail.seller_username || '--' }}</text>
          </view>
        </view>
      </uni-card>

      <!-- 订单操作按钮 -->
      <view class="flex flex-col gap-3 mt-4">
        <button v-if="showPayBtn" class="bg-primary-500 text-white rounded-full font-semibold shadow-md"
          @click="handlePay">
          <uni-icons type="wallet" size="18" color="#fff" class="mr-1" />
          支付订单
        </button>
        <button v-if="showCancelBtn" class="bg-danger-300 text-white rounded-full font-semibold shadow-md"
          @click="handleCancel">
          <uni-icons type="close" size="18" color="#fff" class="mr-1" />
          取消订单
        </button>
        <button v-if="showConfirmBtn" class="bg-success-500 text-white rounded-full font-semibold shadow-md"
          @click="handleConfirm">
          <uni-icons type="checkmarkempty" size="18" color="#fff" class="mr-1" />
          确认收货
        </button>
        <button v-if="showDeliverBtn" class="bg-secondary-500 text-white rounded-full font-semibold shadow-md"
          @click="handleDeliver">
          <uni-icons type="paperplane" size="18" color="#fff" class="mr-1" />
          发货
        </button>
      </view>
    </view>
    <base-pay-panel ref="pay_panel" :price="totalPrice.toFixed(2)" defaultChannel="mock" @paySuccess="paySuccess"
      :productId="orderDetail.product_info_id" :userId="orderDetail.product_info_id"
      :quality="orderDetail.buyer_user_info_id_1" :priceSingle="orderDetail.price" :remark="orderDetail.remark"
      :productSubject="orderDetail.product_name" :tel="orderDetail.mobile" recipient=""
      :shippingAddress="orderDetail.address" orderTableName="order_info" orderTypeFiled="general_orders"
      :orderNo="orderDetail.order_info_id" />
  </base-layout>
</template>

<script setup>
import { onLoad } from '@dcloudio/uni-app';
const { proxy } = getCurrentInstance();


const orderDetail = ref({});
const loginUser = ref({});
const showPayBtn = ref(false);
const showCancelBtn = ref(false);
const showConfirmBtn = ref(false);
const showDeliverBtn = ref(false);

function get_resource_url(url) {
  return proxy.get_resource_url(url);
}

// 计算总价
const totalPrice = computed(() => {
  const price = parseFloat(orderDetail.value.price) || 0;
  const quantity = parseFloat(orderDetail.value.quantity) || 0;
  return price * quantity;
});

// 状态徽章色彩
function getStatusType(status) {
  if (!status) return "default";
  switch (status) {
    case "待支付":
      return "warning";
    case "已支付":
      return "primary";
    case "待发货":
      return "primary";
    case "已发货":
      return "success";
    case "待收货":
      return "primary";
    case "已完成":
      return "success";
    case "已取消":
      return "error";
    default:
      return "default";
  }
}

// 获取订单详情并判断身份与操作权限
async function fetchOrderDetail(order_info_id) {
  // 获取订单详情
  const res = await proxy.$cf.table.get({
    table_name: "order_info",
    param: { order_info_id: Number(order_info_id) }
  });
  if (!res.success) {
    proxy.$cf.toast({ message: "订单信息获取失败", level: "error" });
    return;
  }
  // 平铺quote字段
  const data = res.data || {};

  // 获取商品详情
  let productRes = await proxy.$cf.table.get({
    table_name: "product_info",
    param: { product_info_id: data.product_info_product_info_id_1 }
  });
  let product = productRes.success ? productRes.data : {};

  // 获取买家信息
  let buyerRes = await proxy.$cf.table.get({
    table_name: "user_info",
    param: { user_info_id: data.buyer_user_info_id_1 }
  });
  let buyer = buyerRes.success ? buyerRes.data : {};

  // 获取卖家信息
  let sellerRes = await proxy.$cf.table.get({
    table_name: "user_info",
    param: { user_info_id: data.seller_user_info_id_1 }
  });
  let seller = sellerRes.success ? sellerRes.data : {};

  // 获取订单状态
  let statusRes = await proxy.$cf.table.get({
    table_name: "order_status_enum",
    param: { order_status_enum_id: data.order_status_enum_order_status_enum_id_1 }
  });
  let status = statusRes.success ? statusRes.data : {};

  // 合成展示用数据
  orderDetail.value = {
    ...data,
    ...product,
    buyer_username: buyer.username,
    seller_username: seller.username,
    status_name: status.status_name,
    images: product.images || [],
    price: product.price,
    product_name: product.product_name,
    hot_tags: product.hot_tags,
    category_name: product.category_name,
    description: product.description
  };

  // 获取当前登录用户
  let userRes = await proxy.$cf.login.getLoginUser();
  loginUser.value = userRes.success ? userRes.data : {};
  console.log(orderDetail.value)
  // 判断按钮权限
  setActionBtn();
  orderInit(order_info_id)

}

function setActionBtn() {
  // 当前用户身份
  const uid = loginUser.value.user_info_id;
  const isBuyer = uid == orderDetail.value.buyer_user_info_id_1;
  const isSeller = uid == orderDetail.value.seller_user_info_id_1;
  const status = orderDetail.value.order_status_enum_order_status_enum_id_1;

  showPayBtn.value = isBuyer && status == 1;
  showCancelBtn.value = isBuyer && status == 1;
  showConfirmBtn.value = isBuyer && status == 4;
  showDeliverBtn.value = isSeller && status == 2;
}

async function orderInit(order_info_id) {
  // 查询订单的状态
  let res = await proxy.$cf.payment.getPayOrderMessage({
    'orderNo': orderDetail.value.order_info_id
  })

  if (!res.success) {
    // proxy.$cf.toast({
    //   message: "支付信息不存在",
    //   level: "error"
    // });
    return
  }

  console.log(res,)
  if (res.data == null) {
    return
  }
  let is_reload_page = true
  if (res.data.orderStatus == "PAID_SUCCESS") {
    const payRes = await proxy.$cf.table.update({
      table_name: "order_info",
      param: {
        order_info_id: orderDetail.value.order_info_id,
        order_status_enum_order_status_enum_id_1: 2, // 已支付
        payment_date: getNowDatetime()
      }
    });
  } else if (res.data.orderStatus == "TIMEOUT") {
    const payRes = await proxy.$cf.table.update({
      table_name: "order_info",
      param: {
        order_info_id: orderDetail.value.order_info_id,
        order_status_enum_order_status_enum_id_1: 8, // 已超时
      }
    });
  } else if (res.data.orderStatus == "CONFIRM") {
    const payRes = await proxy.$cf.table.update({
      table_name: "order_info",
      param: {
        order_info_id: orderDetail.value.order_info_id,
        order_status_enum_order_status_enum_id_1: 6, // 订单已经确认收货
      }
    })
  } else if (res.data.orderStatus == "CANCELLED") {
    const payRes = await proxy.$cf.table.update({
      table_name: "order_info",
      param: {
        order_info_id: orderDetail.value.order_info_id,
        order_status_enum_order_status_enum_id_1: 7, // 订单已取消
      }
    })
  } else {
    is_reload_page = false;
  }

  if (is_reload_page) {
    // 获取订单详情
    res = await proxy.$cf.table.get({
      table_name: "order_info",
      param: { order_info_id: Number(order_info_id) }
    });
    if (!res.success) {
      proxy.$cf.toast({ message: "订单信息获取失败", level: "error" });
      return;
    }
    // 平铺quote字段
    const data = res.data || {};

    // 获取商品详情
    let productRes = await proxy.$cf.table.get({
      table_name: "product_info",
      param: { product_info_id: data.product_info_product_info_id_1 }
    });
    let product = productRes.success ? productRes.data : {};

    // 获取买家信息
    let buyerRes = await proxy.$cf.table.get({
      table_name: "user_info",
      param: { user_info_id: data.buyer_user_info_id_1 }
    });
    let buyer = buyerRes.success ? buyerRes.data : {};

    // 获取卖家信息
    let sellerRes = await proxy.$cf.table.get({
      table_name: "user_info",
      param: { user_info_id: data.seller_user_info_id_1 }
    });
    let seller = sellerRes.success ? sellerRes.data : {};

    // 获取订单状态
    let statusRes = await proxy.$cf.table.get({
      table_name: "order_status_enum",
      param: { order_status_enum_id: data.order_status_enum_order_status_enum_id_1 }
    });
    let status = statusRes.success ? statusRes.data : {};

    // 合成展示用数据
    orderDetail.value = {
      ...data,
      ...product,
      buyer_username: buyer.username,
      seller_username: seller.username,
      status_name: status.status_name,
      images: product.images || [],
      price: product.price,
      product_name: product.product_name,
      hot_tags: product.hot_tags,
      category_name: product.category_name,
      description: product.description
    };
    setActionBtn()
  }

  // } else {
  // proxy.$cf.toast({message: "支付失败", level: "error"});
  // }
}

const paySuccess = () => {
  console.log("支付成功")
  orderInit(orderDetail.value.order_info_id)
}

// 操作：支付
async function handlePay() {


  // 查询订单的状态
  let res = await proxy.$cf.payment.getPayOrderMessage({
    'orderNo': orderDetail.value.order_info_id
  })

  if (!res.success) {
    proxy.$cf.toast({
      message: "支付信息不存在",
      level: "error"
    });
  }

  console.log(res)

  //  拉起支付组件，进行支付
  const confirm = await proxy.$cf.model({
    title: "支付订单",
    message: "确定要支付该订单吗？",
    confirmText: "支付",
    cancelText: "取消"
  });
  if (!confirm.confirm) return;

  proxy.$refs.pay_panel.show()


  // // 支付接口（模拟直接更新订单状态为已支付）
  // const payRes = await proxy.$cf.table.update({
  //   table_name: "order_info",
  //   param: {
  //     order_info_id: orderDetail.value.order_info_id,
  //     order_status_enum_order_status_enum_id_1: 2, // 已支付
  //     payment_date: getNowDatetime()
  //   }
  // });
  // if (payRes.success) {
  //   proxy.$cf.toast({message: "支付成功", level: "success"});
  //   fetchOrderDetail(orderDetail.value.order_info_id);
  // } else {
  //   proxy.$cf.toast({message: "支付失败", level: "error"});
  // }
}

// 操作：取消订单
async function handleCancel() {
  const confirm = await proxy.$cf.model({
    title: "取消订单",
    message: "确定要取消该订单吗？",
    confirmText: "取消订单",
    cancelText: "返回"
  });
  if (!confirm.confirm) return;
  let res = await proxy.$cf.payment.cancelPay({
    orderNo: orderDetail.value.order_info_id
  })
  console.log(res, "15698612620")
  if (!res.success) {
    proxy.$cf.toast({ message: res.data | "取消订单失败", level: "error" });
    return
  }
  // 取消接口（更新订单状态为已取消）
  const cancelRes = await proxy.$cf.table.update({
    table_name: "order_info",
    param: {
      order_info_id: orderDetail.value.order_info_id,
      order_status_enum_order_status_enum_id_1: 7 // 已取消
    }
  });
  console.log(cancelRes, "cancelRes")
  if (cancelRes.success) {
    proxy.$cf.toast({ message: "订单已取消", level: "success" });
    fetchOrderDetail(orderDetail.value.order_info_id);
  } else {
    proxy.$cf.toast({ message: "取消失败", level: "error" });
  }
}

// 操作：确认收货
async function handleConfirm() {
  const confirm = await proxy.$cf.model({
    title: "确认收货",
    message: "请确认已收到商品后再操作，确定收货？",
    confirmText: "确认收货",
    cancelText: "返回"
  });
  if (!confirm.confirm) return;

  // 执行去人收货的api
  let res = await proxy.$cf.payment.confirmReceipt({
    orderNo: orderDetail.value.order_info_id
  })
  if (!res.success) {
    proxy.$cf.toast({ message: res.message | "确认收货失败", level: "error" });
    return
  }
  // 更新订单状态为已完成，设置收货时间
  const confirmRes = await proxy.$cf.table.update({
    table_name: "order_info",
    param: {
      order_info_id: orderDetail.value.order_info_id,
      order_status_enum_order_status_enum_id_1: 6, // 已完成
      receipt_date: getNowDatetime()
    }
  });
  if (confirmRes.success) {
    proxy.$cf.toast({ message: "收货成功，订单已完成", level: "success" });
    fetchOrderDetail(orderDetail.value.order_info_id);
  } else {
    proxy.$cf.toast({ message: "收货失败", level: "error" });
  }
}

// 操作：发货
async function handleDeliver() {
  const confirm = await proxy.$cf.model({
    title: "发货",
    message: "确认已发货？",
    confirmText: "确认发货",
    cancelText: "返回"
  });
  if (!confirm.confirm) return;

  // 调用发货的api
  let res = await proxy.$cf.payment.deliver({
    orderNo: orderDetail.value.order_info_id
  })
  if (!res.success) {
    proxy.$cf.toast({ message: res.message | '发货失败', level: "error" });
    return
  }

  // 更新订单状态为已发货，设置发货时间
  const deliverRes = await proxy.$cf.table.update({
    table_name: "order_info",
    param: {
      order_info_id: orderDetail.value.order_info_id,
      order_status_enum_order_status_enum_id_1: 4, // 已发货
      delivery_date: getNowDatetime()
    }
  });
  if (deliverRes.success) {
    proxy.$cf.toast({ message: "发货成功", level: "success" });
    fetchOrderDetail(orderDetail.value.order_info_id);
  } else {
    proxy.$cf.toast({ message: "发货失败", level: "error" });
  }
}

// 获取当前时间字符串
function getNowDatetime() {
  const now = new Date();
  const pad = n => (n < 10 ? '0' + n : n);
  return (
    now.getFullYear() +
    '-' +
    pad(now.getMonth() + 1) +
    '-' +
    pad(now.getDate()) +
    ' ' +
    pad(now.getHours()) +
    ':' +
    pad(now.getMinutes()) +
    ':' +
    pad(now.getSeconds())
  );
}

// 页面加载
onLoad((option) => {
  const { order_info_id } = option;
  if (!order_info_id) {
    proxy.$cf.toast({ message: "参数错误，无法加载订单", level: "error" });
    return;
  }
  fetchOrderDetail(order_info_id);

});
</script>