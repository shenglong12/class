<template>
     
  <base-wrapper
>
<div class="flex-between-start" style="background-color: #5D5FEF; height: 140px;">
    <div class="flex-c-center-start h-full">
      <div class="m-x-20">
          <span style="font-weight: bold; font-size: 20px; color: #FFFFFF;">用户信息</span>
      </div>
      <div class="m-x-20 m-t-4">
        <span style="font-size: 14px; color: #FFFFFF;">主键、姓名、学号、手机号、所属学院、专业、密码、角色、用户状态、注册时间。</span>
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
:to="{ path: '/user_info' }"
>
用户信息管理
</el-breadcrumb-item>
<el-breadcrumb-item
>
用户信息详情
</el-breadcrumb-item>
</el-breadcrumb>
<base-wrapper
class="p-10"
>
<el-tabs
class="demo-tabs"
>
<el-tab-pane
label="用户信息详情"
>
<el-form
:model='form'
ref='dataFormRef'
label-position='top'
:rules='{"studentId":[{"trigger":"blur","message":"学号不能为空","required":true}],"college":[{"trigger":"blur","message":"所属学院不能为空","required":true}],"password":[{"trigger":"blur","message":"密码不能为空","required":true}],"phoneNumber":[{"trigger":"blur","message":"手机号不能为空","required":true}],"major":[{"trigger":"blur","message":"专业不能为空","required":true}],"name":[{"trigger":"blur","message":"姓名不能为空","required":true}],"roleEnumRoleEnumId1":[{"trigger":"blur","message":"角色不能为空","required":true}],"userStatusEnumUserStatusEnumId1":[{"trigger":"blur","message":"用户状态不能为空","required":true}]}'
label-width='150px'
>
    <el-form-item  label="姓名" prop="name">
        <el-input clearable placeholder="请输入姓名" v-model="form.name" />
    </el-form-item>

    <el-form-item  label="学号" prop="studentId">
        <el-input clearable placeholder="请输入学号" v-model="form.studentId" />
    </el-form-item>

    <el-form-item  label="手机号" prop="phoneNumber">
        <el-input clearable placeholder="请输入手机号" v-model="form.phoneNumber" />
    </el-form-item>

    <el-form-item  label="所属学院" prop="college">
        <el-input clearable placeholder="请输入所属学院" v-model="form.college" />
    </el-form-item>

    <el-form-item  label="专业" prop="major">
        <el-input clearable placeholder="请输入专业" v-model="form.major" />
    </el-form-item>

    <el-form-item  label="密码" prop="password">
        <el-input clearable placeholder="请输入密码" v-model="form.password" />
    </el-form-item>

    <el-form-item   label="角色" prop="roleEnumRoleEnumId1">
        <base-select clearable v-model="form.roleEnumRoleEnumId1" api="user_info_all.get_role_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="用户状态" prop="userStatusEnumUserStatusEnumId1">
        <base-select clearable v-model="form.userStatusEnumUserStatusEnumId1" api="user_info_all.get_user_status_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="注册时间" prop="registrationDate">
        <el-date-picker placeholder="请输入注册时间" v-model="form.registrationDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

</el-form>
<base-cell
header="所属角色枚举"
label-width="180px"
class="m-b-10"
>
<base-cell-item
label="角色名称"
>
{{ formAll.roleName }}
</base-cell-item>
</base-cell>
<base-cell
header="所属用户状态"
label-width="180px"
class="m-b-10"
>
<base-cell-item
label="状态名称"
>
{{ formAll.statusName }}
</base-cell-item>
</base-cell>
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
<el-tab-pane
label="查看报修单"
>
<repair_order_component
 :params="{userInfoUserInfoId1:routerQuery.userInfoId}"
 />
</el-tab-pane>
<el-tab-pane
label="查看预约信息"
>
<booking_info_component
 :params="{userInfoUserInfoId1:routerQuery.userInfoId}"
 />
</el-tab-pane>
<el-tab-pane
label="查看背景图片"
>
<background_image_component
 :params="{userInfoUserInfoId1:routerQuery.userInfoId}"
 />
</el-tab-pane>
<el-tab-pane
label="查看扫码记录"
>
<scan_record_component
 :params="{userInfoUserInfoId1:routerQuery.userInfoId}"
 />
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
let detail = ref('all')

function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {

      if(import.meta.env.VITE_APP_MODEL === 'PREVIEW'){
          let res = await proxy.$api.table['update']({
              table_name: 'user_info',
              param: form.value
          });

          proxy.$modal.msgSuccess(res.message);

          if(res.code == 0){
              refresh();
          }
      }
      else{
          let res = await proxy.$api.user_info['update'](form.value);
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
            table_name: 'user_info',
            param: {
                userInfoId: id
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
        let res = await proxy.$api.user_info['get'](id);
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
            table_name: 'user_info',
            param: {
                userInfoId: id
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
        let res = await proxy.$api.user_info_all['get'](id);
        if(res.code == 0){
            formAll.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
}

function refresh(){
    if(routerQuery.userInfoId){
        getDetail(routerQuery.userInfoId);
    }
    if(detail.value == 'all'){
        if(routerQuery.userInfoId){
            getDetailAll(routerQuery.userInfoId);
        }
    }
}

refresh();

</script>

<style lang="scss" scoped>
</style>