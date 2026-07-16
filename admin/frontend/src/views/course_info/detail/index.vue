<template>
     
  <base-wrapper
>
<div class="flex-between-start" style="background-color: #5D5FEF; height: 140px;">
    <div class="flex-c-center-start h-full">
      <div class="m-x-20">
          <span style="font-weight: bold; font-size: 20px; color: #FFFFFF;">课程信息</span>
      </div>
      <div class="m-x-20 m-t-4">
        <span style="font-size: 14px; color: #FFFFFF;">主键、课程名称、学院、专业、教室、开始日期、结束日期、上课星期、时间段、授课内容。</span>
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
:to="{ path: '/course_info' }"
>
课程信息管理
</el-breadcrumb-item>
<el-breadcrumb-item
>
课程信息详情
</el-breadcrumb-item>
</el-breadcrumb>
<base-wrapper
class="p-10"
>
<el-tabs
class="demo-tabs"
>
<el-tab-pane
label="课程信息详情"
>
<el-form
:model='form'
ref='dataFormRef'
label-position='top'
:rules='{"college":[{"trigger":"blur","message":"学院不能为空","required":true}],"courseName":[{"trigger":"blur","message":"课程名称不能为空","required":true}],"major":[{"trigger":"blur","message":"专业不能为空","required":true}],"endDate":[{"trigger":"blur","message":"结束日期不能为空","required":true}],"weekdays":[{"trigger":"blur","message":"上课星期不能为空","required":true}],"timePeriod":[{"trigger":"blur","message":"时间段不能为空","required":true}],"classroomInfoClassroomInfoId1":[{"trigger":"blur","message":"教室不能为空","required":true}],"startDate":[{"trigger":"blur","message":"开始日期不能为空","required":true}]}'
label-width='150px'
>
    <el-form-item  label="课程名称" prop="courseName">
        <el-input clearable placeholder="请输入课程名称" v-model="form.courseName" />
    </el-form-item>

    <el-form-item  label="学院" prop="college">
        <el-input clearable placeholder="请输入学院" v-model="form.college" />
    </el-form-item>

    <el-form-item  label="专业" prop="major">
        <el-input clearable placeholder="请输入专业" v-model="form.major" />
    </el-form-item>

    <el-form-item   label="教室" prop="classroomInfoClassroomInfoId1">
        <base-select clearable v-model="form.classroomInfoClassroomInfoId1" api="course_info_all.get_classroom_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="开始日期" prop="startDate">
        <el-date-picker placeholder="请输入开始日期" v-model="form.startDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

    <el-form-item   label="结束日期" prop="endDate">
        <el-date-picker placeholder="请输入结束日期" v-model="form.endDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

    <el-form-item  label="上课星期" prop="weekdays">
        <el-input clearable placeholder="请输入上课星期" v-model="form.weekdays" />
    </el-form-item>

    <el-form-item  label="时间段" prop="timePeriod">
        <el-input clearable placeholder="请输入时间段" v-model="form.timePeriod" />
    </el-form-item>

    <el-form-item  label="授课内容" prop="content">
        <el-input clearable placeholder="请输入授课内容" v-model="form.content" />
    </el-form-item>

</el-form>
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
              table_name: 'course_info',
              param: form.value
          });

          proxy.$modal.msgSuccess(res.message);

          if(res.code == 0){
              refresh();
          }
      }
      else{
          let res = await proxy.$api.course_info['update'](form.value);
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
            table_name: 'course_info',
            param: {
                courseInfoId: id
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
        let res = await proxy.$api.course_info['get'](id);
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
            table_name: 'course_info',
            param: {
                courseInfoId: id
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
        let res = await proxy.$api.course_info_all['get'](id);
        if(res.code == 0){
            formAll.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
}

function refresh(){
    if(routerQuery.courseInfoId){
        getDetail(routerQuery.courseInfoId);
    }
    if(detail.value == 'all'){
        if(routerQuery.courseInfoId){
            getDetailAll(routerQuery.courseInfoId);
        }
    }
}

refresh();

</script>

<style lang="scss" scoped>
</style>