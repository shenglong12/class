<template>
    <div v-show="!$route.meta.isParentView" class="p-1 h-full bg-color-purplb flex-c-between-start">
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
      size="70%"
      v-model="drawer"
      :show-close="false"
      direction="ltr"
      >
       
      <div class="flex-c-between-start h-full">
        <div class="w-full"> 
            <div class="flex-start-center m-30 ">
                <img src="@/assets/logo/logo.png" width="32px" height="34px" />
                <span class="m-l-10" style="font-weight: bold; font-size: 24px;">CodeFlying</span>
            </div>

            <div class="flex-start-center m-y-30 m-l-22">
                <el-divider direction="vertical" style="border-color: #5D5FEF; border-width: 4px; border-radius: 2px; height: 20px;"/>
                <h3 class="m-l-5">{{ left_title }}</h3>
            </div>
        </div>

        <div class="w-full h-full overflow-y-scroll">
            <el-menu
                :default-active="currentPath"
                class="el-menu-vertical-demo m-l-20 m-r-10"
                @select="handleSelect"
            >
            
                <div v-for="item in dynamicRoutes" :key="item.path">
                    <el-menu-item v-if="!item.meta.shownot" :index="item.path">
                        <template #title>{{ item.meta.title }}</template>
                    </el-menu-item>    
                </div>

                <div>
                    <el-menu-item index="/login_manger">
                        <template #title>登录管理</template>
                    </el-menu-item>
                </div>

            </el-menu>

        </div>

        <div class="w-full m-b-30">
            <el-divider />
            <div class="flex-end-center m-r-20">
                <el-avatar >
                    <img src="@/assets/logo/logo.png" />
                </el-avatar>
                <el-badge is-dot class="item m-l-20">
                    <el-icon size="20"><Bell /></el-icon>
                </el-badge>

                <el-button round class="m-l-20"> 退出登录</el-button>
            </div>
        </div>
      </div>

    </el-drawer>
  </template>
  
  <script setup>
  const { proxy } = getCurrentInstance();
  const route = useRoute();
  const router = useRouter();
  
  const isHome = ref(true);
  const drawer = ref(false)
  const pageTitle = ref('首页');
  const currentPath = ref('');
  
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
      currentPath.value = newPath;
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

.el-menu {
    border-right: 0;
}

.el-menu-item.is-active {
  background-color: #5D5FEF !important; /* 设置你想要的颜色 */
  color: #ffffff !important; /* 选中项文字颜色 */
  border-radius: 4px;
}
  
  </style>
  