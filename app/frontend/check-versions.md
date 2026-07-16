# HBuilderX 版本兼容性检查指南

## 📋 项目使用的 uni-app 版本信息

- **uni-app 版本**: 3.0.0-4020420240722002
- **compilerVersion**: 4.24
- **Vite 版本**: 5.2.8
- **Vue 版本**: 3.4.21

## 🔍 如何检查 HBuilderX 版本

### 方法 1：在 HBuilderX 中查看（推荐）

1. 打开 **HBuilderX**
2. 点击顶部菜单：**帮助** → **关于 HBuilderX**
3. 在弹出的窗口中查看版本号

### 方法 2：使用检查脚本

在项目目录下运行：
```powershell
cd 应用端\frontend
.\check-hbuilderx-version.ps1
```

## ✅ 版本兼容性要求

**推荐 HBuilderX 版本：**
- **HBuilderX 3.8.0** 或更高版本
- 支持 uni-app 3.0+ 和 Vite 5.x

**最低要求：**
- **HBuilderX 3.7.0**（可能支持，但不推荐）

## 🚨 如果 HBuilderX 版本过旧

### 方案 1：升级 HBuilderX（推荐）

1. **下载最新版本**
   - 访问：https://www.dcloud.io/hbuilderx.html
   - 下载最新版 HBuilderX
   - 安装并替换旧版本

2. **重新打开项目**
   - 关闭 HBuilderX
   - 重新打开 HBuilderX
   - 重新打开项目

3. **重新安装依赖**
   ```powershell
   cd 应用端\frontend
   Remove-Item -Recurse -Force node_modules
   Remove-Item -Force package-lock.json
   npm install
   ```

### 方案 2：使用命令行运行（不依赖 HBuilderX）

如果不想升级 HBuilderX，可以直接使用命令行运行：

```powershell
cd 应用端\frontend
npm run dev:mp-weixin
```

编译完成后：
1. 打开微信开发者工具
2. 导入项目
3. 选择目录：`应用端/frontend/dist/dev/mp-weixin`

## 📊 版本对照表

| uni-app 版本 | HBuilderX 最低版本 | 推荐版本 |
|------------|------------------|---------|
| 3.0.0+ | 3.7.0 | 3.8.0+ |
| 2.0.0 | 3.2.0+ | 3.5.0+ |
| 1.x | 2.0.0+ | 3.0.0+ |

## 🔧 快速判断

如果出现以下错误，很可能是版本过旧：
- ❌ `node_modules缺少编译器模块`
- ❌ `编译器版本不匹配`
- ❌ `无法识别 uni-app 版本`

## 💡 建议

**最佳实践：**
1. 保持 HBuilderX 为最新版本
2. 定期更新项目依赖
3. 如果项目多人协作，统一 HBuilderX 版本

**替代方案：**
- 如果团队中有人使用旧版 HBuilderX
- 建议统一升级，或使用命令行方式运行
- 命令行方式更稳定，不依赖 IDE 版本
