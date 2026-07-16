<template>
  <view class=" flex flex-col items-center p-4 box-border">
    <button class="bg-success-500 hover:bg-success-600 text-white rounded-full px-8"
            @click="login_click">{{ $t('login.wx_app_login') }}
    </button>
  </view>
  <view class="radios flex justify-center text-[28rpx]">
    <label class="radio flex justify-center items-center">
      <radio class="scale-[0.74] mr-[18rpx]" @click="isCheck" value="radio1" :checked="radio1"/>
      <span class="text-neutral-400">{{ $t('login.read_and_agree') }}
          <span class="text-primary-500 hover:text-primary-600 cursor-pointer"
          >《{{ $t('login.service_agreement') }}》</span>
          <span>及</span>
          <span class="text-primary-500 hover:text-primary-600 cursor-pointer"
          >《{{ $t('login.privacy_agreement') }}》</span>
        </span>
    </label>
  </view>
  <view v-show="show_mock" class="flex justify-center mt-2">
    <text class="mock p-2">
      当前可自动登录，应用正式发布后，需要使用真实信息进行登录
      <uni-badge class="uni-badge-left-margin" text="?" type="info" @click="jump_help"/>
    </text>
  </view>

</template>

<script setup>

const props = defineProps({
  relevanceTable: {type: String, required: false}
});
const emit = defineEmits(['loginSuccess', 'loginFail']);


const {proxy} = getCurrentInstance();

const radio1 = ref(false)
const isCheck = () => {
  radio1.value = !radio1.value;
}

function login_click() {
  if (radio1.value) {

    if (show_mock.value) {

      proxy.$cf.login.loginByWeApp({code: "codeflying", relevanceTable: props.relevanceTable}).then((res) => {
        login_success(res);
      }).catch(err => {
        login_error(err);
      })

    } else {
      uni.login({
        success: async (res) => {
          console.log(res, "res============")
          let code = res.code
          proxy.$cf.login.loginByWeApp({code: code, relevanceTable: props.relevanceTable}).then((res) => {
            login_success(res);
          }).catch(err => {
            login_error(err);
          })

        }, fail: (error) => {
          uni.showToast({
            title: `必须在微信小程序里使用`,
            icon: 'none'
          })
        }
      })
    }
  } else {
    uni.showToast({
      title: '请先阅读与勾选协议',
      icon: "none",
      duration: 2000
    });
  }
}


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


const show_mock = ref(true)

onLoad(async (options) => {
  login_setting();
})

function login_setting() {
  proxy.$cf.setting.login().then((res) => {
    let configList = res.data;
    if (!shouldShow('login.show.weapp', configList)) {
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
