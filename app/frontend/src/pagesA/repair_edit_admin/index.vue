<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4 flex flex-col">
        <view class="flex items-center gap-2 mb-2">
          <base-icon type="settings-filled" size="24" color="#339cff"></base-icon>
          <text class="text-xl font-bold text-primary-700 truncate">报修单详情</text>
        </view>
      </view>

      <!-- 报修单详情卡片 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <view class="flex flex-col mb-3">
          <text class="text-lg font-semibold text-primary-800 mb-2 truncate">报修信息</text>
          <uni-tag 
            :text="repairDetail.status_name || '待审核'" 
            :type="getStatusTagType(repairDetail.status_name)"
            size="normal"
            circle
          />
        </view>

        <!-- 基本信息 -->
        <view class="mb-4">
          <view class="flex flex-col mb-3">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="person-filled" size="18" color="#339cff"></base-icon>
              <text class="text-sm text-neutral-600 flex-1 truncate">
                <text class="font-medium">报修人：</text> {{ repairDetail.name || '无' }}
              </text>
            </view>

            <view class="flex items-center gap-2 mb-1">
              <base-icon type="location-filled" size="18" color="#339cff"></base-icon>
              <text class="text-sm text-neutral-600 flex-1 truncate">
                <text class="font-medium">教室：</text> {{ repairDetail.room_number || '无' }}
              </text>
            </view>

            <view class="flex items-center gap-2">
              <base-icon type="calendar-filled" size="18" color="#339cff"></base-icon>
              <text class="text-sm text-neutral-600 flex-1 truncate">
                <text class="font-medium">报修时间：</text> {{ formatDateTime(repairDetail.create_time) }}
              </text>
            </view>
          </view>
        </view>

        <!-- 报修内容 -->
        <view>
          <text class="block text-sm font-medium text-neutral-700 mb-2">报修内容</text>
          <view class="bg-neutral-50 rounded-lg p-3 border border-neutral-200">
            <text class="text-sm text-neutral-600 break-words">{{ repairDetail.content || '无内容' }}</text>
          </view>
        </view>
      </view>

      <!-- 处理表单 -->
      <view class="bg-white rounded-xl shadow-lg p-4">
        <view class="flex items-center gap-2 mb-4">
          <base-icon type="compose" size="20" color="#339cff"></base-icon>
          <text class="text-lg font-semibold text-primary-800 truncate">处理报修</text>
        </view>

        <uni-forms ref="processFormRef" :modelValue="processForm" label-position="top" label-width="100%">
          <!-- 状态选择 -->
          <uni-forms-item label="状态" name="repair_status_enum_repair_status_enum_id_1" required>
            <uni-data-select
              v-model="processForm.repair_status_enum_repair_status_enum_id_1"
              :localdata="statusOptions"
              class="w-full box-border"
              placeholder="请选择状态"
            />
          </uni-forms-item>

          <!-- 处理反馈 -->
          <uni-forms-item label="处理反馈" name="feedback" required>
            <uni-easyinput
              type="textarea"
              v-model="processForm.feedback"
              placeholder="请输入处理反馈"
              class="w-full box-border"
              :inputStyle="{ minHeight: '100px' }"
            />
          </uni-forms-item>

          <!-- 保存按钮 -->
          <button
            class="w-full bg-primary-500 text-white rounded-full mt-2 flex items-center justify-center font-semibold active:scale-95 transition-transform"
            :disabled="loading"
            @click="handleSave"
          >
            <base-icon v-if="loading" type="spinner-cycle" size="18" class="animate-spin mr-2" color="#fff"></base-icon>
            <text v-else class="truncate">保存修改</text>
          </button>
        </uni-forms>
      </view>

      <!-- 空状态 -->
      <view v-if="!repairDetail.repair_order_id && !loading" class="flex flex-col items-center justify-center py-20">
        <base-icon type="info" size="60" color="#d4d4d4"></base-icon>
        <text class="text-neutral-400 mt-4 text-base">未找到报修单</text>
      </view>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// 页面参数
const repairOrderId = ref(null)

// 数据状态
const repairDetail = ref({
  repair_order_id: null,
  content: '',
  name: '',
  room_number: '',
  status_name: '',
  create_time: '',
  feedback: ''
})

const processForm = ref({
  repair_status_enum_repair_status_enum_id_1: '',
  feedback: ''
})

const statusOptions = ref([])
const loading = ref(false)
const processFormRef = ref(null)

// ---------------------- 生命周期 ----------------------
onLoad(async (option) => {
  if (option && option.repair_order_id) {
    repairOrderId.value = Number(option.repair_order_id)
    await Promise.all([
      fetchRepairDetail(),
      fetchStatusOptions()
    ])
  } else {
    proxy.$cf.toast({ message: '无效的报修单ID', level: 'error' })
  }
})

// ---------------------- 数据加载 ----------------------
async function fetchRepairDetail() {
  if (!repairOrderId.value) return
  
  loading.value = true
  try {
    const res = await proxy.$cf.table.get({
      table_name: 'repair_order',
      param: { repair_order_id: repairOrderId.value }
    })
    
    if (res.success && res.data) {
      repairDetail.value = {
        ...res.data,
        name: res.data.name || '', // 来自user_info表的name字段
        room_number: res.data.room_number || '', // 来自classroom_info表的room_number字段
        status_name: res.data.status_name || '' // 来自repair_status_enum表的status_name字段
      }
      
      // 初始化表单数据
      processForm.value.repair_status_enum_repair_status_enum_id_1 = res.data.repair_status_enum_repair_status_enum_id_1 || ''
      processForm.value.feedback = res.data.feedback || ''
    } else {
      proxy.$cf.toast({ message: '加载报修单失败', level: 'error' })
    }
  } catch (error) {
    console.error('获取报修详情错误:', error)
    proxy.$cf.toast({ message: '网络错误', level: 'error' })
  } finally {
    loading.value = false
  }
}

async function fetchStatusOptions() {
  try {
    const res = await proxy.$cf.table.list({
      table_name: 'repair_status_enum',
      param: {}
    })
    
    if (res.success) {
      statusOptions.value = res.data.map(item => ({
        value: item.repair_status_enum_id,
        text: item.status_name
      }))
    }
  } catch (error) {
    console.error('获取状态选项错误:', error)
    proxy.$cf.toast({ message: '加载状态选项失败', level: 'error' })
  }
}

// ---------------------- 表单处理 ----------------------
async function handleSave() {
  if (loading.value) return
  
  // 表单验证
  if (!processForm.value.repair_status_enum_repair_status_enum_id_1) {
    proxy.$cf.toast({ message: '请选择状态', level: 'error' })
    return
  }
  // 校验状态选项有效性
  const validStatus = statusOptions.value.some(
    item => item.value == processForm.value.repair_status_enum_repair_status_enum_id_1
  )
  if (!validStatus) {
    proxy.$cf.toast({ message: '请选择有效的状态', level: 'error' })
    return
  }
  
  if (!processForm.value.feedback.trim()) {
    proxy.$cf.toast({ message: '请输入处理反馈', level: 'error' })
    return
  }

  loading.value = true
  try {
    const now = new Date()
    const year = now.getFullYear()
    const month = ('0' + (now.getMonth() + 1)).slice(-2)
    const day = ('0' + now.getDate()).slice(-2)
    const hours = ('0' + now.getHours()).slice(-2)
    const minutes = ('0' + now.getMinutes()).slice(-2)
    const seconds = ('0' + now.getSeconds()).slice(-2)
    const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`

    const res = await proxy.$cf.table.update({
      table_name: 'repair_order',
      param: {
        repair_order_id: repairOrderId.value,
        repair_status_enum_repair_status_enum_id_1: processForm.value.repair_status_enum_repair_status_enum_id_1,
        feedback: processForm.value.feedback,
        update_time: formattedTime // 使用update_time字段记录更新时间
      }
    })
    
    if (res.success) {
      proxy.$cf.toast({ message: '报修单更新成功', level: 'success' })
      // 重新加载数据以更新显示
      await fetchRepairDetail()
    } else {
      proxy.$cf.toast({ message: res.message || '更新报修单失败', level: 'error' })
    }
  } catch (error) {
    console.error('保存报修单错误:', error)
    proxy.$cf.toast({ message: '网络错误', level: 'error' })
  } finally {
    loading.value = false
  }
}

// ---------------------- 工具函数 ----------------------
function getStatusTagType(status) {
  if (!status) return 'default'
  switch (status.toLowerCase()) {
    case '待审核':
    case 'pending':
      return 'warning'
    case '处理中':
    case 'processing':
      return 'primary'
    case '已完成':
    case 'completed':
      return 'success'
    default:
      return 'default'
  }
}

function formatDateTime(dateTime) {
  if (!dateTime) return '无'
  try {
    const date = new Date(dateTime.replace(/-/g, '/'))
    const year = date.getFullYear()
    const month = ('0' + (date.getMonth() + 1)).slice(-2)
    const day = ('0' + date.getDate()).slice(-2)
    const hours = ('0' + date.getHours()).slice(-2)
    const minutes = ('0' + date.getMinutes()).slice(-2)
    return `${year}-${month}-${day} ${hours}:${minutes}`
  } catch (error) {
    return dateTime
  }
}
</script>

<style>
/* 替代space-y-3，使用margin间距 */
.mb-1 {
  margin-bottom: 0.25rem;
}
.mb-2 {
  margin-bottom: 0.5rem;
}
.mb-3 {
  margin-bottom: 0.75rem;
}
.mb-4 {
  margin-bottom: 1rem;
}
</style>