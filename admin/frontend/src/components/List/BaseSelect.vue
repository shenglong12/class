<template>
    <el-select v-bind="$attrs">
        <el-option
            v-for="item in select_value"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            />
    </el-select>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  params: { type: Object, default: () => {} },
  api: { type: String, default: '' },
  show_name: { type: String, default: '' },
});

const select_value = ref([]);

// onMounted(() => {
//   refresh();
// });

watch(
  () => props.api,
  (newValue, oldValue) => {
    // console.log('监听器执行了... ', newValue, oldValue);
    // pageRes = [];
    // if (!props.isPage || (newValue && newValue.length > 0)) {
    //   tableDataList.value = newValue;
    // }
    if(newValue){
      refresh();
    }
  },
  {
    immediate: true, // 初始化执行一次
    deep: true, // 深度监听
  },
);

// 刷新
function refresh() {
  getApiData();
}

// 获取接口数据
async function getApiData() {
  if (!props.api) {
    return;
  }

  let response = await apiMethod(props.params);
  select_value.value = response.data;
}

function apiMethod(params, headers) {
  // eg: proxy.$api.sys_user.save(xx);
  let data = {...params};
  if(headers){
    data = Object.assign(data, headers.value);
  }
  if(import.meta.env.VITE_APP_MODEL === 'PREVIEW' && !props.api.startsWith("login_manger")){
    if(props.show_name){
      data["select_show_name"] = props.show_name;
    }
    const [tableName, methodName] = props.api.split('.');
    return proxy.$api.table[methodName]({
      table_name: tableName,
      param: data
    });
  }
  else {
    return props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(data);
  }
}


</script>