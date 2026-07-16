<template>
  <div v-bind="$attrs">
    <div class=" bg-color-lightgrey p-y-10 " style="border: 1px solid rgb(230, 233, 240); border-radius: 10px 10px 0px 0px;">
      <span class="m-l-10" style="font-weight: bold; font-size: 16px;">{{ header }}</span>
    </div>
    <slot />
  </div>
</template>
<script setup>
const props = defineProps({
  labelWidth: {
    type: String,
    default: '200px',
  },
  header: {
    type: String,
    default: '详情'
  }
});
provide('baseCellLabelWidth', props.labelWidth);
</script>
<style lang="scss" scoped></style>
