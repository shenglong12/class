import request from '@/utils/request';

export default {
  // 通用上传文件
  upload: (data) => request({
    url: '/common/upload',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }),
  // 二维码专用上传（教学楼_教室号命名，存在则覆盖）
  uploadQrcode: (data, buildingName, roomNumber) => request({
    url: '/common/uploadQrcode',
    method: 'post',
    params: { buildingName, roomNumber },
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
};