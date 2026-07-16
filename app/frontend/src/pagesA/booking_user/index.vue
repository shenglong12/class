<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-gradient-to-b from-primary-50 to-white">
      <!-- 顶部标题 -->
      <view class="mb-6 text-center">
        <text class="text-3xl font-extrabold text-primary-700">教室预约</text>
        <text class="block mt-2 text-neutral-600 text-body">为您的活动预订教室</text>
      </view>

      <!-- 教学楼选择 -->
      <uni-data-select
        v-model="selectedBuilding"
        :localdata="buildingOptions"
        clear
        placement="bottom"
        class="w-full box-border mb-3"
        placeholder="请选择教学楼"
        @change="onBuildingChange"
      />

      <!-- 教室选择 -->
      <uni-data-select
        v-model="bookingForm.classroom_info_classroom_info_id_1"
        :localdata="filteredClassrooms"
        clear
        placement="bottom"
        class="w-full box-border mb-4"
        placeholder="请选择教室"
      />

      <!-- 预约日期选择 -->
      <uni-datetime-picker
        type="date"
        v-model="bookingForm.booking_date"
        :start="todayString"
        :end="maxDate"
        class="w-full box-border mb-4"
        placeholder="请选择预约日期"
        @change="onBookingDateChange"
      />

      <!-- 替换原时间选择器为时间轴组件 -->
      <view class="mb-4">
        <text class="block text-sm font-semibold text-primary-700 mb-1">选择预约时间段</text>
        <scroll-view scroll-x="true" class="overflow-x-auto">
          <view class="relative flex h-12 items-center border border-primary-400 rounded-lg bg-primary-50">
            <!-- 时间轴线条 -->
            <view class="absolute top-1/2 left-0 right-0 border-t border-primary-400 transform -translate-y-1/2"></view>

            <!-- 时间刻度和区块 -->
            <view class="flex">
              <template v-for="(segment, index) in timeSegments" :key="index">
                <view
                  class="relative h-10 flex flex-col items-center justify-center cursor-pointer"
                  :style="{ width: segmentWidth + 'px' }"
                  :class="{
                    'bg-primary-400 text-white': segment.selected,
                    'bg-primary-200 text-primary-900': segment.status === 'available' && !segment.selected,
                    'text-gray-400 cursor-not-allowed': segment.status === 'unavailable',
                    'border-2 border-primary-400': segment.selected
                  }"
                  @click="toggleSegmentSelection(index)"
                  :title="getSegmentTooltip(segment)"
                >
                  <text class="text-xs select-none">
                    {{ segment.label }}
                  </text>
                  <base-icon
                    v-if="segment.selected"
                    type="checkmarkempty"
                    size="14"
                    color="#fff"
                    class="absolute top-1 right-1"
                  />
                </view>
              </template>
            </view>
          </view>
        </scroll-view>
      </view>

      <!-- 用途输入 -->
      <uni-easyinput
        type="text"
        v-model="bookingForm.purpose"
        placeholder="请输入用途（课程、活动等）"
        class="w-full box-border mb-6"
      />

      <!-- 提交预约按钮 -->
      <button
        class="w-full bg-primary-500 text-white rounded-full font-semibold flex items-center justify-center active:scale-95 transition-transform"
        :disabled="!canSubmit"
        @click="submitBooking"
      >
        <base-icon
          v-if="!canSubmit"
          type="lock-closed"
          size="18"
          color="#ccc"
          class="mr-2"
        />
        <text>{{ canSubmit ? '提交预约申请' : '预约时间冲突，无法提交' }}</text>
      </button>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

const bookingForm = ref({
  booking_date: '',
  selectedTimeSegments: [],
  classroom_info_classroom_info_id_1: '',
  purpose: ''
})

const selectedBuilding = ref('')
const buildingOptions = ref([])
const classrooms = ref([])
const filteredClassrooms = computed(() => {
  if (!selectedBuilding.value) return []
  return classrooms.value
    .filter(c => c.building_enum_building_enum_id_1 === selectedBuilding.value)
    .map(c => ({ value: c.classroom_info_id, text: c.room_number }))
})

const todayString = getTodayString()
const maxDate = getMaxDateString()

const canSubmit = ref(false)
const timeSegments = ref([]) 
const segmentWidth = 60

onLoad(async () => {
  await loadBuildingOptions()
  await loadClassrooms()
  resetBookingForm()
})

watch(() => bookingForm.value.classroom_info_classroom_info_id_1, () => {
  if (bookingForm.value.booking_date) {
    loadAvailableTimeSegments(bookingForm.value.classroom_info_classroom_info_id_1, bookingForm.value.booking_date)
  }
  bookingForm.value.selectedTimeSegments = []
  canSubmit.value = false
})

watch(() => bookingForm.value.booking_date, () => {
  if (bookingForm.value.classroom_info_classroom_info_id_1) {
    loadAvailableTimeSegments(bookingForm.value.classroom_info_classroom_info_id_1, bookingForm.value.booking_date)
  }
  bookingForm.value.selectedTimeSegments = []
  canSubmit.value = false
})

watch(() => bookingForm.value.selectedTimeSegments, (newVal) => {
  canSubmit.value = newVal.length > 0 && bookingForm.value.classroom_info_classroom_info_id_1 && bookingForm.value.purpose.trim() !== ''
})

async function loadBuildingOptions() {
  const res = await proxy.$cf.table.list({ table_name: 'building_enum', param: {} })
  if (res.success) buildingOptions.value = res.data.map(b => ({ value: b.building_enum_id, text: b.building_name }))
  else buildingOptions.value = []
}

async function loadClassrooms() {
  const res = await proxy.$cf.table.list({ table_name: 'classroom_info', param: {} })
  if (res.success) classrooms.value = res.data
  else classrooms.value = []
}

function onBuildingChange(value) {
  selectedBuilding.value = value
  bookingForm.value.classroom_info_classroom_info_id_1 = ''
  bookingForm.value.selectedTimeSegments = []
  canSubmit.value = false
}

function onBookingDateChange() {
  bookingForm.value.selectedTimeSegments = []
  canSubmit.value = false
}

async function loadAvailableTimeSegments(classroomId, date) {
  if (!classroomId || !date) {
    timeSegments.value = []
    return
  }

  try {
    const response = await fetchAvailableTimeSegmentsMock(classroomId, date)
    if (response && response.success) {
      timeSegments.value = response.data.map(seg => ({
        label: seg.label,
        start: seg.start,
        end: seg.end,
        status: seg.status,
        selected: false
      }))
    } else {
      timeSegments.value = []
    }
  } catch (error) {
    timeSegments.value = []
  }
}

async function fetchAvailableTimeSegmentsMock(classroomId, date) {
  return new Promise(resolve => {
    setTimeout(() => {
      const segments = []
      for (let hour = 8; hour < 20; hour++) {
        segments.push({ label: `${String(hour).padStart(2, '0')}:00`, start: `${String(hour).padStart(2, '0')}:00`, end: `${String(hour).padStart(2, '0')}:30`, status: 'available' })
        segments.push({ label: `${String(hour).padStart(2, '0')}:30`, start: `${String(hour).padStart(2, '0')}:30`, end: `${String(hour+1).padStart(2, '0')}:00`, status: hour === 12 ? 'unavailable' : 'available' })
      }
      resolve({ success: true, data: segments })
    }, 300)
  })
}

function toggleSegmentSelection(index) {
  const seg = timeSegments.value[index]
  if (!seg || seg.status !== 'available') return

  seg.selected = !seg.selected
  bookingForm.value.selectedTimeSegments = timeSegments.value.filter(s => s.selected).map(s => s.label)
}

function getSegmentTooltip(segment) {
  if (!segment || !segment.status) return ''
  if (segment.status === 'unavailable') return '该时段已约满'
  return ''
}

async function submitBooking() {
  if (!canSubmit.value) {
    proxy.$cf.toast({ message: '请完成预约信息并选择可用时间段', level: 'error' })
    return
  }

  try {
    const userRes = await proxy.$cf.login.getLoginUser()
    if (!userRes.success) {
      await proxy.$cf.navigate.to({ url: '/pages/login/index', type: 'page' })
      return
    }

    const now = new Date()
    const pad = n => ('0' + n).slice(-2)
    const formattedTime = `${now.getFullYear()}-${pad(now.getMonth()+1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`

    const timePeriodsString = bookingForm.value.selectedTimeSegments.join(',')

    const res = await proxy.$cf.table.add({
      table_name: 'booking_info',
      param: {
        user_info_user_info_id_1: userRes.data.user_info_id,
        classroom_info_classroom_info_id_1: bookingForm.value.classroom_info_classroom_info_id_1,
        booking_date: bookingForm.value.booking_date,
        time_period: timePeriodsString,
        purpose: bookingForm.value.purpose.trim(),
        booking_status_enum_booking_status_enum_id_1: 1,
        create_time: formattedTime
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '预约申请提交成功', level: 'success' })
      resetBookingForm()
    } else {
      proxy.$cf.toast({ message: res.message || '提交失败', level: 'error' })
    }
  } catch {
    proxy.$cf.toast({ message: '网络错误，请稍后重试', level: 'error' })
  }
}

function resetBookingForm() {
  bookingForm.value = {
    booking_date: todayString,
    selectedTimeSegments: [],
    classroom_info_classroom_info_id_1: '',
    purpose: ''
  }
  canSubmit.value = false
  selectedBuilding.value = ''
}

function getTodayString() {
  const now = new Date()
  return `${now.getFullYear()}-${('0' + (now.getMonth() + 1)).slice(-2)}-${('0' + now.getDate()).slice(-2)}`
}

function getMaxDateString() {
  const now = new Date()
  now.setDate(now.getDate() + 30)
  return `${now.getFullYear()}-${('0' + (now.getMonth() + 1)).slice(-2)}-${('0' + now.getDate()).slice(-2)}`
}
</script>

<style scoped>
.time-segment {
  height: 40px;
  min-width: 60px;
  line-height: 40px;
  text-align: center;
  border-radius: 0.375rem;
  font-size: 0.85rem;
  user-select: none;
  transition: background-color 0.2s, color 0.2s, border 0.2s;
  cursor: pointer;
}
.time-segment.available {
  background-color: #66b5ff;
  color: white;
}
.time-segment.available:hover {
  background-color: #339cff;
}
.time-segment.selected {
  background-color: #1a7ed6;
  color: white;
  border: 2px solid #ffffff;
  position: relative;
}
.time-segment.selected::after {
  content: '\2713';
  position: absolute;
  top: 2px;
  right: 4px;
  color: white;
  font-weight: bold;
  font-size: 0.9rem;
}
.time-segment.unavailable {
  background-color: #d1d5db;
  color: #6b7280;
  cursor: default;
}
.time-segment.unavailable:hover {
  background-color: #d1d5db;
}
</style>