# 第一阶段：构建前端应用
FROM node:18-alpine AS builder

# 设置工作目录
WORKDIR /app

# 复制 package 文件
COPY package*.json ./

# 安装依赖
RUN npm install --registry=https://registry.npmmirror.com

# 复制源代码和配置文件
COPY . .

# 构建生产环境应用
RUN npm run build

# ============================================
# 第二阶段：使用 Nginx 提供静态文件服务
FROM nginx:alpine

# 复制自定义 Nginx 配置
COPY nginx.conf /etc/nginx/nginx.conf

# 从构建阶段复制打包后的文件到 Nginx 目录
COPY --from=builder /app/dist /usr/share/nginx/html/classroom

# 暴露端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]

