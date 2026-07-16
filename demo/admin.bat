@echo off
:: set env

set workdir=%~dp0
echo %workdir%

set MAVEN_HOME=%workdir%apache-maven-3.9.9
set JAVA_HOME=%workdir%jdk1.8.0_431
set NODE_HOME=%workdir%node-v22.12.0

set CLASSPATH=.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar

set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%NODE_HOME%;%NODE_HOME%\node_global;%NODE_HOME%\node_cache;%PATH%

call npm config set registry https://registry.npmmirror.com

:: start  cmd /k "cd download\管理端\backend & mvn spring-boot:run -Dspring-boot.run.jvmArguments='-DSQLITE_PATH=../../demo1.sqlite'"

:: start  cmd /k "cd download\管理端\frontend & npm install & npm run dev"

cmd /k