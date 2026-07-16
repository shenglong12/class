<template>
  <view>
    <uni-data-picker
        :localdata="columns"
        v-model="selectedValue"
        @change="onChange"
        :placeholder="placeholder"
        clear-icon="true"
    />
  </view>
</template>

<script setup>
import {reactive, ref} from 'vue';
import {cityArr} from '../static/cityArr';
import {onLoad} from "@dcloudio/uni-app";

const modelValue = defineModel()

const props = defineProps({
  placeholder: {
    type: String,
    default: "请选择城市"
  }
})
// 状态
const visible = ref(false);

const selectedValue = ref(null);

// 监听 modelValue 变化，保持同步
watch(
    () => modelValue.value,
    (newVal) => {
      // 判断必须时list
      if (newVal && Array.isArray(newVal) && newVal.length > 0) {
        selectedValue.value = Number(newVal[newVal.length - 1]['value'])
      }
    },
    {deep: true, once: true}
)
onLoad(() => {
  if (modelValue.value && Array.isArray(modelValue.value) && modelValue.value.length > 0) {
    selectedValue.value = Number(modelValue.value[modelValue.value.length - 1]['value'])
  }
})

// 构建省市数据
const provList = cityArr.filter(item => item.p === 0);
const cityList = cityArr.filter(item => item.p === provList[0].i);

// columns 数据格式 uni-data-picker 需要
const datas = cityArr
    .filter(p => p.p === 0) // 只取省
    .map(prov => {
      // 找到该省对应的城市
      const cities = cityArr.filter(c => c.p === prov.i)
      // 如果有城市，生成 children，否则省自己就是叶子节点
      return {
        text: prov.n,
        value: prov.i,
        children: cities.length
            ? cities.map(c => ({
              text: c.n,
              value: c.i
            }))
            : undefined
      }
    })
const columns = reactive(datas)



let provName = provList[0].n;
let cityName = cityList[0].n;


// 当选择变化时
function onChange(e) {
  modelValue.value = e.detail.value
}
</script>

<style scoped>
.picker-column {
  text-align: center;
  font-size: 34rpx;
  line-height: 100rpx;
}
</style>
