<template>
    <el-button style="margin-left: 5px;" @click="handleOk" round> 
        <el-icon class="m-r-6"><Tickets /></el-icon>  详情 
    </el-button>
</template>

<script setup>

const { proxy } = getCurrentInstance();
const props = defineProps({
  path: {type: String },
  text: {type: String },
  query: { type: Object, default: () => {} }
});

//组件定义的事件
const emits = defineEmits(['ok']);

function handleOk() {
  if(props.path){
    proxy.$router.push({path: props.path, query: props.query});
  }
  else{
    emits('ok');
  }
}
</script>

<style lang="scss" scoped>

.el-button { 

    border-color: #5D5FEF;
    color: #5D5FEF;
}

</style>