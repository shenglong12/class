<template>
    <div class="h-full flex-c-center-center m-x-20 ">
        <h4 v-if="!app_vip">该系统由 「CodeFlying」 自动生成。如有问题请联系客服。</h4>
    </div>
</template>

<script setup>
let app_vip = import.meta.env.VITE_APP_VIP === 'true'
</script>

<style scoped>

</style>