<template>
<el-drawer v-bind="$attrs" @close="close">
    <template #header="{ close, titleId, titleClass }">
        <h3 :id="titleId" :class="titleClass" class="text-center">{{ title }}</h3>
        <el-button type="info" @click="close" circle>
            <el-icon><Close /></el-icon>
        </el-button>
    </template>
    <slot />
</el-drawer>
</template>
<script setup>

const props = defineProps({
    title :{ type:String, default: ''}
});

function close(){
    console.log(props.title);
}


</script>