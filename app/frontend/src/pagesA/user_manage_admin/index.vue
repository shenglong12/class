<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4">
        <text class="text-title font-bold text-primary-700">用户管理</text>
      </view>
      <!-- 刷新按钮 -->
      <view class="mb-4">
        <button 
          class="bg-primary-500 text-white rounded-full px-4 flex items-center font-semibold active:scale-95 transition-transform"
          @click="refreshUserList"
          aria-label="刷新用户列表"
        >
          <base-icon type="refresh" size="18" class="mr-1" color="#fff" />
          <text>刷新</text>
        </button>
      </view>

      <!-- 筛选区域 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <view class="grid grid-cols-2 gap-3">
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

      <!-- 用户列表 -->
      <view v-if="users.length > 0">
        <view
          v-for="user in users"
          :key="user.user_info_id"
          class="bg-white rounded-xl shadow-lg p-4 mb-4 transition-all duration-150 hover:shadow-xl"
        >
          <!-- 用户基本信息 -->
          <view class="mb-3">
            <view class="mb-1 flex flex-col">
              <view class="flex items-center gap-2 mb-1 flex-wrap">
                <text class="text-lg font-semibold text-primary-800">{{ user.name }}</text>
                <uni-tag
                  :text="user.status_name"
                  size="small"
                  :type="getStatusTagType(user.status_name)"
                  circle
                />
                <uni-tag
                  :text="user.role_name"
                  size="small"
                  type="primary"
                  circle
                />
              </view>
              <text class="block text-sm text-neutral-600">学号: {{ user.student_id }}</text>
              <text class="block text-sm text-neutral-600">{{ user.phone_number }}</text>
            </view>
          </view>

          <!-- 学院专业信息 -->
          <view class="mb-3">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="home-filled" size="16" color="#4b5563" />
              <text class="text-sm text-neutral-700">{{ user.college }}</text>
            </view>
            <view class="flex items-center gap-2">
              <base-icon type="settings-filled" size="16" color="#4b5563" />
              <text class="text-sm text-neutral-700">{{ user.major }}</text>
            </view>
          </view>

          <!-- 注册时间 -->
          <view class="flex items-center gap-2 mb-4">
            <base-icon type="calendar" size="16" color="#4b5563" />
            <text class="text-xs text-neutral-500">
              注册时间: {{ formatDateTime(user.registration_date) }}
            </text>
          </view>

          <!-- 操作按钮 -->
          <view class="flex flex-wrap gap-2">
            <button
              class="flex-1 min-w-[90px] bg-primary-500 text-white rounded-full py-2 font-medium active:scale-95 transition-transform flex items-center justify-center"
              @click="editUser(user.user_info_id)"
              aria-label="编辑用户"
            >
              <base-icon type="compose" size="16" class="mr-1" color="#fff" />
              编辑
            </button>
            
            <!-- 状态操作按钮 -->
            <button
              v-if="user.status_name == '待审核'"
              class="flex-1 min-w-[90px] bg-success-500 text-white rounded-full py-2 font-medium active:scale-95 transition-transform flex items-center justify-center"
              @click="approveUser(user.user_info_id)"
              aria-label="批准用户"
            >
              <base-icon type="checkmarkempty" size="16" class="mr-1" color="#fff" />
              批准
            </button>
            
            <button
              v-if="user.status_name == '待审核'"
              class="flex-1 min-w-[90px] bg-danger-500 text-white rounded-full py-2 font-medium active:scale-95 transition-transform flex items-center justify-center"
              @click="rejectUser(user.user_info_id)"
              aria-label="拒绝用户"
            >
              <base-icon type="closeempty" size="16" class="mr-1" color="#fff" />
              拒绝
            </button>
            
            <button
              v-if="user.status_name == '审核通过'"
              class="flex-1 min-w-[90px] bg-warning-500 text-white rounded-full py-2 font-medium active:scale-95 transition-transform flex items-center justify-center"
              @click="disableUser(user.user_info_id)"
              aria-label="禁用用户"
            >
              <base-icon type="locked" size="16" class="mr-1" color="#fff" />
              禁用
            </button>
            
            <button
              v-if="user.status_name == '禁用'"
              class="flex-1 min-w-[90px] bg-secondary-500 text-white rounded-full py-2 font-medium active:scale-95 transition-transform flex items-center justify-center"
              @click="resetUser(user.user_info_id)"
              aria-label="重置用户"
            >
              <base-icon type="refresh" size="16" class="mr-1" color="#fff" />
              重置
            </button>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else class="flex flex-col items-center justify-center py-24 bg-white rounded-xl shadow-inner">
        <base-icon type="person" size="60" color="#9ca3af" />
        <text class="text-neutral-400 mt-4 text-base font-semibold">未找到用户</text>
        <text class="text-neutral-400 mt-2 text-sm">请调整筛选条件</text>
      </view>

      <!-- 加载更多 -->
      <view v-if="hasMore" class="flex justify-center mt-6 mb-4">
        <button
          class="bg-primary-500 text-white rounded-full px-12 font-semibold active:scale-95 transition-transform"
          :disabled="loading"
          @click="loadMore"
          aria-label="加载更多用户"
        >
          <base-icon v-if="loading" type="spinner-cycle" size="18" class="animate-spin mr-1" color="#fff" />
          <text v-else>加载更多</text>
        </button>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance();

// ------------------ Data ------------------
const users = ref([]);
const filterParams = ref({
  college: '',
  major: ''
});
const pageInfo = ref({
  current: 1,
  pageSize: 10
});
const total = ref(0);
const hasMore = ref(true);
const loading = ref(false);
const currentAdmin = ref(null);
let filterTimer = null;

// ------------------ Lifecycle ------------------
onLoad(async () => {
  await fetchCurrentAdmin();
  await fetchUserList(true);
});

onUnload(() => {
  if (filterTimer) {
    clearTimeout(filterTimer);
    filterTimer = null;
  }
});

// ------------------ Methods ------------------

// 获取当前管理员信息
async function fetchCurrentAdmin() {
  const res = await proxy.$cf.login.getLoginUser();
  if (res.success && res.data) {
    currentAdmin.value = res.data;
  }
}

// 获取用户列表
async function fetchUserList(reset = false) {
  if (loading.value) return;
  loading.value = true;
  
  try {
    if (reset) {
      pageInfo.value.current = 1;
      users.value = [];
      hasMore.value = true;
    }

    // 构建查询参数
    let param = {
      current: pageInfo.value.current,
      pageSize: pageInfo.value.pageSize
    };

    // 添加筛选条件
    if (filterParams.value.college) {
      param.college = filterParams.value.college;
    }
    if (filterParams.value.major) {
      param.major = filterParams.value.major;
    }

    // 按注册时间倒序
    param.order_by = {
      'registration_date': 'desc'
    };

    const res = await proxy.$cf.table.page({
      table_name: 'user_info',
      param
    });

    if (res.success) {
      if (reset) {
        users.value = res.data.records;
      } else {
        users.value = users.value.concat(res.data.records);
      }
      total.value = res.data.total;
      hasMore.value = users.value.length < total.value;
    } else {
      proxy.$cf.toast({ message: res.message || '加载用户失败', level: 'error' });
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，加载用户失败', level: 'error' });
  } finally {
    loading.value = false;
  }
}

// 筛选条件变化
function handleFilterChange() {
  if (filterTimer) clearTimeout(filterTimer);
  filterTimer = setTimeout(() => {
    fetchUserList(true);
  }, 500);
}

// 刷新列表
function refreshUserList() {
  fetchUserList(true);
}

// 加载更多
function loadMore() {
  if (hasMore.value && !loading.value) {
    pageInfo.value.current += 1;
    fetchUserList(false);
  }
}

// 编辑用户
function editUser(userId) {
  proxy.$cf.navigate.to({
    url: `/pages/user_edit_admin/index?user_info_id=${userId}`,
    type: 'page'
  });
}

// 批准用户
async function approveUser(userId) {
  const confirm = await proxy.$cf.model({
    title: "确认批准",
    message: "确定要批准该用户吗？",
    confirmText: '批准',
    cancelText: '取消'
  });

  if (confirm.confirm) {
    await updateUserStatus(userId, 2); // 审核通过
  }
}

// 拒绝用户
async function rejectUser(userId) {
  const confirm = await proxy.$cf.model({
    title: "确认拒绝",
    message: "确定要拒绝该用户吗？",
    confirmText: '拒绝',
    cancelText: '取消'
  });

  if (confirm.confirm) {
    await updateUserStatus(userId, 3); // 禁用（拒绝）
  }
}

// 禁用用户
async function disableUser(userId) {
  const confirm = await proxy.$cf.model({
    title: "确认禁用",
    message: "确定要禁用该用户吗？",
    confirmText: '禁用',
    cancelText: '取消'
  });

  if (confirm.confirm) {
    await updateUserStatus(userId, 3); // 禁用
  }
}

// 重置用户
async function resetUser(userId) {
  const confirm = await proxy.$cf.model({
    title: "确认重置",
    message: "确定要将该用户重置为待审核状态吗？",
    confirmText: '重置',
    cancelText: '取消'
  });

  if (confirm.confirm) {
    await updateUserStatus(userId, 1); // 待审核
  }
}

// 更新用户状态
async function updateUserStatus(userId, statusId) {
  try {
    const res = await proxy.$cf.table.update({
      table_name: 'user_info',
      param: {
        user_info_id: userId,
        user_status_enum_id: statusId
      }
    });

    if (res.success) {
      proxy.$cf.toast({ message: '用户状态更新成功', level: 'success' });
      await fetchUserList(true);
    } else {
      proxy.$cf.toast({ message: res.message || '更新用户状态失败', level: 'error' });
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，更新用户状态失败', level: 'error' });
  }
}

// 状态标签类型
function getStatusTagType(status) {
  if (!status) return 'default';
  if (status == '待审核') return 'warning';
  if (status == '审核通过') return 'success';
  if (status == '禁用') return 'error';
  return 'default';
}

// 时间格式化
function formatDateTime(dt) {
  if (!dt) return '--';
  try {
    const d = new Date(dt.replace(/-/g, '/'));
    if (isNaN(d.getTime())) return '--';
    const y = d.getFullYear();
    const m = ('0' + (d.getMonth() + 1)).slice(-2);
    const day = ('0' + d.getDate()).slice(-2);
    const h = ('0' + d.getHours()).slice(-2);
    const min = ('0' + d.getMinutes()).slice(-2);
    return `${y}-${m}-${day} ${h}:${min}`;
  } catch {
    return '--';
  }
}
</script>

<style>
/* 自定义样式 */
</style>