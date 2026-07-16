<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment="教学楼名称"
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="scan_record.import"
table_name="scan_record"
firstSearchData="buildingName"
>
<template #collapse>
    <el-form-item v-show="!props.params.userInfoUserInfoId1"  label="用户ID" prop="userInfoUserInfoId1">
        <base-select clearable v-model="listQuery.userInfoUserInfoId1" api="scan_record_all.get_user_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="教学楼名称" prop="buildingName">
        <el-input clearable placeholder="请输入教学楼名称" v-model="listQuery.buildingName" />
    </el-form-item>

    <el-form-item  label="教室编号" prop="roomNumber">
        <el-input clearable placeholder="请输入教室编号" v-model="listQuery.roomNumber" />
    </el-form-item>

    <el-form-item   label="扫码时间" prop="scanTime">
        <el-date-picker placeholder="请输入扫码时间" v-model="listQuery.scanTime" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="scan_record_all.page"
>
<el-table-column
width="55"
type="selection"
align="center"
>

</el-table-column>
<el-table-column
prop="scanId"
label="主键ID"
align="center"
>

</el-table-column>
<el-table-column
prop="userInfoUserInfoId1"
label="用户ID"
align="center"
>

</el-table-column>
<el-table-column
prop="buildingName"
label="教学楼名称"
align="center"
>

</el-table-column>
<el-table-column
prop="roomNumber"
label="教室编号"
align="center"
>

</el-table-column>
<el-table-column
prop="scanTime"
label="扫码时间"
align="center"
>
<template #default="scope">
{{ parseTime(scope.row.scanTime,'{y}-{m}-{d} {h}:{i}:{s}') }}
</template>
</el-table-column>
<el-table-column
width="220"
fixed="right"
label="操作"
align="center"
>
<template #default="scope">
<base-info-btn
path="/scan_record/detail"
:query="{scanId: scope.row.scanId}"
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
:rules='{"scanTime":[{"trigger":"blur","message":"扫码时间不能为空","required":true}]}'
label-width='100px'
>
    <el-form-item v-show="!props.params.userInfoUserInfoId1"  label="用户ID" prop="userInfoUserInfoId1">
        <base-select clearable v-model="form.userInfoUserInfoId1" api="scan_record_all.get_user_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="教学楼名称" prop="buildingName">
        <el-input clearable placeholder="请输入教学楼名称" v-model="form.buildingName" />
    </el-form-item>

    <el-form-item  label="教室编号" prop="roomNumber">
        <el-input clearable placeholder="请输入教室编号" v-model="form.roomNumber" />
    </el-form-item>

    <el-form-item   label="扫码时间" prop="scanTime">
        <el-date-picker placeholder="请输入扫码时间" v-model="form.scanTime" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

</el-form>
<base-cell
v-else
label-width="100px"
>
<base-cell-item
label="主键ID"
>
{{ form.scanId }}
</base-cell-item>
<base-cell-item
label="用户ID"
>
{{ form.userInfoUserInfoId1 }}
</base-cell-item>
<base-cell-item
label="教学楼名称"
>
{{ form.buildingName }}
</base-cell-item>
<base-cell-item
label="教室编号"
>
{{ form.roomNumber }}
</base-cell-item>
<base-cell-item
label="扫码时间"
>
{{ form.scanTime }}
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
    multipleSelection.value = selection.map(item => item.scan_id);
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
        table_name: 'scan_record',
        param: {
            scanId: row.scanId
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.scan_record.delete(row.scanId);
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
        table_name: 'scan_record',
        param: {
        ids: ids
        }
    });
    } else {
        res = await proxy.$api.scan_record.deleteBatch(ids);
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
    await proxy.$api.scan_record_all.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.scan_record.downloadExcel({
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
                table_name: 'scan_record',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.scan_record[dialogStatus.value](form.value);
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