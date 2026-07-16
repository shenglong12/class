<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50 flex flex-col">
      <view class="mb-6 text-center">
        <text class="text-3xl font-extrabold text-primary-700">管理员首页</text>
        <text class="block mt-2 text-neutral-600 text-body">欢迎，管理员！管理系统入口</text>
      </view>

      <view class="grid grid-cols-3 gap-6">
        <view
          v-for="item in menuItems"
          :key="item.page"
          class="bg-white rounded-xl shadow-lg p-4 flex flex-col items-center justify-center cursor-pointer active:scale-95 transition-transform"
          @click="goToPage(item.page)"
          role="button"
          tabindex="0"
          :aria-label="`跳转到${item.label}页面`"
        >
          <base-icon :type="item.icon" size="36" color="#339cff" class="mb-3" />
          <text class="text-primary-700 font-semibold text-center">{{ item.label }}</text>
        </view>
      </view>

      <view class="mt-auto p-4 text-center text-neutral-500 text-sm">
        管理员权限专属导航
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

const menuItems = [
  { label: '人员管理', page: 'user_manage_admin', icon: 'personadd' },
  { label: '教室管理', page: 'room_manage_admin', icon: 'home-filled' },
  { label: '课表管理', page: 'schedule_manage_admin', icon: 'calendar-filled' },
  { label: '报修管理', page: 'repair_manage_admin', icon: 'chatbubble-filled' },
  { label: '预约审核', page: 'booking_audit_admin', icon: 'flag-filled' },
  { label: '个人中心', page: 'profile_user', icon: 'person' }
]

function goToPage(pageName) {
  proxy.$cf.navigate.to({
    url: `/pages/${pageName}/index`,
    type: 'page'
  })
}
</script>

<style scoped>
/* 栅格网格布局，每行3个按钮，间距适中 */
.grid-cols-3 {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}
</style>