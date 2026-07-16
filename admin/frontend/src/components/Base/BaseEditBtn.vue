<template>
    <el-button link style="margin-left: 5px;" @click="handleOk"> 
        <el-icon class="m-r-6"><Edit /></el-icon>  编辑 
    </el-button>
</template>

<script setup>

//组件定义的事件
const emits = defineEmits(['ok']);

function handleOk() {
  emits('ok');
}

</script>