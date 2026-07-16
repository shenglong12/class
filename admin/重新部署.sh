#!/bin/bash
# 停止并清理容器
docker-compose down

# 删除旧的镜像
docker rmi classroom-frontend classroom-backend 2>/dev/null || true

# 重新构建并启动
docker-compose up -d --build

echo "部署完成！请访问 http://localhost/classroom/"

