<template>
  <view class="uni-uploader-wrapper">
    <uni-file-picker
        ref="filePickerRef"
        :file-mediatype="mediaType"
        :limit="limit"
        :auto-upload="false"
        :title="title"
        :mode="mode"
        :value="initialFiles"
        @select="onFileSelect"
        @delete="onFileDelete"
    />
  </view>
</template>

<script setup>
import {ref, watch} from 'vue'


// #ifdef MP || APP-ANDROID || APP
const props = defineProps({
  uploadUrl: {
    type: String,
    required: false,
    default: () => import.meta.env.VITE_APP_SERVICE_API === '/' ? "/common/upload" : import.meta.env.VITE_APP_SERVICE_API + "/common/upload"
  },
  limit: {type: Number, default: 5},
  mediaType: {type: String, default: 'image'}, // 'image' | 'video' | 'all'
  mode: {type: String, default: 'grid'},
  title: {type: String, default: '上传文件'},
  maxSize: {type: Number, default: 5}, // MB
  initialFiles: {type: [Array, String], default: () => []} // 可以是对象数组或URL字符串数组
})
// #endif

// #ifdef H5
const props = defineProps({
  uploadUrl: {
    type: String,
    required: false,
    default: () => import.meta.env.VITE_APP_BASE_API === '/' ? "/common/upload" : import.meta.env.VITE_APP_BASE_API + "/common/upload"
  },
  limit: {type: Number, default: 5},
  mediaType: {type: String, default: 'image'}, // 'image' | 'video' | 'all'
  mode: {type: String, default: 'grid'},
  title: {type: String, default: '上传文件'},
  maxSize: {type: Number, default: 5}, // MB
  initialFiles: {type: [Array, String], default: () => []} // 可以是对象数组或URL字符串数组
})
// #endif

const emit = defineEmits(['success', 'fail', 'delete'])

const filePickerRef = ref()
const processedFiles = ref([])

// 处理初始文件数据
const processInitialFiles = (files) => {
  if (!files) return []

  // 如果是单个字符串，转为数组
  if (typeof files === 'string') {
    files = [files]
  }

  return files.map(file => {
    // 如果是字符串，转换为对象
    if (typeof file === 'string') {
      return {
        url: file,
        name: file.split('/').pop(),
        extname: file.split('.').pop(),
        path: file,
        size: 0,
        type: getFileType(file)
      }
    }
    // 已经是对象，直接返回
    return file
  })
}

// 根据URL后缀判断文件类型
const getFileType = (url) => {
  if (typeof url !== 'string') {
    return url.type || 'all'
  }

  const ext = url.split('.').pop().toLowerCase()
  const imageTypes = ['jpg', 'jpeg', 'png', 'gif', 'webp']
  const videoTypes = ['mp4', 'mov', 'avi', 'wmv']

  if (imageTypes.includes(ext)) return 'image'
  if (videoTypes.includes(ext)) return 'video'
  return 'all'
}

// 监听initialFiles变化
// watch(() => props.initialFiles, (newFiles) => {
//   processedFiles.value = processInitialFiles(newFiles)
// }, {immediate: true, deep: true})

const onFileSelect = async (e) => {
  for (const file of e.tempFiles) {
    const valid = validateFile(file)
    if (!valid) continue
    await upload(file)
  }
}


const validateFile = (file) => {
  // 如果是外部URL转换的文件，跳过大小校验
  if (file.url && file.size === 0) return true

  const sizeMB = file.size / 1024 / 1024
  if (sizeMB > props.maxSize) {
    uni.showToast({
      title: `文件超过${props.maxSize}MB限制`,
      icon: 'none'
    })
    emit('fail', {type: 'size', file})
    return false
  }
  return true
}

const upload = (file) => {
  // 如果是外部URL转换的文件，直接返回URL
  if (file.url && file.size === 0) {
    const result = {url: file.url}
    emit('success', result)
    return Promise.resolve(result)
  }

  return new Promise((resolve, reject) => {
    const uploadTask = uni.uploadFile({
      url: props.uploadUrl,
      filePath: file.path,
      name: 'file',
      header: {
        "APP_ID": import.meta.env.VITE_APP_ID,
        "APP_TYPE": import.meta.env.VITE_APP_TYPE,
        "X_Env": import.meta.env.VITE_APP_ENV,
      },
      success: (res) => {
        try {
          const result = JSON.parse(res.data)
          if (result.code === 0 || result.url) {
            result.data.name = result.data.fileName;
            emit('success', result)
            resolve(result)
          } else {
            emit('fail', result)
            reject(result)
          }
        } catch (err) {
          emit('fail', err)
          reject(err)
        }
      },
      fail: (err) => {
        emit('fail', err)
        reject(err)
      }
    })

    uploadTask.onProgressUpdate((res) => {
      console.log(`上传进度：${res.progress}%`)
    })
  })
}

const onFileDelete = (e) => {
  console.log('文件删除', e)
  emit('delete', e)
}

// 对外暴露：主动触发选择（相机/相册）
// parameters 可为对象或 JSON 字符串，例如 { sourceType: ['camera'] }
const choose = (parameters = {}) => {
  try {
    const opts = typeof parameters === 'string' ? JSON.parse(parameters) : (parameters || {})
    const sourceType = Array.isArray(opts.sourceType) && opts.sourceType.length > 0 ? opts.sourceType : ['album', 'camera']

    // 仅处理图片选择，视频可扩展为 chooseVideo
    return new Promise((resolve, reject) => {
      if (props.mediaType === 'image') {
        uni.chooseImage({
          count: Math.max(1, Math.min(props.limit || 1, 9)),
          sourceType,
          success: async (res) => {
            // 兼容不同平台返回
            const tempFiles = res.tempFiles && res.tempFiles.length
                ? res.tempFiles
                : (res.tempFilePaths || []).map(p => ({path: p, size: 0}))

            try {
              await onFileSelect({tempFiles})
              resolve(true)
            } catch (err) {
              reject(err)
            }
          },
          fail: (err) => {
            emit('fail', err)
            reject(err)
          }
        })
      } else if (props.mediaType === 'video') {
        uni.chooseVideo({
          sourceType,
          compressed: false, // 可选：避免过度压缩
          success: async (res) => {
            const tempFiles = [{path: res.tempFilePath, size: res.size}]
            try {
              await onFileSelect({tempFiles})
              resolve(true)
            } catch (err) {
              reject(err)
            }
          },
          fail: (err) => {
            emit('fail', err)
            reject(err)
          }
        })
      } else {
        uni.showToast({
          title: `不支持其他类型`,
          icon: 'none'
        })
      }

    })
  } catch (e) {
    emit('fail', e)
    return Promise.reject(e)
  }
}

defineExpose({
  choose,
  chooseFile: choose,// 别名
  chooseImage: choose
})
</script>

<style scoped>
.uni-uploader-wrapper {
  padding: 20rpx;
}
</style>