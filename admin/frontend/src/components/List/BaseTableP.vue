<template>
  <div class="m-x-20">
    <slot name="custom-header"/>
    <el-table
        ref="baseTableRef"
        v-loading="isLoading"
        v-bind="$attrs"
        :data="isPage ? pageRes.records : tableDataList"
        style="width: 100%;"
        highlight-current-row
        :border="true"
    >
      <el-table-column v-if="selection" type="selection" :width="55"></el-table-column>
      <template v-if="indexCode">
        <el-table-column type="index" label="序号" width="60px"></el-table-column>
      </template>
      <slot/>

      <template v-for="item in columns" :key="item">
        <el-table-column v-if="item.slotName" v-bind="item" :width="item.width">
          <template #scope>
            <slot :name="item.slotName" v-bind="scope"></slot>
          </template>
        </el-table-column>
        <el-table-column v-else v-bind="item"></el-table-column>
      </template>

    </el-table>

    <div v-if="isPage" class="flex-end-center m-t-10">

      <el-pagination
          v-if="pageRes && pageRes.total > 0"
          :current-page="pageParams.current"
          :page-size="pageParams.pageSize"
          :total="pageRes.total == null ? 0 : pageRes.total"
          :small="true"
          :page-sizes="[10, 20, 30, 40, 50]"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper, slot"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>

    </div>

  </div>
</template>
<script setup>

const {proxy} = getCurrentInstance();
const props = defineProps({
  indexCode: {type: Boolean, default: false},
  selection: {type: Boolean, default: false},
  // 动态渲染的表格字段 :columns="[{ prop: 'name', label: '名称', width: '100' }]" =>  <el-table-column prop="name" label="名称" width="100" />
  columns: {type: Array, default: () => []},
  params: {
    type: Object, default: () => {
    }
  },
  api: {type: String, default: ''},
  api_manager: {type: String, default: ''},
  preview_api_manager: {type: String, default: ''},
  data: {type: Array, default: () => []},
  //是否分页
  isPage: {type: Boolean, default: () => true},
});


let isLoading = ref(true);
let pageParams = ref({current: 1, pageSize: 10});
// 分页响应数据
let pageRes = ref({current: 1, pages: 1, size: 10, total: 0, records: []});
let tableDataList = ref([]); // 列表响应数据

// 暴露方法
defineExpose({
  refresh,
});

onMounted(() => {
  refresh();
});

watch(
    () => props.data,
    (newValue, oldValue) => {
      // console.log('监听器执行了... ', newValue, oldValue);
      // pageRes = [];
      if (!props.isPage || (newValue && newValue.length > 0)) {
        tableDataList.value = newValue;
      }
    },
    {
      immediate: true, // 初始化执行一次
      deep: true, // 深度监听
    },
);


// 刷新
function refresh(resetPage = false) {
  isLoading.value = true;
  if (props.data && props.data.length > 0) {
    // 情况1：走父组件传值过来
    tableDataList.value = props.data;
  } else {
    // 情况2：走api接口数据
    if (props.isPage) {
      if (resetPage) {
        pageRes.value = {
          current: 1,
          pages: 1,
          size: 10,
          total: 0,
          records: [],
        };
        pageParams.value.current = 1;
      }
    }
    getApiData();
  }
  isLoading.value = false;
}

// 获取接口数据
async function getApiData(pageObj) {
  if (!props.api) {
    return;
  }

  isLoading.value = true;

  if (props.isPage) {
    // 处理分页参数
    if (pageObj) {
      // 从分页组件中拿到数据
      pageParams.value.current = pageObj.page;
      pageParams.value.pageSize = pageObj.limit;
    }
    let response = await apiMethod(props.params, pageParams);

    pageRes.value = response.data;
  } else {
    let response = await apiMethod(props.params);
    tableDataList.value = response.data;
  }

  isLoading.value = false;
}

function apiMethod(params, headers) {
  let data = {...params};
  if (headers) {
    data = Object.assign(data, headers.value);
  }
  if (import.meta.env.VITE_APP_MODEL === 'PREVIEW') {
    if (props.preview_api_manager) {
      return props.preview_api_manager.split('.').reduce((acc, item) => acc[item], proxy.$api)(data);
    } else {
      const [tableName, methodName] = props.api.split('.');
      return proxy.$api.table[methodName]({
        table_name: tableName,
        param: data
      });
    }
  } else {
    if (props.api_manager) {
      return props.api_manager.split('.').reduce((acc, item) => acc[item], proxy.$api)(data);
    } else {
      return props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(data);
    }
  }
}


// 分页组件参数变更时触发
function handleCurrentChange(val) {
  getApiData({page: val, limit: pageParams.value.pageSize});
}


function handleSizeChange(val) {
  getApiData({page: pageParams.value.current, limit: val});
}

</script>

<style lang="scss" scoped>

.el-table {
  :deep(.cell) {
    padding: 0;
  }
}

</style>