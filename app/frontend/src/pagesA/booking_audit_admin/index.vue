<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4 flex flex-col">
        <text class="text-xl font-bold text-primary-700 mb-2">预约审核</text>
        <button 
          class="bg-primary-500 text-white rounded-full px-4 flex items-center font-semibold active:scale-95 transition-transform w-28"
          @click="refreshList"
        >
          <base-icon type="refresh" size="18" class="mr-1" color="#fff" />
          <text>刷新</text>
        </button>
      </view>

      <!-- 筛选区域 -->
      <view class="bg-white rounded-xl shadow-lg p-3 mb-4 flex flex-col gap-4">
        <uni-data-select
          v-model="filterStatus"
          :localdata="statusOptions"
          clear
          placement="bottom"
          class="w-full"
          placeholder="筛选状态"
          @change="onFilterChange"
        />
        <uni-data-select
          v-model="filterClassroom"
          :localdata="classroomOptions"
          clear
          placement="bottom"
          class="w-full"
          placeholder="筛选教室"
          @change="onFilterChange"
        />
      </view>

      <!-- 预约申请列表 -->
      <view v-if="bookingList.length > 0" class="">
        <view
          v-for="booking in bookingList"
          :key="booking.booking_info_id"
          class="bg-white rounded-xl shadow-lg p-4 mb-4 transition-all duration-150 hover:shadow-xl active:scale-[0.99] cursor-pointer"
          @click="showBookingDetail(booking)"
        >
          <!-- 头部信息 -->
          <view class="flex flex-col mb-3">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="calendar-filled" size="20" color="#1a7ed6" />
              <text class="font-semibold text-primary-700 text-base truncate">{{ booking.room_number }}</text>
            </view>
            <uni-tag
              :text="booking.status_name"
              size="small"
              :type="getStatusTagType(booking.status_name)"
              circle
            />
          </view>

          <!-- 预约详情 -->
          <view class="mb-3">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="person-filled" size="16" color="#475569" />
              <text class="text-sm text-text-primary-700 truncate">{{ booking.name }}</text>
            </view>
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="headphones" size="16" color="#475569" />
              <text class="text-sm text-text-primary-700">{{ formatDate(booking.booking_date) }} • {{ booking.time_period }}</text>
            </view>
            <view class="flex items-center gap-2">
              <base-icon type="flag-filled" size="16" color="#475569" />
              <text class="text-sm text-text-primary-700 truncate">{{ booking.purpose }}</text>
            </view>
          </view>

          <!-- 操作按钮 -->
          <view class="flex flex-col gap-3">
            <view
              class="bg-success-500 rounded-full flex items-center justify-center font-semibold active:scale-95 transition-transform cursor-pointer"
              :class="booking.booking_status_id !== 1 ? 'opacity-50 cursor-not-allowed' : ''"
              @click.stop="booking.booking_status_id == 1 && approveBooking(booking.booking_info_id)"
              style="height: 36px;"
            >
              <view class="flex items-center justify-center">
                <base-icon type="checkmarkempty" size="16" color="#fff" />
              </view>
              <text class="text-white ml-2 select-none">通过</text>
            </view>
            <view
              class="bg-danger-500 rounded-full flex items-center justify-center font-semibold active:scale-95 transition-transform cursor-pointer"
              :class="booking.booking_status_id !== 1 ? 'opacity-50 cursor-not-allowed' : ''"
              @click.stop="booking.booking_status_id == 1 && rejectBooking(booking.booking_info_id)"
              style="height: 36px;"
            >
              <view class="flex items-center justify-center">
                <base-icon type="closeempty" size="16" color="#fff" />
              </view>
              <text class="text-white ml-2 select-none">拒绝</text>
            </view>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else class="flex flex-col items-center justify-center py-24 bg-white rounded-xl shadow-inner mt-8">
        <base-icon type="calendar" size="60" color="#a3a3a3" />
        <text class="text-neutral-400 mt-4 text-base font-semibold">暂无预约申请</text>
      </view>

      <!-- 加载更多 -->
      <view v-if="hasMore" class="flex justify-center mt-6 mb-16">
        <button
          class="bg-primary-500 text-white rounded-full px-12 font-semibold active:scale-95 transition-transform"
          :disabled="loading"
          @click="loadMore"
          style="height: 36px;"
        >
          <base-icon v-if="loading" type="spinner-cycle" size="18" class="animate-spin mr-1" color="#fff" />
          <text v-else>加载更多</text>
        </button>
      </view>

      <!-- 预约详情弹窗 -->
      <uni-popup ref="detailPopup" type="center" class="z-50" :style="{ paddingBottom: '60px' }">
        <view class="bg-white rounded-xl p-6 w-80 max-h-[80vh] overflow-y-auto">
          <view class="flex flex-col mb-4">
            <view class="flex justify-between items-center mb-2">
              <text class="text-lg font-bold text-primary-700">预约详情</text>
              <view 
                class="cursor-pointer"
                @click="closeDetailPopup"
              >
                <base-icon 
                  type="close" 
                  size="20" 
                  color="#999"
                />
              </view>
            </view>

            <view v-if="selectedBooking" class="flex flex-col gap-4">
              <!-- 基本信息 -->
              <view class="flex flex-col gap-3">
                <view class="flex justify-between">
                  <text class="font-semibold text-neutral-700">教室：</text>
                  <text class="text-neutral-600 truncate">{{ selectedBooking.room_number }}</text>
                </view>
                <view class="flex justify-between">
                  <text class="font-semibold text-neutral-700">申请人：</text>
                  <text class="text-neutral-600 truncate">{{ selectedBooking.name }}</text>
                </view>
                <view class="flex justify-between">
                  <text class="font-semibold text-neutral-700">预约日期：</text>
                  <text class="text-neutral-600">{{ formatDate(selectedBooking.booking_date) }}</text>
                </view>
                <view class="flex justify-between">
                  <text class="font-semibold text-neutral-700">时间段：</text>
                  <text class="text-neutral-600">{{ selectedBooking.time_period }}</text>
                </view>
                <view class="flex justify-between">
                  <text class="font-semibold text-neutral-700">用途：</text>
                  <text class="text-neutral-600 truncate">{{ selectedBooking.purpose }}</text>
                </view>
                <view class="flex justify-between">
                  <text class="font-semibold text-neutral-700">状态：</text>
                  <uni-tag
                    :text="selectedBooking.status_name"
                    size="small"
                    :type="getStatusTagType(selectedBooking.status_name)"
                    circle
                  />
                </view>
                <view class="flex justify-between">
                  <text class="font-semibold text-neutral-700">创建时间：</text>
                  <text class="text-neutral-600">{{ formatDateTime(selectedBooking.create_time) }}</text>
                </view>
              </view>

              <!-- 操作按钮 -->
              <view class="flex flex-col gap-3 pt-4 border-t border-neutral-200">
                <view
                  class="bg-success-500 rounded-full flex items-center justify-center font-semibold active:scale-95 transition-transform cursor-pointer"
                  :class="selectedBooking.booking_status_id !== 1 ? 'opacity-50 cursor-not-allowed' : ''"
                  @click="selectedBooking.booking_status_id == 1 && approveBooking(selectedBooking.booking_info_id)"
                  style="height: 36px;"
                >
                  <view class="flex items-center justify-center">
                    <base-icon type="checkmarkempty" size="16" color="#fff" />
                  </view>
                  <text class="text-white ml-2 select-none">通过</text>
                </view>
                <view
                  class="bg-danger-500 rounded-full flex items-center justify-center font-semibold active:scale-95 transition-transform cursor-pointer"
                  :class="selectedBooking.booking_status_id !== 1 ? 'opacity-50 cursor-not-allowed' : ''"
                  @click="selectedBooking.booking_status_id == 1 && rejectBooking(selectedBooking.booking_info_id)"
                  style="height: 36px;"
                >
                  <view class="flex items-center justify-center">
                    <base-icon type="closeempty" size="16" color="#fff" />
                  </view>
                  <text class="text-white ml-2 select-none">拒绝</text>
                </view>
              </view>
            </view>
          </view>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const bookingList = ref([])
const selectedBooking = ref(null)
const filterStatus = ref('')
const filterClassroom = ref('')
const statusOptions = ref([])
const classroomOptions = ref([])
const pageInfo = ref({
  current: 1,
  pageSize: 10
})
const total = ref(0)
const hasMore = ref(true)
const loading = ref(false)
const detailPopup = ref(null)

// ------------------ Lifecycle ------------------
onLoad(async () => {
  await fetchStatusOptions()
  await fetchClassroomOptions()
  await fetchBookingList(true)
})

// ------------------ Methods ------------------

// 获取状态选项
async function fetchStatusOptions() {
  const res = await proxy.$cf.table.list({
    table_name: 'booking_status_enum',
    param: {}
  })
  if (res.success) {
    statusOptions.value = [
      { value: '', text: '全部状态' },
      ...res.data.map(item => ({
        value: item.booking_status_enum_id,
        text: item.status_name
      }))
    ]
  }
}

// 获取教室选项
async function fetchClassroomOptions() {
  const res = await proxy.$cf.table.list({
    table_name: 'classroom_info',
    param: {}
  })
  if (res.success) {
    classroomOptions.value = [
      { value: '', text: '全部教室' },
      ...res.data.map(item => ({
        value: item.classroom_info_id,
        text: item.room_number
      }))
    ]
  }
}

// 字段映射简化，提升视图层可读性
function mapBookingFields(rawList) {
  return rawList.map(item => ({
    ...item,
    booking_status_id: item.booking_status_enum_booking_status_enum_id_1,
    room_number: item.room_number || item.room_number || '', // 直接用room_number字段
    name: item.name || '',
  }))
}

// 获取预约列表
async function fetchBookingList(reset = false) {
  if (loading.value) return
  loading.value = true
  try {
    if (reset) {
      pageInfo.value.current = 1
      bookingList.value = []
      hasMore.value = true
    }

    // 构建查询参数
    let param = {
      current: pageInfo.value.current,
      pageSize: pageInfo.value.pageSize,
      order_by: {
        'create_time': 'desc',
        'booking_info_id': 'desc'
      }
    }

    // 添加筛选条件
    if (filterStatus.value) {
      param.booking_status_enum_booking_status_enum_id_1 = filterStatus.value
    }
    if (filterClassroom.value) {
      param.classroom_info_classroom_info_id_1 = filterClassroom.value
    }

    const res = await proxy.$cf.table.page({
      table_name: 'booking_info',
      param
    })

    if (res.success) {
      const mappedRecords = mapBookingFields(res.data.records)
      if (reset) {
        bookingList.value = mappedRecords
      } else {
        bookingList.value = bookingList.value.concat(mappedRecords)
      }
      total.value = res.data.total
      hasMore.value = bookingList.value.length < total.value
    } else {
      proxy.$cf.toast({ message: res.message || '加载预约失败', level: 'error' })
    }
  } catch (e) {
    proxy.$cf.toast({ message: '网络异常，加载预约失败', level: 'error' })
  } finally {
    loading.value = false
  }
}

// 筛选变化
function onFilterChange() {
  fetchBookingList(true)
}

// 刷新列表
function refreshList() {
  fetchBookingList(true)
}

// 加载更多
function loadMore() {
  if (hasMore.value && !loading.value) {
    pageInfo.value.current += 1
    fetchBookingList(false)
  }
}

// 显示预约详情
function showBookingDetail(booking) {
  selectedBooking.value = booking
  detailPopup.value.open()
}

// 关闭详情弹窗
function closeDetailPopup() {
  detailPopup.value.close()
  selectedBooking.value = null
}

// 格式化当前时间为 YYYY-MM-DD HH:mm:ss
function getFormattedNow() {
  const now = new Date()
  const year = now.getFullYear()
  const month = ('0' + (now.getMonth() + 1)).slice(-2)
  const day = ('0' + now.getDate()).slice(-2)
  const hours = ('0' + now.getHours()).slice(-2)
  const minutes = ('0' + now.getMinutes()).slice(-2)
  const seconds = ('0' + now.getSeconds()).slice(-2)
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

// 批准预约
async function approveBooking(bookingId) {
  const formattedTime = getFormattedNow()
  try {
    const res = await proxy.$cf.table.update({
      table_name: 'booking_info',
      param: {
        booking_info_id: bookingId,
        booking_status_enum_booking_status_enum_id_1: 2, // 审核通过
        update_time: formattedTime
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '预约通过成功', level: 'success' })
      await fetchBookingList(true)
      closeDetailPopup()
    } else {
      proxy.$cf.toast({ message: res.message || '预约通过失败', level: 'error' })
    }
  } catch (e) {
    proxy.$cf.toast({ message: '网络异常，预约通过失败', level: 'error' })
  }
}

// 拒绝预约
async function rejectBooking(bookingId) {
  const formattedTime = getFormattedNow()
  try {
    const res = await proxy.$cf.table.update({
      table_name: 'booking_info',
      param: {
        booking_info_id: bookingId,
        booking_status_enum_booking_status_enum_id_1: 3, // 拒绝
        update_time: formattedTime
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '预约拒绝成功', level: 'success' })
      await fetchBookingList(true)
      closeDetailPopup()
    } else {
      proxy.$cf.toast({ message: res.message || '预约拒绝失败', level: 'error' })
    }
  } catch (e) {
    proxy.$cf.toast({ message: '网络异常，预约拒绝失败', level: 'error' })
  }
}

// 状态标签类型
function getStatusTagType(statusName) {
  if (!statusName) return 'default'
  if (statusName == '待审核') return 'warning'
  if (statusName == '审核通过') return 'success'
  if (statusName == '拒绝') return 'error'
  return 'default'
}

// 格式化日期
function formatDate(dateString) {
  if (!dateString) return '--'
  const date = new Date(dateString.replace(/-/g, '/'))
  const year = date.getFullYear()
  const month = ('0' + (date.getMonth() + 1)).slice(-2)
  const day = ('0' + date.getDate()).slice(-2)
  return `${year}-${month}-${day}`
}

// 格式化日期时间
function formatDateTime(dateTimeString) {
  if (!dateTimeString) return '--'
  const date = new Date(dateTimeString.replace(/-/g, '/'))
  const year = date.getFullYear()
  const month = ('0' + (date.getMonth() + 1)).slice(-2)
  const day = ('0' + date.getDate()).slice(-2)
  const hours = ('0' + date.getHours()).slice(-2)
  const minutes = ('0' + date.getMinutes()).slice(-2)
  return `${year}-${month}-${day} ${hours}:${minutes}`
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