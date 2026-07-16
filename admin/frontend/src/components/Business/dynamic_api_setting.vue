<template>
  <base-wrapper>

    <base-table-p
        ref="baseTableRef"
        :params="listQuery"
        api="dynamic_api_setting.page"
        preview_api_manager="dynamic_api_setting_p.page"
    >


      <el-table-column prop="id" label="主键" align="center"/>
      <el-table-column prop="keyName" label="API标识" align="center"/>
      <el-table-column prop="description" label="API描述" align="center"/>

      <el-table-column prop="protocol" label="协议" align="center"/>
      <el-table-column prop="method" label="API请求方法" align="center"/>
      <el-table-column prop="url" label="API请求地址" align="center"/>
      <el-table-column prop="url" label="token" align="center">
        <template #default="scope">
          <span>******</span>
        </template>
      </el-table-column>
      <el-table-column prop="header" label="API请求头" align="center"/>
      <el-table-column prop="bodyType" label="API请求体类型" align="center"/>
      <el-table-column prop="bodyTemplate" label="API请求体模版" align="center"/>

      <el-table-column width="100" fixed="right" label="操作" align="center">
        <template #default="scope">
          <base-edit-btn
              @ok="handleUpdate(scope.row)"
          >
          </base-edit-btn>
        </template>
      </el-table-column>

    </base-table-p>

    <base-dialog
        :title="dialogTitleObj[dialogStatus]"
        width="50%"
        style="max-width: 600px;"
        v-model="dialogVisible"
        @close="closeDialog"
    >
      <el-form
          ref='dataFormRef'
          v-if='dialogStatus !== "detail"'
          :model='form'
          label-position='top'
          label-width='100px'
      >
        <el-form-item label="API请求方法" prop="method">
          <el-input placeholder="API请求方法" v-model="form.method"/>
        </el-form-item>


        <el-form-item label="API请求地址" prop="url">
          <el-input placeholder="API请求地址" v-model="form.url"/>
        </el-form-item>

        <el-form-item label="token" prop="url">
          <el-input placeholder="token" v-model="form.token"/>
        </el-form-item>

        <el-form-item label="API请求头" prop="header">
          <el-input placeholder="注意是JSON格式" v-model="form.header"/>
        </el-form-item>

        <el-form-item label="API请求体类型" prop="bodyType">
          <el-input placeholder="API请求体类型" v-model="form.bodyType"/>
        </el-form-item>

        <el-form-item label="API请求体类数据模版" prop="bodyType">
          <el-input placeholder="注意是JSON格式" v-model="form.bodyTemplate"/>
        </el-form-item>

      </el-form>

      <template #footer>
        <el-button
            @click="closeDialog"
            round
        >取 消
        </el-button>
        <el-button
            @click="submitForm"
            round
            color="#5D5FEF"
            type="primary"
        >确 定
        </el-button>
      </template>
    </base-dialog>

  </base-wrapper>
</template>
<script setup>
const {proxy} = getCurrentInstance();

let listQuery = ref({});
let form = ref({});
let dialogVisible = ref(false);
let dialogStatus = ref('');
let dialogTitleObj = ref({update: '编辑', add: '添加', detail: '详情'});

const closeDialog = () => {
  dialogVisible.value = false
}

function handleUpdate(row) {
  form.value = Object.assign({}, row);
  dialogStatus.value = 'update';
  dialogVisible.value = true;
}

async function submitForm() {
  if (dialogStatus.value == 'detail') {
    dialogVisible.value = false;
  } else {
    let res = await proxy.$api.dynamic_api_setting_p[dialogStatus.value](form.value);
    proxy.$modal.msgSuccess(res.message);
    refreshTableData();
    dialogVisible.value = false;
  }
}

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}

</script>
