<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="background_image.import"
table_name="background_image"
firstSearchData
>
<template #collapse>
    <el-form-item v-show="!props.params.userInfoUserInfoId1"  label="上传管理员" prop="userInfoUserInfoId1">
        <base-select clearable v-model="listQuery.userInfoUserInfoId1" api="background_image_all.get_user_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="上传时间" prop="uploadTime">
        <el-date-picker placeholder="请输入上传时间" v-model="listQuery.uploadTime" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="background_image_all.page"
>
<el-table-column
width="55"
type="selection"
align="center"
>

</el-table-column>
<el-table-column
prop="backgroundImageId"
label="主键"
align="center"
>

</el-table-column>
<el-table-column
prop="imageFilesResourceKey"
label="背景图片"
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
prop="uploadTime"
label="上传时间"
align="center"
>
<template #default="scope">
{{ parseTime(scope.row.uploadTime,'{y}-{m}-{d} {h}:{i}:{s}') }}
</template>
</el-table-column>
<el-table-column
prop="name"
label="上传管理员"
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
path="/background_image/detail"
:query="{backgroundImageId: scope.row.backgroundImageId}"
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
:rules='{"userInfoUserInfoId1":[{"trigger":"blur","message":"上传管理员不能为空","required":true}]}'
label-width='100px'
>
    <el-form-item v-show="!props.params.userInfoUserInfoId1"  label="上传管理员" prop="userInfoUserInfoId1">
        <base-select clearable v-model="form.userInfoUserInfoId1" api="background_image_all.get_user_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="背景图片" prop="imageFilesResourceKey">
        <image-upload :limit="5" v-model="form.imageFiles" :type="true" />
    </el-form-item>

    <el-form-item   label="上传时间" prop="uploadTime">
        <el-date-picker placeholder="请输入上传时间" v-model="form.uploadTime" type="datetime"
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
{{ form.backgroundImageId }}
</base-cell-item>
<base-cell-item
label="上传管理员"
>
{{ form.userInfoUserInfoId1 }}
</base-cell-item>
<base-cell-item
label="背景图片"
>
{{ form.imageFilesResourceKey }}
</base-cell-item>
<base-cell-item
label="上传时间"
>
{{ form.uploadTime }}
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
    multipleSelection.value = selection.map(item => item.background_image_id);
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
        table_name: 'background_image',
        param: {
            backgroundImageId: row.backgroundImageId
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.background_image.delete(row.backgroundImageId);
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
        table_name: 'background_image',
        param: {
        ids: ids
        }
    });
    } else {
        res = await proxy.$api.background_image.deleteBatch(ids);
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
    await proxy.$api.background_image_all.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.background_image.downloadExcel({
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
                table_name: 'background_image',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.background_image[dialogStatus.value](form.value);
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