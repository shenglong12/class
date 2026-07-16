<template>
  <base-layout>
    <view class="page">
      <view class="page-header">
        <text class="page-title">{{ classroomDetail.room_number || '教室详情' }}</text>
        <text class="page-subtitle">教学楼：{{ classroomDetail.building_name || '--' }}</text>
      </view>

      <view class="page-body">
        <view v-show="activeTab === 'schedule'" class="tab-panel">
          <view class="section-header">
            <text class="section-title">未来 7 天课程</text>
            <text v-if="schedule.length" class="section-count">{{ schedule.length }} 条</text>
          </view>

          <view v-if="loadingSchedule" class="state-box">
            <base-icon type="spinner-cycle" size="24" class="animate-spin text-primary-500" />
            <text class="state-text">课程加载中...</text>
          </view>

          <view v-else-if="schedule.length === 0" class="state-box">
            <text class="state-text">该教室未来 7 天暂无课程安排</text>
          </view>

          <view v-else class="course-list">
            <view
              v-for="(course, index) in schedule"
              :key="getScheduleKey(course, index)"
              class="course-card"
            >
              <view class="course-card-header">
                <text class="course-name">{{ course.course_name || '未命名课程' }}</text>
                <text class="course-time">{{ course.weekdays || '--' }} {{ course.time_period || '--' }}</text>
              </view>
              <text class="course-date">{{ formatCourseDateRange(course) }}</text>
              <text class="course-meta">{{ formatCollegeMajor(course) }}</text>
              <text class="course-content">{{ course.content || '暂无课程内容' }}</text>
            </view>
          </view>
        </view>

        <view v-show="activeTab === 'booking'" class="tab-panel">
          <text class="section-title">预约教室</text>

          <view v-if="!isLoggedIn" class="login-tip">
            <text>请登录后再进行预约。</text>
          </view>

          <view v-else>
            <uni-forms :modelValue="bookingForm" label-position="top" label-width="100%">
              <uni-forms-item label="预约日期" name="booking_date" required>
                <uni-datetime-picker
                  v-model="bookingForm.booking_date"
                  type="date"
                  :start="todayString"
                  class="w-full box-border"
                  placeholder="请选择预约日期"
                  @change="onBookingDateChange"
                />
              </uni-forms-item>

              <uni-forms-item label="开始时间" name="start_time" required>
                <uni-datetime-picker
                  v-model="bookingForm.start_time"
                  type="time"
                  :start="minTime"
                  :end="maxTime"
                  :step="1800"
                  class="w-full box-border"
                  placeholder="请选择开始时间"
                  @change="onStartTimeChange"
                />
              </uni-forms-item>

              <uni-forms-item label="结束时间" name="end_time" required>
                <uni-datetime-picker
                  v-model="bookingForm.end_time"
                  type="time"
                  :start="minTime"
                  :end="maxTime"
                  :step="1800"
                  class="w-full box-border"
                  placeholder="请选择结束时间"
                  @change="onEndTimeChange"
                />
              </uni-forms-item>

              <uni-forms-item label="学院" name="college" required>
                <uni-easyinput v-model="bookingForm.college" type="text" clearable disabled class="w-full box-border" />
              </uni-forms-item>

              <uni-forms-item label="专业" name="major" required>
                <uni-easyinput v-model="bookingForm.major" type="text" clearable disabled class="w-full box-border" />
              </uni-forms-item>

              <uni-forms-item label="用途" name="purpose" required>
                <uni-easyinput
                  v-model="bookingForm.purpose"
                  type="text"
                  clearable
                  class="w-full box-border"
                  placeholder="请输入预约用途"
                />
              </uni-forms-item>

              <view v-if="timeConflict" class="warning-text">
                当前预约时间与已有预约冲突，请重新选择。
              </view>

              <button class="submit-btn" :disabled="!canSubmit" @click="submitBooking">
                提交预约
              </button>
            </uni-forms>
          </view>
        </view>

        <view v-show="activeTab === 'repair'" class="tab-panel">
          <text class="section-title">教室报修</text>

          <view v-if="!isLoggedIn" class="login-tip">
            <text>登录后才能提交报修。</text>
          </view>

          <view v-else>
            <uni-forms :modelValue="repairForm" label-position="top" label-width="100%">
              <uni-forms-item label="报修内容" name="content" required>
                <uni-easyinput
                  v-model="repairForm.content"
                  type="textarea"
                  class="w-full box-border"
                  placeholder="请输入详细报修内容"
                  :inputStyle="{ minHeight: '120px' }"
                />
              </uni-forms-item>

              <button class="submit-btn" :disabled="repairSubmitting" @click="submitRepair">
                {{ repairSubmitting ? '提交中...' : '提交报修' }}
              </button>
            </uni-forms>
          </view>
        </view>
      </view>

      <view class="tabbar">
        <button
          class="tabbar-item"
          :class="{ active: activeTab === 'schedule' }"
          @click="activeTab = 'schedule'"
        >
          课表
        </button>
        <button
          class="tabbar-item"
          :class="{ active: activeTab === 'booking' }"
          @click="handleBookingTab"
        >
          预约
        </button>
        <button
          class="tabbar-item"
          :class="{ active: activeTab === 'repair' }"
          @click="handleRepairTab"
        >
          报修
        </button>
      </view>

      <uni-popup ref="loginRedirectPopup" type="center">
        <view class="login-popup">
          <text class="login-popup-title">登录成功</text>
          <button class="submit-btn" @click="handleLoginRedirectConfirm">返回教室详情</button>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

const routeParams = ref(null)
const classroomDetail = ref({})
const activeTab = ref('schedule')
const schedule = ref([])
const loadingSchedule = ref(false)
const isLoggedIn = ref(false)
const loginRedirectPopup = ref(null)

const bookingForm = ref({
  booking_date: '',
  start_time: '',
  end_time: '',
  college: '',
  major: '',
  purpose: ''
})

const repairForm = ref({
  content: ''
})

const repairSubmitting = ref(false)
const timeConflict = ref(false)
const canSubmit = ref(false)

const todayString = getTodayString()
const minTime = '06:00'
const maxTime = '23:00'
let loginReturnClassroomId = null

onLoad((option) => {
  routeParams.value = option
  initializePage()
})

async function initializePage() {
  const id = Number(routeParams.value?.classroom_info_id)
  if (!id) {
    proxy.$cf.toast({ message: '缺少教室 ID', level: 'error' })
    return
  }

  await loadClassroomDetail(id)
  await loadSchedule(id)
  await checkLoginStatus()
  resetBookingForm()
}

async function loadClassroomDetail(id) {
  const res = await proxy.$cf.table.get({
    table_name: 'classroom_info',
    param: { classroom_info_id: id }
  })

  if (res.success && res.data) {
    classroomDetail.value = res.data
  }
}

async function loadSchedule(classroomId) {
  loadingSchedule.value = true

  try {
    const now = new Date()
    const nextWeek = new Date(now)
    nextWeek.setDate(now.getDate() + 7)

    const res = await proxy.$cf.table.list({
      table_name: 'course_info',
      param: {
        classroom_info_classroom_info_id_1: classroomId
      }
    })

    if (!res.success) {
      schedule.value = []
      return
    }

    const courseList = normalizeCourseList(res.data)
    const nextWeekCourses = courseList.filter((course) => isCourseInNextWeek(course, now, nextWeek))
    schedule.value = sortSchedule(nextWeekCourses)
  } catch {
    schedule.value = []
  } finally {
    loadingSchedule.value = false
  }
}

async function checkLoginStatus() {
  const res = await proxy.$cf.login.getLoginUser()
  isLoggedIn.value = res.success

  if (isLoggedIn.value && res.data) {
    bookingForm.value.college = res.data.college || ''
    bookingForm.value.major = res.data.major || ''
  }
}

function handleBookingTab() {
  if (!isLoggedIn.value) {
    redirectToLogin()
    return
  }
  activeTab.value = 'booking'
}

function handleRepairTab() {
  if (!isLoggedIn.value) {
    redirectToLogin()
    return
  }
  activeTab.value = 'repair'
}

function redirectToLogin() {
  const classroomId = routeParams.value?.classroom_info_id
  loginReturnClassroomId = classroomId
  proxy.$cf.navigate.to({
    url: `/pages/login/index?redirect=classroom_detail&classroom_info_id=${classroomId}`,
    type: 'page'
  })
}

async function handleLoginRedirectConfirm() {
  if (!loginReturnClassroomId) return
  await initializePage()
  loginRedirectPopup.value?.close()
  activeTab.value = 'booking'
}

function getTodayString() {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

function normalizeCourseList(data) {
  if (Array.isArray(data)) return data
  if (Array.isArray(data?.records)) return data.records
  if (data && typeof data === 'object') return [data]
  return []
}

function parseCourseDate(value) {
  if (!value) return null
  const date = new Date(value)
  return Number.isNaN(date.getTime()) ? null : date
}

function toStartOfDay(date) {
  return new Date(date.getFullYear(), date.getMonth(), date.getDate(), 0, 0, 0, 0)
}

function toEndOfDay(date) {
  return new Date(date.getFullYear(), date.getMonth(), date.getDate(), 23, 59, 59, 999)
}

function isCourseInNextWeek(course, now, nextWeek) {
  const start = parseCourseDate(course.start_date)
  const end = parseCourseDate(course.end_date)
  if (!start || !end) return false

  const startDay = toStartOfDay(start)
  const endDay = toEndOfDay(end)
  const currentDay = toStartOfDay(now)
  const nextWeekDay = toEndOfDay(nextWeek)

  return startDay <= nextWeekDay && endDay >= currentDay
}

function sortSchedule(list) {
  return [...list].sort((a, b) => {
    const startA = parseCourseDate(a.start_date)?.getTime() ?? 0
    const startB = parseCourseDate(b.start_date)?.getTime() ?? 0
    if (startA !== startB) {
      return startA - startB
    }
    return String(a.time_period || '').localeCompare(String(b.time_period || ''))
  })
}

function getScheduleKey(course, index) {
  return [
    course.course_info_id ?? `course-${index}`,
    course.start_date ?? '',
    course.end_date ?? '',
    course.time_period ?? ''
  ].join('-')
}

function formatDisplayDate(value) {
  const date = parseCourseDate(value)
  if (!date) return '--'
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

function formatCourseDateRange(course) {
  return `${formatDisplayDate(course.start_date)} - ${formatDisplayDate(course.end_date)}`
}

function formatCollegeMajor(course) {
  const college = course.college || '--'
  const major = course.major || '--'
  return `${college} / ${major}`
}

function resetBookingForm() {
  bookingForm.value = {
    booking_date: todayString,
    start_time: '',
    end_time: '',
    college: bookingForm.value.college || '',
    major: bookingForm.value.major || '',
    purpose: ''
  }
  timeConflict.value = false
  canSubmit.value = false
}

function onBookingDateChange() {
  bookingForm.value.start_time = ''
  bookingForm.value.end_time = ''
  checkTimeConflict()
}

function onStartTimeChange() {
  if (!bookingForm.value.start_time) return
  if (bookingForm.value.end_time && bookingForm.value.end_time <= bookingForm.value.start_time) {
    bookingForm.value.end_time = ''
  }
  checkTimeConflict()
}

function onEndTimeChange() {
  if (!bookingForm.value.end_time) return
  if (bookingForm.value.start_time && bookingForm.value.end_time <= bookingForm.value.start_time) {
    proxy.$cf.toast({ message: '结束时间必须晚于开始时间', level: 'error' })
    bookingForm.value.end_time = ''
  }
  checkTimeConflict()
}

function timeStrToMinutes(value) {
  if (!value) return null
  const [hour, minute] = value.split(':').map((item) => parseInt(item, 10))
  return hour * 60 + minute
}

async function checkTimeConflict() {
  timeConflict.value = false
  canSubmit.value = false

  if (!bookingForm.value.booking_date || !bookingForm.value.start_time || !bookingForm.value.end_time) {
    return
  }

  const start = timeStrToMinutes(bookingForm.value.start_time)
  const end = timeStrToMinutes(bookingForm.value.end_time)
  if (start === null || end === null || end <= start) {
    return
  }

  const param = {
    classroom_info_classroom_info_id_1: classroomDetail.value.classroom_info_id,
    booking_date: bookingForm.value.booking_date,
    start_time: bookingForm.value.start_time,
    end_time: bookingForm.value.end_time
  }

  try {
    const res = await proxy.$cf.table.list({
      table_name: 'booking_info',
      param: {
        classroom_info_classroom_info_id_1: param.classroom_info_classroom_info_id_1,
        booking_date: param.booking_date,
        booking_status_enum_booking_status_enum_id_1: 2
      }
    })

    if (!res.success) {
      canSubmit.value = true
      return
    }

    const bookings = normalizeCourseList(res.data)
    const conflicts = bookings.some((item) => !(param.end_time <= item.start_time || param.start_time >= item.end_time))

    timeConflict.value = conflicts
    canSubmit.value = !conflicts
  } catch {
    canSubmit.value = true
  }
}

async function submitBooking() {
  if (!canSubmit.value) {
    proxy.$cf.toast({ message: '时间冲突，请重新选择预约时间', level: 'error' })
    return
  }

  proxy.$cf.loading.showLoading({ title: '预约提交中...' })

  try {
    const userRes = await proxy.$cf.login.getLoginUser()
    if (!userRes.success) {
      proxy.$cf.toast({ message: '登录失效，请重新登录', level: 'error' })
      return
    }

    const now = new Date()
    const pad = (value) => String(value).padStart(2, '0')
    const formattedTime = `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`

    const res = await proxy.$cf.table.add({
      table_name: 'booking_info',
      param: {
        user_info_user_info_id_1: userRes.data.user_info_id,
        classroom_info_classroom_info_id_1: classroomDetail.value.classroom_info_id,
        booking_date: bookingForm.value.booking_date,
        start_time: bookingForm.value.start_time,
        end_time: bookingForm.value.end_time,
        purpose: bookingForm.value.purpose.trim(),
        booking_status_enum_booking_status_enum_id_1: 1,
        create_time: formattedTime
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '预约申请已提交', level: 'success' })
      resetBookingForm()
    } else {
      proxy.$cf.toast({ message: res.message || '预约提交失败', level: 'error' })
    }
  } catch {
    proxy.$cf.toast({ message: '网络异常，请稍后重试', level: 'error' })
  } finally {
    proxy.$cf.loading.hideLoading()
  }
}

async function submitRepair() {
  if (!repairForm.value.content.trim()) {
    proxy.$cf.toast({ message: '请填写报修内容', level: 'error' })
    return
  }

  repairSubmitting.value = true

  try {
    const userRes = await proxy.$cf.login.getLoginUser()
    if (!userRes.success) {
      proxy.$cf.toast({ message: '登录失效，请重新登录', level: 'error' })
      return
    }

    const now = new Date()
    const pad = (value) => String(value).padStart(2, '0')
    const formattedTime = `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`

    const res = await proxy.$cf.table.add({
      table_name: 'repair_info',
      param: {
        user_info_user_info_id_1: userRes.data.user_info_id,
        classroom_info_classroom_info_id_1: classroomDetail.value.classroom_info_id,
        content: repairForm.value.content.trim(),
        create_time: formattedTime,
        repair_status_enum_repair_status_enum_id_1: 1
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '报修申请已提交', level: 'success' })
      repairForm.value.content = ''
    } else {
      proxy.$cf.toast({ message: res.message || '提交失败', level: 'error' })
    }
  } catch {
    proxy.$cf.toast({ message: '网络异常，请稍后重试', level: 'error' })
  } finally {
    repairSubmitting.value = false
  }
}
</script>

<style>
.page {
  min-height: 100vh;
  padding-bottom: 64px;
  display: flex;
  flex-direction: column;
  background: #f8fafc;
}

.page-header {
  background: #ffffff;
  padding: 16px;
  box-shadow: 0 4px 12px rgba(15, 23, 42, 0.06);
  border-bottom: 1px solid #e5e7eb;
}

.page-title {
  display: block;
  font-size: 20px;
  font-weight: 700;
  color: #1d4ed8;
}

.page-subtitle {
  display: block;
  margin-top: 6px;
  font-size: 14px;
  color: #6b7280;
}

.page-body {
  flex: 1;
  overflow: auto;
  padding: 16px;
}

.tab-panel {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.section-title {
  font-size: 18px;
  font-weight: 700;
  color: #111827;
}

.section-count {
  font-size: 12px;
  color: #2563eb;
}

.state-box {
  min-height: 140px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background: #ffffff;
  border-radius: 16px;
  color: #6b7280;
}

.state-text {
  font-size: 14px;
  color: #6b7280;
}

.course-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.course-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 14px;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
}

.course-card-header {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.course-name {
  font-size: 16px;
  font-weight: 700;
  color: #1d4ed8;
}

.course-time,
.course-date,
.course-meta,
.course-content {
  display: block;
  font-size: 13px;
  line-height: 1.6;
  color: #4b5563;
}

.login-tip {
  padding: 12px 14px;
  border-radius: 12px;
  background: #fef2f2;
  color: #dc2626;
}

.warning-text {
  margin-bottom: 10px;
  color: #dc2626;
  font-size: 14px;
}

.submit-btn {
  width: 100%;
  background: #3b82f6;
  color: #ffffff;
  border-radius: 9999px;
  font-weight: 700;
  padding: 10px 0;
}

.tabbar {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  background: #ffffff;
  border-top: 1px solid #d1d5db;
  box-shadow: 0 -2px 8px rgba(15, 23, 42, 0.08);
}

.tabbar-item {
  flex: 1;
  padding: 12px 0;
  text-align: center;
  color: #6b7280;
  background: transparent;
}

.tabbar-item.active {
  color: #2563eb;
  font-weight: 700;
  border-top: 2px solid #2563eb;
}

.login-popup {
  width: 288px;
  background: #ffffff;
  border-radius: 16px;
  padding: 16px;
}

.login-popup-title {
  display: block;
  text-align: center;
  font-size: 18px;
  font-weight: 700;
  color: #374151;
  margin-bottom: 14px;
}

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
