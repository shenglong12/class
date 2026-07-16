<template>
  <base-layout>
    <view class="p-4 min-h-screen bg-neutral-50 flex flex-col">
      <view class="mb-6">
        <text class="text-2xl font-bold text-primary-700">报修申请</text>
        <text class="text-base text-neutral-600 mt-1">请选择教学楼、教室并描述您的报修问题</text>
      </view>

      <!-- 新增 教学楼选择器 -->
      <uni-forms :modelValue="repairForm" label-position="top" label-width="100%" ref="repairFormRef" class="flex-1">
        <uni-forms-item label="教学楼" name="building_enum_building_enum_id_1" required>
          <uni-data-select
            v-model="repairForm.building_enum_building_enum_id_1"
            :localdata="buildingOptions"
            placeholder="请选择教学楼"
            class="w-full box-border mb-4"
            @change="onBuildingChange"
          />
        </uni-forms-item>

        <!-- 教室选择器，动态筛选 -->
        <uni-forms-item label="教室" name="classroom_info_classroom_info_id_1" required>
          <uni-data-select
            v-model="repairForm.classroom_info_classroom_info_id_1"
            :localdata="filteredClassroomOptions"
            placeholder="请选择教室"
            class="w-full box-border"
          />
        </uni-forms-item>

        <!-- 保持其他报修表单项 -->
        <uni-forms-item label="报修内容" name="content" required>
          <uni-easyinput
            type="textarea"
            v-model="repairForm.content"
            placeholder="请输入详细的报修内容"
            class="w-full box-border"
            :inputStyle="{ minHeight: '120px' }"
          />
        </uni-forms-item>

        <uni-forms-item label="上传图片" name="image_files">
          <base-upload
            :limit="5"
            mode="grid"
            mediaType="image"
            :maxSize="5"
            @success="uploadSuccess"
            @delete="uploadDelete"
            :initialFiles="uploadImages"
          />
          <text class="text-xs text-neutral-500 mt-1">最多上传5张图片，单张不超过5MB</text>
        </uni-forms-item>

        <button
          class="w-full bg-primary-500 text-white rounded-full font-semibold mt-6 flex items-center justify-center active:scale-95 transition-transform"
          :disabled="submitting"
          @click="submitRepair"
        >
          <base-icon v-if="submitting" type="spinner-cycle" size="18" class="animate-spin mr-2" color="#fff" />
          <text>{{ submitting ? '提交中...' : '提交申请' }}</text>
        </button>
      </uni-forms>
    </view>
  </base-layout>
</template>

<script setup>
const { proxy } = getCurrentInstance()

const repairForm = ref({
  building_enum_building_enum_id_1: '',
  classroom_info_classroom_info_id_1: '',
  content: '',
  image_files: []
})
const buildingOptions = ref([])
const classroomOptions = ref([])
const submitting = ref(false)
const repairFormRef = ref(null)
const uploadImages = ref([])

const filteredClassroomOptions = computed(() => {
  if (!repairForm.value.building_enum_building_enum_id_1) return []
  return classroomOptions.value
    .filter(c => c.building_enum_building_enum_id_1 === repairForm.value.building_enum_building_enum_id_1)
    .map(c => ({ value: c.classroom_info_id, text: c.room_number }))
})

onLoad(async () => {
  await fetchBuildings()
  await fetchClassrooms()
})

async function fetchBuildings() {
  const res = await proxy.$cf.table.list({ table_name: 'building_enum', param: {} })
  if (res.success) {
    buildingOptions.value = res.data.map(b => ({ value: b.building_enum_id, text: b.building_name }))
  } else {
    proxy.$cf.toast({ message: '加载教学楼失败', level: 'error' })
  }
}

async function fetchClassrooms() {
  const res = await proxy.$cf.table.list({ table_name: 'classroom_info', param: {} })
  if (res.success) {
    classroomOptions.value = res.data
  } else {
    proxy.$cf.toast({ message: '加载教室失败', level: 'error' })
  }
}

function onBuildingChange() {
  repairForm.value.classroom_info_classroom_info_id_1 = ''
}

function uploadSuccess(item) {
  if (item.success) {
    if (repairForm.value.image_files.length >= 5) {
      proxy.$cf.toast({ message: '最多上传5张图片', level: 'warning' })
      return
    }
    // 保存图片数据，根据 StaticResource 的 JSON 映射：
    // url (相对路径) -> resourcePath, name (文件名) -> resourceName
    repairForm.value.image_files.push({
      url: item.data.url,  // 相对路径，会通过 @JsonProperty("url") 映射到 resourcePath
      name: item.data.newFileName || item.data.fileName || 'repair_image'  // 文件名，会通过 @JsonProperty("name") 映射到 resourceName
    })
    uploadImages.value = repairForm.value.image_files.map(img => ({
      url: proxy.get_resource_url ? proxy.get_resource_url(img.url) : img.url,
      name: img.name
    }))
  }
}

function uploadDelete(e) {
  repairForm.value.image_files.splice(e.index, 1)
  uploadImages.value = repairForm.value.image_files.map(img => ({
    url: proxy.get_resource_url ? proxy.get_resource_url(img.url) : img.url,
    name: img.name
  }))
}

async function submitRepair() {
  if (submitting.value) return

  if (!repairForm.value.building_enum_building_enum_id_1) {
    proxy.$cf.toast({ message: '请选择教学楼', level: 'error' })
    return
  }
  if (!repairForm.value.classroom_info_classroom_info_id_1) {
    proxy.$cf.toast({ message: '请选择教室', level: 'error' })
    return
  }
  if (!repairForm.value.content || repairForm.value.content.trim() === '') {
    proxy.$cf.toast({ message: '请输入报修内容', level: 'error' })
    return
  }

  submitting.value = true
  try {
    const userRes = await proxy.$cf.login.getLoginUser()
    if (!userRes.success) {
      proxy.$cf.toast({ message: '请先登录', level: 'error' })
      submitting.value = false
      return
    }

    const now = new Date()
    const pad = (n) => ('0' + n).slice(-2)
    const formattedTime = `${now.getFullYear()}-${pad(now.getMonth()+1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`

    // 转换图片数据格式，根据 StaticResource 的 @JsonProperty 注解：
    // url 映射到 resourcePath, name 映射到 resourceName
    const imageFiles = repairForm.value.image_files.map(img => ({
      url: img.url || img.resourcePath || '',  // @JsonProperty("url") 映射到 resourcePath（相对路径）
      name: img.name || img.resourceName || 'repair_image'  // @JsonProperty("name") 映射到 resourceName（文件名）
    }))

    const res = await proxy.$cf.table.add({
      table_name: 'repair_order',
      param: {
        user_info_user_info_id_1: userRes.data.user_info_id,
        building_enum_building_enum_id_1: repairForm.value.building_enum_building_enum_id_1,
        classroom_info_classroom_info_id_1: repairForm.value.classroom_info_classroom_info_id_1,
        content: repairForm.value.content.trim(),
        repair_status_enum_repair_status_enum_id_1: 1,
        create_time: formattedTime,
        image_files: imageFiles  // 传递转换后的图片数据
      }
    })

    if (res.success) {
      proxy.$cf.toast({ 
        message: '报修申请提交成功，可在"我的"页面查看', 
        level: 'success',
        duration: 3000
      })

      // 清空表单，留在当前页，用户可以继续提交新的报修
      repairForm.value.building_enum_building_enum_id_1 = ''
      repairForm.value.classroom_info_classroom_info_id_1 = ''
      repairForm.value.content = ''
      repairForm.value.image_files = []
      uploadImages.value = []
    } else {
      proxy.$cf.toast({ message: res.message || '提交失败', level: 'error' })
    }
  } catch (e) {
    proxy.$cf.toast({ message: '网络异常，请重试', level: 'error' })
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
/* 样式保持与现有选择器一致无需变更 */
</style>