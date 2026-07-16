import router from '@/router';

import { dynamicRoutes } from '@/router/dynamic'

import NProgress from 'nprogress'; // 导入 nprogress模块
import 'nprogress/nprogress.css'; // 导入样式
NProgress.configure({ showSpinner: true }); // 显示右上角螺旋加载提示

let hasRouter = false;
const useLogin = import.meta.env.VITE_USE_LOGIN === 'true'
const whiteList = ['/login'];

router.beforeEach(async (to, from, next) => {
    NProgress.start(); // 开启进度条
    if(useLogin){
        // 判断有没有登录
        if (localStorage.getItem("token")) {
            //登录成功
            if (to.path === '/login') {
                if (to.fullPath.startsWith('/login?redirect=')) {
                  let lastPath = to.fullPath.replace('/login?redirect=', '');
                  next({ path: lastPath }); // 跳转到上次退出的页面
                } else {
                  next({ path: '/' }); // 跳转到首页
                }
            } else {
                if (hasRouter) {
                    next(); // 放行
                } else {
                    dynamicRoutes.forEach((e) => router.addRoute(e));
                    hasRouter = true;
                    next({ ...to, replace: true });
                }
            }
        } else {
            if (whiteList.indexOf(to.path) !== -1) {
                next(); // 放行 -- 可以访问白名单页面(eg: 登录页面)
            } else {
                //next(`/login?redirect=${to.path}`); // 无权限 & 白名单页面未配置  =》 跳转到登录页面
                next(`/login`);
            }
        }
    }
    else{
        if (hasRouter) {
            next(); // 放行
        } else {
            dynamicRoutes.forEach((e) => router.addRoute(e));
            hasRouter = true;
            next({ ...to, replace: true });
        }
    }
});

// 全局后置钩子
router.afterEach(() => {
    NProgress.done(); // 完成进度条
});