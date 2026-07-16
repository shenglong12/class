<template>
    <span ref="boxRef">
      <el-popover :visible="visible" placement="top" :width="200" trigger="click">
        <p>您确定删除这条数据吗？</p>
        <div style="text-align: right; margin: 0">
          <el-button link @click="visible = false">取消</el-button>
          <el-button link @click="handleOk">确定</el-button>
        </div>
        <template #reference>
          <el-button link style="margin-left: 5px; color: #f36161" @click="visible = true"> 
            <el-icon class="m-r-6"><Delete /></el-icon>  删除 
          </el-button>
        </template>
      </el-popover>
    </span>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let visible = ref(false);

//组件定义的事件
const emits = defineEmits(['ok']);

function handleOk() {
  emits('ok');

  visible.value = false;
}

</script>