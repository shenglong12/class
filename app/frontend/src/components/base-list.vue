<template>
  <!-- 有数据时 -->
  <view v-if="pageRes.records.length" class="space-y-1 p-1.5 rounded-lg border border-gray-200 bg-gray-50  ">
    <!-- 列表卡片 -->
    <view
        v-for="(item, index) in pageRes.records"
        :key="index"
        @click="click_ok(item)"
        class="rounded-md shadow-sm border border-gray-200 overflow-hidden bg-white hover:shadow-md transition p-2"
    >
      <slot :item="item" :index="index + 1"/>
    </view>

    <!-- 分页 -->
    <view
        v-if="isPage"
        class="p-1 rounded-md border border-gray-200 bg-white shadow-sm flex items-center justify-center"
    >
      <uni-pagination
          show-icon="true"
          :total="pageRes.total"
          :pageSize="pageParams.pageSize"
          :current="pageParams.current"
          @change="handleCurrentChange"
      />
    </view>
  </view>

  <!-- 无数据时 -->
  <view class="flex flex-col items-center justify-center text-gray-400 py-12" v-else>
    <view class="text-xl font-semibold">{{ $t('list.empty_text') }}～</view>
  </view>

</template>
<script setup>
const {proxy} = getCurrentInstance();

// 分页响应数据
let pageRes = ref({current: 1, pages: 1, size: 10, total: 0, records: []});
let pageParams = ref({current: 1, pageSize: 10});
let searchParams = ref({})
const isPage = ref(false)


const props = defineProps({
  params: {
    type: Object,
    default: () => {
    },
  },
  data: {
    type: [String, Array],
    default: () => ''
  },
});



const emits = defineEmits(["click"]);
// 暴露方法
defineExpose({
  refresh,
});

onMounted(() => {
  refresh({});
});

function refresh(search) {
  isPage.value = !Array.isArray(props.data);
  if (!isPage.value) {
    pageRes.value.records = props.data;
  } else {
    searchParams.value = {}
    searchParams.value = {...search}

    pageRes.value = {
      current: 1,
      pages: 1,
      size: 10,
      total: 0,
      records: [],
    };
    pageParams.value.current = 1;

    getApiData();
  }
}

function click_ok(item) {
  emits("click", item);
}

function handleCurrentChange(val) {
  getApiData({page: val.current, limit: pageParams.value.pageSize});
}

async function getApiData(pageObj) {
  if (!props.data) {
    return;
  }

  let api_search_params = {...props.params, ...searchParams.value}

  if (pageObj) {
    // 从分页组件中拿到数据
    pageParams.value.current = pageObj.page;
    pageParams.value.pageSize = pageObj.limit;
  }
  let response = await apiMethod(api_search_params, pageParams);
  pageRes.value = response.data;
}

function apiMethod(params, headers) {
  let data = {...params};
  if (headers) {
    data = Object.assign(data, headers.value);
  }
  return proxy.$cf.table.page({
    table_name: props.data,
    param: data
  });
}

</script>