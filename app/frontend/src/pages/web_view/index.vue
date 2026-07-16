<template>
  <web-view :src="url"></web-view>
  {{url}}

</template>

<script setup>

import {onLoad} from "@dcloudio/uni-app";

const url = ref({})

// 获取页面参数
onLoad(() => {
  const options = getCurrentPages()[getCurrentPages().length - 1].options;
  const link = options.url;
  console.log("web-view", link)

  if (link) {
    url.value = link
  } else {
    // 如果 URL 无效，显示提示
    uni.showToast({
      title: '无效链接',
      icon: 'none'
    });
  }
});


</script>

<style scoped lang="scss">


</style>