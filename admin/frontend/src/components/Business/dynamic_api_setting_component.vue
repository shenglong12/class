<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment="api名称"
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="dynamic_api_setting.import"
table_name="dynamic_api_setting"
firstSearchData="keyName"
>
<template #collapse>
    <el-form-item  label="api名称" prop="keyName">
        <el-input clearable placeholder="请输入api名称" v-model="listQuery.keyName" />
    </el-form-item>

    <el-form-item  label="描述" prop="description">
        <el-input clearable placeholder="请输入描述" v-model="listQuery.description" />
    </el-form-item>

    <el-form-item  label="地址" prop="url">
        <el-input clearable placeholder="请输入地址" v-model="listQuery.url" />
    </el-form-item>

    <el-form-item  label="令牌" prop="token">
        <el-input clearable placeholder="请输入令牌" v-model="listQuery.token" />
    </el-form-item>

    <el-form-item  label="APPID" prop="appId">
        <el-input clearable placeholder="请输入APPID" v-model="listQuery.appId" />
    </el-form-item>

    <el-form-item  label="令牌Key" prop="apiKey">
        <el-input clearable placeholder="请输入令牌Key" v-model="listQuery.apiKey" />
    </el-form-item>

    <el-form-item  label="令牌密钥" prop="apiSecret">
        <el-input clearable placeholder="请输入令牌密钥" v-model="listQuery.apiSecret" />
    </el-form-item>

    <el-form-item  label="方法" prop="method">
        <el-input clearable placeholder="请输入方法" v-model="listQuery.method" />
    </el-form-item>

    <el-form-item  label="请求体类型" prop="bodyType">
        <el-input clearable placeholder="请输入请求体类型" v-model="listQuery.bodyType" />
    </el-form-item>

    <el-form-item  label="请求体模版" prop="bodyTemplate">
        <el-input clearable placeholder="请输入请求体模版" v-model="listQuery.bodyTemplate" />
    </el-form-item>

    <el-form-item  label="请求头模版" prop="header">
        <el-input clearable placeholder="请输入请求头模版" v-model="listQuery.header" />
    </el-form-item>

    <el-form-item  label="授权类型" prop="authType">
        <el-input clearable placeholder="请输入授权类型" v-model="listQuery.authType" />
    </el-form-item>

    <el-form-item  label="请求协议" prop="protocol">
        <el-input clearable placeholder="请输入请求协议" v-model="listQuery.protocol" />
    </el-form-item>

    <el-form-item  label="数据获取路径" prop="dataPath">
        <el-input clearable placeholder="请输入数据获取路径" v-model="listQuery.dataPath" />
    </el-form-item>

    <el-form-item  label="数据类型" prop="dataType">
        <el-input clearable placeholder="请输入数据类型" v-model="listQuery.dataType" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="dynamic_api_setting.page"
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
prop="keyName"
label="api名称"
align="center"
>

</el-table-column>
<el-table-column
prop="description"
label="描述"
align="center"
>

</el-table-column>
<el-table-column
prop="url"
label="地址"
align="center"
>

</el-table-column>
<el-table-column
prop="token"
label="令牌"
align="center"
>

</el-table-column>
<el-table-column
prop="appId"
label="APPID"
align="center"
>

</el-table-column>
<el-table-column
prop="apiKey"
label="令牌Key"
align="center"
>

</el-table-column>
<el-table-column
prop="apiSecret"
label="令牌密钥"
align="center"
>

</el-table-column>
<el-table-column
prop="method"
label="方法"
align="center"
>

</el-table-column>
<el-table-column
prop="bodyType"
label="请求体类型"
align="center"
>

</el-table-column>
<el-table-column
prop="bodyTemplate"
label="请求体模版"
align="center"
>

</el-table-column>
<el-table-column
prop="header"
label="请求头模版"
align="center"
>

</el-table-column>
<el-table-column
prop="authType"
label="授权类型"
align="center"
>

</el-table-column>
<el-table-column
prop="protocol"
label="请求协议"
align="center"
>

</el-table-column>
<el-table-column
prop="dataPath"
label="数据获取路径"
align="center"
>

</el-table-column>
<el-table-column
prop="dataType"
label="数据类型"
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
path="/dynamic_api_setting/detail"
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
:rules='{}'
label-width='100px'
>
    <el-form-item  label="api名称" prop="keyName">
        <el-input clearable placeholder="请输入api名称" v-model="form.keyName" />
    </el-form-item>

    <el-form-item  label="描述" prop="description">
        <el-input clearable placeholder="请输入描述" v-model="form.description" />
    </el-form-item>

    <el-form-item  label="地址" prop="url">
        <el-input clearable placeholder="请输入地址" v-model="form.url" />
    </el-form-item>

    <el-form-item  label="令牌" prop="token">
        <el-input clearable placeholder="请输入令牌" v-model="form.token" />
    </el-form-item>

    <el-form-item  label="APPID" prop="appId">
        <el-input clearable placeholder="请输入APPID" v-model="form.appId" />
    </el-form-item>

    <el-form-item  label="令牌Key" prop="apiKey">
        <el-input clearable placeholder="请输入令牌Key" v-model="form.apiKey" />
    </el-form-item>

    <el-form-item  label="令牌密钥" prop="apiSecret">
        <el-input clearable placeholder="请输入令牌密钥" v-model="form.apiSecret" />
    </el-form-item>

    <el-form-item  label="方法" prop="method">
        <el-input clearable placeholder="请输入方法" v-model="form.method" />
    </el-form-item>

    <el-form-item  label="请求体类型" prop="bodyType">
        <el-input clearable placeholder="请输入请求体类型" v-model="form.bodyType" />
    </el-form-item>

    <el-form-item  label="请求体模版" prop="bodyTemplate">
        <el-input clearable placeholder="请输入请求体模版" v-model="form.bodyTemplate" />
    </el-form-item>

    <el-form-item  label="请求头模版" prop="header">
        <el-input clearable placeholder="请输入请求头模版" v-model="form.header" />
    </el-form-item>

    <el-form-item  label="授权类型" prop="authType">
        <el-input clearable placeholder="请输入授权类型" v-model="form.authType" />
    </el-form-item>

    <el-form-item  label="请求协议" prop="protocol">
        <el-input clearable placeholder="请输入请求协议" v-model="form.protocol" />
    </el-form-item>

    <el-form-item  label="数据获取路径" prop="dataPath">
        <el-input clearable placeholder="请输入数据获取路径" v-model="form.dataPath" />
    </el-form-item>

    <el-form-item  label="数据类型" prop="dataType">
        <el-input clearable placeholder="请输入数据类型" v-model="form.dataType" />
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
label="api名称"
>
{{ form.keyName }}
</base-cell-item>
<base-cell-item
label="描述"
>
{{ form.description }}
</base-cell-item>
<base-cell-item
label="地址"
>
{{ form.url }}
</base-cell-item>
<base-cell-item
label="令牌"
>
{{ form.token }}
</base-cell-item>
<base-cell-item
label="APPID"
>
{{ form.appId }}
</base-cell-item>
<base-cell-item
label="令牌Key"
>
{{ form.apiKey }}
</base-cell-item>
<base-cell-item
label="令牌密钥"
>
{{ form.apiSecret }}
</base-cell-item>
<base-cell-item
label="方法"
>
{{ form.method }}
</base-cell-item>
<base-cell-item
label="请求体类型"
>
{{ form.bodyType }}
</base-cell-item>
<base-cell-item
label="请求体模版"
>
{{ form.bodyTemplate }}
</base-cell-item>
<base-cell-item
label="请求头模版"
>
{{ form.header }}
</base-cell-item>
<base-cell-item
label="授权类型"
>
{{ form.authType }}
</base-cell-item>
<base-cell-item
label="请求协议"
>
{{ form.protocol }}
</base-cell-item>
<base-cell-item
label="数据获取路径"
>
{{ form.dataPath }}
</base-cell-item>
<base-cell-item
label="数据类型"
>
{{ form.dataType }}
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
        table_name: 'dynamic_api_setting',
        param: {
            id: row.id
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.dynamic_api_setting.delete(row.id);
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
        table_name: 'dynamic_api_setting',
        param: {
        ids: ids
        }
    });
    } else {
        res = await proxy.$api.dynamic_api_setting.deleteBatch(ids);
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
    await proxy.$api.dynamic_api_setting.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.dynamic_api_setting.downloadExcel({
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
                table_name: 'dynamic_api_setting',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.dynamic_api_setting[dialogStatus.value](form.value);
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