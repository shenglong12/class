<template>
  <base-layout class="h-full flex flex-col justify-center min-h-screen">
    <!-- 背景层：渐变+高斯模糊+光影 -->
    <view class="absolute inset-0 w-full h-full z-0">
      <image
        src="https://www.codeflying.net/preview/university-campus.jpg"
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
      <view class="flex flex-col items-center mb-6">
        <image
          src="https://www.codeflying.net/preview/university-logo.jpg"
          class="w-20 h-20 rounded-full shadow-xl mb-3 border-4 border-white ring-4 ring-primary-200 animate-bounce-slow"
          mode="aspectFill"
        />
        <text class="text-3xl font-extrabold text-primary-500 mb-1 drop-shadow-lg tracking-wide font-sans">校园连接</text>
        <text class="text-base text-text-secondary font-light tracking-wide opacity-90">加入我们的学术社区</text>
      </view>

      <!-- 注册表单卡片 -->
      <view class="w-full max-w-md box-border bg-white bg-opacity-95 rounded-3xl shadow-2xl py-6 px-6 mb-4 backdrop-blur-lg border border-primary-100 animate-fade-in">
        <uni-forms :modelValue="formData" label-position="top" label-width="100%" ref="formRef">
          <!-- 姓名 -->
          <uni-forms-item required label="姓名" name="name">
            <uni-easyinput 
              type="text" 
              v-model="formData.name" 
              placeholder="请输入您的姓名"
              prefixIcon="person"
              class="box-border"
            />
          </uni-forms-item>

          <!-- 学号 -->
          <uni-forms-item required label="学号" name="student_id">
            <uni-easyinput 
              type="text" 
              v-model="formData.student_id" 
              placeholder="请输入您的学号"
              prefixIcon="contact"
              class="box-border"
            />
          </uni-forms-item>

          <!-- 手机号 -->
          <uni-forms-item required label="手机号" name="phone_number">
            <uni-easyinput 
              type="text" 
              v-model="formData.phone_number" 
              placeholder="请输入您的手机号"
              prefixIcon="phone"
              class="box-border"
            />
          </uni-forms-item>

          <!-- 学院 -->
          <uni-forms-item required label="学院" name="college">
            <uni-easyinput 
              type="text" 
              v-model="formData.college" 
              placeholder="请输入所属学院"
              prefixIcon="home"
              class="box-border"
            />
          </uni-forms-item>

          <!-- 专业 -->
          <uni-forms-item required label="专业" name="major">
            <uni-easyinput 
              type="text" 
              v-model="formData.major" 
              placeholder="请输入专业"
              prefixIcon="staff"
              class="box-border"
            />
          </uni-forms-item>

          <!-- 密码 -->
          <uni-forms-item required label="密码" name="password">
            <uni-easyinput 
              type="password" 
              v-model="formData.password" 
              placeholder="请设置密码"
              prefixIcon="locked"
              class="box-border"
            />
          </uni-forms-item>

          <!-- 确认密码 -->
          <uni-forms-item required label="确认密码" name="confirm_password">
            <uni-easyinput 
              type="password" 
              v-model="formData.confirm_password" 
              placeholder="请确认密码"
              prefixIcon="locked-filled"
              class="box-border"
            />
          </uni-forms-item>

          <!-- 注册按钮 -->
          <button 
            class="w-full box-border bg-primary-500 hover:bg-primary-600 text-white font-semibold rounded-full mt-4 flex items-center justify-center gap-2 shadow transition-all duration-150"
            @click="handleRegister"
            :disabled="loading"
          >
            <base-icon type="personadd" size="20" color="#fff" />
            <text>{{ loading ? '注册中...' : '创建账号' }}</text>
          </button>
        </uni-forms>
      </view>

      <!-- 登录入口 -->
      <view class="w-full flex flex-col items-center mt-2">
        <text class="text-sm text-text-secondary mb-2">已有账号？</text>
        <view
          class="flex flex-col items-center justify-center cursor-pointer select-none py-2"
          style="min-width: 120px;"
          @click="goToLogin"
        >
          <base-icon type="person" size="32" color="#339cff" class="mb-1" />
          <text class="text-base text-primary-500 font-semibold tracking-wide">去登录</text>
        </view>
      </view>
    </view>

    <!-- 成功注册弹窗 -->
    <uni-popup ref="successPopup" type="center">
      <view class="bg-white p-6 rounded-2xl shadow-xl w-80 text-center">
        <base-icon type="checkmarkempty" size="48" color="#33bf71" class="mb-4" />
        <text class="text-xl font-bold text-success-600 block mb-2">注册成功！</text>
        <text class="text-text-secondary text-sm mb-4">您的账号正在审核中，审核通过后将收到通知。</text>
        <button 
          class="w-full bg-success-500 text-white rounded-full py-2 font-semibold"
          @click="closeSuccessPopup"
        >
          继续登录
        </button>
      </view>
    </uni-popup>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance();

const formData = ref({
  name: '',
  student_id: '',
  phone_number: '',
  college: '',
  major: '',
  password: '',
  confirm_password: ''
});

const loading = ref(false);
const formRef = ref(null);
const successPopup = ref(null);

// 处理注册
async function handleRegister() {
  if (loading.value) return;
  
  // 表单验证
  if (!formData.value.name || !formData.value.student_id || !formData.value.phone_number || 
      !formData.value.college || !formData.value.major || !formData.value.password) {
    proxy.$cf.toast({
      message: '请填写所有必填项',
      level: 'error',
      duration: 3000
    });
    return;
  }

  if (formData.value.password !== formData.value.confirm_password) {
    proxy.$cf.toast({
      message: '两次密码输入不一致',
      level: 'error',
      duration: 3000
    });
    return;
  }

  if (formData.value.password.length < 6) {
    proxy.$cf.toast({
      message: '密码长度至少为6位',
      level: 'error',
      duration: 3000
    });
    return;
  }

  // 增加密码复杂度校验（包含字母和数字）
  if (!/(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}/.test(formData.value.password)) {
    proxy.$cf.toast({
      message: '密码必须包含字母和数字',
      level: 'error',
      duration: 3000
    });
    return;
  }

  loading.value = true;

  try {
    // 获取当前时间
    const now = new Date();
    const year = now.getFullYear();
    const month = ('0' + (now.getMonth() + 1)).slice(-2);
    const day = ('0' + now.getDate()).slice(-2);
    const hours = ('0' + now.getHours()).slice(-2);
    const minutes = ('0' + now.getMinutes()).slice(-2);
    const seconds = ('0' + now.getSeconds()).slice(-2);
    const registrationDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

    // 调用注册API，使用数据库实际字段名
    const result = await proxy.$cf.register.register({
      table_name: 'user_info',
      param: {
        name: formData.value.name,
        student_id: formData.value.student_id,
        phone_number: formData.value.phone_number,
        college: formData.value.college,
        major: formData.value.major,
        password: formData.value.password,
        role_enum_role_enum_id_1: 1, // 普通用户角色（使用数据库实际字段名）
        user_status_enum_user_status_enum_id_1: 1, // 待审核状态（使用数据库实际字段名）
        registration_date: registrationDate
      }
    });

    if (result.success) {
      // 显示成功弹窗
      successPopup.value.open();
      proxy.$cf.toast({
        message: '注册提交成功',
        level: 'success',
        duration: 3000
      });
    } else {
      proxy.$cf.toast({
        message: result.message || '注册失败',
        level: 'error',
        duration: 3000
      });
    }
  } catch (error) {
    console.error('注册错误:', error);
    proxy.$cf.toast({
      message: '注册失败，请重试',
      level: 'error',
      duration: 3000
    });
  } finally {
    loading.value = false;
  }
}

// 关闭成功弹窗并跳转到登录页
function closeSuccessPopup() {
  successPopup.value.close();
  goToLogin();
}

// 跳转到登录页
function goToLogin() {
  proxy.$cf.navigate.to({
    url: '/pages/login/index',
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

/* 表单输入框聚焦样式 */
::v-deep .uni-easyinput__content.input-focus {
  border-color: #339cff !important;
  box-shadow: 0 0 0 2px rgba(51, 156, 255, 0.13);
  background: #f5faff !important;
  transition: border-color 0.2s, box-shadow 0.2s, background 0.2s;
}

/* 按钮禁用状态 */
button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>