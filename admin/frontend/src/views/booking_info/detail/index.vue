<template>
     
  <base-wrapper
>
<div class="flex-between-start" style="background-color: #5D5FEF; height: 140px;">
    <div class="flex-c-center-start h-full">
      <div class="m-x-20">
          <span style="font-weight: bold; font-size: 20px; color: #FFFFFF;">预约信息</span>
      </div>
      <div class="m-x-20 m-t-4">
        <span style="font-size: 14px; color: #FFFFFF;">主键、预约用户、教室、预约日期、时间段、预约用途、预约状态、创建时间、预约开始时间、预约结束时间。</span>
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
:to="{ path: '/booking_info' }"
>
预约信息管理
</el-breadcrumb-item>
<el-breadcrumb-item
>
预约信息详情
</el-breadcrumb-item>
</el-breadcrumb>
<base-wrapper
class="p-10"
>
<el-tabs
class="demo-tabs"
>
<el-tab-pane
label="预约信息详情"
>
<el-form
:model='form'
ref='dataFormRef'
label-position='top'
:rules='{"purpose":[{"trigger":"blur","message":"预约用途不能为空","required":true}],"timePeriod":[{"trigger":"blur","message":"时间段不能为空","required":true}],"bookingDate":[{"trigger":"blur","message":"预约日期不能为空","required":true}],"startTime":[{"trigger":"blur","message":"预约开始时间不能为空","required":true}],"endTime":[{"trigger":"blur","message":"预约结束时间不能为空","required":true}],"userInfoUserInfoId1":[{"trigger":"blur","message":"预约用户不能为空","required":true}],"classroomInfoClassroomInfoId1":[{"trigger":"blur","message":"教室不能为空","required":true}],"bookingStatusEnumBookingStatusEnumId1":[{"trigger":"blur","message":"预约状态不能为空","required":true}]}'
label-width='150px'
>
    <el-form-item   label="预约用户" prop="userInfoUserInfoId1">
        <base-select clearable v-model="form.userInfoUserInfoId1" api="booking_info_all.get_user_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="教室" prop="classroomInfoClassroomInfoId1">
        <base-select clearable v-model="form.classroomInfoClassroomInfoId1" api="booking_info_all.get_classroom_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="预约日期" prop="bookingDate">
        <el-date-picker placeholder="请输入预约日期" v-model="form.bookingDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

    <el-form-item  label="时间段" prop="timePeriod">
        <el-input clearable placeholder="请输入时间段" v-model="form.timePeriod" />
    </el-form-item>

    <el-form-item  label="预约用途" prop="purpose">
        <el-input clearable placeholder="请输入预约用途" v-model="form.purpose" />
    </el-form-item>

    <el-form-item   label="预约状态" prop="bookingStatusEnumBookingStatusEnumId1">
        <base-select clearable v-model="form.bookingStatusEnumBookingStatusEnumId1" api="booking_info_all.get_booking_status_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="创建时间" prop="createTime">
        <el-date-picker placeholder="请输入创建时间" v-model="form.createTime" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

    <el-form-item  label="预约开始时间" prop="startTime">
        <el-input clearable placeholder="请输入预约开始时间" v-model="form.startTime" />
    </el-form-item>

    <el-form-item  label="预约结束时间" prop="endTime">
        <el-input clearable placeholder="请输入预约结束时间" v-model="form.endTime" />
    </el-form-item>

</el-form>
<base-cell
header="所属用户信息"
label-width="180px"
class="m-b-10"
>
<base-cell-item
label="姓名"
>
{{ formAll.name }}
</base-cell-item>
<base-cell-item
label="学号"
>
{{ formAll.studentId }}
</base-cell-item>
<base-cell-item
label="手机号"
>
{{ formAll.phoneNumber }}
</base-cell-item>
<base-cell-item
label="所属学院"
>
{{ formAll.college }}
</base-cell-item>
<base-cell-item
label="专业"
>
{{ formAll.major }}
</base-cell-item>
<base-cell-item
label="密码"
>
{{ formAll.password }}
</base-cell-item>
<base-cell-item
label="角色"
>
{{ formAll.roleEnumRoleEnumId1 }}
</base-cell-item>
<base-cell-item
label="用户状态"
>
{{ formAll.userStatusEnumUserStatusEnumId1 }}
</base-cell-item>
<base-cell-item
label="注册时间"
>
{{ formAll.registrationDate }}
</base-cell-item>
</base-cell>
<base-cell
header="所属教室信息"
label-width="180px"
class="m-b-10"
>
<base-cell-item
label="教学楼"
>
{{ formAll.buildingEnumBuildingEnumId1 }}
</base-cell-item>
<base-cell-item
label="教室编号"
>
{{ formAll.roomNumber }}
</base-cell-item>
<base-cell-item
label="二维码路径"
>
{{ formAll.qrCodePath }}
</base-cell-item>
</base-cell>
<base-cell
header="所属预约状态"
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
              table_name: 'booking_info',
              param: form.value
          });

          proxy.$modal.msgSuccess(res.message);

          if(res.code == 0){
              refresh();
          }
      }
      else{
          let res = await proxy.$api.booking_info['update'](form.value);
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
            table_name: 'booking_info',
            param: {
                bookingInfoId: id
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
        let res = await proxy.$api.booking_info['get'](id);
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
            table_name: 'booking_info',
            param: {
                bookingInfoId: id
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
        let res = await proxy.$api.booking_info_all['get'](id);
        if(res.code == 0){
            formAll.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
}

function refresh(){
    if(routerQuery.bookingInfoId){
        getDetail(routerQuery.bookingInfoId);
    }
    if(detail.value == 'all'){
        if(routerQuery.bookingInfoId){
            getDetailAll(routerQuery.bookingInfoId);
        }
    }
}

refresh();

</script>

<style lang="scss" scoped>
</style>