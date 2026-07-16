<template>

    <base-layout :h_full="true" display="flex" x="center" y="center" bg_color="bg-color-primary-q">
        <login @loginSucceess="login_success"></login>
    </base-layout>

</template>
<script setup>

const { proxy } = getCurrentInstance();

function login_success(){
    let fullPath = proxy.$route.fullPath;
    
    if (fullPath.startsWith('/login?redirect=')) {
        let lastPath = fullPath.replace('/login?redirect=', '');
        // 跳转到上次退出的页面
        proxy.$router.push({ path: lastPath });
    } else {
        // 跳转到首页
        proxy.$router.push({ path: '/' });
    }
}

</script>