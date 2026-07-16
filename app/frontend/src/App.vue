<script setup>
import formatError from "./utils/format";

const {proxy} = getCurrentInstance()
// #ifdef H5
setTimeout(() => {
  const fontSize = window.innerWidth / 100; // 根据视口宽度动态计算
  document.documentElement.style.fontSize = `${fontSize * 3.5}px`;
}, 10);
// #endif
// #ifdef MP-WEIXIN
setTimeout(() => {
  try {
    const systemInfo = uni.getSystemInfoSync();
    const fontSize = (systemInfo.windowWidth || 375) / 100; // 根据视口宽度动态计算，默认375
    // 微信小程序中使用 rpx，不需要设置 document.fontSize
    // 如果需要设置字体大小，可以通过 CSS 变量或其他方式
  } catch (e) {
    console.warn('获取系统信息失败:', e);
  }
}, 10);
// #endif

const errorReportUrl = import.meta.env.VITE_APP_ERROR_REPORT_URL
const errorEnable = import.meta.env.VITE_APP_ERROR_ENABLE
const app_id = import.meta.env.VITE_APP_ID
function strToBool(str) {
  if (typeof str !== "string") return Boolean(str); // 非字符串也可处理
  return str.toLowerCase() === "true";
}


onError((err) => {
  console.error(err);
  console.log(typeof errorEnable,"true/false")
  if (!strToBool(errorEnable)) {
    console.log(errorEnable,"true/false")
    return;
  }
  // 进行异常信息的上报
  const pages = getCurrentPages();
  const currentPage = pages[pages.length - 1];
  const route = currentPage ? currentPage.route : "未知页面";
  proxy.$cf.error.report(errorReportUrl, {
    'error_message': formatError(err),
    'path': route,
    "type": "frontend",
    "sub_source":"onError",
    'app_id': app_id
  })


});

onUnhandledRejection((res) => {
  console.error(res);
  if (!strToBool(errorEnable)) {
    return
  }
  // 进行异常信息的上报
  const pages = getCurrentPages();
  const currentPage = pages[pages.length - 1];
  const route = currentPage ? currentPage.route : "未知页面";
  proxy.$cf.error.report(errorReportUrl, {
    'error_message': formatError(res),
    'path': route,
    "type": "frontend",
    "sub_source":"onUnhandledRejection",
    'app_id': app_id
  })
});



</script>
<style>
@import "@/static/iconfont/iconfont.css";
/*每个页面公共css */
::v-deep .uni-popup__wrapper {
  background-color: white !important;
  border-radius: 10px;
  overflow-y: auto;
}

/* 禁止透传 */
::v-deep .uni-popup {
  z-index: 99999999 !important;
}

html, body {
  touch-action: manipulation; /* 禁用双击放大 */
  font-size: 12px;
}

page {
  height: 100% !important;
}

::v-deep .uni-card__cover-image {
  width: 100% !important;
}

*, html, body, view, div {
  box-sizing: border-box;
}
uni-map {
  height: 100%;
}

/*日期选择器的z-index 放在 tabbar 上方*/
::v-deep .uni-calendar__content-mobile{
  z-index: 99999999 !important;
}
</style>
