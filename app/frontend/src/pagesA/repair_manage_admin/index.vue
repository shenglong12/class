<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4 flex flex-col">
        <text class="text-xl font-bold text-primary-700 mb-2">报修管理</text>
        <button 
          class="bg-primary-500 text-white rounded-full px-4 flex items-center font-semibold active:scale-95 transition-transform w-min"
          @click="refreshList"
          aria-label="刷新列表"
        >
          <base-icon type="refresh" size="18" class="mr-1" color="#fff" />
          <text>刷新</text>
        </button>
      </view>

      <!-- 筛选区域 -->
      <view class="bg-white rounded-xl shadow-lg p-4 mb-4">
        <text class="block font-semibold text-neutral-800 mb-3">筛选条件</text>
        <view class="grid grid-cols-2 gap-3">
          <view class="mb-4">
            <text class="block text-sm text-neutral-600 mb-1">教学楼</text>
            <uni-data-select
              v-model="filterBuilding"
              :localdata="buildingOptions"
              clear
              placeholder="请选择教学楼"
              class="w-full box-border"
              @change="onFilterChange"
            />
          </view>
          <view class="mb-4">
            <text class="block text-sm text-neutral-600 mb-1">状态</text>
            <uni-data-select
              v-model="filterStatus"
              :localdata="statusOptions"
              clear
              placeholder="请选择状态"
              class="w-full box-border"
              @change="onFilterChange"
            />
          </view>
        </view>
      </view>

      <!-- 报修单列表 -->
      <view v-if="repairOrders.length > 0">
        <view
          v-for="order in repairOrders"
          :key="order.repair_order_id"
          class="bg-white rounded-xl shadow-lg p-4 mb-4 transition-all duration-150 hover:shadow-xl active:scale-[0.99] cursor-pointer"
          @click="showRepairDetail(order)"
        >
          <!-- 头部信息 -->
          <view class="flex flex-col mb-3">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="flag" size="20" color="#339cff" />
              <text class="font-semibold text-primary-800 truncate">报修单 #{{ order.repair_order_id }}</text>
            </view>
            <uni-tag
              :text="order.status_name"
              size="small"
              :type="getStatusTagType(order.status_name)"
              circle
            />
          </view>

          <!-- 教室信息 -->
          <view class="flex items-center gap-2 mb-2">
            <base-icon type="location-filled" size="16" color="#737373" />
            <text class="text-sm text-neutral-600 truncate">
              {{ order.room_number || '未知教室' }}
            </text>
          </view>

          <!-- 报修内容 -->
          <text class="block text-neutral-700 text-sm mb-3 line-clamp-2">
            {{ order.content || '无描述' }}
          </text>

          <!-- 底部信息 -->
          <view class="flex flex-col text-xs text-neutral-500">
            <view class="flex items-center gap-1 mb-1">
              <base-icon type="person" size="14" color="#737373" />
              <text class="truncate">{{ order.name || '未知用户' }}</text>
            </view>
            <view class="flex items-center gap-1">
              <base-icon type="calendar" size="14" color="#737373" />
              <text>{{ formatDateTime(order.create_time) }}</text>
            </view>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else class="flex flex-col items-center justify-center py-24 bg-white rounded-xl shadow-inner mt-8">
        <base-icon type="list" size="60" color="#B3B3B3" />
        <text class="text-neutral-400 mt-4 text-base font-semibold">暂无报修单</text>
      </view>

      <!-- 加载更多 -->
      <view v-if="hasMore" class="flex justify-center mt-6 mb-4">
        <button
          class="bg-primary-500 text-white rounded-full px-12 font-semibold active:scale-95 transition-transform"
          :disabled="loading"
          @click="loadMore"
          aria-label="加载更多"
        >
          <base-icon v-if="loading" type="spinner-cycle" size="18" class="animate-spin mr-1" color="#fff" />
          <text v-else>加载更多</text>
        </button>
      </view>

      <!-- 报修详情弹窗 -->
      <uni-popup ref="detailPopup" type="center" class="z-50" :style="{ paddingBottom: '60px' }">
        <view class="bg-white rounded-xl p-6 w-80 max-h-[80vh] overflow-y-auto">
          <!-- 弹窗头部 -->
          <view class="flex items-center justify-between mb-4">
            <text class="text-lg font-bold text-primary-700">报修详情</text>
            <view 
              class="cursor-pointer"
              role="button"
              tabindex="0"
              aria-label="关闭弹窗"
              @click="closeDetailPopup"
              @keydown.enter.space.prevent="closeDetailPopup"
            >
              <base-icon 
                type="close" 
                size="20" 
                color="#737373" 
              />
            </view>
          </view>

          <!-- 报修详情 -->
          <view class="mb-4">
            <!-- 基本信息 -->
            <view class="bg-neutral-50 rounded-lg p-3 mb-4">
              <text class="block font-semibold text-neutral-800 mb-2">基本信息</text>
              <view class="flex flex-col text-sm space-y-2">
                <view class="flex justify-between">
                  <text class="text-neutral-600">报修单号：</text>
                  <text class="font-medium">{{ currentRepair.repair_order_id }}</text>
                </view>
                <view class="flex justify-between">
                  <text class="text-neutral-600">用户：</text>
                  <text class="font-medium">{{ currentRepair.name }}</text>
                </view>
                <view class="flex justify-between">
                  <text class="text-neutral-600">教室：</text>
                  <text class="font-medium">{{ currentRepair.room_number }}</text>
                </view>
                <view class="flex justify-between items-center">
                  <text class="text-neutral-600">状态：</text>
                  <uni-tag
                    :text="currentRepair.status_name"
                    size="small"
                    :type="getStatusTagType(currentRepair.status_name)"
                    circle
                  />
                </view>
                <view class="flex justify-between">
                  <text class="text-neutral-600">创建时间：</text>
                  <text class="font-medium">{{ formatDateTime(currentRepair.create_time) }}</text>
                </view>
              </view>
            </view>

            <!-- 报修内容 -->
            <view class="mb-6">
              <text class="block font-semibold text-neutral-800 mb-2">报修内容</text>
              <text class="block text-neutral-700 bg-neutral-50 rounded-lg p-3 min-h-[80px] whitespace-pre-wrap">
                {{ currentRepair.content || '无描述' }}
              </text>
            </view>

            <!-- 处理反馈表单 -->
            <view>
              <text class="block font-semibold text-neutral-800 mb-3">处理反馈</text>
              <uni-forms :modelValue="processForm" label-position="top" label-width="100%">
                <uni-forms-item label="状态" name="repair_status_enum_repair_status_enum_id_1" required>
                  <uni-data-select
                    v-model="processForm.repair_status_enum_repair_status_enum_id_1"
                    :localdata="statusOptions"
                    class="w-full box-border"
                    placeholder="请选择状态"
                  />
                </uni-forms-item>
                <uni-forms-item label="反馈" name="feedback" required>
                  <uni-easyinput
                    type="textarea"
                    v-model="processForm.feedback"
                    placeholder="请输入处理反馈"
                    class="w-full box-border"
                    :input-style="{ minHeight: '100px' }"
                  />
                </uni-forms-item>
              </uni-forms>
            </view>

            <!-- 操作按钮 -->
            <view class="flex gap-4 pt-4">
              <button
                class="flex-1 bg-neutral-200 text-neutral-700 rounded-full font-semibold active:scale-95 transition-transform"
                @click="closeDetailPopup"
                aria-label="取消"
              >
                取消
              </button>
              <button
                class="flex-1 bg-primary-500 text-white rounded-full font-semibold active:scale-95 transition-transform"
                @click="handleProcessRepair"
                :disabled="processing"
                aria-label="保存"
              >
                <base-icon v-if="processing" type="spinner-cycle" size="18" class="animate-spin mr-1" color="#fff" />
                <text>保存</text>
              </button>
            </view>
          </view>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const repairOrders = ref([])
const currentRepair = ref({})
const filterBuilding = ref('')
const filterStatus = ref('')
const buildingOptions = ref([])
const statusOptions = ref([])
const pageInfo = ref({
  current: 1,
  pageSize: 10
})
const total = ref(0)
const hasMore = ref(true)
const loading = ref(false)
const processing = ref(false)

// 处理表单
const processForm = ref({
  repair_status_enum_repair_status_enum_id_1: '',
  feedback: ''
})

// 弹窗引用
const detailPopup = ref(null)

// ------------------ Lifecycle ------------------
onLoad(async () => {
  await fetchBuildingOptions()
  await fetchStatusOptions()
  await fetchRepairOrders(true)
})

// 获取教学楼选项
async function fetchBuildingOptions() {
  const res = await proxy.$cf.table.list({
    table_name: 'building_enum',
    param: {}
  })
  if (res.success) {
    buildingOptions.value = res.data.map(item => ({
      value: item.building_enum_id,
      text: item.building_name
    }))
  } else {
    buildingOptions.value = []
  }
}

// 获取报修状态选项
async function fetchStatusOptions() {
  const res = await proxy.$cf.table.list({
    table_name: 'repair_status_enum',
    param: {}
  })
  if (res.success) {
    statusOptions.value = [
      { value: '', text: '全部状态' },
      ...res.data.map(item => ({
        value: item.repair_status_enum_id,
        text: item.status_name
      }))
    ]
  } else {
    statusOptions.value = []
  }
}

// 获取报修单列表
async function fetchRepairOrders(reset = false) {
  if (loading.value) return
  loading.value = true
  try {
    if (reset) {
      pageInfo.value.current = 1
      repairOrders.value = []
      hasMore.value = true
    }

    // 构建查询参数
    let param = {
      current: pageInfo.value.current,
      pageSize: pageInfo.value.pageSize,
      order_by: {
        'create_time': 'desc',
        'repair_order_id': 'desc'
      }
    }

    // 楼宇筛选
    if (filterBuilding.value) {
      // 根据数据集字段名称，教学楼字段为 classroom_info.building_enum_building_enum_id_1
      // 需要关联查询，因接口限制，先简单过滤repair_order中classroom_info_building_enum_building_enum_id_1字段
      param['classroom_info_building_enum_building_enum_id_1'] = filterBuilding.value
    }

    // 状态筛选
    if (filterStatus.value) {
      param.repair_status_enum_repair_status_enum_id_1 = filterStatus.value
    }

    const res = await proxy.$cf.table.page({
      table_name: 'repair_order',
      param
    })

    if (res.success) {
      if (reset) {
        repairOrders.value = res.data.records
      } else {
        repairOrders.value = repairOrders.value.concat(res.data.records)
      }
      total.value = res.data.total
      hasMore.value = repairOrders.value.length < total.value
    } else {
      proxy.$cf.toast({ message: res.message || '加载报修单失败', level: 'error' })
    }
  } catch (e) {
    proxy.$cf.toast({ message: '网络异常，加载报修单失败', level: 'error' })
  } finally {
    loading.value = false
  }
}

// 筛选变化
function onFilterChange() {
  fetchRepairOrders(true)
}

// 刷新列表
function refreshList() {
  fetchRepairOrders(true)
}

// 加载更多
function loadMore() {
  if (hasMore.value && !loading.value) {
    pageInfo.value.current += 1
    fetchRepairOrders(false)
  }
}

// 显示报修详情
function showRepairDetail(order) {
  currentRepair.value = { ...order }
  // 初始化表单数据
  processForm.value.repair_status_enum_repair_status_enum_id_1 = order.repair_status_enum_repair_status_enum_id_1
  processForm.value.feedback = order.feedback || ''
  detailPopup.value.open()
}

// 关闭详情弹窗
function closeDetailPopup() {
  detailPopup.value.close()
  // 重置表单
  processForm.value = {
    repair_status_enum_repair_status_enum_id_1: '',
    feedback: ''
  }
}

// 处理报修单
async function handleProcessRepair() {
  if (!processForm.value.repair_status_enum_repair_status_enum_id_1) {
    proxy.$cf.toast({ message: '请选择状态', level: 'error' })
    return
  }

  if (!processForm.value.feedback) {
    proxy.$cf.toast({ message: '请输入处理反馈', level: 'error' })
    return
  }

  processing.value = true
  try {
    const res = await proxy.$cf.table.update({
      table_name: 'repair_order',
      param: {
        repair_order_id: currentRepair.value.repair_order_id,
        repair_status_enum_repair_status_enum_id_1: processForm.value.repair_status_enum_repair_status_enum_id_1,
        feedback: processForm.value.feedback
        // 不更新 create_time，避免覆盖创建时间
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '报修单更新成功', level: 'success' })
      closeDetailPopup()
      await fetchRepairOrders(true) // 刷新列表
    } else {
      proxy.$cf.toast({ message: res.message || '更新报修单失败', level: 'error' })
    }
  } catch (e) {
    proxy.$cf.toast({ message: '网络异常，更新报修单失败', level: 'error' })
  } finally {
    processing.value = false
  }
}

// 状态标签类型
function getStatusTagType(status) {
  if (!status) return 'default'
  if (status == '待审核') return 'warning'
  if (status == '处理中') return 'primary'
  if (status == '已完成') return 'success'
  return 'default'
}

// 时间格式化
function formatDateTime(dt) {
  if (!dt) return '--'
  const d = new Date(dt.replace(/-/g, '/'))
  if (isNaN(d.getTime())) return '--'
  const y = d.getFullYear()
  const m = ('0' + (d.getMonth() + 1)).slice(-2)
  const day = ('0' + d.getDate()).slice(-2)
  const h = ('0' + d.getHours()).slice(-2)
  const min = ('0' + d.getMinutes()).slice(-2)
  return `${y}-${m}-${day} ${h}:${min}`
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