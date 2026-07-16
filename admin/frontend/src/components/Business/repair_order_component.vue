<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment="报修内容"
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="repair_order.import"
table_name="repair_order"
firstSearchData="content"
>
<template #collapse>
    <el-form-item v-show="!props.params.userInfoUserInfoId1"  label="报修用户" prop="userInfoUserInfoId1">
        <base-select clearable v-model="listQuery.userInfoUserInfoId1" api="repair_order_all.get_user_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item v-show="!props.params.classroomInfoClassroomInfoId1"  label="教室" prop="classroomInfoClassroomInfoId1">
        <base-select clearable v-model="listQuery.classroomInfoClassroomInfoId1" api="repair_order_all.get_classroom_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="报修内容" prop="content">
        <el-input clearable placeholder="请输入报修内容" v-model="listQuery.content" />
    </el-form-item>

    <el-form-item v-show="!props.params.repairStatusEnumRepairStatusEnumId1"  label="报修状态" prop="repairStatusEnumRepairStatusEnumId1">
        <base-select clearable v-model="listQuery.repairStatusEnumRepairStatusEnumId1" api="repair_order_all.get_repair_status_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="处理反馈" prop="feedback">
        <el-input clearable placeholder="请输入处理反馈" v-model="listQuery.feedback" />
    </el-form-item>

    <el-form-item   label="创建时间" prop="createTime">
        <el-date-picker placeholder="请输入创建时间" v-model="listQuery.createTime" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="repair_order_all.page"
>
<el-table-column
width="55"
type="selection"
align="center"
>

</el-table-column>
<el-table-column
prop="repairOrderId"
label="主键"
align="center"
>

</el-table-column>
<el-table-column
prop="content"
label="报修内容"
align="center"
>

</el-table-column>
<el-table-column
prop="feedback"
label="处理反馈"
align="center"
>

</el-table-column>
<el-table-column
prop="createTime"
label="创建时间"
align="center"
>
<template #default="scope">
{{ parseTime(scope.row.createTime,'{y}-{m}-{d} {h}:{i}:{s}') }}
</template>
</el-table-column>
<el-table-column
prop="imageFilesResourceKey"
label="报修图片"
align="center"
>
<template #default="scope">
<image-preview
:height="50"
:src="scope.row.imageFiles.map(item => item.url).join(',')"
:width="50"
/>
</template>
</el-table-column>
<el-table-column
prop="name"
label="报修用户"
align="center"
>

</el-table-column>
<el-table-column
prop="roomNumber"
label="教室"
align="center"
>

</el-table-column>
<el-table-column
prop="statusName"
label="报修状态"
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
path="/repair_order/detail"
:query="{repairOrderId: scope.row.repairOrderId}"
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
:rules='{"userInfoUserInfoId1":[{"trigger":"blur","message":"报修用户不能为空","required":true}],"classroomInfoClassroomInfoId1":[{"trigger":"blur","message":"教室不能为空","required":true}],"content":[{"trigger":"blur","message":"报修内容不能为空","required":true}],"repairStatusEnumRepairStatusEnumId1":[{"trigger":"blur","message":"报修状态不能为空","required":true}]}'
label-width='100px'
>
    <el-form-item v-show="!props.params.userInfoUserInfoId1"  label="报修用户" prop="userInfoUserInfoId1">
        <base-select clearable v-model="form.userInfoUserInfoId1" api="repair_order_all.get_user_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item v-show="!props.params.classroomInfoClassroomInfoId1"  label="教室" prop="classroomInfoClassroomInfoId1">
        <base-select clearable v-model="form.classroomInfoClassroomInfoId1" api="repair_order_all.get_classroom_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="报修内容" prop="content">
        <el-input clearable placeholder="请输入报修内容" v-model="form.content" />
    </el-form-item>

    <el-form-item v-show="!props.params.repairStatusEnumRepairStatusEnumId1"  label="报修状态" prop="repairStatusEnumRepairStatusEnumId1">
        <base-select clearable v-model="form.repairStatusEnumRepairStatusEnumId1" api="repair_order_all.get_repair_status_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="处理反馈" prop="feedback">
        <el-input clearable placeholder="请输入处理反馈" v-model="form.feedback" />
    </el-form-item>

    <el-form-item  label="报修图片" prop="imageFilesResourceKey">
        <image-upload :limit="5" v-model="form.imageFiles" :type="true" />
    </el-form-item>

</el-form>
<base-cell
v-else
label-width="100px"
>
<base-cell-item
label="主键"
>
{{ form.repairOrderId }}
</base-cell-item>
<base-cell-item
label="报修用户"
>
{{ form.userInfoUserInfoId1 }}
</base-cell-item>
<base-cell-item
label="教室"
>
{{ form.classroomInfoClassroomInfoId1 }}
</base-cell-item>
<base-cell-item
label="报修内容"
>
{{ form.content }}
</base-cell-item>
<base-cell-item
label="报修状态"
>
{{ form.repairStatusEnumRepairStatusEnumId1 }}
</base-cell-item>
<base-cell-item
label="处理反馈"
>
{{ form.feedback }}
</base-cell-item>
<base-cell-item
label="报修图片"
>
{{ form.imageFilesResourceKey }}
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
    multipleSelection.value = selection.map(item => item.repair_order_id);
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
        table_name: 'repair_order',
        param: {
            repairOrderId: row.repairOrderId
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.repair_order.delete(row.repairOrderId);
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
        table_name: 'repair_order',
        param: {
        ids: ids
        }
    });
    } else {
        res = await proxy.$api.repair_order.deleteBatch(ids);
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
    await proxy.$api.repair_order_all.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.repair_order.downloadExcel({
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
                table_name: 'repair_order',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.repair_order[dialogStatus.value](form.value);
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