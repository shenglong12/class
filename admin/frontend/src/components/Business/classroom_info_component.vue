<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment="教室编号"
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="classroom_info.import"
table_name="classroom_info"
firstSearchData="roomNumber"
>
<template #collapse>
    <el-form-item v-show="!props.params.buildingEnumBuildingEnumId1"  label="教学楼" prop="buildingEnumBuildingEnumId1">
        <base-select clearable v-model="listQuery.buildingEnumBuildingEnumId1" api="classroom_info_all.get_building_enum_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item  label="教室编号" prop="roomNumber">
        <el-input clearable placeholder="请输入教室编号" v-model="listQuery.roomNumber" />
    </el-form-item>

    <el-form-item  label="二维码路径" prop="qrCodePath">
        <el-input clearable placeholder="请输入二维码路径" v-model="listQuery.qrCodePath" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="classroom_info_all.page"
>
<el-table-column
width="55"
type="selection"
align="center"
>

</el-table-column>
<el-table-column
prop="classroomInfoId"
label="主键"
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
prop="qrCodePath"
label="二维码路径"
align="center"
>

</el-table-column>
<el-table-column
prop="buildingName"
label="教学楼"
align="center"
>

</el-table-column>
<el-table-column
width="300"
fixed="right"
label="操作"
align="center"
>
<template #default="scope">
<base-info-btn
path="/classroom_info/detail"
:query="{classroomInfoId: scope.row.classroomInfoId}"
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
<el-button
@click="generateQRCode(scope.row)"
type="warning"
size="small"
round
>
生成二维码
</el-button>
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
:rules='{"roomNumber":[{"trigger":"blur","message":"教室编号不能为空","required":true}],"buildingEnumBuildingEnumId1":[{"trigger":"blur","message":"教学楼不能为空","required":true}]}'
label-width='100px'
>
    <el-form-item v-show="!props.params.buildingEnumBuildingEnumId1"  label="教学楼" prop="buildingEnumBuildingEnumId1">
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
v-else
label-width="100px"
>
<base-cell-item
label="主键"
>
{{ form.classroomInfoId }}
</base-cell-item>
<base-cell-item
label="教学楼"
>
{{ form.buildingEnumBuildingEnumId1 }}
</base-cell-item>
<base-cell-item
label="教室编号"
>
{{ form.roomNumber }}
</base-cell-item>
<base-cell-item
label="二维码路径"
>
{{ form.qrCodePath }}
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
import QRCode from 'qrcode';

const { proxy } = getCurrentInstance();

const props = defineProps({
    params: { type: Object, default: () => ({}) },
})
let multipleSelection = ref([])
function handleSelectionChange(selection) {
    multipleSelection.value = selection.map(item => item.classroomInfoId);
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
        table_name: 'classroom_info',
        param: {
            classroomInfoId: row.classroomInfoId
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.classroom_info.delete(row.classroomInfoId);
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

    res = await proxy.$api.classroom_info.deleteBatch(ids);

    proxy.$modal.msgSuccess(res.message || "批量删除成功");
    refreshTableData();
    multipleSelection.value = [];
    } catch (error) {
        console.error("批量删除失败", error);
      proxy.$modal.msgError("批量删除失败，请重试");
    }
}
async function handleExport() {
  try {
    await proxy.$api.classroom_info_all.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.classroom_info.downloadExcel({
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
                table_name: 'classroom_info',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.classroom_info[dialogStatus.value](form.value);
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

// 生成二维码
async function generateQRCode(row) {
  try {
    console.log('=== 二维码生成 V2：使用 uploadQrcode 新接口 ===');
    // 生成二维码内容（不再加随机串，重新生成时内容不变可覆盖旧文件）
    const qrContent = `/classroom/${row.classroomInfoId}`;
    
    // 创建Canvas元素
    const canvas = document.createElement('canvas');
    canvas.width = 200;
    canvas.height = 200;
    
    // 使用qrcode库生成二维码
    await QRCode.toCanvas(canvas, qrContent, {
      width: 200,
      margin: 1,
      color: {
        dark: '#000000',
        light: '#ffffff'
      }
    });
    
    // 转换为Blob对象
    canvas.toBlob(async (blob) => {
      if (blob) {
        // 上传二维码图片（文件名用教学楼+教室号，重新生成可直接覆盖）
        const formData = new FormData();
        const buildingName = row.buildingName || `building_${row.buildingEnumBuildingEnumId1}`;
        const fileName = `${buildingName}_${row.roomNumber}.png`;
        formData.append('file', blob, fileName);
        
        try {
          // 使用二维码专用上传接口（后端自动处理：存在则删除旧图，以教学楼_教室号命名）
          const uploadResult = await proxy.$api.common.uploadQrcode(formData, buildingName, row.roomNumber);
          if (!uploadResult || !uploadResult.data || !uploadResult.data.url) {
            proxy.$modal.msgError('上传二维码失败：服务器返回异常');
            return;
          }
          const qrPath = uploadResult.data.url;

          let res;
          
          if (import.meta.env.VITE_APP_MODEL === 'PREVIEW') {
            res = await proxy.$api.table.update({
              table_name: 'classroom_info',
              param: {
                classroomInfoId: row.classroomInfoId,
                buildingEnumBuildingEnumId1: row.buildingEnumBuildingEnumId1,
                roomNumber: row.roomNumber,
                qrCodePath: qrPath
              }
            });
          } else {
            res = await proxy.$api.classroom_info.update({
              classroomInfoId: row.classroomInfoId,
              buildingEnumBuildingEnumId1: row.buildingEnumBuildingEnumId1,
              roomNumber: row.roomNumber,
              qrCodePath: qrPath
            });
          }
          
          proxy.$modal.msgSuccess('二维码生成成功');
          refreshTableData();
        } catch (error) {
          console.error('上传二维码失败:', error);
          proxy.$modal.msgError('上传二维码失败，请检查后端是否启动');
        }
      } else {
        proxy.$modal.msgError('二维码图片生成失败');
      }
    }, 'image/png');
  } catch (error) {
    console.error('二维码生成失败:', error);
    proxy.$modal.msgError('二维码生成失败');
  }
}
</script>

<style lang="scss" scoped>
</style>