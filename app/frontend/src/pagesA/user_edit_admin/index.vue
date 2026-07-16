<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4 flex flex-col">
        <view class="flex items-center gap-2 mb-2">
          <base-icon type="person-filled" size="24" color="#339cff"></base-icon>
          <text class="text-xl font-bold text-primary-700">编辑用户</text>
        </view>
        <view v-if="loading" class="flex items-center gap-2">
          <view>
            <base-icon type="spinner-cycle" size="20" color="#339cff" class="animate-spin"></base-icon>
          </view>
          <text class="text-neutral-500">加载中...</text>
        </view>
      </view>

      <!-- 用户信息编辑表单 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <uni-forms ref="userFormRef" :modelValue="userForm" label-position="top" label-width="100%">
          <!-- 基本信息 -->
          <view class="mb-6">
            <text class="text-lg font-semibold text-primary-800 mb-3 block">基本信息</text>

            <uni-forms-item label="姓名" name="name" required>
              <uni-easyinput
                type="text"
                v-model="userForm.name"
                placeholder="请输入姓名"
                class="w-full box-border"
                clearable
              />
            </uni-forms-item>

            <uni-forms-item label="学号" name="student_id" required>
              <uni-easyinput
                type="text"
                v-model="userForm.student_id"
                placeholder="请输入学号"
                class="w-full box-border"
                clearable
              />
            </uni-forms-item>

            <uni-forms-item label="手机号" name="phone_number" required>
              <uni-easyinput
                type="text"
                v-model="userForm.phone_number"
                placeholder="请输入手机号"
                class="w-full box-border"
                clearable
              />
            </uni-forms-item>

            <uni-forms-item label="学院" name="college" required>
              <uni-easyinput
                type="text"
                v-model="userForm.college"
                placeholder="请输入学院"
                class="w-full box-border"
                clearable
              />
            </uni-forms-item>

            <uni-forms-item label="专业" name="major" required>
              <uni-easyinput
                type="text"
                v-model="userForm.major"
                placeholder="请输入专业"
                class="w-full box-border"
                clearable
              />
            </uni-forms-item>
          </view>

          <!-- 角色和状态 -->
          <view class="mb-6">
            <text class="text-lg font-semibold text-primary-800 mb-3 block">账户设置</text>

            <uni-forms-item label="用户角色" name="role_enum_role_enum_id_1" required>
              <uni-data-select
                v-model="userForm.role_enum_role_enum_id_1"
                :localdata="roleOptions"
                class="w-full box-border"
                placeholder="请选择用户角色"
                clear
              />
            </uni-forms-item>

            <uni-forms-item label="账户状态" name="user_status_enum_user_status_enum_id_1" required>
              <uni-data-select
                v-model="userForm.user_status_enum_user_status_enum_id_1"
                :localdata="statusOptions"
                class="w-full box-border"
                placeholder="请选择账户状态"
                clear
              />
            </uni-forms-item>
          </view>

          <!-- 注册时间显示 -->
          <view class="mb-6 p-3 bg-neutral-50 rounded-lg">
            <text class="text-sm font-medium text-neutral-700 block mb-1">注册时间</text>
            <text class="text-sm text-neutral-600">{{ userForm.registration_date || '无' }}</text>
          </view>

          <!-- 操作按钮 -->
          <view class="flex flex-col gap-4">
            <button
              class="bg-primary-500 text-white rounded-full flex items-center justify-center font-semibold transition-transform active:scale-95"
              style="height: 40px;"
              @click="handleSave"
              :disabled="saving"
              aria-label="保存用户信息"
            >
              <view v-if="saving" class="flex items-center justify-center">
                <base-icon type="spinner-cycle" size="18" class="animate-spin mr-2" color="#fff"></base-icon>
                <text>保存中...</text>
              </view>
              <view v-else class="flex items-center justify-center">
                <base-icon type="checkmarkempty" size="18" class="mr-2" color="#fff"></base-icon>
                <text>保存修改</text>
              </view>
            </button>

            <button
              class="bg-neutral-200 text-neutral-700 rounded-full flex items-center justify-center font-semibold transition-transform active:scale-95"
              style="height: 40px;"
              @click="handleReset"
              aria-label="重置表单"
            >
              <base-icon type="refresh" size="18" class="mr-2" color="#525252"></base-icon>
              <text>重置</text>
            </button>
          </view>
        </uni-forms>
      </view>

      <!-- 成功提示 -->
      <uni-popup ref="successPopup" type="center">
        <view class="bg-white p-6 rounded-2xl shadow-2xl w-80 flex flex-col items-center">
          <base-icon type="checkmarkempty" size="48" color="#33bf71" class="mb-4"></base-icon>
          <text class="text-xl font-bold text-success-700 mb-2">成功</text>
          <text class="text-neutral-600 text-center mb-6">用户信息已成功更新。</text>
          <button
            class="w-full bg-success-500 text-white rounded-full font-semibold transition-transform active:scale-95"
            style="height: 40px;"
            @click="closeSuccessPopup"
            aria-label="关闭成功提示"
          >
            <text>确定</text>
          </button>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const userForm = ref({
  name: '',
  student_id: '',
  phone_number: '',
  college: '',
  major: '',
  role_enum_role_enum_id_1: '',
  user_status_enum_user_status_enum_id_1: '',
  registration_date: ''
})

const originalUserData = ref({})
const roleOptions = ref([])
const statusOptions = ref([])
const loading = ref(false)
const saving = ref(false)
const userInfoId = ref(null)
const userFormRef = ref(null)
const successPopup = ref(null)

// ------------------ Lifecycle ------------------
onLoad(async (option) => {
  if (option && option.user_info_id) {
    userInfoId.value = Number(option.user_info_id)
    await Promise.all([
      fetchUserDetail(),
      fetchRoleOptions(),
      fetchStatusOptions()
    ])
  } else {
    proxy.$cf.toast({ message: '用户ID无效', level: 'error' })
    setTimeout(() => {
      proxy.$cf.navigate.back()
    }, 1500)
  }
})

// ------------------ Methods ------------------

// 获取用户详情
async function fetchUserDetail() {
  loading.value = true
  try {
    const res = await proxy.$cf.table.get({
      table_name: 'user_info',
      param: { user_info_id: userInfoId.value }
    })
    
    if (res.success && res.data) {
      const userData = res.data
      userForm.value = {
        name: userData.name || '',
        student_id: userData.student_id || '',
        phone_number: userData.phone_number || '',
        college: userData.college || '',
        major: userData.major || '',
        role_enum_role_enum_id_1: userData.role_enum_role_enum_id_1 || '',
        user_status_enum_user_status_enum_id_1: userData.user_status_enum_user_status_enum_id_1 || '',
        registration_date: userData.registration_date || ''
      }
      originalUserData.value = { ...userForm.value }
    } else {
      proxy.$cf.toast({ message: res.message || '加载用户数据失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络错误，加载用户数据失败', level: 'error' })
  } finally {
    loading.value = false
  }
}

// 获取角色选项
async function fetchRoleOptions() {
  try {
    const res = await proxy.$cf.table.list({
      table_name: 'role_enum',
      param: {}
    })
    if (res.success) {
      roleOptions.value = res.data.map(item => ({
        value: item.role_enum_id,
        text: item.role_name
      }))
    }
  } catch (error) {
    proxy.$cf.toast({ message: '加载角色选项失败', level: 'error' })
  }
}

// 获取状态选项
async function fetchStatusOptions() {
  try {
    const res = await proxy.$cf.table.list({
      table_name: 'user_status_enum',
      param: {}
    })
    if (res.success) {
      statusOptions.value = res.data.map(item => ({
        value: item.user_status_enum_id,
        text: item.status_name
      }))
    }
  } catch (error) {
    proxy.$cf.toast({ message: '加载状态选项失败', level: 'error' })
  }
}

// 保存用户信息
async function handleSave() {
  if (saving.value) return

  // 表单验证
  if (!userForm.value.name?.trim()) {
    proxy.$cf.toast({ message: '姓名为必填项', level: 'error' })
    return
  }
  if (!userForm.value.student_id?.trim()) {
    proxy.$cf.toast({ message: '学号为必填项', level: 'error' })
    return
  }
  if (!userForm.value.phone_number?.trim()) {
    proxy.$cf.toast({ message: '手机号为必填项', level: 'error' })
    return
  }
  if (!userForm.value.college?.trim()) {
    proxy.$cf.toast({ message: '学院为必填项', level: 'error' })
    return
  }
  if (!userForm.value.major?.trim()) {
    proxy.$cf.toast({ message: '专业为必填项', level: 'error' })
    return
  }
  if (!userForm.value.role_enum_role_enum_id_1) {
    proxy.$cf.toast({ message: '用户角色为必填项', level: 'error' })
    return
  }
  if (!userForm.value.user_status_enum_user_status_enum_id_1) {
    proxy.$cf.toast({ message: '账户状态为必填项', level: 'error' })
    return
  }

  saving.value = true
  try {
    const res = await proxy.$cf.table.update({
      table_name: 'user_info',
      param: {
        user_info_id: userInfoId.value,
        name: userForm.value.name.trim(),
        student_id: userForm.value.student_id.trim(),
        phone_number: userForm.value.phone_number.trim(),
        college: userForm.value.college.trim(),
        major: userForm.value.major.trim(),
        role_enum_role_enum_id_1: userForm.value.role_enum_role_enum_id_1,
        user_status_enum_user_status_enum_id_1: userForm.value.user_status_enum_user_status_enum_id_1
      }
    })

    if (res.success) {
      originalUserData.value = { ...userForm.value }
      successPopup.value.open()
    } else {
      proxy.$cf.toast({ message: res.message || '更新用户失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络错误，更新用户失败', level: 'error' })
  } finally {
    saving.value = false
  }
}

// 重置表单
function handleReset() {
  userForm.value = { ...originalUserData.value }
  proxy.$cf.toast({ message: '表单已重置为原始值', level: 'none' })
}

// 关闭成功弹窗
function closeSuccessPopup() {
  successPopup.value.close()
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