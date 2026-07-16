<template>
  <view class=" flex flex-col items-center p-4 box-border">
    <button class="bg-success-500 hover:bg-success-600 text-white rounded-full px-8"
            @click="login_click">{{ $t('login.wx_login') }}
    </button>
    <view v-show="show_mock" class="mt-2">
      <text class="mock p-2">
        当前可自动登录，应用正式发布后，需要使用真实信息进行登录
        <uni-badge class="uni-badge-left-margin" text="?" type="info" @click="jump_help"/>
      </text>
    </view>
  </view>
</template>

<script setup>

const props = defineProps({
  relevanceTable: {type: String, required: false}
});
const emit = defineEmits(['loginSuccess', 'loginFail']);

const {proxy} = getCurrentInstance();

async function login_click() {

  if (show_mock.value) {
    let wechatForm = {code: 'codeflying'};
    wechatForm.relevanceTable = props.relevanceTable;
    proxy.$cf.login.loginByWechat(wechatForm).then((res) => {
      login_success(res);
    }).catch(err => {
      login_error(err);
    });
  } else {
    if (isWeixinBrowser()) {
      let res = await proxy.$cf.login.loginWechatAuthUri();
      if (res.success) {
        window.location.href = res.data;
      }
    } else {
      uni.showToast({
        title: `必须在微信浏览器里使用`,
        icon: 'none'
      })
    }
  }
}

onLoad(async (options) => {

  if (options.code != null && options.code !== '') {

    let wechatForm = {...options};
    wechatForm.relevanceTable = props.relevanceTable;

    proxy.$cf.login.loginByWechat(wechatForm).then((res) => {

      login_success(res);

    }).catch(err => {
      console.error(err)
      login_error(err);
    });
  }

})

function login_error(err) {
  emit("loginFail");
}

const login_success = async (res) => {

  uni.showToast({
    title: proxy.$tt('login.login_success_text'),
    icon: "success",
    duration: 2000
  });

  await proxy.$cf.globalVariable.write({
    variableName: "h5_token",
    value: res.data
  })

  proxy.$cf.login.getLoginUser().then((res) => {
    const item = res.data;
    proxy.$cf.globalVariable.write({
      variableName: "currentUser",
      value: item
    })

    emit("loginSuccess", item);

  }).catch(err => {
    console.log(err);
    emit("loginFail", err);
  });

}

function isWeixinBrowser() {
  return typeof window !== 'undefined' &&
      window.navigator.userAgent.toLowerCase().toString().includes('micromessenger')
}

const show_mock = ref(true)

onLoad(async (options) => {
  login_setting();
})

function login_setting() {
  proxy.$cf.setting.login().then((res) => {
    let configList = res.data;
    if (!shouldShow('login.show.wechat', configList)) {
      show_mock.value = false;
    } else {
      show_mock.value = true;
    }
  });
}

function shouldShow(nameKey, configList) {
  const item = configList.find(item => item.name === nameKey);
  return item && item.content === "true";
}

function jump_help() {
  // #ifdef H5
  window.open("https://vvx03gck2p.feishu.cn/wiki/HCWuwdzk5imkICkhUo5cvkuSnTf", "_blank");
  // #endif
}

</script>

<style>
.mock {
  display: block;
  border-radius: 10px 10px 10px 10px;
  background-color: rgba(255, 255, 255, 1);
  color: rgba(65, 67, 79, 1);
  font-size: 12px;
  text-align: left;
  box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.4);
}
</style>