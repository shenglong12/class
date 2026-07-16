<template>
  <base-layout class="h-full flex flex-col justify-center min-h-screen">
    <!-- 背景层：渐变+高斯模糊+光影 -->
    <view class="absolute inset-0 w-full h-full z-0">
      <image
        src="https://www.codeflying.net/preview/student-class.jpg"
        class="w-full h-full object-cover absolute inset-0 opacity-70"
        mode="aspectFill"
      />
      <view class="absolute inset-0 bg-gradient-to-br from-primary-100 via-secondary-100 to-primary-200 opacity-90 backdrop-blur-xl"></view>
      <view class="absolute top-0 left-0 w-1/2 h-1/3 bg-white opacity-20 rounded-br-3xl blur-2xl shadow-2xl"></view>
      <view class="absolute bottom-0 right-0 w-2/3 h-1/3 bg-primary-100 opacity-20 rounded-tl-3xl blur-2xl shadow-2xl"></view>
    </view>
    
    <!-- 内容容器 -->
    <view class="relative flex flex-col items-center justify-center flex-1 px-6 z-10 min-h-screen">
      <!-- 顶部logo与欢迎语 -->
      <view class="flex flex-col items-center mb-8">
        <image
          src="https://www.codeflying.net/preview/student-class.jpg"
          class="w-24 h-24 rounded-full shadow-xl mb-4 border-4 border-white ring-4 ring-primary-200 animate-bounce-slow"
          mode="aspectFill"
        />
        <text class="text-3xl font-extrabold text-primary-600 mb-2 drop-shadow-lg tracking-wide font-sans">教室管理系统</text>
        <text class="text-base text-text-secondary font-light tracking-wide opacity-90">智慧校园访问平台</text>
      </view>

      <!-- 登录表单卡片 -->
      <view class="w-full max-w-md box-border bg-white bg-opacity-95 rounded-3xl shadow-2xl py-8 px-6 mb-6 backdrop-blur-lg border border-primary-100 animate-fade-in">
        <base-login
          login_type="passwd"
          show_title="欢迎登录"
          relevanceTable="user_info"
          @loginSuccess="onLoginSuccess"
          @loginFail="onLoginFail"
          class="login-form-animate"
        >
          <!-- 登录按钮自定义插槽 -->
          <template #loginButton>
            <button class="w-full box-border bg-primary-500 hover:bg-primary-600 text-white font-semibold rounded-full mt-6 flex items-center justify-center gap-2 shadow transition-all duration-150 active:scale-95">
              <base-icon type="locked" size="20" color="#fff" />
              登录
            </button>
          </template>
        </base-login>
      </view>

      <!-- 注册入口 -->
      <view class="w-full flex flex-col items-center">
        <text class="text-sm text-text-secondary mb-3">还没有账号？</text>
        <view
          class="flex flex-col items-center justify-center cursor-pointer select-none py-2"
          style="min-width: 120px;"
          @click="goRegister"
        >
          <base-icon type="personadd" size="32" color="#1a7ed6" class="mb-1" />
          <text class="text-base text-primary-700 font-semibold tracking-wide">注册新账号</text>
        </view>
      </view>

      <!-- 角色提示 -->
      <view class="mt-8 bg-white bg-opacity-80 rounded-xl p-4 max-w-md">
        <text class="text-sm text-text-secondary text-center block leading-relaxed">
          • 学生与教职工：访问教室课程表和预约管理
          <br />
          • 管理员：管理用户、教室及系统设置
        </text>
      </view>
    </view>
  </base-layout>
</template>


<script setup>
const { proxy } = getCurrentInstance();

// 登录成功回调
async function onLoginSuccess(userData) {
  if (!userData || !userData.role_enum_role_enum_id_1) {
    proxy.$cf.toast({ message: '登录信息异常', level: 'error' });
    return;
  }

  // 用户角色
  const roleId = userData.role_enum_role_enum_id_1

  // 写入全局变量user_role
  const userRole = (roleId === 2) ? 'admin' : 'user';
  await proxy.$cf.globalVariable.write({
    variableName: 'user_role',
    value: userRole
  });

  // 根据角色跳转不同首页
  if (userRole === 'admin') {
    await proxy.$cf.navigate.to({
      url: '/pages/admin_home/index',
      type: 'page'
    });
  } else {
    await proxy.$cf.navigate.to({
      url: '/pages/home_user/index',
      type: 'page'
    });
  }

  proxy.$cf.toast({
    message: '登录成功！',
    level: 'success',
    duration: 2000
  });
}

// 登录失败回调
function onLoginFail() {
  proxy.$cf.toast({
    message: '登录失败，请检查账号和密码。',
    level: 'error',
    duration: 3000
  });
}

// 跳转到注册
function goRegister() {
  proxy.$cf.navigate.to({
    url: '/pages/register/index',
    type: 'page'
  });
}
</script>

<style scoped>
@keyframes bounce-slow {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
}
.animate-bounce-slow {
  animation: bounce-slow 2.8s infinite;
}

@keyframes fade-in {
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}
.animate-fade-in {
  animation: fade-in 0.8s cubic-bezier(.4,0,.2,1) both;
}

::v-deep .uni-easyinput__content.input-focus {
  border-color: #339cff !important;
  box-shadow: 0 0 0 2px rgba(51, 156, 255, 0.13);
  background: #f5faff !important;
  transition: border-color 0.2s, box-shadow 0.2s, background 0.2s;
}

.login-form-animate {
  animation-delay: 0.2s;
}
</style>