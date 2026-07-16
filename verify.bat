@echo off
:: ==========================================
:: 项目构建验证脚本
:: 用法: verify.bat [app|admin|all]
:: ==========================================

setlocal

set WORKDIR=%~dp0
set JAVA_HOME=%WORKDIR%demo\jdk1.8.0_431
set MAVEN=%WORKDIR%demo\apache-maven-3.9.9\bin\mvn
set PATH=%JAVA_HOME%\bin;%PATH%
set MAVEN_OPTS=-Xmx1024m

if "%1"=="" (
    echo 用法: verify.bat [app^|admin^|all]
    echo   app   - 只编译 app 模块
    echo   admin - 只编译 admin 模块
    echo   all   - 编译两个模块并运行测试
    exit /b 1
)

if "%1"=="app" goto :build_app
if "%1"=="admin" goto :build_admin
if "%1"=="all" goto :build_all
echo 未知参数: %1
exit /b 1

:build_app
echo ===== 编译 app/backend =====
cd /d "%WORKDIR%app\backend"
call "%MAVEN%" compile
if %ERRORLEVEL% NEQ 0 (
    echo ❌ app 编译失败
    exit /b 1
)
echo ✅ app 编译成功
goto :end

:build_admin
echo ===== 编译 admin/backend =====
cd /d "%WORKDIR%admin\backend"
call "%MAVEN%" compile
if %ERRORLEVEL% NEQ 0 (
    echo ❌ admin 编译失败
    exit /b 1
)
echo ✅ admin 编译成功
goto :end

:build_all
echo ===== 编译 app/backend =====
cd /d "%WORKDIR%app\backend"
call "%MAVEN%" compile
if %ERRORLEVEL% NEQ 0 (
    echo ❌ app 编译失败
    exit /b 1
)
echo ✅ app 编译成功

echo.
echo ===== 编译 admin/backend =====
cd /d "%WORKDIR%admin\backend"
call "%MAVEN%" compile
if %ERRORLEVEL% NEQ 0 (
    echo ❌ admin 编译失败
    exit /b 1
)
echo ✅ admin 编译成功

echo.
echo ===== 运行测试 =====
echo --- app 测试 ---
call "%MAVEN%" test
echo --- admin 测试 ---
cd /d "%WORKDIR%admin\backend"
call "%MAVEN%" test
goto :end

:end
echo.
echo ===== 完成 =====
endlocal
