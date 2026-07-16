<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 预约详情卡片 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <!-- 头部信息 -->
        <view class="mb-4">
          <text class="text-xl font-bold text-primary-700 mb-4 block">预约详情</text>
          <uni-tag
            :text="bookingDetail.status_name"
            size="normal"
            :type="getStatusTagType(bookingDetail.status_name)"
            circle
          />
        </view>

        <!-- 基本信息 -->
        <view>
          <!-- 教室信息 -->
          <view class="mb-4 flex items-start gap-3">
            <base-icon type="home-filled" size="20" color="#339cff" />
            <view class="flex-1">
              <text class="block text-sm text-neutral-500 mb-1">教室</text>
              <text class="block text-base font-medium text-neutral-800">
                {{ bookingDetail.room_number || '--' }}
              </text>
            </view>
          </view>

          <!-- 预约日期 -->
          <view class="mb-4 flex items-start gap-3">
            <base-icon type="calendar-filled" size="20" color="#33bf71" />
            <view class="flex-1">
              <text class="block text-sm text-neutral-500 mb-1">预约日期</text>
              <text class="block text-base font-medium text-neutral-800">
                {{ formatDate(bookingDetail.booking_date) || '--' }}
              </text>
            </view>
          </view>

          <!-- 时间段 -->
          <view class="mb-4 flex items-start gap-3">
            <base-icon type="headphones" size="20" color="#ffa31a" />
            <view class="flex-1">
              <text class="block text-sm text-neutral-500 mb-1">时间段</text>
              <text class="block text-base font-medium text-neutral-800">
                {{ bookingDetail.time_period || '--' }}
              </text>
            </view>
          </view>

          <!-- 预约用途 -->
          <view class="mb-4 flex items-start gap-3">
            <base-icon type="info-filled" size="20" color="#c166ff" />
            <view class="flex-1">
              <text class="block text-sm text-neutral-500 mb-1">预约用途</text>
              <text class="block text-base font-medium text-neutral-800">
                {{ bookingDetail.purpose || '--' }}
              </text>
            </view>
          </view>

          <!-- 创建时间 -->
          <view class="mb-4 flex items-start gap-3">
            <base-icon type="calendar" size="20" color="#737373" />
            <view class="flex-1">
              <text class="block text-sm text-neutral-500 mb-1">创建时间</text>
              <text class="block text-base font-medium text-neutral-800">
                {{ formatDateTime(bookingDetail.create_time) || '--' }}
              </text>
            </view>
          </view>

          <!-- 预约用户 -->
          <view class="mb-4 flex items-start gap-3">
            <base-icon type="person-filled" size="20" color="#ff3333" />
            <view class="flex-1">
              <text class="block text-sm text-neutral-500 mb-1">预约用户</text>
              <text class="block text-base font-medium text-neutral-800">
                {{ bookingDetail.name || '--' }}
              </text>
            </view>
          </view>
        </view>

        <!-- 状态说明 -->
        <view class="mt-6 p-3 bg-neutral-50 rounded-lg">
          <text class="block text-sm font-medium text-neutral-700 mb-1">状态说明</text>
          <text class="block text-sm text-neutral-600">
            {{ getStatusDescription(bookingDetail.status_name) }}
          </text>
        </view>
      </view>

      <!-- 操作按钮区域 -->
      <view class="bg-white rounded-xl shadow-lg p-4">
        <button 
          class="w-full bg-primary-500 text-white rounded-full font-semibold active:scale-95 transition-transform"
          @click="refreshBookingDetail"
        >
          <base-icon type="refresh" size="18" class="mr-2" color="#fff" />
          刷新详情
        </button>
      </view>

      <!-- 加载状态 -->
      <view v-if="loading" class="flex justify-center items-center py-8">
        <base-icon type="spinner-cycle" size="24" class="animate-spin text-primary-500" />
        <text class="ml-2 text-neutral-600">正在加载预约详情...</text>
      </view>

      <!-- 空状态 -->
      <view v-else-if="!bookingDetail.booking_info_id" class="flex flex-col items-center justify-center py-16">
        <base-icon type="calendar" size="60" color="#d4d4d4" />
        <text class="text-neutral-400 mt-4 text-base font-medium">未找到预约详情</text>
        <text class="text-neutral-400 mt-2 text-sm">请检查预约ID是否正确</text>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const bookingDetail = ref({})
const loading = ref(false)
const bookingInfoId = ref(null)

// ------------------ Lifecycle ------------------
onLoad(async (option) => {
  if (option && option.booking_info_id) {
    bookingInfoId.value = Number(option.booking_info_id)
    await fetchBookingDetail()
  }
})

// ------------------ Methods ------------------

// 获取预约详情
async function fetchBookingDetail() {
  if (loading.value) return
  loading.value = true
  try {
    const res = await proxy.$cf.table.get({
      table_name: 'booking_info',
      param: { booking_info_id: bookingInfoId.value }
    })
    if (res.success && res.data) {
      bookingDetail.value = res.data
    } else {
      proxy.$cf.toast({ 
        message: res.message || '加载预约详情失败', 
        level: 'error' 
      })
    }
  } catch (error) {
    proxy.$cf.toast({ 
      message: '网络异常，请重试', 
      level: 'error' 
    })
  } finally {
    loading.value = false
  }
}

// 刷新预约详情
async function refreshBookingDetail() {
  await fetchBookingDetail()
  proxy.$cf.toast({ 
    message: '预约详情已刷新', 
    level: 'success' 
  })
}

// 状态标签类型
function getStatusTagType(statusName) {
  if (!statusName) return 'default'
  if (statusName == '审核通过') return 'success'
  if (statusName == '待审核') return 'warning'
  if (statusName == '拒绝') return 'error'
  return 'default'
}

// 状态描述
function getStatusDescription(statusName) {
  if (!statusName) return '无状态信息'
  
  const descriptions = {
    '待审核': '您的预约申请正在管理员审核中。',
    '审核通过': '您的预约已通过审核，可以按计划使用教室。',
    '拒绝': '您的预约申请已被拒绝，请联系管理员了解详情。'
  }
  
  return descriptions[statusName] || '无状态信息'
}

// 日期格式化
function formatDate(dateString) {
  if (!dateString) return '--'
  try {
    const date = new Date(dateString.replace(/-/g, '/'))
    const year = date.getFullYear()
    const month = ('0' + (date.getMonth() + 1)).slice(-2)
    const day = ('0' + date.getDate()).slice(-2)
    return `${year}-${month}-${day}`
  } catch {
    return dateString
  }
}

// 日期时间格式化
function formatDateTime(dateTimeString) {
  if (!dateTimeString) return '--'
  try {
    const date = new Date(dateTimeString.replace(/-/g, '/'))
    const year = date.getFullYear()
    const month = ('0' + (date.getMonth() + 1)).slice(-2)
    const day = ('0' + date.getDate()).slice(-2)
    const hours = ('0' + date.getHours()).slice(-2)
    const minutes = ('0' + date.getMinutes()).slice(-2)
    return `${year}-${month}-${day} ${hours}:${minutes}`
  } catch {
    return dateTimeString
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