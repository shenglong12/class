<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题和操作区，纵向排列避免折行错位 -->
      <view class="mb-4">
        <text class="text-xl font-bold text-primary-700 mb-2">教室管理</text>
        <button 
          class="bg-primary-600 text-white rounded-full px-4 font-semibold active:scale-95 transition-transform"
          @click="openAddClassroomPopup"
        >
          <base-icon type="plus" size="18" color="#ffffffcc" class="mr-1" />
          添加教室
        </button>
      </view>

      <!-- 教学楼筛选，纵向排列 -->
      <view class="bg-white rounded-xl shadow-lg p-3 mb-4">
        <uni-data-select
          v-model="selectedBuilding"
          :localdata="buildingOptions"
          clear
          placement="bottom"
          class="w-full mb-3"
          placeholder="请选择教学楼"
          @change="onBuildingFilterChange"
        />
        <button
          class="bg-primary-600 text-white rounded-full px-4 font-semibold active:scale-95 transition-transform w-full"
          @click="refreshClassroomList"
        >
          <base-icon type="refresh" size="18" class="inline-block mr-1" color="#ffffffcc" />
          刷新列表
        </button>
      </view>

      <!-- 教室列表，单列避免排列不均 -->
      <view v-if="classrooms.length > 0">
        <view
          v-for="classroom in classrooms"
          :key="classroom.classroom_info_id"
          class="bg-white rounded-xl shadow-lg p-4 mb-4 transition-all duration-150 hover:shadow-xl active:scale-[0.99] cursor-pointer"
        >
          <!-- 教室基本信息，纵向布局避免拥挤 -->
          <view class="mb-3">
            <view class="flex items-center gap-2 mb-1">
              <base-icon type="home-filled" size="22" color="#1a7ed6" />
              <text class="font-semibold text-primary-800 text-base block truncate">
                {{ classroom.building_name }} - {{ classroom.room_number }}
              </text>
            </view>
            <text class="text-xs text-neutral-500">教室ID: {{ classroom.classroom_info_id }}</text>
          </view>

          <!-- 操作按钮，横向排列，增加间距 -->
          <view class="flex gap-4 mb-3 flex-wrap">
            <button
              class="bg-warning-600 text-white rounded-full px-4 font-semibold active:scale-95 transition-transform flex items-center"
              @click.stop="generateQRCode(classroom.classroom_info_id)"
              aria-label="生成二维码"
            >
              <base-icon type="headphones" size="16" class="mr-1" color="#fff" />
              生成二维码
            </button>
            <button
              class="bg-neutral-600 text-white rounded-full px-4 font-semibold active:scale-95 transition-transform flex items-center"
              @click.stop="editClassroom(classroom)"
              aria-label="编辑教室"
            >
              <base-icon type="compose" size="16" class="mr-1" color="#fff" />
              编辑
            </button>
          </view>

          <!-- 二维码预览 -->
          <view v-if="classroom.qr_code_path" class="p-3 bg-neutral-50 rounded-lg">
            <text class="block text-sm font-medium text-neutral-700 mb-2">二维码预览</text>
            <view class="flex items-center justify-center mb-2">
              <base-qr-code 
                :text="classroom.qr_code_path" 
                :size="120"
                ref="qrCodeRef"
              />
            </view>
            <button
              class="w-full bg-green-600 text-white rounded-full py-2 font-semibold active:scale-95 transition-transform flex items-center justify-center"
              @click.stop.prevent="exportQRCode(classroom)"
              :disabled="exportingButtonId === classroom.classroom_info_id"
              aria-label="导出二维码"
            >
              <base-icon
                v-if="exportingButtonId === classroom.classroom_info_id"
                type="spinner-cycle"
                size="16"
                class="animate-spin mr-1"
                color="#fff"
              />
              <base-icon
                v-else
                type="download"
                size="16"
                class="mr-1"
                color="#fff"
              />
              <text>
                {{ exportingButtonId === classroom.classroom_info_id ? '导出中...' : '导出二维码' }}
              </text>
            </button>
          </view>
          <view v-else class="p-3 bg-warning-100 rounded-lg">
            <text class="text-warning-700 text-sm">尚未生成二维码</text>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else class="flex flex-col items-center justify-center py-24 bg-white rounded-xl shadow-inner mt-8">
        <base-icon type="home" size="60" color="#B3B3B3" />
        <text class="text-neutral-400 mt-4 text-base font-semibold">暂无教室数据</text>
        <text class="text-neutral-400 mt-2 text-sm">请添加第一个教室开始管理</text>
      </view>

      <!-- 批量导出按钮 -->
      <view v-if="classrooms.length > 0" class="mt-6 mb-4">
        <button
          class="w-full bg-success-600 text-white rounded-full py-2 font-semibold active:scale-95 transition-transform flex items-center justify-center"
          @click="batchExportQRCode"
          aria-label="批量导出所有二维码"
        >
          <base-icon type="download-filled" size="18" class="mr-2" color="#fff" />
          批量导出所有二维码
        </button>
      </view>

      <!-- 添加/编辑教室弹窗 -->
      <uni-popup ref="classroomPopup" type="center">
        <view class="bg-white p-4 rounded-xl max-w-xs w-full mx-auto">
          <view class="flex items-center justify-between mb-4">
            <text class="text-lg font-bold text-primary-700">
              {{ isEditing ? '编辑教室' : '添加教室' }}
            </text>
            <base-icon 
              type="close" 
              size="20" 
              color="#666" 
              @click="closeClassroomPopup"
              class="cursor-pointer"
              aria-label="关闭教室弹窗"
              role="button"
              tabindex="0"
            />
          </view>

          <uni-forms 
            ref="classroomFormRef" 
            :modelValue="classroomForm" 
            label-position="top" 
            label-width="100%"
          >
            <uni-forms-item label="教学楼" name="building_enum_building_enum_id_1" required>
              <uni-data-select
                v-model="classroomForm.building_enum_building_enum_id_1"
                :localdata="buildingOptions"
                class="w-full box-border"
                placeholder="请选择教学楼"
              />
            </uni-forms-item>

            <uni-forms-item label="教室编号" name="room_number" required>
              <uni-easyinput
                type="text"
                v-model="classroomForm.room_number"
                placeholder="请输入教室编号"
                class="w-full box-border"
              />
            </uni-forms-item>

            <view class="flex gap-3 mt-4">
              <button
                class="flex-1 bg-neutral-200 text-neutral-700 rounded-full py-2 font-semibold active:scale-95 transition-transform"
                @click="closeClassroomPopup"
                aria-label="取消编辑"
                role="button"
                tabindex="0"
              >
                取消
              </button>
              <button
                class="flex-1 bg-primary-600 text-white rounded-full py-2 font-semibold active:scale-95 transition-transform"
                @click="saveClassroom"
                aria-label="保存教室"
                role="button"
                tabindex="0"
              >
                {{ isEditing ? '更新' : '保存' }}
              </button>
            </view>
          </uni-forms>
        </view>
      </uni-popup>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

// ------------------ Data ------------------
const classrooms = ref([])
const buildingOptions = ref([])
const selectedBuilding = ref('')
const isEditing = ref(false)
const currentClassroomId = ref(null)
const qrCodeRef = ref(null)
const exportingButtonId = ref(null)

const classroomForm = ref({
  building_enum_building_enum_id_1: '',
  room_number: ''
})

// ------------------ Lifecycle ------------------
onLoad(async () => {
  await fetchBuildingOptions()
  await fetchClassroomList()
})

// ------------------ Methods ------------------

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
  }
}

// 获取教室列表
async function fetchClassroomList() {
  const res = await proxy.$cf.table.list({
    table_name: 'classroom_info',
    param: selectedBuilding.value ? { 
      building_enum_building_enum_id_1: selectedBuilding.value 
    } : {}
  })
  if (res.success) {
    classrooms.value = res.data.map(item => ({
      ...item,
      building_name: item.building_name
    }))
  } else {
    proxy.$cf.toast({ message: res.message || '加载教室列表失败', level: 'error' })
  }
}

// 教学楼筛选变化
function onBuildingFilterChange() {
  fetchClassroomList()
}

// 刷新列表
function refreshClassroomList() {
  fetchClassroomList()
}

// 生成二维码
async function generateQRCode(classroomId) {
  try {
    proxy.$cf.loading.showLoading({ title: '生成二维码中...' })
    const qrPath = `/classroom/${classroomId}`
    const res = await proxy.$cf.table.update({
      table_name: 'classroom_info',
      param: {
        classroom_info_id: classroomId,
        qr_code_path: qrPath
      }
    })
    proxy.$cf.loading.hideLoading()

    if (res.success) {
      proxy.$cf.toast({ message: '二维码生成成功', level: 'success' })
      await fetchClassroomList()
    } else {
      proxy.$cf.toast({ message: res.message || '生成二维码失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.loading.hideLoading()
    proxy.$cf.toast({ message: '生成二维码失败', level: 'error' })
  }
}

// 导出二维码图片，保存到手机相册，包含加载状态、权限检测和错误处理
async function exportQRCode(classroom) {
  if (exportingButtonId.value) {
    // 正在导出中，阻止重复点击
    return
  }
  exportingButtonId.value = classroom.classroom_info_id

  try {
    if (!classroom.qr_code_path) {
      proxy.$cf.toast({ message: '该教室暂无二维码可导出', level: 'error' })
      exportingButtonId.value = null
      return
    }

    const imageUrl = proxy.get_resource_url ? proxy.get_resource_url(classroom.qr_code_path) : classroom.qr_code_path

    const downloadRes = await proxy.$cf.file.downloadFile({ url: imageUrl })

    if (!(downloadRes.success && downloadRes.tempFilePath)) {
      proxy.$cf.toast({ message: '二维码下载失败，请稍后重试', level: 'error' })
      exportingButtonId.value = null
      return
    }

    const saveImageToAlbum = () => {
      uni.saveImageToPhotosAlbum({
        filePath: downloadRes.tempFilePath,
        success() {
          proxy.$cf.toast({ message: '导出成功，二维码已保存到相册', level: 'success' })
        },
        fail(err) {
          if (err.errMsg && (err.errMsg.includes('auth deny') || err.errMsg.includes('auth denied'))) {
            proxy.$cf.model({
              title: '需要授权',
              message: '请授权访问相册，以便保存二维码图片',
              confirmText: '去授权',
              cancelText: '取消'
            }).then(res => {
              if (res.confirm) {
                uni.openSetting()
              }
            })
          } else {
            proxy.$cf.toast({ message: '保存失败，请检查权限或稍后重试', level: 'error' })
          }
        },
        complete() {
          exportingButtonId.value = null
        }
      })
    }

    saveImageToAlbum()

  } catch (error) {
    proxy.$cf.toast({ message: '导出失败，请稍后重试', level: 'error' })
    exportingButtonId.value = null
  }
}

// 新增导出全部二维码功能提示
async function batchExportQRCode() {
  const classroomsWithQRCode = classrooms.value.filter(c => c.qr_code_path)
  if (classroomsWithQRCode.length === 0) {
    proxy.$cf.toast({ message: '暂无二维码可供导出', level: 'warning' })
    return
  }
  await proxy.$cf.model({
    title: '提示',
    message: `批量导出功能正在开发中，当前共有${classroomsWithQRCode.length}个二维码，敬请期待！`,
    confirmText: '知道了'
  })
}

// 编辑教室
function editClassroom(classroom) {
  isEditing.value = true
  currentClassroomId.value = classroom.classroom_info_id
  classroomForm.value = {
    building_enum_building_enum_id_1: classroom.building_enum_building_enum_id_1,
    room_number: classroom.room_number
  }
  proxy.$refs.classroomPopup.open()
}

// 打开添加教室弹窗
function openAddClassroomPopup() {
  isEditing.value = false
  currentClassroomId.value = null
  classroomForm.value = {
    building_enum_building_enum_id_1: '',
    room_number: ''
  }
  proxy.$refs.classroomPopup.open()
}

// 关闭教室弹窗
function closeClassroomPopup() {
  proxy.$refs.classroomPopup.close()
}

// 保存教室信息
async function saveClassroom() {
  if (!classroomForm.value.building_enum_building_enum_id_1 || !classroomForm.value.room_number) {
    proxy.$cf.toast({ message: '请填写所有必填项', level: 'error' })
    return
  }
  try {
    proxy.$cf.loading.showLoading({ title: '保存教室信息中...' })
    let res
    if (isEditing.value) {
      res = await proxy.$cf.table.update({
        table_name: 'classroom_info',
        param: {
          classroom_info_id: currentClassroomId.value,
          building_enum_building_enum_id_1: classroomForm.value.building_enum_building_enum_id_1,
          room_number: classroomForm.value.room_number
        }
      })
    } else {
      res = await proxy.$cf.table.add({
        table_name: 'classroom_info',
        param: {
          building_enum_building_enum_id_1: classroomForm.value.building_enum_building_enum_id_1,
          room_number: classroomForm.value.room_number
        }
      })
    }
    proxy.$cf.loading.hideLoading()
    if (res.success) {
      proxy.$cf.toast({ message: `教室${isEditing.value ? '更新' : '添加'}成功`, level: 'success' })
      closeClassroomPopup()
      await fetchClassroomList()
    } else {
      proxy.$cf.toast({ message: res.message || '保存教室失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.loading.hideLoading()
    proxy.$cf.toast({ message: '保存教室失败', level: 'error' })
  }
}
</script>

<style>
/* 移除多列布局，保持单列 */
</style>
