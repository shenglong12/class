<template>

  <base-login-passwd v-if="login_type === 'passwd'" :openId="wx_open_id" :relevanceTable="relevanceTable" @loginSuccess="login_success" @loginFail="login_error"/>
  <base-login-mail v-else-if="login_type === 'mail'" :relevanceTable="relevanceTable" @loginSuccess="login_success" @loginFail="login_error"/>
  <base-login-wechat v-else-if="login_type === 'wechat'" :relevanceTable="relevanceTable" @loginSuccess="login_success" @loginFail="login_error"/>
  <base-login-weapp v-else-if="login_type === 'weapp'" :relevanceTable="relevanceTable" @loginSuccess="login_success" @loginFail="login_error"/>
  <base-login-phone v-else-if="login_type === 'phone'" :relevanceTable="relevanceTable" @loginSuccess="login_success" @loginFail="login_error"/>


  <view v-if="login_type === 'passwd' || login_type === 'mail' || login_type === 'wechat' || login_type === 'phone'"
        class="w-full flex justify-center text-[12px] text-neutral-400 mb-1">
    {{ end_content_show }}
  </view>
</template>
<script>
export default {
  options: {
    styleIsolation: 'shared', // 解除样式隔离
  }
};
</script>
<script setup>

const {proxy} = getCurrentInstance();

const emit = defineEmits(["loginSuccess", "loginFail"]);

const props = defineProps({
  login_type: {type: String, default: 'passwd'},
  show_title: {type: String, default: "登陆"},
  relevanceTable: {type: String, required: true,},
  is_register: {type: String, required: false,},
  register_page: {type: String, required: false, default: ""},
  title: {type: String, required: false, default: ""},
  end_content: {type: String, required: false, default: ""}
});

const end_content_show = ref('')
const wx_open_id = ref(null)
let left_title = import.meta.env.VITE_APP_NAME;
let app_vip = import.meta.env.VITE_APP_VIP === 'true'
if (app_vip) {
  end_content_show.value = ''
} else {
  end_content_show.value = props.end_content
  left_title = "CodeFlying!"
}

function login_error(err) {
  emit("loginFail");
}

const login_success = async (res) => {
  emit("loginSuccess", res);
}

onLoad(async (options) => {
  //  判断token 是否存在
  try {

    if (options && options.openid) {
      console.log("openId 参数为：", options.openid);
      wx_open_id.value=options.openid;
    }

    let res = await proxy.$cf.globalVariable.read({
      variableName: "h5_token",
    })
    if (res.success) {
      let res = await proxy.$cf.login.getLoginUser()
      if (res.success && res.data.code!=40100){
        emit("loginSuccess")
      }

    }
  } catch (e) {
  }

})


</script>
