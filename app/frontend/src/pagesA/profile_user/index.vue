<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-gradient-to-b from-primary-50 to-neutral-50">
      <!-- 用户信息卡片 -->
      <view class="bg-white rounded-2xl shadow-xl p-6 mb-6 border border-neutral-100">
        <view class="flex flex-col gap-3 mb-4">
          <view class="w-20 h-20 rounded-full bg-primary-100 flex items-center justify-center border-4 border-primary-300 self-center">
            <base-icon type="person-filled" size="32" color="#339cff" />
          </view>
          <view class="text-center">
            <text class="block text-xl font-bold text-neutral-800 mb-1 truncate">{{ userInfo.name || '用户' }}</text>
            <text class="block text-sm text-neutral-500 truncate">{{ userInfo.student_id || '学号' }}</text>
          </view>
          <view class="flex justify-center gap-3">
            <uni-tag 
              :text="userInfo.role_name" 
              size="small" 
              :type="userInfo.role_name == '管理员' ? 'primary' : 'default'"
              circle
            />
            <uni-tag 
              :text="userInfo.status_name" 
              size="small" 
              :type="getStatusTagType(userInfo.status_name)"
              circle
            />
          </view>
        </view>

        <!-- 管理员身份切换按钮 -->
        <view v-if="isAdminUser" class="mt-6 flex justify-center">
          <button
            class="bg-primary-500 text-white rounded-full px-6 py-2 font-semibold active:scale-95 transition-transform"
            @click="toggleIdentity"
            aria-label="切换身份"
          >
            {{ isAdminView ? '切换到用户视图' : '切换到管理员视图' }}
          </button>
        </view>

        <view 
          class="w-full mt-4 bg-primary-500 text-white rounded-full flex items-center justify-center font-semibold active:scale-95 transition-transform cursor-pointer select-none"
          @click="showEditPopup = true"
          role="button"
          tabindex="0"
        >
          <view class="flex items-center gap-2 px-4 py-2">
            <base-icon type="compose" size="18" color="#fff" />
            <text>编辑资料</text>
          </view>
        </view>
      </view>

      <!-- 报修记录 -->
      <view class="bg-white rounded-2xl shadow-xl p-6 mb-6 border border-neutral-100">
        <view class="flex items-center justify-between mb-4">
          <view class="flex items-center gap-3">
            <base-icon type="chatbubble-filled" size="20" color="#339cff" />
            <text class="text-lg font-bold text-neutral-800">报修记录</text>
          </view>
          <view class="flex items-center gap-2">
            <view 
              class="p-2 cursor-pointer active:scale-95 transition-transform"
              @click="loadRepairOrders"
              role="button"
              tabindex="0"
            >
              <base-icon type="refresh" size="16" color="#339cff" />
            </view>
            <uni-badge :text="repairOrders.length.toString()" type="primary" />
          </view>
        </view>

        <view v-if="repairOrders.length == 0" class="text-center py-8">
          <base-icon type="chatbubble" size="48" color="#d4d4d4" />
          <text class="block text-neutral-400 mt-2 text-sm">暂无报修记录</text>
        </view>

        <view v-else>
          <view 
            v-for="order in repairOrders.slice(0, 3)" 
            :key="order.repair_order_id"
            class="bg-neutral-50 rounded-xl p-4 border border-neutral-200 mb-4 active:scale-95 transition-transform cursor-pointer"
            @click="goToRepairDetail(order.repair_order_id)"
          >
            <view class="flex flex-col gap-2">
              <view class="flex items-center justify-between">
                <text class="font-semibold text-neutral-800 text-sm truncate flex-1 mr-2">
                  {{ order.room_number }}
                </text>
                <uni-tag 
                  :text="order.status_name" 
                  size="small" 
                  :type="getRepairStatusTagType(order.status_name)"
                  circle
                />
              </view>
              <text class="block text-neutral-600 text-sm line-clamp-2">
                {{ order.content }}
              </text>
              <view class="flex items-center justify-between text-xs text-neutral-500">
                <text>{{ formatDateTime(order.create_time) }}</text>
                <view class="flex items-center gap-1 select-none">
                  <base-icon type="eye" size="12" color="#525252" />
                  <text>查看详情</text>
                </view>
              </view>
            </view>
          </view>

          <view 
            v-if="repairOrders.length > 3"
            class="w-full text-primary-600 text-sm font-medium py-2 flex items-center justify-center cursor-pointer select-none"
            @click="showAllRepairs = true"
            role="button"
            tabindex="0"
          >
            查看全部 {{ repairOrders.length }} 条记录
            <base-icon type="arrow-down" size="14" class="ml-1" color="#339cff" />
          </view>
        </view>
      </view>

      <!-- 操作菜单 -->
      <view class="bg-white rounded-2xl shadow-xl p-4 border border-neutral-100">
        <view class="flex flex-col gap-2">
          <view 
            class="w-full flex items-center justify-between p-3 rounded-xl active:bg-neutral-50 transition-colors cursor-pointer select-none font-medium"
            @click="showEditPopup = true"
            role="button"
            tabindex="0"
          >
            <view class="flex items-center gap-3">
              <base-icon type="person" size="20" color="#339cff" />
              <text class="text-neutral-700 font-medium">编辑资料</text>
            </view>
            <base-icon type="right" size="16" color="#a3a3a3" />
          </view>

          <view 
            class="w-full flex items-center justify-between p-3 rounded-xl active:bg-neutral-50 transition-colors cursor-pointer select-none font-medium"
            @click="showAllRepairs = true"
            role="button"
            tabindex="0"
          >
            <view class="flex items-center gap-3">
              <base-icon type="list" size="20" color="#33bf71" />
              <text class="text-neutral-700 font-medium">全部报修记录</text>
            </view>
            <uni-badge :text="repairOrders.length.toString()" size="small" type="success" />
          </view>

          <view 
            class="w-full flex items-center justify-between p-3 rounded-xl active:bg-neutral-50 transition-colors cursor-pointer select-none font-medium"
            @click="handleLogout"
            role="button"
            tabindex="0"
          >
            <view class="flex items-center gap-3">
              <base-icon type="undo" size="20" color="#ff3333" />
              <text class="text-neutral-700 font-medium">退出登录</text>
            </view>
            <base-icon type="right" size="16" color="#a3a3a3" />
          </view>
        </view>
      </view>

      <!-- 编辑个人信息弹窗 -->
      <uni-popup ref="editPopup" type="center">
        <view class="bg-white p-6 rounded-2xl w-80 max-w-full">
          <view class="flex items-center justify-between mb-4">
            <text class="text-xl font-bold text-neutral-800">编辑资料</text>
            <view 
              class="cursor-pointer select-none"
              @click="showEditPopup = false"
              role="button"
              tabindex="0"
            >
              <base-icon type="close" size="20" color="#525252" />
            </view>
          </view>

          <uni-forms ref="editFormRef" :modelValue="editForm" label-position="top" label-width="100%">
            <uni-forms-item label="姓名" name="name" required>
              <uni-easyinput
                type="text"
                v-model="editForm.name"
                placeholder="请输入姓名"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="学号" name="student_id" required>
              <uni-easyinput
                type="text"
                v-model="editForm.student_id"
                placeholder="请输入学号"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="手机号" name="phone_number" required>
              <uni-easyinput
                type="text"
                v-model="editForm.phone_number"
                placeholder="请输入手机号"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="学院" name="college" required>
              <uni-easyinput
                type="text"
                v-model="editForm.college"
                placeholder="请输入学院"
                class="w-full box-border"
              />
            </uni-forms-item>

            <uni-forms-item label="专业" name="major" required>
              <uni-easyinput
                type="text"
                v-model="editForm.major"
                placeholder="请输入专业"
                class="w-full box-border"
              />
            </uni-forms-item>

            <view class="flex gap-3 mt-6">
              <view 
                class="flex-1 border border-neutral-300 text-neutral-700 rounded-full flex items-center justify-center cursor-pointer select-none font-medium active:scale-95 transition-transform"
                @click="showEditPopup = false"
                role="button"
                tabindex="0"
              >
                取消
              </view>
              <view 
                class="flex-1 bg-primary-500 text-white rounded-full flex items-center justify-center cursor-pointer select-none font-medium active:scale-95 transition-transform"
                @click="handleSaveProfile"
                :class="{ 'opacity-50 cursor-not-allowed': saving }"
                role="button"
                tabindex="0"
              >
                <base-icon v-if="saving" type="spinner-cycle" size="18" class="animate-spin mr-1" color="#fff" />
                <text>{{ saving ? '保存中...' : '保存' }}</text>
              </view>
            </view>
          </uni-forms>
        </view>
      </uni-popup>

      <!-- 全部报修记录弹窗 -->
      <uni-popup ref="repairsPopup" type="bottom">
        <view class="bg-white rounded-t-3xl p-6 max-h-96 pb-16">
          <view class="flex items-center justify-between mb-4">
            <text class="text-xl font-bold text-neutral-800">全部报修记录</text>
            <view 
              class="cursor-pointer select-none"
              @click="showAllRepairs = false"
              role="button"
              tabindex="0"
            >
              <base-icon type="close" size="20" color="#525252" />
            </view>
          </view>

          <scroll-view class="max-h-64" scroll-y="true">
            <view v-if="repairOrders.length == 0" class="text-center py-8">
              <base-icon type="chatbubble" size="32" color="#d4d4d4" />
              <text class="block text-neutral-400 mt-2 text-sm">暂无报修记录</text>
            </view>

            <view>
              <view 
                v-for="order in repairOrders" 
                :key="order.repair_order_id"
                class="bg-neutral-50 rounded-xl p-4 border border-neutral-200 mb-4 active:scale-95 transition-transform cursor-pointer"
                @click="goToRepairDetail(order.repair_order_id)"
              >
                <view class="flex flex-col gap-2">
                  <view class="flex items-center justify-between">
                    <text class="font-semibold text-neutral-800 text-sm truncate flex-1 mr-2">
                      {{ order.room_number }}
                    </text>
                    <uni-tag
                      :text="order.status_name"
                      size="small"
                      :type="getRepairStatusTagType(order.status_name)"
                      circle
                    />
                  </view>
                  <text class="block text-neutral-600 text-sm line-clamp-2">
                    {{ order.content }}
                  </text>
                  <view class="flex items-center justify-between text-xs text-neutral-500">
                    <text>{{ formatDateTime(order.create_time) }}</text>
                    <view class="flex items-center gap-1 select-none">
                      <base-icon type="eye" size="12" color="#525252" />
                      <text>查看详情</text>
                    </view>
                  </view>
                </view>
              </view>
            </view>
          </scroll-view>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// 响应式数据
const userInfo = ref({})
const repairOrders = ref([])
const showEditPopup = ref(false)
const showAllRepairs = ref(false)
const saving = ref(false)
const loading = ref(false)

// 编辑表单
const editForm = ref({
  name: '',
  student_id: '',
  phone_number: '',
  college: '',
  major: ''
})

const editFormRef = ref(null)
const editPopup = ref(null)
const repairsPopup = ref(null)

// 管理员视图状态
const isAdminUser = computed(() => userInfo.value.role_name === '管理员')
const isAdminView = ref(false)

// 数据初始化
onLoad(async () => {
  loading.value = true
  await loadUserInfo()
  await loadRepairOrders()
  loading.value = false
})

// 监听身份视图变化，写入全局变量
watch(isAdminView, (val) => {
  proxy.$cf.globalVariable.write({ variableName: 'current_view_mode', value: val ? 'admin' : 'user' })
})

// 加载用户信息
async function loadUserInfo() {
  try {
    const res = await proxy.$cf.login.getLoginUser()
    if (res.success && res.data) {
      userInfo.value = res.data
      isAdminView.value = false
      // 初始化编辑表单
      editForm.value = {
        name: res.data.name || '',
        student_id: res.data.student_id || '',
        phone_number: res.data.phone_number || '',
        college: res.data.college || '',
        major: res.data.major || ''
      }
    }
  } catch (error) {
    proxy.$cf.toast({ message: '加载用户信息失败', level: 'error' })
  }
}

// 加载报修记录
async function loadRepairOrders() {
  try {
    const userRes = await proxy.$cf.login.getLoginUser()
    if (!userRes.success || !userRes.data) return

    const res = await proxy.$cf.table.list({
      table_name: 'repair_order',
      param: {
        user_info_user_info_id_1: userRes.data.user_info_id
      }
    })

    if (res.success) {
      repairOrders.value = res.data || []
      console.log('报修记录加载成功，共', repairOrders.value.length, '条')
    } else {
      console.error('加载报修记录失败:', res.message)
    }
  } catch (error) {
    console.error('加载报修记录异常:', error)
  }
}

// 切换视图身份
function toggleIdentity() {
  isAdminView.value = !isAdminView.value
  proxy.$cf.toast({ message: isAdminView.value ? '已切换到管理员视图' : '已切换到用户视图', level: 'success' })
  // 此处应结合系统导航组件，动态调整显示的tabBar，如果无能力设置运行时导航，建议由上级逻辑监听此变量，做菜单项过滤
}

// 保存编辑资料
async function handleSaveProfile() {
  if (saving.value) return

  if (!editForm.value.name || !editForm.value.student_id || !editForm.value.phone_number || 
      !editForm.value.college || !editForm.value.major) {
    proxy.$cf.toast({ message: '请填写所有必填项', level: 'error' })
    return
  }

  saving.value = true
  try {
    const res = await proxy.$cf.table.update({
      table_name: 'user_info',
      param: {
        user_info_id: userInfo.value.user_info_id,
        name: editForm.value.name,
        student_id: editForm.value.student_id,
        phone_number: editForm.value.phone_number,
        college: editForm.value.college,
        major: editForm.value.major
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '资料更新成功', level: 'success' })
      await loadUserInfo()
      showEditPopup.value = false
    } else {
      proxy.$cf.toast({ message: res.message || '更新失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络错误，请重试', level: 'error' })
  } finally {
    saving.value = false
  }
}

// 退出登录
async function handleLogout() {
  const result = await proxy.$cf.model({
    title: '确认退出',
    message: '确定要退出登录吗？',
    confirmText: '退出',
    cancelText: '取消'
  })

  if (result.confirm) {
    try {
      const res = await proxy.$cf.logout.logout({})
      if (res.success) {
        proxy.$cf.navigate.to({
          url: '/pages/login/index',
          type: 'page'
        })
      }
    } catch (error) {
      proxy.$cf.toast({ message: '退出失败', level: 'error' })
    }
  }
}

// 跳转报修详情
function goToRepairDetail(repairOrderId) {
  proxy.$cf.navigate.to({
    url: `/pages/repair_detail_user/index?repair_order_id=${repairOrderId}`,
    type: 'page'
  })
}

// 状态标签类型
function getStatusTagType(status) {
  if (!status) return 'default'
  if (status == '审核通过') return 'success'
  if (status == '待审核') return 'warning'
  if (status == '禁用') return 'error'
  return 'default'
}

function getRepairStatusTagType(status) {
  if (!status) return 'default'
  if (status == '已完成') return 'success'
  if (status == '处理中') return 'primary'
  if (status == '待审核') return 'warning'
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
    return '无效日期'
  }
}

function formatDateTime(dateTimeString) {
  if (!dateTimeString) return '无'
  try {
    const date = new Date(dateTimeString.replace(/-/g, '/'))
    return date.toLocaleDateString('zh-CN', {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch {
    return '无效日期'
  }
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