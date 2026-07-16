<template>

  <base-layout v-if="type==='admin'" display="flex" direction="c" :w_full="true">
    <base-layout display="flex" x="between" :w_full="true">
      <base-layout display="flex" class="m-l-20">
        <el-button round @click="eventAdd">
          <el-icon class="m-r-6">
            <CirclePlus/>
          </el-icon>
          添加
        </el-button>
        <el-button round @click="batchDelete">
          <el-icon class="m-r-6">
            <Delete/>
          </el-icon>
          批量删除
        </el-button>
        <el-button round @click="exportExcel">
          <el-icon class="m-r-6">
            <Notebook/>
          </el-icon>
          导出Excel
        </el-button>
        <el-button round @click="importExcel">
          <el-icon>
            <UploadFilled/>
          </el-icon>
          导入Excel/PDF
        </el-button>
        <el-button round @click="downloadTemplate">
          <el-icon>
            <DocumentChecked/>
          </el-icon>
          下载Excel模版
        </el-button>
<!--        <el-button v-if="table_name=='system_config' || table_name=='kf_system_config'" round @click="open_document">
          <el-icon>
            <DocumentChecked/>
          </el-icon>
          点击查看操作文档
        </el-button>-->
      </base-layout>

      <el-dialog :title="data.upload.title" v-model="data.upload.open" width="400px"
                 @close="handleDialogClose">
        <el-upload
            class="upload-demo"
            :auto-upload="false"
            :limit="1"
            ref="uploadRef"
            :accept="'.xls,.xlsx,.pdf'"
        :before-upload="beforeUpload"
            :http-request="httpRequest"
        >
          <el-icon class="el-icon&#45;&#45;upload">
            <upload-filled/>
          </el-icon>
          <div class="el-upload__text" style="color:cornflowerblue;margin-left: 5px">
            将文件拖到此处，或<em>点击上传</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              提示：仅允许导入“xls”、“xlsx”、“pdf”格式文件！<br/>
              <span style="color: red; font-weight: bold;">注意：PDF文件处理时间会比较长！！！</span>
            </div>
          </template>
        </el-upload>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitFile" :loading="loadingBut">确 定</el-button>
          <el-button @click="data.upload.open = false">取 消</el-button>
        </div>
      </el-dialog>

      <base-layout display="flex" class="m-r-20">
        <el-input v-if="firstSearchData" v-model="props.searchData[firstSearchData]" @keyup.native.enter="onSearch"
                  :placeholder="'请输入'+firstSearchComment">
          <template #suffix>
            <el-icon @click="onSearch">
              <Search/>
            </el-icon>
          </template>
        </el-input>

        <el-button round class="m-l-6" @click="toggleAdvanced">
          高级搜索
          <a style="margin-left: 8px">

            <el-icon v-if="advanced">
              <ArrowUp/>
            </el-icon>
            <el-icon v-else>
              <ArrowDown/>
            </el-icon>
          </a>
        </el-button>

      </base-layout>
    </base-layout>
    <base-layout class="m-t-20 m-l-20">
      <el-form :inline="true" :model="props.searchData" class="search-form-inline w-full">
        <el-collapse-transition>
          <div v-show="advanced">
            <slot name="collapse">
            </slot>
            <el-form-item>
              <el-button round color="#5D5FEF" @click="onSearch">
                <el-icon size="20" class="m-r-6">
                  <Search/>
                </el-icon>
                搜索
              </el-button>
            </el-form-item>
          </div>
        </el-collapse-transition>
      </el-form>
    </base-layout>
  </base-layout>

  <base-layout v-else display="flex" direction="c" :w_full="true">
    <base-layout display="flex" x="end" :w_full="true" class="p-x-20">
      <el-input v-if="firstSearchData" v-model="props.searchData[firstSearchData]" @keyup.native.enter="onSearch"
                :placeholder="'请输入'+firstSearchComment">
        <template #suffix>
          <el-icon @click="onSearch">
            <Search/>
          </el-icon>
        </template>
      </el-input>

      <el-button round class="m-l-6" @click="toggleAdvanced">
        <el-icon>
          <More/>
        </el-icon>
      </el-button>
    </base-layout>
    <base-layout class="m-t-20 m-l-20" display="flex">
      <el-button round @click="eventAdd">
        <el-icon class="m-r-6">
          <CirclePlus/>
        </el-icon>
        添加
      </el-button>

      <!--            <el-button round><el-icon class="m-r-6"><Operation /></el-icon> 筛选</el-button>-->

      <!--            <el-button round><el-icon class="m-r-6"><Notebook /></el-icon> 导出Excel</el-button>-->

    </base-layout>

    <base-drawer
        :show-close="false"
        direction="btt"
        title="高级搜索"
        v-model="advanced"
        size="80%"
    >
      <el-form label-position='top' :model="props.searchData">
        <slot name="collapse">
        </slot>
        <el-form-item>
          <el-button style="width: 100%;" round color="#5D5FEF" @click="onSearch">
            <el-icon size="20" class="m-r-6">
              <Search/>
            </el-icon>
            搜索
          </el-button>
        </el-form-item>
      </el-form>
    </base-drawer>
  </base-layout>

</template>

<script setup>

import {Delete, Search} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import {getCurrentInstance} from "vue";


const type = import.meta.env.VITE_APP_TYPE;
const {proxy} = getCurrentInstance()

const props = defineProps({
  searchItems: {type: Array, default: () => []},
  searchData: {
    type: Object, default: () => {
    }
  },
  firstSearchData: {type: String},
  firstSearchComment: {type: String, default: "要搜索的内容"},
  uploadExcelAPI: {type: String, default: ""},
  table_name: {type: String, default: ""},
});

const advanced = ref(false);

// 定义事件
const emit = defineEmits(["refreshTableData", 'addEvent']);

const onSearch = () => {
  emit("refreshTableData");
  advanced.value = false;
};

function toggleAdvanced() {
  advanced.value = !advanced.value
}

function eventAdd() {
  emit("addEvent");
}

function exportExcel() {
  emit("exportEvent");
}

function batchDelete() {
  emit("batchDeleteEvent");
}


function downloadTemplate() {
  emit("downloadEvent");
}

const loadingBut = ref(false)
const uploadRef = ref(null)
const data = reactive({
  form: {},
  upload: {
    // 是否显示弹出层（用户导入）
    open: false,
    // 弹出层标题（用户导入）
    title: "",
    // 是否禁用上传
    isUploading: false,
    // 是否更新已经存在的用户数据
    updateSupport: 0,
  }
})

/** 导入按钮 */
function importExcel() {
  data.upload.title = "导入";
  data.upload.open = true;
}

// 提交上传文件
function submitFile() {
  console.log(uploadRef.value, "uploadRef")
  uploadRef.value.submit();
  loadingBut.value = true
}

const beforeUpload = (file) => {
  // 可以在这里检查文件类型
  const isXls = file.name.endsWith(".xls");
  const isXlsx = file.name.endsWith(".xlsx");
  const isPdf = file.name.endsWith(".pdf");
  if (!isXls && !isXlsx && !isPdf) {
    proxy.$modal.msgError("只能上传 xls 或 xlsx 文件！");
    return false;
  }
  return true;
};

const handleDialogClose = () => {
  console.log("close")
  uploadRef.value.clearFiles();
  data.upload.open = false
}

const httpRequest = async item => {
  console.log("item", item)
  const formData = new FormData();
  formData.append('file', item.file);
  try {
    // let res = await proxy.$api.project_info.page({});
    const res = await props.uploadExcelAPI.split(".").reduce((acc, item) => acc[item], proxy.$api)(
        formData
    );
    if (res.code === 0) {
      ElMessage({
        showClose: true,
        message: '导入成功',
        type: 'success',
      })
      data.upload.open = false
      loadingBut.value = false
    }
  } catch (error) {
    loadingBut.value = false
    data.upload.open = false
    formData.delete(item.file.name)
  }



};

const  open_document=()=>{
  window.open('https://vvx03gck2p.feishu.cn/docx/JZscddNawo0aXPx2HZzcy2ybn0b?from=from_copylink', '_blank');
}

</script>