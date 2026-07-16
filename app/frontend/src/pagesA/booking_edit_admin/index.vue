<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4">
        <view class="flex items-center gap-2">
          <base-icon type="calendar-filled" size="24" color="#1a7ed6"></base-icon>
          <text class="text-xl font-bold text-primary-700">预约申请审核</text>
        </view>
      </view>

      <!-- 加载状态 -->
      <view v-if="loading" class="flex justify-center items-center py-12">
        <base-icon type="spinner-cycle" size="32" color="#1a7ed6" class="animate-spin"></base-icon>
        <text class="ml-2 text-neutral-600">正在加载预约详情...</text>
      </view>

      <!-- 预约详情卡片 -->
      <view v-else-if="bookingDetail" class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <!-- 基本信息 -->
        <view class="mb-4">
          <text class="block text-lg font-semibold text-primary-800 mb-3">预约信息</text>
          
          <view>
            <!-- 申请人 -->
            <view class="mb-3">
              <text class="text-neutral-700 font-semibold mb-1 block">申请人</text>
              <view class="flex items-center gap-2">
                <base-icon type="person-filled" size="18" color="#475569"></base-icon>
                <text class="font-medium text-neutral-900 truncate flex-1">{{ bookingDetail.name || '无' }}</text>
              </view>
            </view>

            <!-- 教室 -->
            <view class="mb-3">
              <text class="text-neutral-700 font-semibold mb-1 block">教室</text>
              <view class="flex items-center gap-2">
                <base-icon type="location-filled" size="18" color="#475569"></base-icon>
                <text class="font-medium text-neutral-900 truncate flex-1">{{ bookingDetail.room_number || '无' }}</text>
              </view>
            </view>

            <!-- 预约日期 -->
            <view class="mb-3">
              <text class="text-neutral-700 font-semibold mb-1 block">预约日期</text>
              <view class="flex items-center gap-2">
                <base-icon type="calendar" size="18" color="#475569"></base-icon>
                <text class="font-medium text-neutral-900 truncate flex-1">{{ formatDate(bookingDetail.booking_date) || '无' }}</text>
              </view>
            </view>

            <!-- 时间段 -->
            <view class="mb-3">
              <text class="text-neutral-700 font-semibold mb-1 block">时间段</text>
              <view class="flex items-center gap-2">
                <base-icon type="headphones" size="18" color="#475569"></base-icon>
                <text class="font-medium text-neutral-900 truncate flex-1">{{ bookingDetail.time_period || '无' }}</text>
              </view>
            </view>

            <!-- 用途 -->
            <view class="mb-3">
              <text class="text-neutral-700 font-semibold mb-1 block">用途</text>
              <view class="flex items-start gap-2">
                <base-icon type="info-filled" size="18" color="#475569"></base-icon>
                <text class="font-medium text-neutral-900 flex-1">{{ bookingDetail.purpose || '无' }}</text>
              </view>
            </view>

            <!-- 当前状态 -->
            <view class="mb-3">
              <text class="text-neutral-700 font-semibold mb-1 block">当前状态</text>
              <view class="flex items-center gap-2">
                <base-icon type="flag-filled" size="18" color="#475569"></base-icon>
                <uni-tag 
                  :text="bookingDetail.status_name" 
                  size="small"
                  :type="getStatusTagType(bookingDetail.status_name)"
                  circle
                />
              </view>
            </view>

            <!-- 创建时间 -->
            <view>
              <text class="text-neutral-700 font-semibold mb-1 block">创建时间</text>
              <view class="flex items-center gap-2">
                <base-icon type="calendar-filled" size="18" color="#475569"></base-icon>
                <text class="font-medium text-neutral-900 truncate flex-1">{{ formatDateTime(bookingDetail.create_time) || '无' }}</text>
              </view>
            </view>
          </view>
        </view>

        <!-- 审核操作表单 -->
        <view class="border-t border-neutral-200 pt-4">
          <text class="block text-lg font-semibold text-primary-800 mb-3">审核操作</text>
          
          <uni-forms ref="reviewFormRef" :modelValue="reviewForm" label-position="top" label-width="100%">
            <uni-forms-item label="预约状态" name="booking_status_enum_booking_status_enum_id_1" required>
              <uni-data-select
                v-model="reviewForm.booking_status_enum_booking_status_enum_id_1"
                :localdata="statusOptions"
                class="w-full box-border"
                placeholder="请选择审核状态"
              />
            </uni-forms-item>

            <uni-forms-item label="审核备注（选填）" name="review_notes">
              <uni-easyinput
                type="textarea"
                v-model="reviewForm.review_notes"
                placeholder="请输入审核备注"
                class="w-full box-border"
                :maxlength="500"
              />
            </uni-forms-item>

            <!-- 操作按钮 -->
            <view class="flex gap-3 mt-6">
              <view
                class="flex-1 bg-success-500 text-white rounded-full flex items-center justify-center font-semibold transition-transform active:scale-95 cursor-pointer select-none"
                @click="handleApprove"
                :class="{ 'opacity-50 pointer-events-none': submitting }"
              >
                <base-icon type="checkmarkempty" size="18" color="#fff" class="mr-2"></base-icon>
                <text>批准</text>
              </view>

              <view
                class="flex-1 bg-danger-500 text-white rounded-full flex items-center justify-center font-semibold transition-transform active:scale-95 cursor-pointer select-none"
                @click="handleReject"
                :class="{ 'opacity-50 pointer-events-none': submitting }"
              >
                <base-icon type="closeempty" size="18" color="#fff" class="mr-2"></base-icon>
                <text>拒绝</text>
              </view>
            </view>

            <view
              class="w-full bg-primary-500 text-white rounded-full mt-3 flex items-center justify-center font-semibold transition-transform active:scale-95 cursor-pointer select-none"
              @click="handleSave"
              :class="{ 'opacity-50 pointer-events-none': submitting }"
            >
              <base-icon type="paperplane-filled" size="18" color="#fff" class="mr-2"></base-icon>
              <text>{{ submitting ? '保存中...' : '保存修改' }}</text>
            </view>
          </uni-forms>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else class="flex flex-col items-center justify-center py-24 bg-white rounded-xl shadow-inner">
        <base-icon type="calendar" size="60" color="#a3a3a3"></base-icon>
        <text class="text-neutral-400 mt-4 text-base font-semibold">未找到预约信息</text>
        <text class="text-neutral-400 mt-2 text-sm text-center">您查找的预约申请不存在或已被删除。</text>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const bookingId = ref(null)
const bookingDetail = ref(null)
const loading = ref(false)
const submitting = ref(false)
const currentUser = ref(null)

// 审核表单
const reviewForm = ref({
  booking_status_enum_booking_status_enum_id_1: '',
  review_notes: ''
})

const statusOptions = ref([])
const reviewFormRef = ref(null)

// ------------------ Lifecycle ------------------
onLoad(async (option) => {
  if (option && option.booking_info_id) {
    bookingId.value = Number(option.booking_info_id)
    await fetchCurrentUser()
    await fetchStatusOptions()
    await fetchBookingDetail()
  } else {
    proxy.$cf.toast({ message: '无效的预约ID', level: 'error' })
  }
})

// ------------------ Methods ------------------

// 获取当前用户信息
async function fetchCurrentUser() {
  try {
    const res = await proxy.$cf.login.getLoginUser()
    if (res.success && res.data) {
      currentUser.value = res.data
    }
  } catch (error) {
    console.error('获取当前用户失败:', error)
  }
}

// 获取预约状态选项
async function fetchStatusOptions() {
  const res = await proxy.$cf.table.list({
    table_name: 'booking_status_enum',
    param: {}
  })
  if (res.success) {
    statusOptions.value = res.data.map(item => ({
      value: item.booking_status_enum_id,
      text: item.status_name
    }))
  }
}

// 获取预约详情
async function fetchBookingDetail() {
  if (!bookingId.value) return
  
  loading.value = true
  try {
    const res = await proxy.$cf.table.get({
      table_name: 'booking_info',
      param: { booking_info_id: bookingId.value }
    })
    
    if (res.success && res.data) {
      bookingDetail.value = {
        ...res.data,
        name: res.data.name || res.data.user_info_name || '', // 申请人姓名，容错处理
        room_number: res.data.room_number || '', // 教室编号
        status_name: res.data.status_name || '' // 状态名称
      }
      
      // 设置表单初始值
      reviewForm.value.booking_status_enum_booking_status_enum_id_1 = 
        res.data.booking_status_enum_booking_status_enum_id_1
      reviewForm.value.review_notes = ''
    } else {
      proxy.$cf.toast({ message: '加载预约详情失败', level: 'error' })
    }
  } catch (error) {
    console.error('获取预约详情错误:', error)
    proxy.$cf.toast({ message: '网络错误，加载预约详情失败', level: 'error' })
  } finally {
    loading.value = false
  }
}

// 批准预约
function handleApprove() {
  // 找到审核通过的状态ID
  const approvedStatus = statusOptions.value.find(option => 
    option.text.toLowerCase().includes('审核通过') || option.text.toLowerCase().includes('approved')
  )
  
  if (approvedStatus) {
    reviewForm.value.booking_status_enum_booking_status_enum_id_1 = approvedStatus.value
    handleSave()
  }
}

// 拒绝预约
function handleReject() {
  // 找到拒绝的状态ID
  const rejectedStatus = statusOptions.value.find(option => 
    option.text.toLowerCase().includes('拒绝') || option.text.toLowerCase().includes('rejected')
  )
  
  if (rejectedStatus) {
    reviewForm.value.booking_status_enum_booking_status_enum_id_1 = rejectedStatus.value
    handleSave()
  }
}

// 保存更改
async function handleSave() {
  if (!bookingId.value || !currentUser.value) return
  
  // 表单验证
  if (!reviewForm.value.booking_status_enum_booking_status_enum_id_1) {
    proxy.$cf.toast({ message: '请选择预约状态', level: 'error' })
    return
  }
  
  submitting.value = true
  
  try {
    // 获取当前时间
    const now = new Date()
    const year = now.getFullYear()
    const month = ('0' + (now.getMonth() + 1)).slice(-2)
    const day = ('0' + now.getDate()).slice(-2)
    const hours = ('0' + now.getHours()).slice(-2)
    const minutes = ('0' + now.getMinutes()).slice(-2)
    const seconds = ('0' + now.getSeconds()).slice(-2)
    const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
    
    const updateData = {
      booking_info_id: bookingId.value,
      booking_status_enum_booking_status_enum_id_1: reviewForm.value.booking_status_enum_booking_status_enum_id_1,
      update_time: formattedTime
    }
    
    // 保存审核备注
    if (reviewForm.value.review_notes && reviewForm.value.review_notes.trim() !== '') {
      updateData.review_notes = reviewForm.value.review_notes.trim()
    }
    
    const res = await proxy.$cf.table.update({
      table_name: 'booking_info',
      param: updateData
    })
    
    if (res.success) {
      proxy.$cf.toast({ message: '预约状态更新成功', level: 'success' })
      // 重新加载数据以显示更新后的状态
      await fetchBookingDetail()
    } else {
      proxy.$cf.toast({ message: res.message || '更新预约状态失败', level: 'error' })
    }
  } catch (error) {
    console.error('更新预约错误:', error)
    proxy.$cf.toast({ message: '网络错误，更新预约失败', level: 'error' })
  } finally {
    submitting.value = false
  }
}

// 状态标签类型
function getStatusTagType(statusName) {
  if (!statusName) return 'default'
  
  const status = statusName.toLowerCase()
  if (status.includes('待审核') || status.includes('pending')) return 'warning'
  if (status.includes('审核通过') || status.includes('approved')) return 'success'
  if (status.includes('拒绝') || status.includes('rejected')) return 'error'
  return 'default'
}

// 日期格式化
function formatDate(dateString) {
  if (!dateString) return '无'
  try {
    const date = new Date(dateString.replace(/-/g, '/'))
    return date.toLocaleDateString('zh-CN', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    })
  } catch {
    return dateString
  }
}

// 日期时间格式化
function formatDateTime(dateTimeString) {
  if (!dateTimeString) return '无'
  try {
    const date = new Date(dateTimeString.replace(/-/g, '/'))
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    })
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