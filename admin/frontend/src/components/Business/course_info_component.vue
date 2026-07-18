<template>
    <base-wrapper
>
<base-header>
<search-operation
@batchDeleteEvent="handleBatchDelete"
@downloadEvent="downloadTemplate"
@exportEvent="handleExport"
firstSearchComment="课程名称"
:searchData="listQuery"
@refreshTableData="refreshTableData"
@addEvent="handleAdd"
uploadExcelAPI="course_info.import"
table_name="course_info"
firstSearchData="courseName"
>
<template #collapse>
    <el-form-item  label="课程名称" prop="courseName">
        <el-input clearable placeholder="请输入课程名称" v-model="listQuery.courseName" />
    </el-form-item>

    <el-form-item  label="学院" prop="college">
        <el-input clearable placeholder="请输入学院" v-model="listQuery.college" />
    </el-form-item>

    <el-form-item  label="专业" prop="major">
        <el-input clearable placeholder="请输入专业" v-model="listQuery.major" />
    </el-form-item>

    <el-form-item v-show="!props.params.classroomInfoClassroomInfoId1"  label="教室" prop="classroomInfoClassroomInfoId1">
        <base-select clearable v-model="listQuery.classroomInfoClassroomInfoId1" api="course_info_all.get_classroom_info_list" show_name=""></base-select>
    </el-form-item>

    <el-form-item   label="开始日期" prop="startDate">
        <el-date-picker placeholder="请输入开始日期" v-model="listQuery.startDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

    <el-form-item   label="结束日期" prop="endDate">
        <el-date-picker placeholder="请输入结束日期" v-model="listQuery.endDate" type="datetime"
         format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
    </el-form-item>

    <el-form-item  label="上课星期" prop="weekdays">
        <el-input clearable placeholder="请输入上课星期" v-model="listQuery.weekdays" />
    </el-form-item>

    <el-form-item  label="时间段" prop="timePeriod">
        <el-input clearable placeholder="请输入时间段" v-model="listQuery.timePeriod" />
    </el-form-item>

    <el-form-item  label="授课内容" prop="content">
        <el-input clearable placeholder="请输入授课内容" v-model="listQuery.content" />
    </el-form-item>

</template>
</search-operation>
</base-header>
<base-table-p
@selection-change="handleSelectionChange"
ref="baseTableRef"
:params="listQuery"
api="course_info_all.page"
>
<el-table-column
width="55"
type="selection"
align="center"
>

</el-table-column>
<el-table-column
prop="courseInfoId"
label="主键"
align="center"
>

</el-table-column>
<el-table-column
prop="courseName"
label="课程名称"
align="center"
>

</el-table-column>
<el-table-column
prop="college"
label="学院"
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
prop="startDate"
label="开始日期"
align="center"
>
<template #default="scope">
{{ parseTime(scope.row.startDate,'{y}-{m}-{d} {h}:{i}:{s}') }}
</template>
</el-table-column>
<el-table-column
prop="endDate"
label="结束日期"
align="center"
>
<template #default="scope">
{{ parseTime(scope.row.endDate,'{y}-{m}-{d} {h}:{i}:{s}') }}
</template>
</el-table-column>
<el-table-column
prop="weekdays"
label="上课星期"
align="center"
>

</el-table-column>
<el-table-column
prop="timePeriod"
label="时间段"
align="center"
>

</el-table-column>
<el-table-column
prop="content"
label="授课内容"
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
width="220"
fixed="right"
label="操作"
align="center"
>
<template #default="scope">
<base-info-btn
path="/course_info/detail"
:query="{courseInfoId: scope.row.courseInfoId}"
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
:rules='{"college":[{"trigger":"blur","message":"学院不能为空","required":true}],"courseName":[{"trigger":"blur","message":"课程名称不能为空","required":true}],"major":[{"trigger":"blur","message":"专业不能为空","required":true}],"endDate":[{"trigger":"blur","message":"结束日期不能为空","required":true}],"weekdays":[{"trigger":"blur","message":"上课星期不能为空","required":true}],"timePeriod":[{"trigger":"blur","message":"时间段不能为空","required":true}],"classroomInfoClassroomInfoId1":[{"trigger":"blur","message":"教室不能为空","required":true}],"startDate":[{"trigger":"blur","message":"开始日期不能为空","required":true}]}'
label-width='100px'
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

    <el-form-item v-show="!props.params.classroomInfoClassroomInfoId1"  label="教室" prop="classroomInfoClassroomInfoId1">
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
v-else
label-width="100px"
>
<base-cell-item
label="主键"
>
{{ form.courseInfoId }}
</base-cell-item>
<base-cell-item
label="课程名称"
>
{{ form.courseName }}
</base-cell-item>
<base-cell-item
label="学院"
>
{{ form.college }}
</base-cell-item>
<base-cell-item
label="专业"
>
{{ form.major }}
</base-cell-item>
<base-cell-item
label="教室"
>
{{ form.classroomInfoClassroomInfoId1 }}
</base-cell-item>
<base-cell-item
label="开始日期"
>
{{ form.startDate }}
</base-cell-item>
<base-cell-item
label="结束日期"
>
{{ form.endDate }}
</base-cell-item>
<base-cell-item
label="上课星期"
>
{{ form.weekdays }}
</base-cell-item>
<base-cell-item
label="时间段"
>
{{ form.timePeriod }}
</base-cell-item>
<base-cell-item
label="授课内容"
>
{{ form.content }}
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
    multipleSelection.value = selection.map(item => item.courseInfoId);
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
        table_name: 'course_info',
        param: {
            courseInfoId: row.courseInfoId
        }
    });
    proxy.$modal.msgSuccess(res.message);
  }
  else{
    let res = await proxy.$api.course_info.delete(row.courseInfoId);
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

    res = await proxy.$api.course_info.deleteBatch(ids);

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
    await proxy.$api.course_info_all.downloadExcel({
      ...listQuery.value
    }, `${new Date().getTime()}.xlsx`, "export", "post")
  }catch (error) {
    console.error("导出失败", error)
  }
}
async function downloadTemplate() {
  try {
    await proxy.$api.course_info.downloadExcel({
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
                table_name: 'course_info',
                param: form.value
            });
            proxy.$modal.msgSuccess(res.message);
          }
          else{
            let res = await proxy.$api.course_info[dialogStatus.value](form.value);
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