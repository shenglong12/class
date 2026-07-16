<template>
  <view class="w-full flex flex-col items-center p-4 box-border bg-gray-100">

    <!-- 卡片容器 -->
    <view class="w-full max-w-[460px] bg-white rounded-md shadow-lg p-2">
      <!-- 表单 -->
      <uni-forms
          ref="formRef"
          class="w-full"
          :modelValue="form"
          :rules="rules"
          label-width="auto"
          status-icon
      >
        <!-- 邮箱 -->
        <uni-forms-item name="email">
          <uni-easyinput
              v-model="form.email"
              :placeholder="$t('login.enter_email')"
              class="placeholder-neutral-400 bg-neutral-50 rounded-[30px] border-none"
          />
        </uni-forms-item>

        <!-- 验证码 -->
        <uni-forms-item name="code">
          <view class="flex items-center gap-1">
            <uni-easyinput
                v-model="form.code"
                :placeholder="$t('login.enter_code')"
                class="flex-1 placeholder-neutral-400 bg-neutral-50 rounded-[30px] border-none"
            />
            <button size="mini" class="bg-primary-500 text-white rounded-[20px] shadow
             disabled:bg-gray-400 disabled:text-gray-200" @click="sendCode">
              {{ countdown > 0 ? countdown + 's' : $t('login.get_code') }}
            </button>
          </view>
        </uni-forms-item>

        <!-- 登录按钮 -->
        <uni-forms-item>
          <button
              id="login_ref_button_kf"
              class="bg-primary-500 hover:bg-primary-600 text-white w-full h-[50px] mt-2 rounded-[30px]
                   shadow-md flex items-center justify-center transition-colors duration-300"
              @click="submitForm(formRef)"
          >
            {{ $t('login.login_btn') }}
          </button>
        </uni-forms-item>
      </uni-forms>

      <view v-show="show_mock" class="flex justify-center mt-2">
        <text class="mock p-2">
          当前可自动登录，应用正式发布后，需要使用真实信息进行登录
          <uni-badge class="uni-badge-left-margin" text="?" type="info" @click="jump_help"/>
        </text>
      </view>
    </view>
  </view>
</template>

<script setup>
const {proxy} = getCurrentInstance();

const props = defineProps({
  openId: {type: String, default: ''},
  relevanceTable: {type: String, required: false}
});

const emit = defineEmits(['loginSuccess', 'loginFail']);

const formRef = ref();

const form = ref({
  email: "",
  code: "",
});

const rules = ref({
  email: {
    rules: [
      {required: true, errorMessage: proxy.$tt('login.enter_email')},
      {format: 'email', errorMessage: proxy.$tt('login.email_format_error')}
    ]
  },
  code: {
    rules: [
      {required: true, errorMessage: proxy.$tt('login.enter_code')},
      {minLength: 4, maxLength: 6, errorMessage: proxy.$tt('login.code_length_rule_text')}
    ]
  }
});

// 倒计时
const countdown = ref(0);
let timer = null;


const sendCode = () => {
  if (!form.value.email) {
    return uni.showToast({title: proxy.$tt('login.enter_email'), icon: 'none'});
  }
  proxy.$refs.formRef.validateField('email').then(res => {
    proxy.$cf.login.sendEmailCode({email: form.value.email})
        .then((res) => {
          uni.showToast({title: proxy.$tt('login.send_code_success'), icon: 'success'});
          if(show_mock.value){
            form.value.code = res.data;
          }
          countdown.value = 60;
          timer = setInterval(() => {
            countdown.value--;
            if (countdown.value <= 0) {
              clearInterval(timer);
            }
          }, 1000);
        })
        .catch(err => {
          console.error(err);
          uni.showToast({title: proxy.$tt('login.send_code_fail'), icon: 'none'});
        });
  }).catch(err => {
    console.log(err)
  });
};

const submitForm = async (formEl) => {
  if (!formEl) return;
  await proxy.$refs.formRef.validate().then(res => {

    form.value.relevanceTable = props.relevanceTable
    form.value.openId = props.openId

    proxy.$cf.login.loginByMail(form.value).then((res) => {
      login_success(res);
    }).catch(err => {
      console.error(err)
      login_error(err);
    });
  }).catch(err => {
    console.log("error submit!", err);
  });
};


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
    if (!shouldShow('login.show.mail', configList)) {
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