<template>
    <el-dialog :close-on-click-modal="false" v-bind="$attrs" @close="closeDialog">
      <slot />
  
      <template #footer>
        <slot name="footer" />
      </template>
    </el-dialog>
</template>

<script setup>
  var emits=defineEmits(["close"])
  const closeDialog=()=>{
    emits("close")
  }

</script>
<style lang="scss" scoped></style>
  