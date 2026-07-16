import {createSSRApp} from "vue";
import api from "@/api"
import App from "./App.vue";
import '@/styles/index.css';
import {createI18n} from 'vue-i18n';
import en from '@/i18n/en.js';
import zh from '@/i18n/zh.js';
import get_resource_url from "@/api/config/static_config";
import wxShare from "./api/utils/wxShare";
import AudioPlayer from '@/api/utils/audioPlayer';
import formatError from "./utils/format";

export function createApp() {
    const lange = import.meta.env.VITE_APP_LANGE;
    const i18n = createI18n({
        locale: lange,         // 默认语言
        fallbackLocale: 'en', // 备用语言
        messages: {
            en,
            zh
        }
    });

    const app = createSSRApp(App);
    app.config.globalProperties.$cf = api;
    app.config.globalProperties.get_resource_url= get_resource_url;
    app.config.globalProperties.$audioPlayer = AudioPlayer;

    app.use(i18n);
    app.mixin(wxShare)
    app.config.globalProperties.$tt = i18n.global.t;
    // #ifdef H5
    window.addEventListener("unhandledrejection", event => {
        // const err = event.reason;
        console.error("Promise", event);

        const pages = getCurrentPages();
        const currentPage = pages[pages.length - 1];
        const route = currentPage ? currentPage.route : "未知页面";

        let targetError = event.reason?.errMsg
            ? event.reason.errMsg
            : (event.reason ?? event);

        app.config.globalProperties.$cf.error.report(import.meta.env.VITE_APP_ERROR_REPORT_URL, {
            error_message: formatError(targetError),
            path: route,
            type: "frontend",
            sub_source: "window_unhandledrejection",
            app_id: import.meta.env.VITE_APP_ID
        });
    });
// #endif
    return {
        app,
    };
}