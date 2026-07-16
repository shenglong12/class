<template>
     
  <base-wrapper
>
<div class="flex-between-start" style="background-color: #5D5FEF; height: 140px;">
    <div class="flex-c-center-start h-full">
      <div class="m-x-20">
          <span style="font-weight: bold; font-size: 20px; color: #FFFFFF;">API配置</span>
      </div>
      <div class="m-x-20 m-t-4">
        <span style="font-size: 14px; color: #FFFFFF;">主键、api名称、描述、地址、令牌、APPID、令牌Key、令牌密钥、方法、请求体类型、请求体模版、请求头模版、授权类型、请求协议、数据获取路径、数据类型。</span>
      </div>
    </div>
    <div class="flex-start-end h-full m-r-10">
        <img src="@/assets/logo/document.png" height="130px" />
    </div>
</div>
<el-breadcrumb
separator="/"
class="m-20"
>
<el-breadcrumb-item
:to="{ path: '/' }"
>
首页
</el-breadcrumb-item>
<el-breadcrumb-item
:to="{ path: '/dynamic_api_setting' }"
>
API配置管理
</el-breadcrumb-item>
<el-breadcrumb-item
>
API配置详情
</el-breadcrumb-item>
</el-breadcrumb>
<base-wrapper
class="p-10"
>
<el-tabs
class="demo-tabs"
>
<el-tab-pane
label="API配置详情"
>
<el-form
:model='form'
ref='dataFormRef'
label-position='top'
:rules='{}'
label-width='150px'
>
    <el-form-item  label="api名称" prop="keyName">
        <el-input clearable placeholder="请输入api名称" v-model="form.keyName" />
    </el-form-item>

    <el-form-item  label="描述" prop="description">
        <el-input clearable placeholder="请输入描述" v-model="form.description" />
    </el-form-item>

    <el-form-item  label="地址" prop="url">
        <el-input clearable placeholder="请输入地址" v-model="form.url" />
    </el-form-item>

    <el-form-item  label="令牌" prop="token">
        <el-input clearable placeholder="请输入令牌" v-model="form.token" />
    </el-form-item>

    <el-form-item  label="APPID" prop="appId">
        <el-input clearable placeholder="请输入APPID" v-model="form.appId" />
    </el-form-item>

    <el-form-item  label="令牌Key" prop="apiKey">
        <el-input clearable placeholder="请输入令牌Key" v-model="form.apiKey" />
    </el-form-item>

    <el-form-item  label="令牌密钥" prop="apiSecret">
        <el-input clearable placeholder="请输入令牌密钥" v-model="form.apiSecret" />
    </el-form-item>

    <el-form-item  label="方法" prop="method">
        <el-input clearable placeholder="请输入方法" v-model="form.method" />
    </el-form-item>

    <el-form-item  label="请求体类型" prop="bodyType">
        <el-input clearable placeholder="请输入请求体类型" v-model="form.bodyType" />
    </el-form-item>

    <el-form-item  label="请求体模版" prop="bodyTemplate">
        <el-input clearable placeholder="请输入请求体模版" v-model="form.bodyTemplate" />
    </el-form-item>

    <el-form-item  label="请求头模版" prop="header">
        <el-input clearable placeholder="请输入请求头模版" v-model="form.header" />
    </el-form-item>

    <el-form-item  label="授权类型" prop="authType">
        <el-input clearable placeholder="请输入授权类型" v-model="form.authType" />
    </el-form-item>

    <el-form-item  label="请求协议" prop="protocol">
        <el-input clearable placeholder="请输入请求协议" v-model="form.protocol" />
    </el-form-item>

    <el-form-item  label="数据获取路径" prop="dataPath">
        <el-input clearable placeholder="请输入数据获取路径" v-model="form.dataPath" />
    </el-form-item>

    <el-form-item  label="数据类型" prop="dataType">
        <el-input clearable placeholder="请输入数据类型" v-model="form.dataType" />
    </el-form-item>

</el-form>
<base-layout
style="text-align: center;"
w_full="true"
>
<el-button
@click="submitForm"
round
color="#5D5FEF"
style="width: 200px;"
type="primary"
>保 存</el-button>
</base-layout>
</el-tab-pane>
</el-tabs>
</base-wrapper>
</base-wrapper>
   
</template>

<script setup>

const { proxy } = getCurrentInstance();
let form = ref({});
let formAll = ref({});
let rules = ref({});
let routerQuery = proxy.$route.query;
let detail = ref('normally')

function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {

      if(import.meta.env.VITE_APP_MODEL === 'PREVIEW'){
          let res = await proxy.$api.table['update']({
              table_name: 'dynamic_api_setting',
              param: form.value
          });

          proxy.$modal.msgSuccess(res.message);

          if(res.code == 0){
              refresh();
          }
      }
      else{
          let res = await proxy.$api.dynamic_api_setting['update'](form.value);
          proxy.$modal.msgSuccess(res.message);
          if(res.code == 0){
            refresh();
          }
      }
    }
  });
}

async function getDetail(id) {
    if(import.meta.env.VITE_APP_MODEL === 'PREVIEW'){
        let res = await proxy.$api.table.get({
            table_name: 'dynamic_api_setting',
            param: {
                id: id
            }
        });

        if(res.code == 0){
            form.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
    else{
        let res = await proxy.$api.dynamic_api_setting['get'](id);
        if(res.code == 0){
            form.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
}

async function getDetailAll(id){
    if(import.meta.env.VITE_APP_MODEL === 'PREVIEW'){
        let res = await proxy.$api.table.get({
            table_name: 'dynamic_api_setting',
            param: {
                id: id
            }
        });

        if(res.code == 0){
            formAll.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
    else{
        let res = await proxy.$api.dynamic_api_setting['get'](id);
        if(res.code == 0){
            formAll.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
}

function refresh(){
    if(routerQuery.id){
        getDetail(routerQuery.id);
    }
    if(detail.value == 'all'){
        if(routerQuery.id){
            getDetailAll(routerQuery.id);
        }
    }
}

refresh();

</script>

<style lang="scss" scoped>
</style>