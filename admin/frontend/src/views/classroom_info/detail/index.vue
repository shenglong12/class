<template>
     
  <base-wrapper
>
<div class="flex-between-start" style="background-color: #5D5FEF; height: 140px;">
    <div class="flex-c-center-start h-full">
      <div class="m-x-20">
          <span style="font-weight: bold; font-size: 20px; color: #FFFFFF;">教室信息</span>
      </div>
      <div class="m-x-20 m-t-4">
        <span style="font-size: 14px; color: #FFFFFF;">主键、教学楼、教室编号、二维码路径。</span>
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
:to="{ path: '/classroom_info' }"
>
教室信息管理
</el-breadcrumb-item>
<el-breadcrumb-item
>
教室信息详情
</el-breadcrumb-item>
</el-breadcrumb>
<base-wrapper
class="p-10"
>
<el-tabs
class="demo-tabs"
>
<el-tab-pane
label="教室信息详情"
>
<el-form
:model='form'
ref='dataFormRef'
label-position='top'
:rules='{"roomNumber":[{"trigger":"blur","message":"教室编号不能为空","required":true}],"buildingEnumBuildingEnumId1":[{"trigger":"blur","message":"教学楼不能为空","required":true}]}'
label-width='150px'
>
    <el-form-item   label="教学楼" prop="buildingEnumBuildingEnumId1">
        <base-select clearable v-model="form.buildingEnumBuildingEnumId1" api="classroom_info_all.get_building_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="教室编号" prop="roomNumber">
        <el-input clearable placeholder="请输入教室编号" v-model="form.roomNumber" />
    </el-form-item>

    <el-form-item  label="二维码路径" prop="qrCodePath">
        <el-input clearable placeholder="请输入二维码路径" v-model="form.qrCodePath" />
    </el-form-item>

</el-form>
<base-cell
header="所属教学楼枚举"
label-width="180px"
class="m-b-10"
>
<base-cell-item
label="教学楼名称"
>
{{ formAll.buildingName }}
</base-cell-item>
</base-cell>
<base-cell
header="二维码预览"
label-width="180px"
class="m-b-10"
v-if="form.qrCodePath"
>
<base-cell-item
label="二维码图片"
>
<el-image
:src="form.qrCodePath"
style="width: 200px; height: 200px;"
fit="contain"
:preview-src-list="[form.qrCodePath]"
/>
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
label="查看课程信息"
>
<course_info_component
 :params="{classroomInfoClassroomInfoId1:routerQuery.classroomInfoId}"
 />
</el-tab-pane>
<el-tab-pane
label="查看报修单"
>
<repair_order_component
 :params="{classroomInfoClassroomInfoId1:routerQuery.classroomInfoId}"
 />
</el-tab-pane>
<el-tab-pane
label="查看预约信息"
>
<booking_info_component
 :params="{classroomInfoClassroomInfoId1:routerQuery.classroomInfoId}"
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
              table_name: 'classroom_info',
              param: form.value
          });

          proxy.$modal.msgSuccess(res.message);

          if(res.code == 0){
              refresh();
          }
      }
      else{
          let res = await proxy.$api.classroom_info['update'](form.value);
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
            table_name: 'classroom_info',
            param: {
                classroomInfoId: id
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
        let res = await proxy.$api.classroom_info['get'](id);
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
            table_name: 'classroom_info',
            param: {
                classroomInfoId: id
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
        let res = await proxy.$api.classroom_info_all['get'](id);
        if(res.code == 0){
            formAll.value = Object.assign({}, res.data);
        }
        else{
            proxy.$modal.msgError(res.message);
        }
    }
}

function refresh(){
    if(routerQuery.classroomInfoId){
        getDetail(routerQuery.classroomInfoId);
    }
    if(detail.value == 'all'){
        if(routerQuery.classroomInfoId){
            getDetailAll(routerQuery.classroomInfoId);
        }
    }
}

refresh();

</script>

<style lang="scss" scoped>
</style>