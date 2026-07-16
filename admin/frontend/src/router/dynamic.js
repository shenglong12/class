export const dynamicRoutes = [{
        path: '/',
        meta: {
            title: '首页',
            isParentView: false
        },
        component: ()=> import('@/views/dashboard_statistic/index.vue'),
    }, {
        path: '/kf_system_config',
        meta: {
            title: '系统配置',
            shownot: false
        },
        component: ()=> import('@/views/kf_system_config/index.vue'),
    }, {
        path: '/role_enum',
        meta: {
            title: '角色枚举',
            shownot: false
        },
        component: ()=> import('@/views/role_enum/index.vue'),
    }, {
        path: '/user_status_enum',
        meta: {
            title: '用户状态',
            shownot: false
        },
        component: ()=> import('@/views/user_status_enum/index.vue'),
    }, {
        path: '/building_enum',
        meta: {
            title: '教学楼枚举',
            shownot: false
        },
        component: ()=> import('@/views/building_enum/index.vue'),
    }, {
        path: '/user_info',
        meta: {
            title: '用户信息',
            shownot: false
        },
        component: ()=> import('@/views/user_info/index.vue'),
    }, {
        path: '/classroom_info',
        meta: {
            title: '教室信息',
            shownot: false
        },
        component: ()=> import('@/views/classroom_info/index.vue'),
    }, {
        path: '/course_info',
        meta: {
            title: '课程信息',
            shownot: false
        },
        component: ()=> import('@/views/course_info/index.vue'),
    }, {
        path: '/repair_status_enum',
        meta: {
            title: '报修状态',
            shownot: false
        },
        component: ()=> import('@/views/repair_status_enum/index.vue'),
    }, {
        path: '/repair_order',
        meta: {
            title: '报修单',
            shownot: false
        },
        component: ()=> import('@/views/repair_order/index.vue'),
    }, {
        path: '/booking_status_enum',
        meta: {
            title: '预约状态',
            shownot: false
        },
        component: ()=> import('@/views/booking_status_enum/index.vue'),
    }, {
        path: '/booking_info',
        meta: {
            title: '预约信息',
            shownot: false
        },
        component: ()=> import('@/views/booking_info/index.vue'),
    }, {
        path: '/background_image',
        meta: {
            title: '背景图片',
            shownot: false
        },
        component: ()=> import('@/views/background_image/index.vue'),
    }, {
        path: '/scan_record',
        meta: {
            title: '扫码记录',
            shownot: false
        },
        component: ()=> import('@/views/scan_record/index.vue'),
    }, {
        path: '/dynamic_api_setting',
        meta: {
            title: 'API配置',
            shownot: false
        },
        component: ()=> import('@/views/dynamic_api_setting/index.vue'),
    }, {
        path: '/kf_system_config/detail',
        meta: {
            title: '系统配置详情',
            shownot: true
        },
        component: ()=> import('@/views/kf_system_config/detail/index.vue'),
    }, {
        path: '/role_enum/detail',
        meta: {
            title: '角色枚举详情',
            shownot: true
        },
        component: ()=> import('@/views/role_enum/detail/index.vue'),
    }, {
        path: '/user_status_enum/detail',
        meta: {
            title: '用户状态详情',
            shownot: true
        },
        component: ()=> import('@/views/user_status_enum/detail/index.vue'),
    }, {
        path: '/building_enum/detail',
        meta: {
            title: '教学楼枚举详情',
            shownot: true
        },
        component: ()=> import('@/views/building_enum/detail/index.vue'),
    }, {
        path: '/user_info/detail',
        meta: {
            title: '用户信息详情',
            shownot: true
        },
        component: ()=> import('@/views/user_info/detail/index.vue'),
    }, {
        path: '/classroom_info/detail',
        meta: {
            title: '教室信息详情',
            shownot: true
        },
        component: ()=> import('@/views/classroom_info/detail/index.vue'),
    }, {
        path: '/course_info/detail',
        meta: {
            title: '课程信息详情',
            shownot: true
        },
        component: ()=> import('@/views/course_info/detail/index.vue'),
    }, {
        path: '/repair_status_enum/detail',
        meta: {
            title: '报修状态详情',
            shownot: true
        },
        component: ()=> import('@/views/repair_status_enum/detail/index.vue'),
    }, {
        path: '/repair_order/detail',
        meta: {
            title: '报修单详情',
            shownot: true
        },
        component: ()=> import('@/views/repair_order/detail/index.vue'),
    }, {
        path: '/booking_status_enum/detail',
        meta: {
            title: '预约状态详情',
            shownot: true
        },
        component: ()=> import('@/views/booking_status_enum/detail/index.vue'),
    }, {
        path: '/booking_info/detail',
        meta: {
            title: '预约信息详情',
            shownot: true
        },
        component: ()=> import('@/views/booking_info/detail/index.vue'),
    }, {
        path: '/background_image/detail',
        meta: {
            title: '背景图片详情',
            shownot: true
        },
        component: ()=> import('@/views/background_image/detail/index.vue'),
    }, {
        path: '/scan_record/detail',
        meta: {
            title: '扫码记录详情',
            shownot: true
        },
        component: ()=> import('@/views/scan_record/detail/index.vue'),
    }, {
        path: '/dynamic_api_setting/detail',
        meta: {
            title: 'API配置详情',
            shownot: true
        },
        component: ()=> import('@/views/dynamic_api_setting/detail/index.vue'),
    }]