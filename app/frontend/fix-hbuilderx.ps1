# HBuilderX 编译器模块修复脚本
# 请在 HBuilderX 的内置终端中执行这些命令

Write-Host "正在修复 HBuilderX 编译器模块问题..." -ForegroundColor Yellow

# 进入前端目录
Set-Location "应用端\frontend"

# 清理并重新安装依赖
Write-Host "步骤 1: 清理旧的 node_modules..." -ForegroundColor Cyan
Remove-Item -Recurse -Force node_modules -ErrorAction SilentlyContinue
Remove-Item -Force package-lock.json -ErrorAction SilentlyContinue

Write-Host "步骤 2: 清理 npm 缓存..." -ForegroundColor Cyan
npm cache clean --force

Write-Host "步骤 3: 重新安装依赖..." -ForegroundColor Cyan
npm install

Write-Host "步骤 4: 验证关键模块..." -ForegroundColor Cyan
if (Test-Path "node_modules\@dcloudio\vite-plugin-uni") {
    Write-Host "✓ @dcloudio/vite-plugin-uni 已安装" -ForegroundColor Green
} else {
    Write-Host "✗ @dcloudio/vite-plugin-uni 未找到" -ForegroundColor Red
}

if (Test-Path "node_modules\@dcloudio\uni-mp-weixin") {
    Write-Host "✓ @dcloudio/uni-mp-weixin 已安装" -ForegroundColor Green
} else {
    Write-Host "✗ @dcloudio/uni-mp-weixin 未找到" -ForegroundColor Red
}

if (Test-Path "node_modules\@dcloudio\uni-cli-shared") {
    Write-Host "✓ @dcloudio/uni-cli-shared 已安装" -ForegroundColor Green
} else {
    Write-Host "✗ @dcloudio/uni-cli-shared 未找到" -ForegroundColor Red
}

Write-Host "`n修复完成！请：`n1. 关闭并重新打开 HBuilderX`n2. 在 HBuilderX 中重新打开项目`n3. 再次尝试运行到微信小程序" -ForegroundColor Yellow

