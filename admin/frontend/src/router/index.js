import { createRouter, createWebHistory } from 'vue-router'

export const constantRouters = [
    {
        path: '/login',
        meta: {
            isParentView: true,
            title: "登录",
        },
        component: () => import('@/views/login/index.vue'),
    },
    {
        path: '/login_manger',
        meta: {
            title: '登陆信息',
            shownot: false
        },
        component: ()=> import('@/views/login_manger/index.vue'),
    }, {
        path: '/api_setting_manger',
        meta: {
            title: 'API配置',
            shownot: false
        },
        component: () => import('@/views/dynamic_api_setting/index.vue'),
    }
    // ,
    // {
    //     path: "/:pathMatch(.*)*",
    //     component: () => import('@/views/error/404.vue'),
    //     hidden: true
    // }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.VITE_BASE),
    routes: constantRouters,
});

export default router;