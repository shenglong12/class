# Project: 教室管理系统 (Classroom Management System)

## 项目概述

大学教室管理系统，包含小程序端（学生/教师扫码查课表、预约教室、报修）和管理后台（Web端，管理教室、课程、预约、报修、用户等）。

## 技术栈

| 层 | 技术 |
|---|------|
| 后端框架 | Spring Boot 2.5.0 + MyBatis-Plus |
| 后端语言 | Java 8 |
| 小程序前端 | uni-app (Vue 3)，支持微信小程序 + H5 |
| 管理后台前端 | Vue 3 + Vite + Element Plus |
| 数据库 | MySQL（生产）/ SQLite（开发），通过 Flyway 迁移 |
| 构建工具 | Maven 3.9.9（项目内置在 `demo/`） |
| JDK | 1.8.0_431（项目内置在 `demo/`） |

## 项目结构

```
class/
├── app/                          # 小程序端
│   ├── backend/                  # Spring Boot 后端
│   └── frontend/                 # uni-app 前端
├── admin/                        # 管理后台
│   ├── backend/                  # Spring Boot 后端
│   └── frontend/                 # Vue 3 + Vite 前端
├── demo/
│   ├── jdk1.8.0_431/             # 内置 JDK（必须用这个路径）
│   └── apache-maven-3.9.9/       # 内置 Maven（必须用这个路径）
├── verify.bat                    # 编译验证脚本
└── CLAUDE.md                     # 本文件
```

## 编译和运行

### 设置环境（每次编译前必须执行）

```bash
export JAVA_HOME="d:/Project/class/demo/jdk1.8.0_431"
export PATH="$JAVA_HOME/bin:$PATH"
```

### 编译后端

```bash
# 编译 app 模块
cd app/backend
d:/Project/class/demo/apache-maven-3.9.9/bin/mvn compile -q

# 编译 admin 模块
cd admin/backend
d:/Project/class/demo/apache-maven-3.9.9/bin/mvn compile -q

# 或用 verify.bat
./verify.bat app      # 只编译 app
./verify.bat admin    # 只编译 admin
./verify.bat all      # 两个都编译
```

### 前端构建

```bash
cd admin/frontend && npm run build       # 管理后台
cd app/frontend && npm run build:mp-weixin  # 小程序
```

## 关键架构

### 动态接口（核心模式）

小程序端不写死 Controller，而是通过统一入口 `POST /api/data/invoke?table=X&method=Y` 动态调用：

```
前端 cf.table.list({ table_name: 'course_info', param: {...} })
  → POST /api/data/invoke?table=course_info&method=list
  → UnifiedDataController → VoConverter(snake_case→camelCase转换)
  → 检查是否存在 XxxAll 实体（联表查询），有则自动路由到 XxxAllControllerService
  → DynamicServiceInvoker 反射调用对应 ControllerService 的方法
```

### JWT 认证

- 登录返回 token（UUID），实际用户数据存在缓存（本地 Map 或 Redis）
- JWT 里只存 `login_user_key`，不存用户信息
- `SessionCreationPolicy.STATELESS`，每个请求通过 JwtAuthenticationTokenFilter 验证
- app 端 token 存为 `h5_token`（前端 localStorage/uni.storage）
- token 有效期 30 天（配置：`token.expireTime: 43200`）

### 外键命名约定

低代码平台自动生成外键，格式：`{关联表}_{关联主键}_{序号}`

例如：
- `classroom_info_classroom_info_id_1` → 指向 `classroom_info.classroom_info_id`
- `user_info_user_info_id_1` → 指向 `user_info.user_info_id`
- `building_enum_building_enum_id_1` → 指向 `building_enum.building_enum_id`

前端传参用 snake_case（如 `classroom_info_classroom_info_id_1`），VoConverter 自动转为 camelCase。

### 缓存

- 开发环境默认用本地缓存（`CACHE_TYPE: local`），ConcurrentHashMap + expireTimeMap
- 生产环境可用 Redis（`114.116.233.39:6379`，密码 `lmxderedis`，database 3）
- 服务重启后本地缓存丢失，所有用户需要重新登录

### 二维码

- 管理端前端用 `qrcode` 库在浏览器生成二维码 PNG → 上传到服务器 → 存 `qr_code_path`
- 内容格式：`/classroom/{classroomInfoId}`
- App 扫码用正则 `/^\/classroom\/(\d+)(?:_.+)?$/` 提取教室ID → 跳转教室详情页
- 教室详情页有3个Tab：课表、预约、报修

## 重要规则

### Git 提交
- **作者必须是 shenglong12 <sl2006727@gmail.com>**，不能用 Claude 的名字
- 提交信息不要有 `Co-Authored-By: Claude` 这行

### 通信风格
- 用户是技术小白，解释要简单易懂但不失专业性
- 每次说明都要讲"为什么"

## 常见问题

1. **"找不到项目根目录"** — 项目有自动检测逻辑，找不到返回 null 而非 user.dir
2. **app/admin 同名文件** — 两个模块有很多同名类（如 TokenService），修改时要两个一起改
3. **SQLite 路径** — 数据库文件路径从项目根目录出发
4. **前端 401 处理** — `request.js` 拦截器检测 `res.data.code == 401` → 自动跳转登录页
