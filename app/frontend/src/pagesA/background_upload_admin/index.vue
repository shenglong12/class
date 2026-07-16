<template>
  <base-layout>
    <view class="p-2 min-h-screen bg-neutral-50">
      <!-- 页面标题 -->
      <view class="mb-4">
        <view class="flex items-center gap-2 mb-2">
          <base-icon type="image-filled" size="24" color="#1460a3"></base-icon>
          <text class="text-xl font-bold text-primary-700">背景图片管理</text>
        </view>
        <button 
          class="bg-primary-500 text-white rounded-full px-4 font-semibold active:scale-95 transition-transform"
          @click="showUploadPopup = true"
        >
          <base-icon type="plus" size="18" class="mr-1" color="#fff"></base-icon>
          <text>上传图片</text>
        </button>
      </view>

      <!-- 背景图片列表 -->
      <view v-if="backgroundImages.length > 0" class="grid grid-cols-2 gap-4">
        <view 
          v-for="image in backgroundImages" 
          :key="image.background_image_id"
          class="bg-white rounded-xl shadow-lg overflow-hidden transition-all duration-200 hover:shadow-xl active:scale-[0.98]"
        >
          <image
            :src="get_resource_url(image.image_files[0]?.url)"
            mode="aspectFill"
            class="w-full h-40 object-cover"
          />
          <view class="p-3">
            <view class="flex items-center justify-between mb-2">
              <text class="text-sm text-neutral-600 font-medium truncate">
                {{ formatDate(image.upload_time) }}
              </text>
              <view class="flex items-center gap-3">
                <view 
                  class="p-1 rounded-full hover:bg-neutral-100 transition-colors cursor-pointer"
                  @click.stop="editImage(image)"
                  aria-label="编辑图片"
                >
                  <base-icon type="compose" size="16" color="#525252"></base-icon>
                </view>
                <view 
                  class="p-1 rounded-full hover:bg-neutral-100 transition-colors cursor-pointer"
                  @click.stop="deleteImage(image.background_image_id)"
                  aria-label="删除图片"
                >
                  <base-icon type="trash" size="16" color="#d62929"></base-icon>
                </view>
              </view>
            </view>
            <text class="text-xs text-neutral-500 block truncate" title="上传者信息">
              上传者: {{ image.name || '管理员' }}
            </text>
          </view>
        </view>
      </view>

      <!-- 空状态 -->
      <view v-else class="flex flex-col items-center justify-center py-20">
        <base-icon type="image" size="60" color="#d4d4d4"></base-icon>
        <text class="text-neutral-400 mt-4 text-base font-medium">暂无背景图片</text>
        <text class="text-neutral-400 text-sm mt-1">请上传第一张背景图片</text>
      </view>

      <!-- 上传弹窗 -->
      <uni-popup ref="uploadPopup" type="center" :show="showUploadPopup" @update:show="val => showUploadPopup = val" :style="{paddingBottom: '60px'}">
        <view class="bg-white p-4 w-80 rounded-2xl shadow-2xl">
          <view class="flex items-center justify-between mb-4">
            <text class="text-lg font-bold text-primary-700">上传背景图片</text>
            <view @click="showUploadPopup = false" class="p-1 rounded-full hover:bg-neutral-100 cursor-pointer" aria-label="关闭上传弹窗">
              <base-icon type="close" size="18" color="#525252"></base-icon>
            </view>
          </view>
          
          <uni-forms ref="uploadFormRef" :modelValue="uploadForm" label-position="top" label-width="100%">
            <uni-forms-item label="背景图片" name="image_files" required>
              <base-upload
                :limit="5"
                mode="grid"
                @success="uploadSuccess"
                @delete="uploadDelete"
                :initialFiles="uploadImages"
              />
              <text class="text-xs text-neutral-500 mt-1">最多上传5张背景图片</text>
            </uni-forms-item>
            
            <view class="flex gap-3 mt-4">
              <button 
                class="flex-1 bg-neutral-200 text-neutral-700 rounded-full font-medium active:scale-95 transition-transform"
                @click="showUploadPopup = false"
              >
                取消
              </button>
              <button 
                class="flex-1 bg-primary-500 text-white rounded-full font-medium active:scale-95 transition-transform"
                @click="handleUpload"
                :disabled="uploadLoading"
              >
                <text v-if="uploadLoading">上传中...</text>
                <text v-else>保存</text>
              </button>
            </view>
          </uni-forms>
        </view>
      </uni-popup>

      <!-- 编辑弹窗 -->
      <uni-popup ref="editPopup" type="center" :show="showEditPopup" @update:show="val => showEditPopup = val" :style="{paddingBottom: '60px'}">
        <view class="bg-white p-4 w-80 rounded-2xl shadow-2xl">
          <view class="flex items-center justify-between mb-4">
            <text class="text-lg font-bold text-primary-700">编辑背景图片</text>
            <view @click="showEditPopup = false" class="p-1 rounded-full hover:bg-neutral-100 cursor-pointer" aria-label="关闭编辑弹窗">
              <base-icon type="close" size="18" color="#525252"></base-icon>
            </view>
          </view>
          
          <uni-forms ref="editFormRef" :modelValue="editForm" label-position="top" label-width="100%">
            <uni-forms-item label="背景图片" name="image_files" required>
              <base-upload
                :limit="5"
                mode="grid"
                @success="editUploadSuccess"
                @delete="editUploadDelete"
                :initialFiles="editImages"
              />
              <text class="text-xs text-neutral-500 mt-1">最多上传5张背景图片</text>
            </uni-forms-item>
            
            <view class="flex gap-3 mt-4">
              <button 
                class="flex-1 bg-neutral-200 text-neutral-700 rounded-full font-medium active:scale-95 transition-transform"
                @click="showEditPopup = false"
              >
                取消
              </button>
              <button 
                class="flex-1 bg-primary-500 text-white rounded-full font-medium active:scale-95 transition-transform"
                @click="handleEdit"
                :disabled="editLoading"
              >
                <text v-if="editLoading">更新中...</text>
                <text v-else>更新</text>
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
const backgroundImages = ref([])
const showUploadPopup = ref(false)
const showEditPopup = ref(false)
const uploadLoading = ref(false)
const editLoading = ref(false)

// 上传表单
const uploadForm = ref({
  image_files: []
})
const uploadImages = ref([])
const uploadFormRef = ref(null)

// 编辑表单
const editForm = ref({
  background_image_id: null,
  image_files: []
})
const editImages = ref([])
const editFormRef = ref(null)

// 弹窗引用
const uploadPopup = ref(null)
const editPopup = ref(null)

// ------------------ Lifecycle ------------------
onLoad(async () => {
  await fetchBackgroundImages()
})

// ------------------ Methods ------------------

// 获取背景图片列表
async function fetchBackgroundImages() {
  try {
    const res = await proxy.$cf.table.list({
      table_name: 'background_image',
      param: {}
    })
    if (res.success) {
      backgroundImages.value = res.data
    } else {
      proxy.$cf.toast({ message: res.message || '加载背景图片失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，加载图片失败', level: 'error' })
  }
}

// 上传成功处理
function uploadSuccess(item) {
  if (item.success) {
    if (uploadForm.value.image_files.length >= 5) {
      proxy.$cf.toast({ message: '最多上传5张图片', level: 'warning' })
      return
    }
    uploadForm.value.image_files.push({
      url: item.data.url,
      name: item.data.newFileName || 'background_image'
    })
    uploadImages.value = uploadForm.value.image_files.map(img => ({
      url: proxy.get_resource_url ? proxy.get_resource_url(img.url) : img.url,
      name: img.name
    }))
  }
}

// 上传删除处理
function uploadDelete(e) {
  uploadForm.value.image_files.splice(e.index, 1)
  uploadImages.value = uploadForm.value.image_files.map(img => ({
    url: proxy.get_resource_url ? proxy.get_resource_url(img.url) : img.url,
    name: img.name
  }))
}

// 编辑上传成功处理
function editUploadSuccess(item) {
  if (item.success) {
    if (editForm.value.image_files.length >= 5) {
      proxy.$cf.toast({ message: '最多上传5张图片', level: 'warning' })
      return
    }
    editForm.value.image_files.push({
      url: item.data.url,
      name: item.data.newFileName || 'background_image'
    })
    editImages.value = editForm.value.image_files.map(img => ({
      url: proxy.get_resource_url ? proxy.get_resource_url(img.url) : img.url,
      name: img.name
    }))
  }
}

// 编辑上传删除处理
function editUploadDelete(e) {
  editForm.value.image_files.splice(e.index, 1)
  editImages.value = editForm.value.image_files.map(img => ({
    url: proxy.get_resource_url ? proxy.get_resource_url(img.url) : img.url,
    name: img.name
  }))
}

// 处理上传
async function handleUpload() {
  if (uploadForm.value.image_files.length == 0) {
    proxy.$cf.toast({ message: '请至少上传一张图片', level: 'error' })
    return
  }

  uploadLoading.value = true
  try {
    const userRes = await proxy.$cf.login.getLoginUser()
    if (!userRes.success) {
      proxy.$cf.toast({ message: '请先登录', level: 'error' })
      uploadLoading.value = false
      return
    }

    const now = new Date()
    const year = now.getFullYear()
    const month = ('0' + (now.getMonth() + 1)).slice(-2)
    const day = ('0' + now.getDate()).slice(-2)
    const hours = ('0' + now.getHours()).slice(-2)
    const minutes = ('0' + now.getMinutes()).slice(-2)
    const seconds = ('0' + now.getSeconds()).slice(-2)
    const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`

    const res = await proxy.$cf.table.add({
      table_name: 'background_image',
      param: {
        user_info_user_info_id_1: userRes.data.user_info_id,
        image_files: uploadForm.value.image_files,
        upload_time: formattedTime
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '背景图片上传成功', level: 'success' })
      showUploadPopup.value = false
      uploadForm.value.image_files = []
      uploadImages.value = []
      await fetchBackgroundImages()
    } else {
      proxy.$cf.toast({ message: res.message || '上传失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，上传失败', level: 'error' })
  } finally {
    uploadLoading.value = false
  }
}

// 编辑图片
function editImage(image) {
  editForm.value.background_image_id = image.background_image_id
  editForm.value.image_files = image.image_files || []
  editImages.value = editForm.value.image_files.map(img => ({
    url: proxy.get_resource_url ? proxy.get_resource_url(img.url) : img.url,
    name: img.name
  }))
  showEditPopup.value = true
}

// 处理编辑
async function handleEdit() {
  if (editForm.value.image_files.length == 0) {
    proxy.$cf.toast({ message: '请至少上传一张图片', level: 'error' })
    return
  }

  editLoading.value = true
  try {
    const res = await proxy.$cf.table.update({
      table_name: 'background_image',
      param: {
        background_image_id: editForm.value.background_image_id,
        image_files: editForm.value.image_files
      }
    })

    if (res.success) {
      proxy.$cf.toast({ message: '背景图片更新成功', level: 'success' })
      showEditPopup.value = false
      editForm.value.background_image_id = null
      editForm.value.image_files = []
      editImages.value = []
      await fetchBackgroundImages()
    } else {
      proxy.$cf.toast({ message: res.message || '更新失败', level: 'error' })
    }
  } catch (error) {
    proxy.$cf.toast({ message: '网络异常，更新失败', level: 'error' })
  } finally {
    editLoading.value = false
  }
}

// 删除图片
async function deleteImage(imageId) {
  const resConfirm = await proxy.$cf.model({
    title: '删除背景图片',
    message: '确定要删除该背景图片吗？此操作不可恢复。',
    confirmText: '删除',
    cancelText: '取消'
  })

  if (resConfirm.confirm) {
    try {
      const res = await proxy.$cf.table.delete({
        table_name: 'background_image',
        param: { background_image_id: imageId }
      })

      if (res.success) {
        proxy.$cf.toast({ message: '背景图片删除成功', level: 'success' })
        await fetchBackgroundImages()
      } else {
        proxy.$cf.toast({ message: res.message || '删除失败', level: 'error' })
      }
    } catch (error) {
      proxy.$cf.toast({ message: '网络异常，删除失败', level: 'error' })
    }
  }
}

// 格式化日期
function formatDate(dateString) {
  if (!dateString) return '未知日期'
  const date = new Date(dateString.replace(/-/g, '/'))
  if (isNaN(date.getTime())) return '未知日期'
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

// 获取资源URL
function get_resource_url(path) {
  return proxy.get_resource_url ? proxy.get_resource_url(path) : path
}
</script>

<style>
/* 自定义样式 */
.grid-cols-2 {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}
</style>