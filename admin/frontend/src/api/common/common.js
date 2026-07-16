import request from '@/utils/request';

export default {
  // 上传文件
  upload: (data) => request({
    url: '/common/upload',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
};