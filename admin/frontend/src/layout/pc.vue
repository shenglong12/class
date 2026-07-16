<template>
    <div v-show="!$route.meta.isParentView" class="flex h-full w-full">
        <div class="menu" id="sidebar">
            <h3 class="flex-center-center p-y-10 text-color-primary">{{ left_title }}</h3>
            <el-menu class="el-menu-vertical-demo" @select="handleSelect">
                <div v-for="item in dynamicRoutes" :key="item.path">
                    <el-menu-item v-if="!item.meta.shownot" :index="item.path">
                        <template #title>{{ item.meta.title }}</template>
                    </el-menu-item>    
                </div>
            </el-menu>
        </div>
        <div class="flex-1 flex-column m-l-10 overflow-y-scroll" :style="{height: appMainHeight+'px', width: appMainWidth+'px'}">
          <!-- 主页面 -->
          <router-view />
        </div>
    </div>
    <div v-if="$route.meta.isParentView" class="h-full">
        <router-view />
    </div>
    </template>
    
    <script setup>        
        import { dynamicRoutes } from '@/router/dynamic'
        const { proxy } = getCurrentInstance();
        let left_title = import.meta.env.VITE_APP_NAME

        const route = useRoute();

        watch(
            () => route.fullPath,
            (newPath, oldPath) => {
                console.log('Route changed from', oldPath, 'to', newPath);
            }
        );
        
        function handleSelect(index, indexPath, item, routeResult){
            proxy.$router.push(index);
        }

        let appMainWidth = ref(0);
        let appMainHeight = ref(0);

        onMounted(() => {
        // 窗口宽高变化时触发 -- tips：window.onresize只能在项目内触发1次
            window.onresize = function windowResize() {
                calWidthAndHeight();
            };
        });

        // 注册一个回调函数，在组件因为响应式状态变更而更新其 DOM 树之后调用。
        onUpdated(() => {
            calWidthAndHeight();
        });


        function calWidthAndHeight() {
            let sidebar = document.getElementById('sidebar');    
            let sidebarW = sidebar ? sidebar.offsetWidth : 0;
            appMainWidth.value = window.innerWidth - sidebarW;

            let top = document.getElementById('top');
            let topH = top ? top.offsetHeight : 0;
            appMainHeight.value = window.innerHeight - topH;
        }

    </script>
    
<style lang="scss" scoped>
.menu {
    box-shadow: 1px 0 5px rgba(0, 0, 0, 0.2);
    width: 250px;
}
</style>