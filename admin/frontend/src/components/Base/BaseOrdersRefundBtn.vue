<template>
  <el-button style="margin-left: 5px;" @click="showDialog" round>
    <el-icon class="m-r-6">
      <Tickets/>
    </el-icon>
    退款
  </el-button>
  <!--  打开弹窗-->

  <el-dialog v-model="dialogVisible" title="提示">
    <span>确认退款？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>


import {ElMessageBox} from "element-plus";

const {proxy} = getCurrentInstance();
const props = defineProps({
  general_orders_id: {type: String}
});
const emits = defineEmits("ok")
const dialogVisible = ref(false)

function showDialog() {
  // 请求退款接口，然后刷新列表
  ElMessageBox.alert('是否要退款？', '退款', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: 'OK',
    callback: (action) => {
      handleOk()
    },
  })

}

const handleOk = async () => {
  let res= await proxy.$api.payment.refund(props.general_orders_id)
  if (res.code == 0){
    emits("ok")
  }else {
    proxy.$modal.msgError(res.message | "系统异常，请稍后重试")
  }


}
</script>

<style scoped>

</style>