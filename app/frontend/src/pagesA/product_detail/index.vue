<template>
  <base-layout>
    <view class="px-4 py-4 w-full box-border">
      <!-- 商品图片轮播/展示 -->
      <view class="w-full box-border px-4 mt-2">
        <view
            class="rounded-2xl overflow-hidden shadow-lg mb-4 bg-neutral-100 relative flex items-center justify-center">
          <image
              v-if="product.images && product.images.length > 0"
              :src="get_resource_url(product.images[0].url)"
              class="w-full h-60 object-cover rounded-2xl"
              mode="aspectFill"
          />
          <image
              v-else
              src="https://www.codeflying.net/preview/scenery-climbing.jpg"
              class="w-full h-60 object-cover rounded-2xl"
              mode="aspectFill"
          />
          <view class="absolute top-3 left-3 flex gap-2">
            <uni-badge v-if="product.hot_tags" :text="product.hot_tags" type="warning" class="shadow-sm"/>
          </view>
        </view>

        <!-- 商品基本信息 -->
        <view class="mb-4">
          <view class="flex items-center justify-between">
            <text class="text-xl font-bold text-text-primary truncate">{{ product.product_name }}</text>
            <view class="flex items-center gap-2">
              <uni-tag
                  v-if="isMine"
                  text="我的发布"
                  type="primary"
                  size="normal"
                  circle
              />
            </view>
          </view>
          <view class="flex items-center gap-2 mt-2">
            <uni-icons type="wallet" size="20" color="#4f46e5"/>
            <text class="text-lg font-semibold text-primary-500">￥{{ product.price }}</text>
            <uni-badge :text="'库存 ' + product.stock" type="success" size="normal" class="ml-2"/>
          </view>
          <view class="flex items-center gap-2 mt-2">
            <uni-icons type="shop" size="18" color="#6366f1"/>
            <text class="text-sm text-text-secondary">{{ product.category_name }}</text>
          </view>
        </view>

        <!-- 商品描述 -->
        <view class="mb-4">
          <text class="block text-base font-semibold text-text-primary mb-2">商品描述</text>
          <text class="text-sm text-text-secondary leading-relaxed whitespace-pre-line">
            {{ product.description || '暂无描述' }}
          </text>
        </view>

        <!-- 发布者信息 -->
        <view class="mb-4 flex items-center gap-3">
          <uni-icons type="person" size="20" color="#6366f1"/>
          <text class="text-base text-text-secondary">发布者：</text>
          <text class="text-base text-text-primary font-medium">{{ product.username || '匿名' }}</text>
        </view>

        <!-- 操作按钮区 -->
        <view class="flex gap-4 mt-6">
          <!-- 非本人可下单 -->
          <button
              v-if="!isMine"
              class="flex-1 bg-primary-500 text-white rounded-full shadow-md font-semibold"
              :disabled="product.stock <= 0 || loading"
              @click="openOrderPopup"
          >
            <uni-icons type="cart" size="18" color="#fff" class="mr-1"/>
            {{ product.stock > 0 ? '立即下单' : '已售罄' }}
          </button>
          <!-- 本人可管理 -->
          <button
              v-if="isMine"
              class="flex-1 bg-secondary-500 text-white rounded-full shadow-md font-semibold"
              @click="openManagePopup"
          >
            <uni-icons type="settings" size="18" color="#fff" class="mr-1"/>
            管理商品
          </button>
        </view>

        <!-- 管理弹窗 -->
        <uni-popup ref="managePopup" type="center">
          <view class="bg-white p-4 w-80 rounded-xl">
            <view class="text-lg font-semibold text-text-primary mb-2 flex items-center gap-2">
              <uni-icons type="settings" size="20" color="#4f46e5"/>
              商品管理
            </view>
            <view class="flex flex-col gap-3">
              <button
                  class="bg-primary-500 text-white rounded-full shadow"
                  @click="onEdit"
              >
                <uni-icons type="compose" size="16" color="#fff" class="mr-1"/>
                编辑商品
              </button>
              <button
                  class="bg-danger-300 text-white rounded-full shadow"
                  @click="onDelete"
              >
                <uni-icons type="trash" size="16" color="#fff" class="mr-1"/>
                删除商品
              </button>
              <button
                  class="bg-neutral-200 text-text-primary rounded-full"
                  @click="closeManagePopup"
              >
                取消
              </button>
            </view>
          </view>
        </uni-popup>

        <!-- 下单弹窗 -->
        <uni-popup ref="orderPopup" type="center">
          <view class="bg-white p-4 w-80 rounded-2xl">
            <text class="block text-lg font-bold text-primary-700 mb-4">填写订单信息</text>
            <uni-forms :modelValue="orderForm" label-position="top">
              <uni-forms-item label="手机号" name="mobile" required>
                <uni-easyinput
                    type="text"
                    v-model="orderForm.mobile"
                    placeholder="请输入手机号"
                    clearable
                />
              </uni-forms-item>
              <uni-forms-item label="收货地址" name="address" required>
                <uni-easyinput
                    type="textarea"
                    v-model="orderForm.address"
                    placeholder="请输入收货地址"
                    :autoHeight="true"
                    clearable
                />
              </uni-forms-item>
              <uni-forms-item label="订单备注" name="remark">
                <uni-easyinput
                    type="textarea"
                    v-model="orderForm.remark"
                    placeholder="可填写特殊要求"
                    :autoHeight="true"
                    clearable
                />
              </uni-forms-item>
              <uni-forms-item label="购买数量" name="quantity" required>
                <uni-easyinput
                    type="number"
                    v-model.number="orderForm.quantity"
                    placeholder="请输入购买数量"
                    :min="0.01"
                    :max="product.stock"
                    clearable
                />
              </uni-forms-item>
            </uni-forms>
            <view class="mt-4 text-right text-lg font-semibold text-primary-600">
              <text>总价：￥{{ totalPrice.toFixed(2) }}</text>
            </view>
            <view class="flex gap-3 mt-4">
              <button
                  class="flex-1 bg-primary-500 text-white rounded-full"
                  :loading="loading"
                  @click="submitOrder"
              >
                确认下单
              </button>
              <button class="flex-1 bg-neutral-200 text-gray-700 rounded-full" @click="closeOrderPopup">
                取消
              </button>
            </view>
          </view>
        </uni-popup>
      </view>
    </view>
    <!--    <button-->
    <!--        class="bg-green-500 text-white p-3 rounded-xl"-->
    <!--        @click="openPay"-->
    <!--    >-->
    <!--      打开支付弹窗-->
    <!--    </button>-->
    <base-pay-panel ref="pay_panel" :price="totalPrice.toFixed(2)" defaultChannel="mock" @paySuccess="paySuccess"
                    :productId="productId"
                    :userId="loginUserId"
                    :quality="orderForm.quantity"
                    :priceSingle="product.price "
                    :remark="orderForm.remark"
                    :productSubject="product.product_name"
                    :tel="orderForm.mobile"
                    recipient=""
                    :shippingAddress="orderForm.address"
                    orderTableName="order_info"
                    orderTypeFiled="general_orders"
                    :orderNo="orderResId"
    />
  </base-layout>
</template>

<script setup>
const {proxy} = getCurrentInstance();

const product = ref({
  product_name: '',
  price: 0,
  stock: 0,
  description: '',
  images: [],
  hot_tags: '',
  username: '',
  user_info_user_info_id_1: null,
  category_name: '',
  category_info_category_info_id_1: null
});
const loading = ref(false);
const isMine = ref(false);
const productId = ref(null);
const loginUserId = ref(null);
const orderNo=ref(null)
const managePopup = ref(null);
const orderPopup = ref(null);

const orderForm = ref({
  mobile: '',
  address: '',
  remark: '',
  quantity: 1
});
const openPay = () => {
  proxy.$refs.pay_panel.show();
}

// 计算总价
const totalPrice = computed(() => {
  const price = parseFloat(product.value.price) || 0;
  const quantity = parseFloat(orderForm.value.quantity) || 0;
  return price * quantity;
});

// 获取页面参数并加载商品详情
onLoad(async (option) => {
  if (!option.product_info_id) {
    await proxy.$cf.toast({message: '参数错误，无法加载商品', level: 'error'});
    return;
  }
  productId.value = Number(option.product_info_id);

  // 获取当前登录用户
  const loginRes = await proxy.$cf.login.getLoginUser();
  if (!loginRes.success) {
    await proxy.$cf.toast({message: '请先登录', level: 'error'});
    return;
  }
  loginUserId.value = loginRes.data.user_info_id;

  // 获取商品详情
  const res = await proxy.$cf.table.get({
    table_name: 'product_info',
    param: {product_info_id: productId.value}
  });
  if (res.success && res.data) {
    product.value = {
      ...res.data,
      category_name: res.data.category_name,
      username: res.data.username
    };
    isMine.value = res.data.user_info_user_info_id_1 == loginUserId.value;
  } else {
    await proxy.$cf.toast({message: '商品不存在', level: 'error'});
  }
});

// 打开管理弹窗
function openManagePopup() {
  proxy.$refs.managePopup.open();
}

// 关闭管理弹窗
function closeManagePopup() {
  proxy.$refs.managePopup.close();
}

// 编辑商品
function onEdit() {
  proxy.$cf.navigate.to({
    url: `/pages/product_publish/index?product_info_id=${productId.value}`,
    type: 'page'
  });
  closeManagePopup();
}

// 删除商品
async function onDelete() {
  const confirm = await proxy.$cf.model({
    title: '确认删除',
    message: '确定要删除该商品吗？删除后无法恢复。',
    confirmText: '删除',
    cancelText: '取消'
  });
  if (confirm.confirm) {
    const delRes = await proxy.$cf.table.delete({
      table_name: 'product_info',
      param: {product_info_id: productId.value}
    });
    if (delRes.success) {
      await proxy.$cf.toast({message: '删除成功', level: 'success'});
      proxy.$cf.navigate.back();
    } else {
      await proxy.$cf.toast({message: delRes.message || '删除失败', level: 'error'});
    }
    closeManagePopup();
  }
}

// 打开下单弹窗
function openOrderPopup() {
  // 重置表单
  orderForm.value = {
    mobile: '',
    address: '',
    remark: '',
    quantity: 1
  };
  proxy.$refs.orderPopup.open();
}

// 关闭下单弹窗
function closeOrderPopup() {
  proxy.$refs.orderPopup.close();
}

// 校验手机号格式
function validateMobile(mobile) {
  const mobileRegex = /^1[3-9]\d{9}$/;
  return mobileRegex.test(mobile);
}

const orderResId = ref(null);

// 提交订单
async function submitOrder() {
  if (loading.value) return;
  // 表单校验
  if (!orderForm.value.mobile) {
    await proxy.$cf.toast({message: '请输入手机号', level: 'error'});
    return;
  }
  if (!validateMobile(orderForm.value.mobile)) {
    await proxy.$cf.toast({message: '手机号格式不正确', level: 'error'});
    return;
  }
  if (!orderForm.value.address) {
    await proxy.$cf.toast({message: '请输入收货地址', level: 'error'});
    return;
  }
  if (!orderForm.value.quantity || orderForm.value.quantity <= 0) {
    await proxy.$cf.toast({message: '购买数量必须大于0', level: 'error'});
    return;
  }
  if (orderForm.value.quantity > product.value.stock) {
    await proxy.$cf.toast({message: '购买数量不能超过库存', level: 'error'});
    return;
  }

  loading.value = true;
  // 创建订单时间
  const now = new Date();
  const pad = (n) => n.toString().padStart(2, '0');
  // 调用api创建预支付订单
  const orderDate = `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`;


  const orderRes = await proxy.$cf.table.add({
    table_name: 'order_info',
    param: {
      product_info_product_info_id_1: productId.value,
      buyer_user_info_id_1: loginUserId.value,
      seller_user_info_id_1: product.value.user_info_user_info_id_1,
      order_status_enum_order_status_enum_id_1: 1,
      order_date: orderDate,
      // 自定义字段存储用户填写信息
      mobile: orderForm.value.mobile,
      address: orderForm.value.address,
      remark: orderForm.value.remark,
      quantity: orderForm.value.quantity
    }
  });


  loading.value = false;

  if (orderRes.success) {
    orderResId.value = orderRes.data; // 赋值订单id
  }

  // 隐藏下单弹窗
  closeOrderPopup()


  proxy.$refs.pay_panel.show(); // 打开支付弹窗
}


// 图片资源url处理
function get_resource_url(path) {
  return proxy.get_resource_url(path);
}

const paySuccess = async (paymentOrderId) => {
  console.log('执行支付成功的操作，跳转页面还是干嘛')

  // if (orderRes.success) {
  await proxy.$cf.toast({message: '下单成功，正在跳转', level: 'success'});
  proxy.$refs.orderPopup.close();
  proxy.$cf.navigate.to({
    url: `/pages/order_detail/index?order_info_id=${orderResId.value}`,
    type: 'page'
  });

  // } else {
  //   await proxy.$cf.toast({ message: orderRes.message || '下单失败', level: 'error' });
  // // }
}
</script>