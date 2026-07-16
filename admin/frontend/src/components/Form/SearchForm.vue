<template>
  <base-layout display="flex" x="between" y="end">
  <el-form :inline="true" :model="props.searchData" class="search-form-inline w-full">
    <slot />
    
    <el-form-item
      v-for="item in props.searchItems"
      :key="item.props"
      :label="item.label"
    >
      <el-input
        clearable
        :placeholder="`请输入${item.label}`"
        v-model="props.searchData[item.props]"
      />
    </el-form-item>

    <el-collapse-transition>
      <div v-show="advanced">
        <slot name="collapse">
        </slot>
      </div>
    </el-collapse-transition>
  </el-form>
  
    <div style="width: 180px; margin-bottom: 18px;">
        <div style="float: right;">
          <el-button @click="onSearch" type="primary">搜索</el-button>
          <a style="margin-left: 8px" @click="toggleAdvanced">
            <!-- {{ advanced ? '收起' : '展开' }} -->
            <el-icon v-if="advanced"><ArrowUp /></el-icon>
            <el-icon v-else><ArrowDown /></el-icon>
          </a>
        </div>
      </div>
  </base-layout>    
  
</template>

<script setup>

const props = defineProps({
  searchItems: { type: Array, default: () => [] },
  searchData: { type: Object, default: () => {} },
});

const advanced = ref(false);

// 定义事件
const emit = defineEmits(["refreshTableData"]);

const onSearch = () => {
  emit("refreshTableData");
};

function toggleAdvanced() {
  advanced.value = !advanced.value
}

</script>