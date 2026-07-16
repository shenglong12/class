<template>
  <view class="tabbar" v-if="tabLists.length">
    <view
        :class="currentRoute == item.url ? 'tab_box on' : 'tab_box'"
        v-for="(item, index) in tabLists"
        :key="index"
        :style="{ width: 100 / tabLists.length + '%' }"
    >
      <view class="tab_top" @click="tabFn(item.url)">
        <image
            class="tab_icon"
            :style="{ width: '48rpx',height: '48rpx' }"
            :src="currentRoute == item.url ? item.onicon : item.icon"

        />
      </view>
      <view class="tab_bot">
        {{ item.title || item.text }}
      </view>
    </view>
  </view>
</template>

<script setup>
import {ref, watch} from 'vue';

const {proxy} = getCurrentInstance()
const props = defineProps({
  tabList: {
    type: Array,
    default: () => [],
  },
});

const tabLists = ref(props.tabList);
const currentRoute = ref('');

const getCurrentRoute = () => {
  const pages = getCurrentPages();
  if (pages.length) {
    const currentPage = pages[pages.length - 1];
    currentRoute.value = `/${currentPage.route}`;
  }
  return currentRoute.value;
};

// 初始化获取当前路由
getCurrentRoute();

// 监听路由变化
// watch(() => props.tabList, (newVal) => {
//   tabLists.value = newVal;
// }, { immediate: true });

function tabFn(url) {
  // uni.redirectTo({ url });
  proxy.$cf.navigate.to({
    url,
    type: 'page',

    mode:"redirect"
  })
}
</script>

<style scoped>
.tabbar {
  width: 100%;
  height: 100rpx;
  background: white;
  display: flex;
  position: fixed;
  bottom: 0;
  left: 0;
  z-index: 9;
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.tab_box {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.on {
  color: #527FF4;
}

.tab_top {
  width: 100%;
  text-align: center;
}

.tab_icon {
  width: 48rpx;
  height: 48rpx;
}

.tab_bot {
  width: 100%;
  text-align: center;
  font-size: 24rpx;
  margin-top: 6rpx;
}
</style>