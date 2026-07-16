# 使用官方 Maven 镜像作为构建环境
FROM maven:3.9-eclipse-temurin-8 AS builder

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 文件
COPY pom.xml .

# 下载依赖（利用 Docker 缓存层）
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 打包应用（使用 sqlite profile，跳过测试）
RUN mvn clean package -Dmaven.test.skip=true -P sqlite

# ============================================
# 使用轻量级 JRE 镜像作为运行环境
FROM eclipse-temurin:8-jre-alpine

# 设置工作目录
WORKDIR /app

# 创建数据目录（用于存放 SQLite 数据库和上传文件）
RUN mkdir -p /app/data /app/uploadPath

# 从构建阶段复制 jar 包
COPY --from=builder /app/target/app-0.0.1-SNAPSHOT.jar /app/app.jar

# 暴露端口
EXPOSE 6789

# 设置环境变量
ENV PORT=6789 \
    LOGIN_ENABLE=true \
    CACHE_TYPE=local \
    DB_TYPE=sqlite \
    UPLOAD_PATH=/app/uploadPath

# 启动应用
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx500m", "/app/app.jar"]

