<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-gradient-to-b from-primary-50 to-white">
      <!-- 页面标题 -->
      <view class="mb-6 text-center">
        <text class="text-3xl font-extrabold text-primary-700">教室课程查询</text>
        <text class="block mt-2 text-neutral-600 text-body">请选择教学楼和教室查看未来7天课程</text>
      </view>

      <!-- 选择区域 -->
      <view class="bg-white rounded-2xl shadow-xl p-4 mb-6 border border-neutral-100">
        <!-- 教学楼选择 -->
        <view class="mb-4">
          <text class="block text-subtitle font-semibold text-primary-800 mb-3">选择教学楼</text>
          <uni-data-select
            v-model="selectedBuilding"
            :localdata="buildingOptions"
            clear
            placement="bottom"
            class="w-full box-border"
            placeholder="请选择教学楼..."
            @change="onBuildingChange"
          />
        </view>

        <!-- 教室选择 -->
        <view class="mb-4">
          <text class="block text-subtitle font-semibold text-primary-800 mb-3">选择教室</text>
          <uni-data-select
            v-model="selectedClassroom"
            :localdata="classroomOptions"
            clear
            placement="bottom"
            class="w-full box-border"
            placeholder="请选择教室..."
            :disabled="!selectedBuilding"
            @change="onClassroomChange"
          />
        </view>

        <!-- 查询按钮 -->
        <button
          class="w-full bg-primary-500 text-white rounded-full font-semibold shadow-lg transition-all duration-200 active:scale-95 disabled:opacity-50 disabled:cursor-not-allowed"
          :disabled="!selectedClassroom || loading"
          @click="fetchCourseSchedule"
        >
          <view class="flex items-center justify-center">
            <base-icon v-if="loading" type="spinner-cycle" size="20" class="animate-spin mr-2" color="#fff" />
            <base-icon v-else type="search" size="20" class="mr-2" color="#fff" />
            <text class="text-lg">查看课程</text>
          </view>
        </button>
      </view>

      <!-- 课程列表 -->
      <view v-if="courses.length > 0" class="bg-white rounded-2xl shadow-xl p-4 mb-6 border border-neutral-100">
        <view class="flex flex-col mb-4">
          <text class="text-subtitle font-semibold text-primary-800 mb-2">未来7天课程</text>
          <uni-badge :text="courses.length.toString()" type="primary" size="normal" />
        </view>

        <view>
          <view
            v-for="course in courses"
            :key="course.course_info_id"
            class="bg-gradient-to-r from-primary-50 to-white rounded-xl p-4 border border-primary-200 shadow-sm transition-all duration-200 hover:shadow-md active:scale-[0.98] cursor-pointer mb-4"
            @click="goToCourseDetail(course.course_info_id)"
          >
            <!-- 课程基本信息 -->
            <view class="flex flex-col mb-3">
              <text class="block text-lg font-bold text-primary-700 mb-1 line-clamp-1">{{ course.course_name }}</text>
              <view class="flex items-center gap-2 mb-2">
                <base-icon type="calendar" size="16" color="#475569" />
                <text class="text-sm text-neutral-600">{{ formatDateRange(course.start_date, course.end_date) }}</text>
              </view>
            </view>

            <!-- 时间信息 -->
            <view class="flex flex-wrap gap-3 mb-3">
              <view class="flex items-center gap-1">
                <base-icon type="headphones" size="14" color="#475569" />
                <text class="text-xs text-neutral-600">{{ course.time_period }}</text>
              </view>
              <view class="flex items-center gap-1">
                <base-icon type="location-filled" size="14" color="#475569" />
                <text class="text-xs text-neutral-600">{{ course.weekdays }}</text>
              </view>
            </view>

            <!-- 内容摘要 -->
            <view v-if="course.content" class="bg-neutral-50 rounded-lg p-3 border border-neutral-200">
              <text class="block text-sm font-medium text-neutral-700 mb-1">课程内容</text>
              <text class="block text-sm text-neutral-600 line-clamp-2">{{ course.content }}</text>
            </view>

            <!-- 学院专业信息 -->
            <view class="flex items-center justify-between mt-3 pt-3 border-t border-neutral-200">
              <view class="flex items-center gap-2">
                <base-icon type="person" size="14" color="#475569" />
                <text class="text-xs text-neutral-500">{{ course.college }} - {{ course.major }}</text>
              </view>
              <uni-tag :text="course.room_number" size="small" type="primary" circle />
            </view>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else-if="hasSearched" class="bg-white rounded-2xl shadow-xl p-8 text-center border border-neutral-100">
        <base-icon type="info" size="48" color="#94a3b8" class="mb-4" />
        <text class="block text-lg font-semibold text-neutral-500 mb-2">无课程信息</text>
        <text class="block text-neutral-400 text-body">该教室未来7天内无排课</text>
      </view>

      <!-- 初始提示 -->
      <view v-else class="bg-white rounded-2xl shadow-xl p-8 text-center border border-neutral-100">
        <base-icon type="search" size="48" color="#94a3b8" class="mb-4" />
        <text class="block text-lg font-semibold text-neutral-500 mb-2">请选择教室查看课程</text>
        <text class="block text-neutral-400 text-body">请选择教学楼和教室以查看未来课程</text>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance();

// ------------------ Data ------------------
const selectedBuilding = ref('');
const selectedClassroom = ref('');
const buildingOptions = ref([]);
const classroomOptions = ref([]);
const courses = ref([]);
const loading = ref(false);
const hasSearched = ref(false);
const loginUser = ref(null);

// ------------------ Lifecycle ------------------
onLoad(async () => {
  await fetchLoginUser();
  await fetchBuildingOptions();
});

// ------------------ Methods ------------------

// 获取当前登录用户信息
async function fetchLoginUser() {
  const res = await proxy.$cf.login.getLoginUser();
  if (res.success && res.data) {
    loginUser.value = res.data;
  }
}

// 获取教学楼选项
async function fetchBuildingOptions() {
  const res = await proxy.$cf.table.list({
    table_name: 'building_enum',
    param: {}
  });
  if (res.success) {
    buildingOptions.value = res.data.map(item => ({
      value: item.building_enum_id,
      text: item.building_name
    }));
  } else {
    proxy.$cf.toast({ message: '加载教学楼失败', level: 'error' });
  }
}

// 教学楼变更事件
async function onBuildingChange() {
  selectedClassroom.value = '';
  classroomOptions.value = [];
  courses.value = [];
  hasSearched.value = false;

  if (selectedBuilding.value) {
    await fetchClassroomOptions();
  }
}

// 获取教室选项
async function fetchClassroomOptions() {
  const res = await proxy.$cf.table.list({
    table_name: 'classroom_info',
    param: {
      building_enum_building_enum_id_1: selectedBuilding.value
    }
  });
  if (res.success) {
    classroomOptions.value = res.data.map(item => ({
      value: item.classroom_info_id,
      text: item.room_number
    }));
  } else {
    proxy.$cf.toast({ message: '加载教室失败', level: 'error' });
  }
}

// 教室变更事件
function onClassroomChange() {
  courses.value = [];
  hasSearched.value = false;
}

// 查询课程安排
async function fetchCourseSchedule() {
  if (!selectedClassroom.value) {
    proxy.$cf.toast({ message: '请选择教室', level: 'error' });
    return;
  }

  loading.value = true;
  try {
    // 计算未来7天的日期范围
    const today = new Date();
    const sevenDaysLater = new Date();
    sevenDaysLater.setDate(today.getDate() + 7);

    const startDate = formatDateForAPI(today);
    const endDate = formatDateForAPI(sevenDaysLater);

    // 传递日期范围参数给后端
    const res = await proxy.$cf.table.list({
      table_name: 'course_info',
      param: {
        classroom_info_classroom_info_id_1: selectedClassroom.value,
        start_date_gte: startDate,
        end_date_lte: endDate
      }
    });

    if (res.success) {
      courses.value = res.data;
      hasSearched.value = true;

      if (courses.value.length == 0) {
        proxy.$cf.toast({ message: '未来7天无课程', level: 'none' });
      }
    } else {
      proxy.$cf.toast({ message: res.message || '加载课程失败', level: 'error' });
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，请重试', level: 'error' });
  } finally {
    loading.value = false;
  }
}

// 跳转到课程详情页
function goToCourseDetail(courseInfoId) {
  proxy.$cf.navigate.to({
    url: `/pages/course_detail/index?course_info_id=${courseInfoId}`,
    type: 'page'
  });
}

// 格式化日期范围显示
function formatDateRange(startDate, endDate) {
  if (!startDate || !endDate) return '';

  const start = parseDateOnly(startDate);
  const end = parseDateOnly(endDate);

  const startStr = start.toLocaleDateString('zh-CN', { month: 'short', day: 'numeric' });
  const endStr = end.toLocaleDateString('zh-CN', { month: 'short', day: 'numeric', year: 'numeric' });

  return `${startStr} - ${endStr}`;
}

// 解析日期字符串，去除时分秒，确保比较准确
function parseDateOnly(dateStr) {
  const d = new Date(dateStr);
  return new Date(d.getFullYear(), d.getMonth(), d.getDate());
}

// 格式化日期为API需要的格式 (YYYY-MM-DD)
function formatDateForAPI(date) {
  const year = date.getFullYear();
  const month = ('0' + (date.getMonth() + 1)).slice(-2);
  const day = ('0' + date.getDate()).slice(-2);
  return `${year}-${month}-${day}`;
}
</script>

<style>
.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>