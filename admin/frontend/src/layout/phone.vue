<template>
    <div v-show="!$route.meta.isParentView" class="p-1 h-full bg-color-purple flex-c-between-start">
      <div class="head w-full flex-between-center">
        <div class="m-l-20">
          <el-icon  :size="30" @click="drawer=true"><Expand color="white" /></el-icon>
          <!-- <el-icon v-else :size="30" @click="goHome"><HomeFilled color="white" /></el-icon> -->
        </div>
        <div class="text-color-white">{{ pageTitle }}</div>
        <div class="m-r-20">
          <el-icon :size="30"><InfoFilled color="white" /></el-icon>
        </div>
      </div>
      <div class="middle bg-color-white overflow-y-auto">
        <router-view   />
      </div>
      <div class="foot w-full bg-color-white flex-c-center-center">
        <span>Powered by CodeFlying</span>
      </div>  
    </div>
  
    <div v-if="$route.meta.isParentView" class="h-full w-full">
      <router-view />
    </div>
  
    <el-drawer
      size="40%"
      v-model="drawer"
      :show-close="false"
      direction="ltr"
      >
        
        <h3 class="flex-center-center p-y-10 text-color-primary">{{ left_title }}</h3>
        <el-menu class="el-menu-vertical-demo" @select="handleSelect">
            <div v-for="item in dynamicRoutes" :key="item.path">
                <el-menu-item v-if="!item.meta.shownot" :index="item.path">
                    <template #title>{{ item.meta.title }}</template>
                </el-menu-item>    
            </div>
        </el-menu>


    </el-drawer>
  </template>
  
  <script setup>
  const { proxy } = getCurrentInstance();
  const route = useRoute();
  const router = useRouter();
  
  const isHome = ref(true);
  const drawer = ref(false)
  const pageTitle = ref('首页');
  
  function getBreadcrumb(){
    let matched = route;
    if(matched.meta.isHome){
      isHome.value = true;
    }
    else{
      isHome.value = false;
    }
    pageTitle.value = matched.meta.title;
  }
  
  function goHome(){
    router.push({path: '/'})
  }
  
  watch(
    () => route.fullPath,
    (newPath, oldPath) => {
      console.log('Route changed from', oldPath, 'to', newPath);
      getBreadcrumb();
    }
  );
  
  let left_title = import.meta.env.VITE_APP_NAME;
  import { dynamicRoutes } from '@/router/dynamic';
  
    function handleSelect(index, indexPath, item, routeResult){
      proxy.$router.push(index);
      drawer.value = false;
    }
  
  </script>
  
  <style scoped>
  
  .head{
    height: 70px;
  }
  
  .middle{
    height: 100%;
    width: 100% ;
    border-radius: 2px 2px 0px 0px;
  }
  
  .foot{
    height: 100px;
  }
  
  </style>
  