# 后台管理系统

基于若依（RuoYi v3.9.2）前后端分离框架搭建的后台管理系统，采用 Spring Boot 4.0.3 + Vue 2.6.14，主题色已定制为青绿色调。

## 环境要求

| 软件 | 版本要求 |
|------|---------|
| JDK | 17+ |
| Maven | 3.6+ |
| MySQL | 8.0+ |
| Redis | 任意稳定版 |
| Node.js | 14+ |

## 数据库初始化

1. 创建数据库：

```sql
CREATE DATABASE `ry-vue` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 按顺序导入 SQL 脚本：

```bash
# 【必须】主表结构 + 初始数据
mysql -u root -p ry-vue < sql/ry_20260321.sql

# 【必须】演示用种子数据（各表补充 10+ 条记录）
mysql -u root -p ry-vue < sql/ry_20260424_seed_extra_data.sql

# 【可选】定时任务表（如需使用定时任务功能）
mysql -u root -p ry-vue < sql/quartz.sql
```

> 默认管理员账号：`admin`，密码：`admin123`

**其他 SQL 脚本说明**（按需使用）：

| 文件 | 用途 |
|------|------|
| `sql/ry_20260321.sql` | 主建表 + 基础数据（必须首先导入） |
| `sql/ry_20260424_seed_extra_data.sql` | 演示种子数据，各表补充 10+ 条 |
| `sql/quartz.sql` | Quartz 定时任务表结构 |
| `sql/ry_20260423_simulation.sql` | 无人船仿真场景建表（sim_scene 等） |
| `sql/ry_20260423_seed_demo_data.sql` | 仿真模块演示数据 |
| `sql/ry_20260423_usv_menu_reorg.sql` | USV 菜单重组脚本 |
| `sql/ry_20260423_menu_cleanup.sql` | 菜单去重清理脚本 |
| `sql/ru-vue.sql` | 无人机场菜单 SQL（代码生成器产出） |

## 后端配置与启动

### 修改数据库连接

编辑 `ruoyi-admin/src/main/resources/application-druid.yml`：

```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://localhost:3306/ry-vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: root
        password: 你的数据库密码
```

### 确认 Redis 配置

编辑 `ruoyi-admin/src/main/resources/application.yml` 中 `spring.data.redis` 部分，确认 `host` 和 `port` 正确。如果 Redis 设置了密码，需要在此处补充 `password` 字段。

### 启动后端

```bash
# 先确保 Redis 已启动
redis-server

# 构建并启动（端口 8080）
mvn clean package -DskipTests
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

其他启动方式：

```bash
# Maven 直接运行
mvn -pl ruoyi-admin spring-boot:run

# 生产环境脚本（支持 start|stop|restart|status）
./ry.sh start
```

## 前端启动

```bash
cd ruoyi-ui

# 安装依赖
npm install

# 启动开发服务器（默认代理到 localhost:8080）
npm run dev
```

启动后访问 http://localhost:80 即可进入系统。

> 端口 80 在 Linux/macOS 下可能需要 sudo 权限，如遇权限问题可修改 `ruoyi-ui/vue.config.js` 中的 `port` 值。

前端有三个环境配置文件（一般无需修改）：

| 文件 | 用途 |
|------|------|
| `.env.development` | 开发环境，API 前缀 `/dev-api`，代理到 `localhost:8080` |
| `.env.staging` | 预发布环境 |
| `.env.production` | 生产环境 |

## 项目结构

```
├── ruoyi-admin        # 启动模块，含主入口和配置文件
├── ruoyi-framework    # 框架核心：安全认证、Redis、JWT
├── ruoyi-system       # 系统业务：用户、角色、菜单、部门等
├── ruoyi-common       # 通用工具类
├── ruoyi-quartz       # 定时任务
├── ruoyi-generator    # 代码生成器
├── ruoyi-ui           # Vue 前端
├── sql/               # 数据库脚本
└── ry.sh              # 生产环境启动脚本
```
