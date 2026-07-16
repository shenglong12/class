<template>
    <div v-if="login_type === 'passwd'" class="login">
      <h2>登录</h2>
      <el-form
        ref="formRef"
        style="max-width: 320px"
        maxlength="10"
        :model="form"
        :rules="rules"
        label-width="auto"
        class="demo-form"
        status-icon
      >
        <el-form-item prop="phone">
          <el-input placeholder="请输入用户名" v-model="form.phone" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            type="password"
            v-model="form.password"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(formRef)">
            登录
          </el-button>
        </el-form-item>
      </el-form>
      <h5>默认账户密码：admin / 123456</h5>
    </div>
    <div v-else>
        <el-button type="success" round size="large" @click="login_click">点击微信授权登录</el-button>
    </div>
</template>

<script setup>

    const { proxy } = getCurrentInstance();

    // 定义事件
    const emit = defineEmits(["loginSucceess"]);

    /**
     * 数据
     */
    const form = ref({
        name: "",
        phone: "",
        password: "",
    });

    const formRef = ref();

    const rules = reactive({
        phone: [
            { required: true, message: "请输入用户名", trigger: "blur" },
            // { min: 1, max: 15, message: "用户名长度1-15位", trigger: "blur" },
        ],
        password: [
            {
            required: true,
            message: "请输入密码",
            trigger: "blur",
            },
            // { min: 6, max: 18, message: "密码长度", trigger: "blur" },
        ],
    });

    const query = proxy.$route.query;
    const login_type = import.meta.env.VITE_USE_LOGIN_TYPE;

    const appId = import.meta.env.VITE_WX_APP_ID;
    const callback = import.meta.env.VITE_WX_CALL_BACK;

    if(login_type == 'wx_h5' && query.code!=null && query.code!= ''){
        proxy.$api.login.loginWxWeb(query).then((res)=> {            
            login_success(res);
        });
    }

    //  登录
    const submitForm = async (formEl) => {
        if (!formEl) return;
        await formEl.validate((valid, fields) => {
            if (valid) {
                proxy.$api.login.loginPasswd(form.value).then((res)=> {
                    login_success(res);
                });
            } else {
                console.log("error submit!", fields);
            }
        });
    };

    function login_success(res){
        proxy.$modal.msgSuccess("登录成功");
        localStorage.setItem("token", res.data);
        emit("loginSucceess");
    }

    function login_click(){
        let url = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=${encodeURIComponent(callback)}&response_type=code&scope=snsapi_base&state=codeflying#wechat_redirect`;
        window.location.href = url;
    }

    onMounted(() => {
      login_setting();
    });

    function login_setting() {
      proxy.$api.setting.login().then((res) => {
        let configList = res.data;
        if(!shouldShow('login.show.account', configList)){
          form.value.phone = '';
          form.value.password = '';
        }
        else{
          form.value.phone = 'admin';
          form.value.password = '123456';
        }
      });
    }

    function shouldShow(nameKey, configList) {
      const item = configList.find(item => item.name === nameKey);
      return item && item.content === "true";
    }

</script>

<style lang="scss" scoped>
.login {
  width: 440px;
  height: 464px;
  padding-top: 60px;
  background: #ffffff;
  border-radius: 6px;
  text-align: center;
  h2 {
    text-align: center;
  }
  .el-form {
    margin: 0 auto;
    margin-top: 45px;
    height: 230px;
    .el-form-item,
    .el-input__wrapper,
    .el-input {
      height: 40px;
      .el-input__inner {
        height: 100%;
      }
    }
    .el-button {
      width: 100%;
      height: 40px;
      margin-top: 22px;
    }
  }
}
</style>