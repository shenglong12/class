<template>
          <div class="welcome-container">
    <div class="welcome-content">
      <div class="welcome-text">
        <h1 class="title">欢迎回来</h1>
        <p class="subtitle">管理后台系统</p>
        <div class="divider"></div>
        <p class="quote">"效率是做好工作的灵魂"</p>
      </div>
      <div class="welcome-graphic">
        <div class="graphic-circle"></div>
        <div class="graphic-line"></div>
      </div>
    </div>
  </div>  
    <el-row :gutter="20" class="stats-container" style="margin-left: 0.01rem">
      <el-col :span="6">
        <div class="stat-card gradient-purple">
          <el-statistic
              title="网络状态 Network status"
              :value="networkInfo.value"
              :value-style="valueStyle"
              class="glow-text"
          >
            <template #prefix>
              <el-icon :size="20" class="icon-bounce">
                <Connection />
              </el-icon>
            </template>
            <template #suffix>
<!--              <span class="trend-up">{{ effectiveSpeed.value }}</span>-->
            </template>
          </el-statistic>
          <div class="corner-ribbon">HOT</div>
        </div>
      </el-col>
  
      <el-col :span="6">
        <div class="stat-card gradient-blue">
          <el-statistic
              :value="currentTime + '\t' + weekday"
              :value-style="valueStyle"
          >
            <template #title>
              <div class="title-with-icon">
                北京时间 Beijing time
                <el-icon style="margin-left: 4px" :size="24" class="gender-icon">
                  <Timer/>
                </el-icon>
              </div>
            </template>
            <template #suffix></template>
          </el-statistic>
          <div class="sparkline"></div>
        </div>
      </el-col>
  
      <el-col :span="6">
        <div class="stat-card gradient-green">
          <el-statistic
              :value="browserInfo.value + '\t' + browserVersion.value"
              :value-style="valueStyle"
              class="pulse-animation"
          >
            <template #title>
              <div class="title-with-icon">
                浏览器信息 Browser information
                <el-icon style="margin-left: 4px" :size="24" class="gender-icon">
                  <ChromeFilled />
                </el-icon>
              </div>
            </template>
            <<!--template #suffix>
                <el-icon :size="20" class="icon-float">
                  <ChatLineRound/>
                </el-icon>
              </template>-->
          </el-statistic>
          <div class="bubble-animation">
            <div class="bubble" v-for="i in 3" :key="i"></div>
          </div>
        </div>
      </el-col>
  
      <el-col :span="6">
        <div class="stat-card gradient-orange">
          <el-statistic
              title="设备信息 Equipment information"
              :value="'网页分辨率' + '\t' + screenInfo.value"
              :value-style="valueStyle"
          >
            <template #prefix>
              <el-icon :size="20" class="">
                <Monitor/>
              </el-icon>
            </template>
            <template #suffix>
              <span class="currency">{{ deviceInfo.name }}</span>
            </template>
          </el-statistic>
          <div class="progress-bar">
<!--            <div class="progress-fill" :style="{width: `${Math.min(outputValue/10000 * 100, 100)}%`}"></div>-->
          </div>
        </div>
      </el-col>
    </el-row><div style="display:flex;flex-wrap: wrap;justify-content: space-between;">

  <base-statistics
group_result_show_display="line"
:is_group="false"
ref="booking_info_booking_date_datetime_statistic_eae3f8e9"
model="booking_info_booking_date_datetime_statistic_eae3f8e9_count"
api="booking_info_statistic"
title="预约日期"
>

</base-statistics>
<base-statistics
group_result_show_display="pie"
:is_group="false"
ref="booking_info_booking_date_datetime_statistic_e87a89ae"
model="booking_info_booking_date_datetime_statistic_e87a89ae_count"
api="booking_info_statistic"
title="预约日期"
>

</base-statistics>
<base-statistics
group_result_show_display="pie"
:is_group="false"
ref="user_info_statistic_d748e97d"
model="user_info_statistic_d748e97d_count"
api="user_info_statistic"
title="用户信息"
>

</base-statistics>
<base-statistics
group_result_show_display="bar"
:is_group="false"
ref="user_info_statistic_fd86a11f"
model="user_info_statistic_fd86a11f_count"
api="user_info_statistic"
title="用户信息"
>

</base-statistics>
<base-statistics
group_result_show_display="line"
:is_group="false"
ref="user_info_registration_date_datetime_statistic_c6d4320e"
model="user_info_registration_date_datetime_statistic_c6d4320e_count"
api="user_info_statistic"
title="注册时间"
>

</base-statistics>
<base-statistics
group_result_show_display="bar"
:is_group="false"
ref="user_info_registration_date_datetime_statistic_f89dba19"
model="user_info_registration_date_datetime_statistic_f89dba19_count"
api="user_info_statistic"
title="注册时间"
>

</base-statistics>
<base-statistics
group_result_show_display="line"
:is_group="false"
ref="repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4"
model="repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_4de288b4_count"
api="repair_order_statistic"
title="报修状态"
>

</base-statistics>
<base-statistics
group_result_show_display="pie"
:is_group="false"
ref="repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b"
model="repair_order_repair_status_enum_repair_status_enum_id_1_status_statistic_86902e9b_count"
api="repair_order_statistic"
title="报修状态"
>

</base-statistics>
<base-statistics
group_result_show_display="pie"
:is_group="false"
ref="booking_info_end_time_datetime_statistic_e6adae60"
model="booking_info_end_time_datetime_statistic_e6adae60_count"
api="booking_info_statistic"
title="预约结束时间"
>

</base-statistics>
<base-statistics
group_result_show_display="bar"
:is_group="false"
ref="booking_info_end_time_datetime_statistic_d22436f5"
model="booking_info_end_time_datetime_statistic_d22436f5_count"
api="booking_info_statistic"
title="预约结束时间"
>

</base-statistics>
  </div>

</template>

<script setup>
import {useTransition} from '@vueuse/core'
  import {ref, onMounted, onBeforeUnmount} from 'vue'
  
  const weekday = ref('')
  const currentTime = ref('')
  // 设备信息
  const deviceInfo = ref({})
  const screenInfo = ref({})
  // 网络在线状态
  const networkInfo = ref({})
  const effectiveSpeed = ref({})
  
  
  function updateTime() {
    const now = new Date()
    currentTime.value = now.toLocaleString()
  }
  
  // 判断是否是免费开发的
  function updateWeekday() {
    const days = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
    const today = new Date()
    weekday.value = days[today.getDay()]
  }
  
  
  // 获取设备信息
  const getDeviceInfo = () => {
    const isMobile = /Mobi|Android|iPhone|iPad|iPod/i.test(navigator.userAgent)
  
    deviceInfo.value = {type: isMobile ? '移动设备' : '桌面设备', name: isMobile ? 'Mobile phone' : "PC"}
    screenInfo.value = {label: '屏幕分辨率', value: `${window.screen.width} × ${window.screen.height}`}
  
  }
  
  // 浏览器信息
  const browserInfo = ref({})
  const browserVersion = ref({})
  
  // 获取浏览器信息
  const getBrowserInfo = () => {
    const ua = navigator.userAgent
    let browserName = '未知浏览器'
  
    if (ua.includes('Firefox')) browserName = 'Firefox'
    else if (ua.includes('Chrome')) browserName = 'Chrome'
    else if (ua.includes('Safari')) browserName = 'Safari'
    else if (ua.includes('Edge')) browserName = 'Edge'
    else if (ua.includes('Opera')) browserName = 'Opera'
    else if (ua.includes('Trident')) browserName = 'IE'
  
    browserInfo.value =
        {label: '浏览器', value: browserName}
    browserVersion.value = {
      label: '版本',
      value: ua.match(/(Chrome|Firefox|Safari|Edge|Opera|MSIE|rv:)[\s\/:]([\w\d\.]+)/i)?.[2] || '未知'
    }
  }
  
  const getNetworkInfo = () => {
    const connection = navigator.connection || navigator.mozConnection || navigator.webkitConnection
    networkInfo.value = {label: '在线状态', value: navigator.onLine ? '已登陆管理后台' : '未登录管理后台'}
    effectiveSpeed.value = {label: '网络速度', value: connection?.effectiveType.toUpperCase() || '未知'}
  }
  
  let timerDate
  let timerWeek
  
  onMounted(() => {
    getDeviceInfo()
    getBrowserInfo()
    getNetworkInfo()
    updateTime() // 立即更新一次
    updateWeekday()
    timerDate = setInterval(updateTime, 1000) // 每秒更新一次
    timerWeek = setInterval(updateTime, 600000) // 每分钟更新
  })
  
  onBeforeUnmount(() => {
    clearInterval(timerDate) // 组件卸载时清除定时器
    clearInterval(timerWeek) // 组件卸载时清除定时器
  })
  
  const source = ref(0)
  const outputValue = useTransition(source, {
    duration: 1500,
  })
  const valueStyle = {
    fontSize: '32px',
    fontWeight: 'bold',
    color: '#fff'
  }
  
  source.value = 172000
</script>

<style lang="scss" scoped>
    .stats-container {
    margin: 20px 0;
  }
  
  .stat-card {
    position: relative;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    color: white;
    transition: all 0.3s ease;
    height: 140px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  
  .stat-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
  }
  
  .gradient-purple {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  }
  
  .gradient-blue {
    background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  }
  
  .gradient-orange {
    background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
  }
  
  .gradient-green {
    background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  }
  
  .glow-text {
    text-shadow: 0 0 10px rgba(255, 255, 255, 0.5);
  }
  
  .trend-up {
    background: rgba(255, 255, 255, 0.2);
    padding: 2px 8px;
    border-radius: 10px;
    font-size: 16px;
    margin-left: 5px;
  }
  
  .corner-ribbon {
    position: absolute;
    top: 10px;
    right: -25px;
    width: 100px;
    background: #ff4757;
    color: white;
    text-align: center;
    line-height: 20px;
    font-size: 12px;
    transform: rotate(45deg);
    box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);
  }
  
  .title-with-icon {
    display: inline-flex;
    align-items: center;
    font-size: 14px;
    opacity: 0.9;
  }
  
  .gender-icon {
    background: rgba(255, 255, 255, 0.2);
    padding: 3px;
    border-radius: 50%;
  }
  
  .sparkline {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 30px;
    background: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 20" preserveAspectRatio="none"><path d="M0,10 C20,15 40,5 60,15 80,5 100,10" fill="none" stroke="rgba(255,255,255,0.5)" stroke-width="2"/></svg>') no-repeat;
    background-size: 100% 100%;
    opacity: 0.5;
  }
  
  .progress-bar {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 4px;
    background: rgba(255, 255, 255, 0.2);
  }
  
  .progress-fill {
    height: 100%;
    background: white;
    transition: width 1s ease;
  }
  
  .bubble-animation {
    position: absolute;
    bottom: 10px;
    right: 10px;
  }
  
  .bubble {
    width: 8px;
    height: 8px;
    background: white;
    border-radius: 50%;
    display: inline-block;
    margin: 0 2px;
    opacity: 0.6;
    animation: bubble 1.5s infinite ease-in-out;
  }
  
  .bubble:nth-child(1) {
    animation-delay: 0s;
  }
  
  .bubble:nth-child(2) {
    animation-delay: 0.3s;
  }
  
  .bubble:nth-child(3) {
    animation-delay: 0.6s;
  }
  
  @keyframes bubble {
    0%, 100% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-10px);
    }
  }
  
  .icon-bounce {
    animation: bounce 2s infinite;
  }
  
  /*旋转*/
  .icon-spin {
    animation: spin 3s linear infinite;
  }
  
  .icon-float {
    animation: float 3s ease-in-out infinite;
  }
  
  @keyframes bounce {
    0%, 100% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-5px);
    }
  }
  
  @keyframes spin {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(360deg);
    }
  }
  
  @keyframes float {
    0%, 100% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-5px);
    }
  }
  
  .pulse-animation {
    // animation: pulse 2s infinite;
  }
  
  @keyframes pulse {
    0% {
      transform: scale(1);
    }
    50% {
      transform: scale(1.05);
    }
    100% {
      transform: scale(1);
    }
  }
  
  .currency {
    font-size: 14px;
    opacity: 0.8;
    margin-left: 5px;
  }
  
  /*标题css*/
 .welcome-container {
  position: relative;
  height: 300px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.welcome-content {
  display: flex;
  width: 80%;
  max-width: 1200px;
  justify-content: space-between;
  align-items: center;
}

.welcome-text {
  flex: 1;
  padding-right: 40px;
  text-align: left;
}

.title {
  font-size: 3.5rem;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
  line-height: 1.2;
  animation: fadeInUp 0.8s ease-out;
}

.subtitle {
  font-size: 1.8rem;
  color: #7f8c8d;
  margin: 10px 0 20px;
  font-weight: 400;
  animation: fadeInUp 0.8s ease-out 0.2s both;
}

.divider {
  //width: 60px;
  width: 10%;
  height: 4px;
  background: linear-gradient(to right, #3498db, #9b59b6);
  margin: 20px 0;
  animation: scaleIn 0.8s ease-out 0.4s both;
}

.quote {
  font-size: 1.2rem;
  color: #95a5a6;
  font-style: italic;
  margin-top: 30px;
  animation: fadeInUp 0.8s ease-out 0.6s both;
}

.welcome-graphic {
  position: relative;
  width: 300px;
  height: 300px;
  flex-shrink: 0;
}

.graphic-circle {
  position: absolute;
  width: 250px;
  height: 250px;
  border-radius: 50%;
  background: linear-gradient(135deg, rgba(52, 152, 219, 0.1) 0%, rgba(155, 89, 182, 0.1) 100%);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: pulse 4s infinite ease-in-out;
}

.graphic-line {
  position: absolute;
  width: 200px;
  height: 200px;
  border: 15px solid transparent;
  border-top-color: #3498db;
  border-right-color: #3498db;
  border-radius: 50%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(45deg);
  animation: rotate 12s linear infinite;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes scaleIn {
  from {
    transform: scaleX(0);
    opacity: 0;
  }
  to {
    transform: scaleX(1);
    opacity: 1;
  }
}

@keyframes pulse {
  0% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0.6;
  }
  50% {
    transform: translate(-50%, -50%) scale(1.05);
    opacity: 0.9;
  }
  100% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0.6;
  }
}

@keyframes rotate {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .welcome-content {
    flex-direction: column;
    text-align: center;
  }

  .welcome-text {
    padding-right: 0;
    margin-bottom: 30px;
    text-align: center;
  }

  .title {
    font-size: 2.5rem;
  }

  .subtitle {
    font-size: 1.4rem;
  }

  .divider {
    margin: 20px auto;
  }

  .welcome-graphic {
    width: 200px;
    height: 200px;
  }

  .graphic-circle {
    width: 180px;
    height: 180px;
  }

  .graphic-line {
    width: 150px;
    height: 150px;
  }
}
</style>