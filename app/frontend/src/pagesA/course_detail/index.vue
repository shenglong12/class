<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-gradient-to-b from-primary-50 to-neutral-50">
      <!-- 课程详情卡片 -->
      <view class="bg-white rounded-2xl shadow-xl p-6 mb-4 border border-neutral-100">
        <!-- 课程标题区域 -->
        <view class="flex flex-col gap-2 mb-4">
          <view class="flex items-center gap-3">
            <base-icon type="calendar-filled" size="28" color="#339cff" />
            <text class="text-2xl font-bold text-primary-800 block truncate">
              {{ courseDetail.course_name || '课程详情' }}
            </text>
          </view>
          <view class="flex flex-wrap gap-2">
            <uni-tag 
              :text="courseDetail.college" 
              size="small" 
              type="primary" 
              circle 
              class="shadow-sm"
            />
            <uni-tag 
              :text="courseDetail.major" 
              size="small" 
              type="success" 
              circle 
              class="shadow-sm"
            />
          </view>
        </view>

        <!-- 基本信息网格 -->
        <view class="grid grid-cols-2 gap-4 mb-6">
          <!-- 教室信息 -->
          <view class="bg-primary-50 rounded-xl p-3 border border-primary-100">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="location-filled" size="18" color="#1a7ed6" />
              <text class="text-sm font-semibold text-primary-700">教室</text>
            </view>
            <text class="text-lg font-bold text-primary-800 truncate">{{ courseDetail.room_number || '--' }}</text>
          </view>

          <!-- 时间段 -->
          <view class="bg-warning-50 rounded-xl p-3 border border-warning-100">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="headphones" size="18" color="#cc8214" />
              <text class="text-sm font-semibold text-warning-700">时间段</text>
            </view>
            <text class="text-lg font-bold text-warning-800 truncate">{{ courseDetail.time_period || '--' }}</text>
          </view>
        </view>

        <!-- 日期周期信息 -->
        <view class="bg-neutral-50 rounded-xl p-4 mb-4 border border-neutral-200">
          <view class="flex flex-col gap-3">
            <view class="flex justify-between items-center">
              <text class="text-sm font-medium text-neutral-600">日期范围：</text>
              <text class="text-sm font-semibold text-neutral-800">
                {{ formatDate(courseDetail.start_date) }} - {{ formatDate(courseDetail.end_date) }}
              </text>
            </view>
            <view class="flex justify-between items-center">
              <text class="text-sm font-medium text-neutral-600">上课星期：</text>
              <text class="text-sm font-semibold text-neutral-800 truncate">{{ courseDetail.weekdays || '--' }}</text>
            </view>
          </view>
        </view>

        <!-- 授课内容 -->
        <view class="bg-success-50 rounded-xl p-4 border border-success-100">
          <view class="flex items-center gap-2 mb-3">
            <base-icon type="info-filled" size="20" color="#29995a" />
            <text class="text-lg font-semibold text-success-800">授课内容</text>
          </view>
          
          <view class="bg-white rounded-lg p-3 border border-success-200 max-h-72 overflow-auto">
            <text class="text-neutral-700 leading-relaxed" selectable="true">
              {{ courseDetail.content || '暂无授课内容。' }}
            </text>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-if="(!courseDetail.course_info_id && courseDetail.course_info_id !== 0) && !loading" class="flex flex-col items-center justify-center py-20">
        <base-icon type="info" size="60" color="#a3a3a3" />
        <text class="text-neutral-500 mt-4 text-lg font-semibold">未找到课程</text>
        <text class="text-neutral-400 mt-2 text-center max-w-xs">您查看的课程不存在或已被删除。</text>
      </view>

      <!-- 加载状态 -->
      <view v-if="loading" class="flex flex-col items-center justify-center py-20">
        <base-icon type="spinner-cycle" size="48" color="#339cff" class="animate-spin" />
        <text class="text-primary-600 mt-4 text-lg font-semibold">课程详情加载中...</text>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const courseDetail = ref({
  course_info_id: null,
  course_name: '',
  college: '',
  major: '',
  room_number: '',
  start_date: '',
  end_date: '',
  weekdays: '',
  time_period: '',
  content: ''
})
const loading = ref(false)
const courseId = ref(null)

// ------------------ Lifecycle ------------------
onLoad(async (option) => {
  if (option && option.course_info_id) {
    const id = Number(option.course_info_id)
    if (isNaN(id)) {
      proxy.$cf.toast({ message: '无效的课程ID', level: 'error', duration: 3000 })
      return
    }
    courseId.value = id
    await fetchCourseDetail()
  } else {
    proxy.$cf.toast({ 
      message: '课程ID不能为空', 
      level: 'error',
      duration: 3000
    })
  }
})

// ------------------ Methods ------------------

// 获取课程详情
async function fetchCourseDetail() {
  if (!courseId.value) return
  
  loading.value = true
  try {
    const res = await proxy.$cf.table.get({
      table_name: 'course_info',
      param: { course_info_id: courseId.value }
    })
    
    if (res.success && res.data) {
      courseDetail.value = {
        ...res.data,
        room_number: res.data.room_number || '--'
      }
    } else {
      proxy.$cf.toast({ 
        message: res.message || '加载课程详情失败', 
        level: 'error',
        duration: 3000
      })
    }
  } catch (error) {
    console.error('获取课程详情出错:', error)
    proxy.$cf.toast({ 
      message: '网络异常，请重试', 
      level: 'error',
      duration: 3000
    })
  } finally {
    loading.value = false
  }
}

// 日期格式化
function formatDate(dateString) {
  if (!dateString) return '--'
  
  try {
    const date = new Date(dateString.replace(/-/g, '/'))
    if (isNaN(date.getTime())) return dateString
    
    const year = date.getFullYear()
    const month = ('0' + (date.getMonth() + 1)).slice(-2)
    const day = ('0' + date.getDate()).slice(-2)
    
    return `${year}-${month}-${day}`
  } catch (error) {
    return dateString
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

.leading-relaxed {
  line-height: 1.625;
}
</style>