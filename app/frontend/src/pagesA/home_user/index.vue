
<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-gradient-to-b from-primary-50 to-neutral-50">
      <!-- 顶部标题 -->
      <view class="mb-6 text-center">
        <text class="text-3xl font-extrabold text-primary-700">教室助手</text>
        <text class="block mt-2 text-neutral-600 text-body">扫码 · 预约 · 报修，一站式服务</text>
      </view>

      <!-- 主要功能卡片 -->
      <view class="grid grid-cols-2 gap-4 mb-6">
        <!-- 新增 扫描教室按钮 -->
        <view 
          class="bg-white rounded-2xl shadow-xl p-4 flex flex-col items-center justify-center transition-all duration-300 active:scale-95 cursor-pointer"
          @click="handleScanClassroom"
          role="button"
          tabindex="0"
          aria-label="扫描教室"
        >
          <view class="w-16 h-16 bg-primary-100 rounded-full flex items-center justify-center mb-3">
            <base-icon type="scan" size="32" color="#339cff" />
          </view>
          <text class="font-semibold text-primary-700 text-center">扫描教室</text>
          <text class="text-xs text-neutral-500 text-center mt-1">查看未来7天课程</text>
        </view>

        <!-- 扫码历史 -->
        <view 
          class="bg-white rounded-2xl shadow-xl p-4 flex flex-col items-center justify-center transition-all duration-300 active:scale-95 cursor-pointer"
          @click="showScanHistory"
          role="button"
          tabindex="0"
          aria-label="查看扫码历史"
        >
          <view class="w-16 h-16 bg-secondary-100 rounded-full flex items-center justify-center mb-3">
            <base-icon type="headphones" size="32" color="#c166ff" />
          </view>
          <text class="font-semibold text-secondary-700 text-center">扫码历史</text>
          <text class="text-xs text-neutral-500 text-center mt-1">查看历史记录</text>
        </view>
      </view>

      <!-- 最近扫码历史 -->
      <view class="bg-white rounded-2xl shadow-lg p-4 mb-6">
        <view class="flex items-center justify-between mb-4">
          <text class="text-lg font-bold text-primary-800">最近扫码</text>
          <button 
            class="bg-primary-500 text-white rounded-full px-3 flex items-center font-semibold"
            @click="showScanHistory"
          >
            <base-icon type="more" size="16" class="mr-1" color="#fff" />
            查看全部
          </button>
        </view>
        
        <view v-if="recentScans.length > 0" class="">
          <view 
            v-for="scan in recentScans" 
            :key="scan.scan_id"
            class="bg-neutral-50 rounded-xl p-3 border border-neutral-200 mb-3 transition-all duration-200 active:scale-95 cursor-pointer"
            @click="showCourseDetailByScan(scan)"
          >
            <view class="mb-2">
              <text class="font-semibold text-primary-700 block truncate">{{ scan.building_name }} - {{ scan.room_number }}</text>
              <uni-tag 
                :text="formatDateTime(scan.scan_time)" 
                size="small" 
                type="default" 
              />
            </view>
            <text class="text-sm text-neutral-600">
              扫描时间：{{ formatDateTime(scan.scan_time) }}
            </text>
          </view>
        </view>
        
        <view v-else class="text-center py-8">
          <base-icon type="search" size="48" color="#d4d4d4" />
          <text class="block mt-2 text-neutral-400">暂无扫码历史</text>
          <text class="text-sm text-neutral-500">请扫描教室二维码开始使用</text>
        </view>
      </view>

      <!-- 今日课程提醒 -->
      <view class="bg-white rounded-2xl shadow-lg p-4">
        <view class="flex items-center justify-between mb-4">
          <text class="text-lg font-bold text-primary-800">今日课程</text>
          <base-icon type="calendar-filled" size="20" color="#339cff" />
        </view>
        
        <view v-if="todayCourses.length > 0" class="">
          <view 
            v-for="course in todayCourses" 
            :key="course.course_info_id"
            class="bg-primary-50 rounded-xl p-3 border border-primary-200 mb-3 transition-all duration-200 active:scale-95"
            @click="showCourseDetailPopup(course)"
          >
            <view class="mb-1">
              <text class="font-semibold text-primary-700 block truncate">{{ course.course_name }}</text>
              <uni-tag 
                :text="course.time_period" 
                size="small" 
                type="primary" 
                circle 
              />
            </view>
            <text class="text-primary-600 block truncate">{{ course.building_name }} - {{ course.room_number }}</text>
            <text class="block text-xs text-primary-500 mt-1 line-clamp-2">
              {{ course.content || '暂无课程描述' }}
            </text>
          </view>
        </view>
        
        <view v-else class="text-center py-6">
          <base-icon type="calendar" size="36" color="#d4d4d4" />
          <text class="block mt-2 text-neutral-400">今日无课程安排</text>
        </view>
      </view>

      <!-- 扫码历史弹窗 -->
      <uni-popup ref="scanHistoryPopup" type="bottom" :style="{paddingBottom: '60px'}">
        <view class="bg-white rounded-t-3xl p-6 max-h-[calc(100vh-60px)]">
          <view class="flex items-center justify-between mb-4">
            <text class="text-xl font-bold text-primary-800">扫码历史</text>
            <view class="p-2 cursor-pointer" @click="closeScanHistory">
              <base-icon type="close" size="20" color="#475569" />
            </view>
          </view>
          
          <scroll-view scroll-y="true" class="max-h-[calc(100vh-200px)]">
            <view v-if="scanHistory.length > 0">
              <view 
                v-for="scan in scanHistory" 
                :key="scan.scan_id"
                class="bg-neutral-50 rounded-xl p-4 border border-neutral-200 mb-3"
                @click="showCourseDetailByScan(scan)"
              >
                <view class="mb-2">
                  <text class="font-semibold text-primary-700 block truncate">{{ scan.building_name }} - {{ scan.room_number }}</text>
                  <uni-tag 
                    :text="formatDateTime(scan.scan_time)" 
                    size="small" 
                    type="default" 
                  />
                </view>
                <text class="text-sm text-neutral-600">
                  扫描时间：{{ formatDateTime(scan.scan_time) }}
                </text>
              </view>
            </view>
            <view v-else class="text-center py-8">
              <base-icon type="search" size="48" color="#d4d4d4" />
              <text class="block mt-2 text-neutral-400">暂无扫码历史</text>
            </view>
          </scroll-view>
        </view>
      </uni-popup>

      <!-- 课程详情弹窗 -->
      <uni-popup ref="courseDetailPopup" type="center" :style="{maxHeight: 'calc(100vh - 60px)'}">
        <view class="bg-white rounded-2xl p-6 w-80 max-h-[calc(100vh-100px)] overflow-y-auto">
          <view class="flex items-center justify-between mb-4">
            <text class="text-xl font-bold text-primary-800">课程详情</text>
            <view class="p-2 cursor-pointer" @click="closeCourseDetail">
              <base-icon type="close" size="20" color="#475569" />
            </view>
          </view>
          
          <view v-if="selectedCourse" class="mb-4">
            <view class="mb-4">
              <text class="block text-sm font-semibold text-neutral-600 mb-1">课程名称</text>
              <text class="text-lg font-bold text-primary-700 truncate">{{ selectedCourse.course_name }}</text>
            </view>
            
            <view class="mb-4">
              <text class="block text-sm font-semibold text-neutral-600 mb-1">教室位置</text>
              <text class="text-primary-600 truncate">{{ selectedCourse.building_name }} - {{ selectedCourse.room_number }}</text>
            </view>
            
            <view class="mb-4">
              <text class="block text-sm font-semibold text-neutral-600 mb-1">上课时间</text>
              <text class="text-primary-600 truncate">{{ selectedCourse.weekdays }}, {{ selectedCourse.time_period }}</text>
              <text class="block text-sm text-neutral-500 mt-1">
                {{ formatDate(selectedCourse.start_date) }} - {{ formatDate(selectedCourse.end_date) }}
              </text>
            </view>
            
            <view v-if="selectedCourse.content" class="mb-4">
              <text class="block text-sm font-semibold text-neutral-600 mb-1">课程描述</text>
              <text class="text-neutral-700">{{ selectedCourse.content }}</text>
            </view>
            
            <view>
              <text class="block text-sm font-semibold text-neutral-600 mb-1">学院专业</text>
              <text class="text-primary-600 truncate">{{ selectedCourse.college }} - {{ selectedCourse.major }}</text>
            </view>
          </view>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

const recentScans = ref([])
const scanHistory = ref([])
const todayCourses = ref([])
const selectedCourse = ref(null)

const scanHistoryPopup = ref(null)
const courseDetailPopup = ref(null)

onLoad(async () => {
  await fetchRecentScans()
  await fetchScanHistory()
  await fetchTodayCourses()
})

// 首页完善“扫描教室”按钮功能
async function handleScanClassroom() {
  try {
    const res = await proxy.$cf.qr.scanCode({ onlyFromCamera: true })
    if (res.success && res.result) {
      const qrString = res.result
      const regex = /^\/classroom\/(\d+)(?:_.+)?$/;
      const match = qrString.match(regex);
      if (!match || match.length < 2) {
        await proxy.$cf.model({
          title: '扫码失败',
          message: '二维码格式无效，请重新扫描或确认二维码有效性',
          confirmText: '重新扫码',
          cancelText: '取消'
        })
        return
      }
      const classroomId = match[1]
      proxy.$cf.loading.showLoading({ title: '加载教室信息...' })
      await proxy.$cf.navigate.to({
        url: `/pages/classroom_detail/index?classroom_info_id=${classroomId}`,
        type: 'page'
      })
      proxy.$cf.loading.hideLoading()
    } else {
      await proxy.$cf.model({
        title: '扫码失败',
        message: '扫码失败，请重新扫码',
        confirmText: '重新扫码',
        cancelText: '取消'
      })
    }
  } catch (err) {
    await proxy.$cf.model({
      title: '扫码异常',
      message: '扫码过程中出现错误，请检查摄像头权限并重试',
      confirmText: '重新扫码',
      cancelText: '取消'
    })
  }
}

async function fetchRecentScans() {
  const res = await proxy.$cf.table.page({
    table_name: "scan_record",
    param: { current: 1, pageSize: 5, order_by: { scan_time: "desc" } }
  })
  if (res.success) recentScans.value = res.data.records
  else recentScans.value = []
}

async function fetchScanHistory() {
  const res = await proxy.$cf.table.page({
    table_name: "scan_record",
    param: { current: 1, pageSize: 50, order_by: { scan_time: "desc" } }
  })
  if (res.success) scanHistory.value = res.data.records
  else scanHistory.value = []
}

async function fetchTodayCourses() {
  const now = new Date()
  const year = now.getFullYear()
  const month = ("0" + (now.getMonth() + 1)).slice(-2)
  const day = ("0" + now.getDate()).slice(-2)
  const today = `${year}-${month}-${day}`
  const res = await proxy.$cf.table.list({
    table_name: "course_info",
    param: { start_date: today }
  })
  if (res.success) todayCourses.value = res.data
  else todayCourses.value = []
}

function showScanHistory() {
  if (scanHistoryPopup.value) scanHistoryPopup.value.open()
}

function closeScanHistory() {
  if (scanHistoryPopup.value) scanHistoryPopup.value.close()
}

function showCourseDetailByScan(scan) {
  selectedCourse.value = null
  if (courseDetailPopup.value) courseDetailPopup.value.open()
}

function showCourseDetailPopup(course) {
  selectedCourse.value = course
  if (courseDetailPopup.value) courseDetailPopup.value.open()
}

function closeCourseDetail() {
  if (courseDetailPopup.value) courseDetailPopup.value.close()
  selectedCourse.value = null
}

function formatDateTime(time) {
  if (!time) return ""
  const dt = new Date(time)
  const Y = dt.getFullYear()
  const M = ("0" + (dt.getMonth() + 1)).slice(-2)
  const D = ("0" + dt.getDate()).slice(-2)
  const h = ("0" + dt.getHours()).slice(-2)
  const m = ("0" + dt.getMinutes()).slice(-2)
  const s = ("0" + dt.getSeconds()).slice(-2)
  return `${Y}-${M}-${D} ${h}:${m}:${s}`
}

function formatDate(time) {
  if (!time) return ""
  const dt = new Date(time)
  const Y = dt.getFullYear()
  const M = ("0" + (dt.getMonth() + 1)).slice(-2)
  const D = ("0" + dt.getDate()).slice(-2)
  return `${Y}-${M}-${D}`
}
</script>

<style>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
