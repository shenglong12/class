<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment="姓名"
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="user_info.import"
table_name="user_info"
firstSearchData="name"
>
<template #collapse>
    <el-form-item  label="姓名" prop="name">
        <el-input clearable placeholder="请输入姓名" v-model="listQuery.name" />
    </el-form-item>

    <el-form-item  label="学号" prop="studentId">
        <el-input clearable placeholder="请输入学号" v-model="listQuery.studentId" />
    </el-form-item>

    <el-form-item  label="手机号" prop="phoneNumber">
        <el-input clearable placeholder="请输入手机号" v-model="listQuery.phoneNumber" />
    </el-form-item>

    <el-form-item  label="所属学院" prop="college">
        <el-input clearable placeholder="请输入所属学院" v-model="listQuery.college" />
    </el-form-item>

    <el-form-item  label="专业" prop="major">
        <el-input clearable placeholder="请输入专业" v-model="listQuery.major" />
    </el-form-item>

    <el-form-item v-show="!props.params.roleEnumRoleEnumId1"  label="角色" prop="roleEnumRoleEnumId1">
        <base-select clearable v-model="listQuery.roleEnumRoleEnumId1" api="user_info_all.get_role_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item v-show="!props.params.userStatusEnumUserStatusEnumId1"  label="用户状态" prop="userStatusEnumUserStatusEnumId1">
        <base-select clearable v-model="listQuery.userStatusEnumUserStatusEnumId1" api="user_info_all.get_user_status_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="注册时间" prop="registrationDate">
        <el-date-picker placeholder="请输入注册时间" v-model="listQuery.registrationDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="user_info_all.page"
>
<el-table-column
width="55"
type="selection"
align="center"
>

</el-table-column>
<el-table-column
prop="userInfoId"
label="主键"
align="center"
>

</el-table-column>
<el-table-column
prop="name"
label="姓名"
align="center"
>

</el-table-column>
<el-table-column
prop="studentId"
label="学号"
align="center"
>

</el-table-column>
<el-table-column
prop="phoneNumber"
label="手机号"
align="center"
>

</el-table-column>
<el-table-column
prop="college"
label="所属学院"
align="center"
>

</el-table-column>
<el-table-column
prop="major"
label="专业"
align="center"
>

</el-table-column>
<el-table-column
prop="registrationDate"
label="注册时间"
align="center"
>
<template #default="scope">
{{ parseTime(scope.row.registrationDate,'{y}-{m}-{d} {h}:{i}:{s}') }}
</template>
</el-table-column>
<el-table-column
prop="roleName"
label="角色"
align="center"
>

</el-table-column>
<el-table-column
prop="statusName"
label="用户状态"
align="center"
>

</el-table-column>
<el-table-column
width="220"
fixed="right"
label="操作"
align="center"
>
<template #default="scope">
<base-info-btn
path="/user_info/detail"
:query="{userInfoId: scope.row.userInfoId}"
text="详情"
>
</base-info-btn>
<base-edit-btn
@ok="handleUpdate(scope.row)"
>
</base-edit-btn>
<base-delete-btn
@ok="handleDelete(scope.row)"
></base-delete-btn>
</template>
</el-table-column>
</base-table-p>
<base-dialog
v-if="dialogVisible"
:title="dialogTitleObj[dialogStatus]"
width="50%"
style="max-width: 600px;"
v-model="dialogVisible"
>
<el-form
ref='dataFormRef'
v-if='dialogStatus !== "detail"'
:model='form'
label-position='top'
:rules='{"studentId":[{"trigger":"blur","message":"学号不能为空","required":true}],"college":[{"trigger":"blur","message":"所属学院不能为空","required":true}],"password":[{"trigger":"blur","message":"密码不能为空","required":true}],"phoneNumber":[{"trigger":"blur","message":"手机号不能为空","required":true}],"major":[{"trigger":"blur","message":"专业不能为空","required":true}],"name":[{"trigger":"blur","message":"姓名不能为空","required":true}],"roleEnumRoleEnumId1":[{"trigger":"blur","message":"角色不能为空","required":true}],"userStatusEnumUserStatusEnumId1":[{"trigger":"blur","message":"用户状态不能为空","required":true}]}'
label-width='100px'
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

    <el-form-item v-show="!props.params.roleEnumRoleEnumId1"  label="角色" prop="roleEnumRoleEnumId1">
        <base-select clearable v-model="form.roleEnumRoleEnumId1" api="user_info_all.get_role_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item v-show="!props.params.userStatusEnumUserStatusEnumId1"  label="用户状态" prop="userStatusEnumUserStatusEnumId1">
        <base-select clearable v-model="form.userStatusEnumUserStatusEnumId1" api="user_info_all.get_user_status_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="注册时间" prop="registrationDate">
        <el-date-picker placeholder="请输入注册时间" v-model="form.registrationDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

</el-form>
<base-cell
v-else
label-width="100px"
>
<base-cell-item
label="主键"
>
{{ form.userInfoId }}
</base-cell-item>
<base-cell-item
label="姓名"
>
{{ form.name }}
</base-cell-item>
<base-cell-item
label="学号"
>
{{ form.studentId }}
</base-cell-item>
<base-cell-item
label="手机号"
>
{{ form.phoneNumber }}
</base-cell-item>
<base-cell-item
label="所属学院"
>
{{ form.college }}
</base-cell-item>
<base-cell-item
label="专业"
>
{{ form.major }}
</base-cell-item>
<base-cell-item
label="密码"
>
{{ form.password }}
</base-cell-item>
<base-cell-item
label="角色"
>
{{ form.roleEnumRoleEnumId1 }}
</base-cell-item>
<base-cell-item
label="用户状态"
>
{{ form.userStatusEnumUserStatusEnumId1 }}
</base-cell-item>
<base-cell-item
label="注册时间"
>
{{ form.registrationDate }}
</base-cell-item>
</base-cell>
<template #footer>
<el-button
@click="dialogVisible = false"
round
>取 消</el-button>
<el-button
@click="submitForm"
round
color="#5D5FEF"
type="primary"
>确 定</el-button>
</template>
</base-dialog>
</base-wrapper>
</template>

<script setup>
import {ElMessageBox} from "element-plus";

const { proxy } = getCurrentInstance();

const props = defineProps({
    params: { type: Object, default: () => ({}) },
})
let multipleSelection = ref([])
function handleSelectionChange(selection) {
    multipleSelection.value = selection.map(item => item.user_info_id);
}

// 计算属性，用于判断 params 的长度
const paramsLength = computed(() =>  Object.keys(props.params).length)

// 计算属性，判断 params 是否为空
const isParamsEmpty = computed(() => paramsLength.value === 0)

let listQuery = ref({});
let form = ref({});
let dialogVisible = ref(false);
let dialogStatus = ref('');
let dialogTitleObj = ref({ update: '编辑', add: '添加', detail: '详情'});
let rules = ref({});
if(isParamsEmpty.value){
  let routerQuery = proxy.$route.query;
  Object.assign(listQuery.value, routerQuery);
}
else{
  Object.assign(listQuery.value, props.params);
}


function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleDetail(row) {
  form.value = Object.assign({}, row);
  dialogStatus.value = 'detail';
  dialogVisible.value = true;
}
function handleAdd() {
  form.value = { ...props.params };
  dialogStatus.value = 'add';
  dialogVisible.value = true;
}
function handleUpdate(row) {
  form.value = Object.assign({}, row);
  dialogStatus.value = 'update';
  dialogVisible.value = true;
}
async function handleDelete(row) {
  if(import.meta.env.VITE_APP_MODEL === 'PREVIEW'){
    let res = await proxy.$api.table.delete({
        table_name: 'user_info',
        param: {
            userInfoId: row.userInfoId
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.user_info.delete(row.userInfoId);
    proxy.$modal.msgSuccess(res.message);
  }
  refreshTableData();
}

// 批量删除
async function handleBatchDelete() {
    if (multipleSelection.value.length === 0) return;

    try {
    await ElMessageBox.confirm(
        `确定要删除选中的 ${multipleSelection.value.length} 条数据吗？`,
        '提示',
        {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        }
    );
    const ids = multipleSelection.value
    let res;

    if (import.meta.env.VITE_APP_MODEL === 'PREVIEW') {
    res = await proxy.$api.table.deleteBatch({
        table_name: 'user_info',
        param: {
        ids: ids
        }
    });
    } else {
        res = await proxy.$api.user_info.deleteBatch(ids);
    }

    proxy.$modal.msgSuccess(res.message || "批量删除成功");
    refreshTableData();
    multipleSelection.value = [];
    } catch (error) {
        console.error("批量删除失败", error);
    }
}
async function handleExport() {
  try {
    await proxy.$api.user_info_all.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.user_info.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "downloadTemplate", "get")
  }catch (error) {
    console.error("导出失败", error)
  }
}
function submitForm() {
  if(dialogStatus.value == 'detail'){
      dialogVisible.value = false;
  }
  else {
      proxy.$refs.dataFormRef.validate(async (valid) => {
        if (valid) {
          if(import.meta.env.VITE_APP_MODEL === 'PREVIEW'){
            let res = await proxy.$api.table[dialogStatus.value]({
                table_name: 'user_info',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.user_info[dialogStatus.value](form.value);
            proxy.$modal.msgSuccess(res.message);
          }
          refreshTableData();
          dialogVisible.value = false;
        }
      });
  }
}

function choose(item){
    form.value = Object.assign({}, item);
    dialogVisible.value = true;
    dialogStatus.value = 'update';
}
</script>

<style lang="scss" scoped>
</style>