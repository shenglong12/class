<template>
     
  <base-wrapper
>
<div class="flex-between-start" style="background-color: #5D5FEF; height: 140px;">
    <div class="flex-c-center-start h-full">
      <div class="m-x-20">
          <span style="font-weight: bold; font-size: 20px; color: #FFFFFF;">系统配置</span>
      </div>
      <div class="m-x-20 m-t-4">
        <span style="font-size: 14px; color: #FFFFFF;">主键、配置项名称、中文名称、配置描述、配置值、备注、配置类型。</span>
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
:to="{ path: '/kf_system_config' }"
>
系统配置管理
</el-breadcrumb-item>
<el-breadcrumb-item
>
系统配置详情
</el-breadcrumb-item>
</el-breadcrumb>
<base-wrapper
class="p-10"
>
<el-tabs
class="demo-tabs"
>
<el-tab-pane
label="系统配置详情"
>
<el-form
:model='form'
ref='dataFormRef'
label-position='top'
:rules='{"name":[{"trigger":"blur","message":"配置项名称不能为空","required":true}]}'
label-width='150px'
>
    <el-form-item  label="配置项名称" prop="name">
        <el-input clearable placeholder="请输入配置项名称" v-model="form.name" />
    </el-form-item>

    <el-form-item  label="中文名称" prop="chineseName">
        <el-input clearable placeholder="请输入中文名称" v-model="form.chineseName" />
    </el-form-item>

    <el-form-item  label="配置描述" prop="description">
        <el-input clearable placeholder="请输入配置描述" v-model="form.description" />
    </el-form-item>

    <el-form-item  label="配置值" prop="content">
        <el-input clearable placeholder="请输入配置值" v-model="form.content" />
    </el-form-item>

    <el-form-item  label="备注" prop="remark">
        <el-input clearable placeholder="请输入备注" v-model="form.remark" />
    </el-form-item>

    <el-form-item  label="配置类型" prop="type">
        <el-input clearable placeholder="请输入配置类型" v-model="form.type" />
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
              table_name: 'kf_system_config',
              param: form.value
          });

          proxy.$modal.msgSuccess(res.message);

          if(res.code == 0){
              refresh();
          }
      }
      else{
          let res = await proxy.$api.kf_system_config['update'](form.value);
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
            table_name: 'kf_system_config',
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
        let res = await proxy.$api.kf_system_config['get'](id);
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
            table_name: 'kf_system_config',
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
        let res = await proxy.$api.kf_system_config['get'](id);
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