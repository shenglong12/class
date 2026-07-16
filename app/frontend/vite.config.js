import { defineConfig, loadEnv } from 'vite'
import path from 'path'
import uni from '@dcloudio/vite-plugin-uni'
import AutoImport from 'unplugin-auto-import/vite'
import tailwindcss from "tailwindcss";
import banner from 'vite-plugin-banner'
import { UnifiedViteWeappTailwindcssPlugin as uvwt } from 'weapp-tailwindcss/vite';
import autoprefixer from "autoprefixer";


// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
    const timestamp = new Date().getTime()
    const env = loadEnv(mode, process.cwd())
    console.log(env)
    return {
        base: env.VITE_BASE,
        plugins: [
            uni(),
            uvwt(),
            AutoImport({
                imports: [
                    'vue',
                    'uni-app'
                ],
                dts: false
            }),
            banner('/* codeflying */')
        ],
        css: {
            postcss: {
                plugins:[tailwindcss(),autoprefixer()]
            },
            preprocessorOptions: {
                scss: {
                    silenceDeprecations: ['legacy-js-api'],
                },
            },
        },
        server: {
            host: "0.0.0.0",
            proxy: {
                [env.VITE_APP_BASE_API]: {
                    target: env.VITE_APP_SERVICE_API,
                    changeOrigin: true,
                    rewrite: (path) => path.replace(new RegExp('^' + env.VITE_APP_BASE_API), ''),
                },
            }
        },
        resolve: {
            alias: {
                // 设置路径
                '~': path.resolve(__dirname, './'),
                // 设置别名
                '@': path.resolve(__dirname, './src')
            }
        },
        build:{
            rollupOptions: {
                output: {
                    // JS 入口文件
                    entryFileNames: `assets/[name].[hash].t${timestamp}.js`,
                    // JS 分块文件
                    chunkFileNames: `assets/[name].[hash].t${timestamp}.js`,
                    // CSS / 图片 / 其他资源
                    assetFileNames: `assets/[name].[hash].t${timestamp}.[ext]`
                }
            }
        }
    }
})