<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment="配置项名称"
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="kf_system_config.import"
table_name="kf_system_config"
firstSearchData="name"
>
<template #collapse>
    <el-form-item  label="配置项名称" prop="name">
        <el-input clearable placeholder="请输入配置项名称" v-model="listQuery.name" />
    </el-form-item>

    <el-form-item  label="中文名称" prop="chineseName">
        <el-input clearable placeholder="请输入中文名称" v-model="listQuery.chineseName" />
    </el-form-item>

    <el-form-item  label="配置描述" prop="description">
        <el-input clearable placeholder="请输入配置描述" v-model="listQuery.description" />
    </el-form-item>

    <el-form-item  label="配置值" prop="content">
        <el-input clearable placeholder="请输入配置值" v-model="listQuery.content" />
    </el-form-item>

    <el-form-item  label="备注" prop="remark">
        <el-input clearable placeholder="请输入备注" v-model="listQuery.remark" />
    </el-form-item>

    <el-form-item  label="配置类型" prop="type">
        <el-input clearable placeholder="请输入配置类型" v-model="listQuery.type" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="kf_system_config.page"
>
<el-table-column
width="55"
type="selection"
align="center"
>

</el-table-column>
<el-table-column
prop="id"
label="主键"
align="center"
>

</el-table-column>
<el-table-column
prop="name"
label="配置项名称"
align="center"
>

</el-table-column>
<el-table-column
prop="chineseName"
label="中文名称"
align="center"
>

</el-table-column>
<el-table-column
prop="description"
label="配置描述"
align="center"
>

</el-table-column>
<el-table-column
prop="content"
label="配置值"
align="center"
>

</el-table-column>
<el-table-column
prop="remark"
label="备注"
align="center"
>

</el-table-column>
<el-table-column
prop="type"
label="配置类型"
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
path="/kf_system_config/detail"
:query="{id: scope.row.id}"
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
:rules='{"name":[{"trigger":"blur","message":"配置项名称不能为空","required":true}]}'
label-width='100px'
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
<base-cell
v-else
label-width="100px"
>
<base-cell-item
label="主键"
>
{{ form.id }}
</base-cell-item>
<base-cell-item
label="配置项名称"
>
{{ form.name }}
</base-cell-item>
<base-cell-item
label="中文名称"
>
{{ form.chineseName }}
</base-cell-item>
<base-cell-item
label="配置描述"
>
{{ form.description }}
</base-cell-item>
<base-cell-item
label="配置值"
>
{{ form.content }}
</base-cell-item>
<base-cell-item
label="备注"
>
{{ form.remark }}
</base-cell-item>
<base-cell-item
label="配置类型"
>
{{ form.type }}
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
    multipleSelection.value = selection.map(item => item.id);
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
        table_name: 'kf_system_config',
        param: {
            id: row.id
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.kf_system_config.delete(row.id);
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
        table_name: 'kf_system_config',
        param: {
        ids: ids
        }
    });
    } else {
        res = await proxy.$api.kf_system_config.deleteBatch(ids);
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
    await proxy.$api.kf_system_config.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.kf_system_config.downloadExcel({
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
                table_name: 'kf_system_config',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.kf_system_config[dialogStatus.value](form.value);
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