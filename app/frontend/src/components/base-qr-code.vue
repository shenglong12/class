<template>
  <!--  <canvas ref="qrCanvas" :width="size" :height="size"></canvas>-->

  <view class="qr-container">
    <canvas
        id="qrcode"
        canvas-id="qrcode"
        class="qr-canvas"
        :style="{ width: `${props.size}px`, height: `${props.size}px` }"
    ></canvas>
  </view>
</template>

<script setup>
import {ref, onMounted, watch} from 'vue';
import UQRCode from 'uqrcodejs'


const props = defineProps({

  text: {
    type: String,
    required: true,
  },
  size: {
    type: Number,
    default: 200,
    validator: (value) => value > 0,
  },
})

const qrCanvas = ref(null);
// 生成二维码
const generateQRCode = async () => {
  // if (!qrCanvas.value || !props.text) return;
  console.log("开始绘制")
  try {
    // 获取uQRCode实例
    let qr = new UQRCode();
    // 设置二维码内容
    qr.data = props.text;
    // 设置二维码大小，必须与canvas设置的宽高一致
    qr.size = props.size;
    // 调用制作二维码方法
    qr.make();
    // 获取canvas上下文
    const canvasContext = uni.createCanvasContext('qrcode', this); // 如果是组件，this必须传入
    // 设置uQRCode实例的canvas上下文
    qr.canvasContext = canvasContext;
    // 调用绘制方法将二维码图案绘制到canvas上
    qr.drawCanvas();

  } catch (error) {
    console.error('QR Code generation failed:', error);
  }
};

// 组件挂载时生成二维码
onMounted(generateQRCode);


const reload=()=>{
  generateQRCode()
}
/**
 * 外部可调用的方法
 */
defineExpose({
  reload,
})


</script>


<style lang="scss">
.qr-container {
  @apply flex justify-center items-center;

  .qr-canvas {
    @apply block;
    // 确保canvas显示为块级元素
    aspect-ratio: 1/1; // 保持正方形比例
  }
}
</style>