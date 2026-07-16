<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50 pb-16">
      <!-- 报修详情卡片 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <!-- 头部信息 -->
        <view class="mb-6">
          <text class="text-2xl font-bold text-gray-800 mb-2 break-words">报修详情</text>
          <uni-tag
            :text="repairDetail.status_name"
            :type="getStatusTagType(repairDetail.status_name)"
            size="normal"
            circle
            class="inline-block"
          />
        </view>

        <!-- 基本信息 纵向排列，避免折行错位 -->
        <view>
          <!-- 报修用户 -->
          <view class="flex items-center mb-4">
            <base-icon type="person-filled" size="20" color="#3b82f6" />
            <view class="ml-3">
              <text class="text-xs text-gray-500">报修用户</text>
              <text class="block text-base font-medium text-gray-700 max-w-[90vw] break-words">{{ repairDetail.name || '--' }}</text>
            </view>
          </view>

          <!-- 教室信息 -->
          <view class="flex items-center mb-4">
            <base-icon type="location-filled" size="20" color="#3b82f6" />
            <view class="ml-3">
              <text class="text-xs text-gray-500">教室</text>
              <text class="block text-base font-medium text-gray-700 max-w-[90vw] break-words">{{ repairDetail.room_number || '--' }}</text>
            </view>
          </view>

          <!-- 创建时间 -->
          <view class="flex items-center">
            <base-icon type="calendar-filled" size="20" color="#3b82f6" />
            <view class="ml-3">
              <text class="text-xs text-gray-500">创建时间</text>
              <text class="block text-base font-medium text-gray-700 max-w-[90vw] break-words">{{ formatDateTime(repairDetail.create_time) || '--' }}</text>
            </view>
          </view>
        </view>
      </view>

      <!-- 报修内容 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <view class="flex items-center mb-4">
          <base-icon type="chatbubble-filled" size="20" color="#3b82f6" />
          <text class="text-lg font-semibold text-gray-800 ml-2">报修内容</text>
        </view>
        <view class="bg-gray-50 rounded-lg p-3">
          <text class="text-gray-700 leading-relaxed break-words whitespace-pre-wrap">{{ repairDetail.content || '暂无内容' }}</text>
        </view>
      </view>

      <!-- 处理反馈 -->
      <view v-if="repairDetail.feedback" class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <view class="flex items-center mb-4">
          <base-icon type="info-filled" size="20" color="#3b82f6" />
          <text class="text-lg font-semibold text-gray-800 ml-2">处理反馈</text>
        </view>
        <view class="bg-green-50 border border-green-200 rounded-lg p-3">
          <text class="text-green-700 leading-relaxed break-words whitespace-pre-wrap">{{ repairDetail.feedback }}</text>
        </view>
      </view>

      <!-- 空状态提示 -->
      <view v-if="!repairDetail.repair_order_id && !loading" class="flex flex-col items-center justify-center min-h-[calc(100vh-100px)]">
        <base-icon type="info" size="60" color="#d4d4d4" />
        <text class="text-gray-400 mt-4 text-base">暂无报修详情</text>
      </view>

      <!-- 加载状态 -->
      <view v-if="loading" class="flex flex-col items-center justify-center min-h-[calc(100vh-100px)]">
        <base-icon type="spinner-cycle" size="24" class="animate-spin" color="#339cff" />
        <text class="mt-2 text-gray-500">加载报修详情中...</text>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance();

// 响应式数据
const repairDetail = ref({
  repair_order_id: null,
  name: '',
  room_number: '',
  content: '',
  status_name: '',
  feedback: '',
  create_time: ''
});
const loading = ref(false);
const repairOrderId = ref(null);

// 状态标签类型映射，便于维护
const statusTagMap = {
  'Pending': 'warning',
  '待审核': 'warning',
  'Processing': 'primary',
  '处理中': 'primary',
  'Completed': 'success',
  '已完成': 'success'
};

// 生命周期
onLoad(async (option) => {
  if (option && option.repair_order_id) {
    repairOrderId.value = Number(option.repair_order_id);
    await fetchRepairDetail();
  }
});

// 获取报修详情
async function fetchRepairDetail() {
  if (!repairOrderId.value) return;

  loading.value = true;
  try {
    const res = await proxy.$cf.table.get({
      table_name: 'repair_order',
      param: { repair_order_id: repairOrderId.value }
    });

    if (res.success && res.data) {
      repairDetail.value = {
        repair_order_id: res.data.repair_order_id,
        name: res.data.name, // 来自user_info表的平铺字段
        room_number: res.data.room_number, // 来自classroom_info表的平铺字段
        content: res.data.content,
        status_name: res.data.status_name, // 来自repair_status_enum表的平铺字段
        feedback: res.data.feedback,
        create_time: res.data.create_time
      };
    } else {
      proxy.$cf.toast({
        message: res.message || '加载报修详情失败',
        level: 'error'
      });
    }
  } catch (error) {
    console.error('获取报修详情出错:', error);
    proxy.$cf.toast({
      message: '网络异常，请重试',
      level: 'error'
    });
  } finally {
    loading.value = false;
  }
}

// 状态标签类型
function getStatusTagType(statusName) {
  return statusTagMap[statusName] || 'default';
}

// 时间格式化，增强无效日期判断
function formatDateTime(datetime) {
  if (!datetime) return '--';

  try {
    const date = new Date(datetime.replace(/-/g, '/'));
    if (isNaN(date.getTime())) return '--';
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);

    return `${year}-${month}-${day} ${hours}:${minutes}`;
  } catch {
    return '--';
  }
}
</script>

<style>
.animate-spin {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>