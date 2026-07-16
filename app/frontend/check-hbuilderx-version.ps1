# HBuilderX 版本检查脚本
# 用于排除 HBuilderX 版本过旧的问题

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "HBuilderX 版本兼容性检查" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 1. 检查项目使用的 uni-app 版本
Write-Host "1. 项目使用的 uni-app 版本：" -ForegroundColor Yellow
$packageJson = Get-Content "package.json" | ConvertFrom-Json
$vitePluginVersion = $packageJson.devDependencies.'@dcloudio/vite-plugin-uni'
Write-Host "   @dcloudio/vite-plugin-uni: $vitePluginVersion" -ForegroundColor Green

# 检查 compilerVersion
if (Test-Path "node_modules\@dcloudio\vite-plugin-uni\package.json") {
    $pluginPkg = Get-Content "node_modules\@dcloudio\vite-plugin-uni\package.json" | ConvertFrom-Json
    if ($pluginPkg.'uni-app'.compilerVersion) {
        Write-Host "   compilerVersion: $($pluginPkg.'uni-app'.compilerVersion)" -ForegroundColor Green
    }
}

Write-Host ""

# 2. 检查 HBuilderX 版本
Write-Host "2. 检查 HBuilderX 版本：" -ForegroundColor Yellow

# 方法 1: 检查 HBuilderX 安装目录
$hbuilderxPaths = @(
    "${env:ProgramFiles}\HBuilderX",
    "${env:ProgramFiles(x86)}\HBuilderX",
    "${env:LOCALAPPDATA}\HBuilderX",
    "${env:APPDATA}\HBuilderX"
)

$hbuilderxFound = $false
foreach ($path in $hbuilderxPaths) {
    if (Test-Path $path) {
        $versionFile = Join-Path $path "version.txt"
        if (Test-Path $versionFile) {
            $version = Get-Content $versionFile -ErrorAction SilentlyContinue
            Write-Host "   找到 HBuilderX 安装目录: $path" -ForegroundColor Green
            Write-Host "   版本文件: $version" -ForegroundColor Green
            $hbuilderxFound = $true
            break
        }
        
        # 检查 package.json（如果存在）
        $pkgJson = Join-Path $path "package.json"
        if (Test-Path $pkgJson) {
            try {
                $pkg = Get-Content $pkgJson | ConvertFrom-Json
                if ($pkg.version) {
                    Write-Host "   找到 HBuilderX 安装目录: $path" -ForegroundColor Green
                    Write-Host "   版本: $($pkg.version)" -ForegroundColor Green
                    $hbuilderxFound = $true
                    break
                }
            } catch {
                # 忽略错误
            }
        }
    }
}

if (-not $hbuilderxFound) {
    Write-Host "   ⚠ 未在标准位置找到 HBuilderX，请手动检查版本" -ForegroundColor Yellow
    Write-Host "   检查方法：" -ForegroundColor Yellow
    Write-Host "     1. 打开 HBuilderX" -ForegroundColor White
    Write-Host "     2. 点击菜单：帮助 → 关于" -ForegroundColor White
    Write-Host "     3. 查看版本号" -ForegroundColor White
}

Write-Host ""

# 3. 版本兼容性要求
Write-Host "3. 版本兼容性要求：" -ForegroundColor Yellow
Write-Host "   项目使用的版本: 3.0.0-4020420240722002" -ForegroundColor White
Write-Host "   compilerVersion: 4.24" -ForegroundColor White
Write-Host ""
Write-Host "   推荐 HBuilderX 版本：" -ForegroundColor Cyan
Write-Host "     - HBuilderX 3.8.0 或更高版本" -ForegroundColor Green
Write-Host "     - 支持 uni-app 3.0+ 和 Vite 5.x" -ForegroundColor Green
Write-Host ""
Write-Host "   如果您的 HBuilderX 版本低于 3.8.0：" -ForegroundColor Yellow
Write-Host "     1. 下载最新版 HBuilderX: https://www.dcloud.io/hbuilderx.html" -ForegroundColor White
Write-Host "     2. 或者使用命令行方式运行项目（不依赖 HBuilderX）" -ForegroundColor White

Write-Host ""

# 4. 检查 node_modules 中的关键模块
Write-Host "4. 检查关键编译器模块：" -ForegroundColor Yellow
$modules = @(
    "@dcloudio/vite-plugin-uni",
    "@dcloudio/uni-cli-shared",
    "@dcloudio/uni-mp-weixin",
    "@dcloudio/uni-mp-compiler"
)

foreach ($module in $modules) {
    $modulePath = "node_modules\$module"
    if (Test-Path $modulePath) {
        Write-Host "   ✓ $module - 已安装" -ForegroundColor Green
    } else {
        Write-Host "   ✗ $module - 未找到" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "检查完成" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
