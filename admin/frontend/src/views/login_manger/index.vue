<template>
    <base-wrapper
  >
  <div class="flex-between-start" style="background-color: #5D5FEF; height: 140px;">
      <div class="flex-c-center-start h-full">
        <div class="m-x-20">
            <span style="font-weight: bold; font-size: 20px; color: #FFFFFF;">登陆管理</span>
        </div>
        <div class="m-x-20 m-t-4">
          <span style="font-size: 14px; color: #FFFFFF;">新用户的默认密码为123456</span>
        </div>
      </div>
      <div class="flex-start-end h-full m-r-10">
          <img src="@/assets/logo/document.png" height="130px" />
      </div>
  </div>
  <el-breadcrumb
  separator="/"
  class="m-20"
  >
  <el-breadcrumb-item
  :to="{ path: '/' }"
  >
  首页
  </el-breadcrumb-item>
  <el-breadcrumb-item
  :to="{ path: '/login_manger' }"
  >
  登陆信息管理
  </el-breadcrumb-item>
  </el-breadcrumb>
  <login_manger />
  </base-wrapper>
  </template>
  
  <script setup>
  const {proxy} = getCurrentInstance();
  </script>
  
  <style lang="scss" scoped></style>