<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4 flex flex-col">
        <text class="text-title font-bold text-primary-700 mb-4">课程管理</text>
        <button 
          class="bg-primary-500 text-white rounded-full px-4 font-semibold active:scale-95 transition-transform mb-4 self-start"
          @click="openCoursePopup"
        >
          <base-icon type="plus" size="18" color="#fff" class="mr-1" />
          <text>新增课程</text>
        </button>
      </view>

      <!-- 筛选区域 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <view class="grid grid-cols-1 gap-4 sm:grid-cols-2">
          <view>
            <text class="block text-sm font-medium text-neutral-700 mb-2">学院</text>
            <uni-easyinput
              type="text"
              v-model="filterParams.college"
              placeholder="按学院筛选"
              class="w-full box-border"
              @input="handleFilterChange"
            />
          </view>
          <view>
            <text class="block text-sm font-medium text-neutral-700 mb-2">专业</text>
            <uni-easyinput
              type="text"
              v-model="filterParams.major"
              placeholder="按专业筛选"
              class="w-full box-border"
              @input="handleFilterChange"
            />
          </view>
        </view>
      </view>

      <!-- 课程列表 -->
      <view v-if="courses.length > 0">
        <view
          v-for="course in courses"
          :key="course.course_info_id"
          class="bg-white rounded-xl shadow-lg p-4 mb-4 cursor-pointer transition-all duration-150 hover:shadow-xl active:scale-[0.99]"
          @click="editCourse(course)"
        >
          <!-- 课程基本信息 -->
          <view class="flex flex-col">
            <text class="block text-lg font-semibold text-primary-800 mb-2 break-words">{{ course.course_name }}</text>
            <view class="flex flex-col mb-2">
              <view class="flex items-center gap-2 mb-1">
                <base-icon type="person" size="16" color="#475569" />
                <text class="text-sm text-neutral-700 break-words">{{ course.college }} - {{ course.major }}</text>
              </view>
              <view class="flex items-center gap-2">
                <base-icon type="location-filled" size="16" color="#475569" />
                <text class="text-sm text-neutral-700 break-words">教室 {{ course.room_number }}</text>
              </view>
            </view>

            <!-- 课程时间信息 -->
            <view class="grid grid-cols-1 gap-2 sm:grid-cols-2 mb-2 text-sm text-neutral-700">
              <view class="flex items-center gap-2">
                <base-icon type="calendar" size="14" color="#475569" />
                <text>{{ formatDate(course.start_date) }} - {{ formatDate(course.end_date) }}</text>
              </view>
              <view class="flex items-center gap-2">
                <base-icon type="headphones" size="14" color="#475569" />
                <text>{{ course.time_period }}</text>
              </view>
            </view>

            <!-- 上课周期 -->
            <view class="mb-2">
              <text class="text-sm text-neutral-700 break-words">上课周期: {{ course.weekdays }}</text>
            </view>

            <!-- 课程内容摘要 -->
            <view v-if="course.content" class="p-3 bg-neutral-50 rounded-lg">
              <text class="text-sm text-neutral-800 line-clamp-2 break-words">{{ course.content }}</text>
            </view>
          </view>

          <!-- 删除按钮，独立一行，避免误触 -->
          <view class="flex justify-end mt-3">
            <button
              class="p-2 rounded-full bg-neutral-100 hover:bg-neutral-200 active:scale-95 transition-all"
              @click.stop="deleteCourse(course.course_info_id)"
              aria-label="删除课程"
            >
              <base-icon type="trash" size="18" color="#b91c1c" />
            </button>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else class="flex flex-col items-center justify-center py-16 bg-white rounded-xl shadow-inner">
        <base-icon type="list" size="60" color="#d4d4d4" />
        <text class="text-neutral-400 mt-4 text-base font-semibold">暂无课程数据</text>
        <text class="text-neutral-400 mt-2 text-sm">点击“新增课程”开始创建</text>
      </view>

      <!-- 加载更多 -->
      <view v-if="hasMore" class="flex justify-center mt-6 mb-4">
        <button
          class="bg-primary-500 text-white rounded-full px-8 font-semibold active:scale-95 transition-transform disabled:opacity-50 disabled:cursor-not-allowed"
          :disabled="loading"
          @click="loadMore"
        >
          <base-icon v-if="loading" type="spinner-cycle" size="18" class="animate-spin mr-1" color="#fff" />
          <text v-else>加载更多</text>
        </button>
      </view>

      <!-- 课程编辑弹窗 -->
      <uni-popup ref="coursePopup" type="center">
        <view class="bg-white p-4 w-80 rounded-2xl shadow-2xl">
          <view class="flex items-center justify-between mb-4">
            <text class="text-lg font-bold text-primary-700 leading-tight">
              {{ isEditing ? '编辑课程' : '新增课程' }}
            </text>
            <view @click="closeCoursePopup" class="p-1 rounded-full hover:bg-neutral-100 cursor-pointer" role="button" tabindex="0" aria-label="关闭弹窗">
              <base-icon type="close" size="20" color="#475569" />
            </view>
          </view>

          <uni-forms ref="courseFormRef" :modelValue="courseForm" label-position="top" label-width="100%">
            <uni-forms-item label="课程名称" name="course_name" required>
              <uni-easyinput
                type="text"
                v-model="courseForm.course_name"
                placeholder="请输入课程名称"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="学院" name="college" required>
              <uni-easyinput
                type="text"
                v-model="courseForm.college"
                placeholder="请输入学院"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="专业" name="major" required>
              <uni-easyinput
                type="text"
                v-model="courseForm.major"
                placeholder="请输入专业"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="教室" name="classroom_info_classroom_info_id_1" required>
              <uni-data-select
                v-model="courseForm.classroom_info_classroom_info_id_1"
                :localdata="classroomOptions"
                class="w-full box-border"
                placeholder="请选择教室"
              />
            </uni-forms-item>

            <view class="grid grid-cols-1 gap-4 sm:grid-cols-2">
              <uni-forms-item label="开始日期" name="start_date" required>
                <uni-datetime-picker
                  type="date"
                  v-model="courseForm.start_date"
                  class="w-full box-border"
                />
              </uni-forms-item>

              <uni-forms-item label="结束日期" name="end_date" required>
                <uni-datetime-picker
                  type="date"
                  v-model="courseForm.end_date"
                  class="w-full box-border"
                />
              </uni-forms-item>
            </view>

            <uni-forms-item label="上课周期" name="weekdays" required>
              <uni-easyinput
                type="text"
                v-model="courseForm.weekdays"
                placeholder="例如：周一，周三，周五"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="时间段" name="time_period" required>
              <uni-easyinput
                type="text"
                v-model="courseForm.time_period"
                placeholder="例如：09:00-10:30"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="课程内容" name="content">
              <uni-easyinput
                type="textarea"
                v-model="courseForm.content"
                placeholder="请输入课程内容"
                class="w-full box-border"
              />
            </uni-forms-item>

            <button
              class="w-full bg-primary-500 text-white rounded-full font-semibold mt-4 active:scale-95 transition-transform"
              @click="saveCourse"
              :disabled="saving"
            >
              <base-icon v-if="saving" type="spinner-cycle" size="18" class="animate-spin mr-1" color="#fff" />
              <text>{{ isEditing ? '更新课程' : '创建课程' }}</text>
            </button>
          </uni-forms>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const courses = ref([])
const classroomOptions = ref([])
const filterParams = ref({
  college: '',
  major: ''
})
const pageInfo = ref({
  current: 1,
  pageSize: 10
})
const total = ref(0)
const hasMore = ref(true)
const loading = ref(false)
const saving = ref(false)

// 课程表单
const courseForm = ref({
  course_name: '',
  college: '',
  major: '',
  classroom_info_classroom_info_id_1: '',
  start_date: '',
  end_date: '',
  weekdays: '',
  time_period: '',
  content: ''
})
const isEditing = ref(false)
const editingCourseId = ref(null)

// Refs
const coursePopup = ref(null)
const courseFormRef = ref(null)

// 防抖timer变量
let filterTimer = null

// ------------------ Lifecycle ------------------
onLoad(async () => {
  await fetchClassrooms()
  await fetchCourses(true)
})

// ------------------ Methods ------------------

// 获取教室列表
async function fetchClassrooms() {
  const res = await proxy.$cf.table.list({
    table_name: 'classroom_info',
    param: {}
  })
  if (res.success) {
    classroomOptions.value = res.data.map(classroom => ({
      value: classroom.classroom_info_id,
      text: `教室 ${classroom.room_number} - ${classroom.building_name}`
    }))
  }
}

// 获取课程列表
async function fetchCourses(reset = false) {
  if (loading.value) return
  loading.value = true
  
  try {
    if (reset) {
      pageInfo.value.current = 1
      courses.value = []
      hasMore.value = true
    }

    let param = {
      current: pageInfo.value.current,
      pageSize: pageInfo.value.pageSize
    }

    // 添加筛选条件
    if (filterParams.value.college) {
      param.college = filterParams.value.college
    }
    if (filterParams.value.major) {
      param.major = filterParams.value.major
    }

    // 按课程名称排序
    param.order_by = {
      'course_name': 'asc'
    }

    const res = await proxy.$cf.table.page({
      table_name: 'course_info',
      param
    })

    if (res.success) {
      if (reset) {
        courses.value = res.data.records
      } else {
        courses.value = courses.value.concat(res.data.records)
      }
      total.value = res.data.total
      hasMore.value = courses.value.length < total.value
    } else {
      proxy.$cf.toast({ message: res.message || '加载课程失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，加载课程失败', level: 'error' })
  } finally {
    loading.value = false
  }
}

// 筛选变化处理
function handleFilterChange() {
  if (filterTimer) clearTimeout(filterTimer)
  filterTimer = setTimeout(() => {
    fetchCourses(true)
  }, 500)
}

// 加载更多
function loadMore() {
  if (hasMore.value && !loading.value) {
    pageInfo.value.current += 1
    fetchCourses(false)
  }
}

// 打开课程弹窗
function openCoursePopup() {
  isEditing.value = false
  editingCourseId.value = null
  resetCourseForm()
  coursePopup.value.open()
}

// 关闭课程弹窗
function closeCoursePopup() {
  coursePopup.value.close()
  resetCourseForm()
}

// 重置课程表单
function resetCourseForm() {
  courseForm.value = {
    course_name: '',
    college: '',
    major: '',
    classroom_info_classroom_info_id_1: '',
    start_date: '',
    end_date: '',
    weekdays: '',
    time_period: '',
    content: ''
  }
  if (courseFormRef.value) {
    courseFormRef.value.clearValidate()
  }
}

// 编辑课程
function editCourse(course) {
  isEditing.value = true
  editingCourseId.value = course.course_info_id
  
  // 填充表单数据
  courseForm.value = {
    course_name: course.course_name || '',
    college: course.college || '',
    major: course.major || '',
    classroom_info_classroom_info_id_1: course.classroom_info_classroom_info_id_1 || '',
    start_date: course.start_date || '',
    end_date: course.end_date || '',
    weekdays: course.weekdays || '',
    time_period: course.time_period || '',
    content: course.content || ''
  }
  
  coursePopup.value.open()
}

// 保存课程
async function saveCourse() {
  if (saving.value) return
  
  // 表单验证
  if (!courseForm.value.course_name?.trim()) {
    proxy.$cf.toast({ message: '课程名称为必填项', level: 'error' })
    return
  }
  if (!courseForm.value.college?.trim()) {
    proxy.$cf.toast({ message: '学院为必填项', level: 'error' })
    return
  }
  if (!courseForm.value.major?.trim()) {
    proxy.$cf.toast({ message: '专业为必填项', level: 'error' })
    return
  }
  if (!courseForm.value.classroom_info_classroom_info_id_1) {
    proxy.$cf.toast({ message: '教室为必填项', level: 'error' })
    return
  }
  if (!courseForm.value.start_date) {
    proxy.$cf.toast({ message: '开始日期为必填项', level: 'error' })
    return
  }
  if (!courseForm.value.end_date) {
    proxy.$cf.toast({ message: '结束日期为必填项', level: 'error' })
    return
  }
  if (!courseForm.value.weekdays?.trim()) {
    proxy.$cf.toast({ message: '上课周期为必填项', level: 'error' })
    return
  }
  if (!courseForm.value.time_period?.trim()) {
    proxy.$cf.toast({ message: '时间段为必填项', level: 'error' })
    return
  }

  saving.value = true
  
  try {
    let res
    if (isEditing.value) {
      // 更新课程
      res = await proxy.$cf.table.update({
        table_name: 'course_info',
        param: {
          course_info_id: editingCourseId.value,
          ...courseForm.value
        }
      })
    } else {
      // 新增课程
      res = await proxy.$cf.table.add({
        table_name: 'course_info',
        param: courseForm.value
      })
    }

    if (res.success) {
      proxy.$cf.toast({ 
        message: isEditing.value ? '课程更新成功' : '课程创建成功', 
        level: 'success' 
      })
      closeCoursePopup()
      await fetchCourses(true)
    } else {
      proxy.$cf.toast({ message: res.message || '保存课程失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，保存课程失败', level: 'error' })
  } finally {
    saving.value = false
  }
}

// 删除课程
async function deleteCourse(courseId) {
  const confirm = await proxy.$cf.model({
    title: '删除确认',
    message: '确定要删除该课程吗？此操作不可恢复。',
    confirmText: '删除',
    cancelText: '取消'
  })

  if (confirm.confirm) {
    const res = await proxy.$cf.table.delete({
      table_name: 'course_info',
      param: { course_info_id: courseId }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '课程删除成功', level: 'success' })
      await fetchCourses(true)
    } else {
      proxy.$cf.toast({ message: res.message || '删除课程失败', level: 'error' })
    }
  }
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
</script>

<style>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>