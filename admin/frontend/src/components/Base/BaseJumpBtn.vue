<template>
    <el-link type="primary" @click="jump">{{ text }}</el-link>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  path: {type: String },
  text: {type: String },
  query: { type: Object, default: () => {} }
});

console.log('========', props.query)

function jump(){
    proxy.$router.push({path: props.path, query: props.query});
}

</script>