<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4 flex flex-col">
        <view class="flex items-center gap-3 mb-2">
          <base-icon type="compose" size="24" color="#339cff"></base-icon>
          <text class="text-title font-bold text-primary-700 truncate">
            {{ isEditMode ? '编辑课程' : '新增课程' }}
          </text>
        </view>
        <view v-if="isEditMode" class="flex items-center gap-2">
          <uni-tag 
            :text="courseDetail?.course_name || '课程'" 
            size="small" 
            type="primary" 
            circle 
          />
        </view>
      </view>

      <!-- 课程编辑表单 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <uni-forms 
          ref="courseFormRef" 
          :modelValue="courseForm" 
          label-position="top" 
          label-width="100%"
          class="space-y-4"
        >
          <!-- 课程名称 -->
          <uni-forms-item 
            label="课程名称" 
            name="course_name" 
            required
          >
            <uni-easyinput
              type="text"
              v-model="courseForm.course_name"
              placeholder="请输入课程名称"
              class="w-full box-border"
              :clearable="true"
              maxlength="100"
            />
          </uni-forms-item>

          <!-- 学院 -->
          <uni-forms-item 
            label="学院" 
            name="college" 
            required
          >
            <uni-easyinput
              type="text"
              v-model="courseForm.college"
              placeholder="请输入学院名称"
              class="w-full box-border"
              :clearable="true"
              maxlength="100"
            />
          </uni-forms-item>

          <!-- 专业 -->
          <uni-forms-item 
            label="专业" 
            name="major" 
            required
          >
            <uni-easyinput
              type="text"
              v-model="courseForm.major"
              placeholder="请输入专业名称"
              class="w-full box-border"
              :clearable="true"
              maxlength="100"
            />
          </uni-forms-item>

          <!-- 教室选择 -->
          <uni-forms-item 
            label="教室" 
            name="classroom_info_classroom_info_id_1" 
            required
          >
            <uni-data-select
              v-model="courseForm.classroom_info_classroom_info_id_1"
              :localdata="classroomOptions"
              placeholder="请选择教室"
              class="w-full box-border"
              clear
            />
          </uni-forms-item>

          <!-- 开始日期 -->
          <uni-forms-item 
            label="开始日期" 
            name="start_date" 
            required
          >
            <uni-datetime-picker
              type="date"
              v-model="courseForm.start_date"
              placeholder="请选择开始日期"
              class="w-full box-border"
            />
          </uni-forms-item>

          <!-- 结束日期 -->
          <uni-forms-item 
            label="结束日期" 
            name="end_date" 
            required
          >
            <uni-datetime-picker
              type="date"
              v-model="courseForm.end_date"
              placeholder="请选择结束日期"
              class="w-full box-border"
            />
          </uni-forms-item>

          <!-- 上课星期 -->
          <uni-forms-item 
            label="上课星期" 
            name="weekdays" 
            required
          >
            <uni-data-checkbox
              v-model="courseForm.weekdays"
              :localdata="weekdayOptions"
              mode="tag"
              multiple
              class="w-full box-border"
            />
          </uni-forms-item>

          <!-- 时间段 -->
          <uni-forms-item 
            label="时间段" 
            name="time_period" 
            required
          >
            <uni-easyinput
              type="text"
              v-model="courseForm.time_period"
              placeholder="例如：09:00-11:00"
              class="w-full box-border"
              :clearable="true"
              maxlength="50"
            />
          </uni-forms-item>

          <!-- 授课内容 -->
          <uni-forms-item 
            label="授课内容" 
            name="content"
          >
            <uni-easyinput
              type="textarea"
              v-model="courseForm.content"
              placeholder="请输入授课内容描述"
              class="w-full box-border"
              :clearable="true"
              maxlength="1000"
            />
          </uni-forms-item>
        </uni-forms>

        <!-- 操作按钮 -->
        <view class="flex flex-col gap-4 mt-6">
          <button
            class="bg-primary-500 text-white rounded-full flex items-center justify-center font-semibold transition-transform active:scale-95"
            @click="handleSaveCourse"
            :disabled="loading"
            style="min-height: 40px;"
          >
            <base-icon 
              v-if="loading" 
              type="spinner-cycle" 
              size="18" 
              class="animate-spin mr-2" 
              color="#fff"
            ></base-icon>
            <text v-else class="truncate">
              {{ isEditMode ? '更新课程' : '创建课程' }}
            </text>
          </button>
          
          <button
            v-if="isEditMode"
            class="bg-neutral-200 text-neutral-700 rounded-full flex items-center justify-center font-semibold transition-transform active:scale-95"
            @click="handleResetForm"
            style="min-height: 40px;"
          >
            <text class="truncate">重置</text>
          </button>
        </view>
      </view>

      <!-- 加载状态 -->
      <view v-if="initialLoading" class="flex flex-col items-center justify-center py-12">
        <base-icon type="spinner-cycle" size="24" class="animate-spin mb-2" color="#339cff"></base-icon>
        <text class="text-neutral-600">课程数据加载中...</text>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance();

// ------------------ Data ------------------
const courseForm = ref({
  course_name: '',
  college: '',
  major: '',
  classroom_info_classroom_info_id_1: '',
  start_date: '',
  end_date: '',
  weekdays: [],
  time_period: '',
  content: ''
});

const courseDetail = ref(null);
const classroomOptions = ref([]);
const isEditMode = ref(false);
const courseId = ref(null);
const loading = ref(false);
const initialLoading = ref(false);
const courseFormRef = ref(null);

// 星期选项
const weekdayOptions = ref([
  { value: 'Monday', text: '星期一' },
  { value: 'Tuesday', text: '星期二' },
  { value: 'Wednesday', text: '星期三' },
  { value: 'Thursday', text: '星期四' },
  { value: 'Friday', text: '星期五' },
  { value: 'Saturday', text: '星期六' },
  { value: 'Sunday', text: '星期日' }
]);

// ------------------ Lifecycle ------------------
onLoad(async (options) => {
  if (options && options.course_info_id) {
    courseId.value = Number(options.course_info_id);
    isEditMode.value = true;
    await loadCourseDetail();
  }
  await loadClassroomOptions();
});

// ------------------ Methods ------------------

// 加载教室选项
async function loadClassroomOptions() {
  try {
    const res = await proxy.$cf.table.list({
      table_name: 'classroom_info',
      param: {}
    });
    
    if (res.success) {
      classroomOptions.value = res.data.map(classroom => ({
        value: classroom.classroom_info_id,
        text: `${classroom.building_name} - ${classroom.room_number}`
      }));
    } else {
      proxy.$cf.toast({ 
        message: res.message || '加载教室失败', 
        level: 'error' 
      });
    }
  } catch (error) {
    proxy.$cf.toast({ 
      message: '网络错误，加载教室失败', 
      level: 'error' 
    });
  }
}

// 加载课程详情
async function loadCourseDetail() {
  if (!courseId.value) return;
  
  initialLoading.value = true;
  try {
    const res = await proxy.$cf.table.get({
      table_name: 'course_info',
      param: { course_info_id: courseId.value }
    });
    
    if (res.success && res.data) {
      courseDetail.value = res.data;
      
      // 填充表单数据
      courseForm.value = {
        course_name: res.data.course_name || '',
        college: res.data.college || '',
        major: res.data.major || '',
        classroom_info_classroom_info_id_1: res.data.classroom_info_classroom_info_id_1 || '',
        start_date: res.data.start_date || '',
        end_date: res.data.end_date || '',
        weekdays: res.data.weekdays ? res.data.weekdays.split(',') : [],
        time_period: res.data.time_period || '',
        content: res.data.content || ''
      };
    } else {
      proxy.$cf.toast({ 
        message: res.message || '加载课程详情失败', 
        level: 'error' 
      });
    }
  } catch (error) {
    proxy.$cf.toast({ 
      message: '网络错误，加载课程详情失败', 
      level: 'error' 
    });
  } finally {
    initialLoading.value = false;
  }
}

// 保存课程信息
async function handleSaveCourse() {
  if (loading.value) return;
  
  // 表单验证
  if (!courseForm.value.course_name?.trim()) {
    proxy.$cf.toast({ message: '课程名称不能为空', level: 'error' });
    return;
  }
  
  if (!courseForm.value.college?.trim()) {
    proxy.$cf.toast({ message: '学院不能为空', level: 'error' });
    return;
  }
  
  if (!courseForm.value.major?.trim()) {
    proxy.$cf.toast({ message: '专业不能为空', level: 'error' });
    return;
  }
  
  if (!courseForm.value.classroom_info_classroom_info_id_1) {
    proxy.$cf.toast({ message: '请选择教室', level: 'error' });
    return;
  }
  
  if (!courseForm.value.start_date) {
    proxy.$cf.toast({ message: '请选择开始日期', level: 'error' });
    return;
  }
  
  if (!courseForm.value.end_date) {
    proxy.$cf.toast({ message: '请选择结束日期', level: 'error' });
    return;
  }
  
  if (!courseForm.value.weekdays?.length) {
    proxy.$cf.toast({ message: '请选择至少一个上课星期', level: 'error' });
    return;
  }
  
  if (!courseForm.value.time_period?.trim()) {
    proxy.$cf.toast({ message: '时间段不能为空', level: 'error' });
    return;
  }

  loading.value = true;
  
  try {
    const formData = {
      ...courseForm.value,
      weekdays: courseForm.value.weekdays.join(',')
    };

    let result;
    
    if (isEditMode.value) {
      // 更新课程
      result = await proxy.$cf.table.update({
        table_name: 'course_info',
        param: {
          course_info_id: courseId.value,
          ...formData
        }
      });
    } else {
      // 新增课程
      result = await proxy.$cf.table.add({
        table_name: 'course_info',
        param: formData
      });
    }

    if (result.success) {
      proxy.$cf.toast({ 
        message: isEditMode.value ? '课程更新成功' : '课程创建成功', 
        level: 'success' 
      });
      
      // 延迟返回上一页
      setTimeout(() => {
        proxy.$cf.navigate.back();
      }, 1500);
    } else {
      proxy.$cf.toast({ 
        message: result.message || `课程${isEditMode.value ? '更新' : '创建'}失败`, 
        level: 'error' 
      });
    }
  } catch (error) {
    proxy.$cf.toast({ 
      message: `网络错误，课程${isEditMode.value ? '更新' : '创建'}失败`, 
      level: 'error' 
    });
  } finally {
    loading.value = false;
  }
}

// 重置表单
function handleResetForm() {
  if (courseDetail.value) {
    courseForm.value = {
      course_name: courseDetail.value.course_name || '',
      college: courseDetail.value.college || '',
      major: courseDetail.value.major || '',
      classroom_info_classroom_info_id_1: courseDetail.value.classroom_info_classroom_info_id_1 || '',
      start_date: courseDetail.value.start_date || '',
      end_date: courseDetail.value.end_date || '',
      weekdays: courseDetail.value.weekdays ? courseDetail.value.weekdays.split(',') : [],
      time_period: courseDetail.value.time_period || '',
      content: courseDetail.value.content || ''
    };
    proxy.$cf.toast({ message: '表单已重置为原始值', level: 'success' });
  }
}
</script>

<style>
.space-y-4 > * + * {
  margin-top: 1rem;
}
</style>