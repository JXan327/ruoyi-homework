# 后台管理系统

基于若依（RuoYi v3.9.2）前后端分离框架搭建的后台管理系统，主题色已定制为青绿色调。

## 环境要求

| 软件 | 版本要求 |
|------|---------|
| JDK | 17+ |
| MySQL | 8.0+ |
| Redis | 任意稳定版 |
| Node.js | 14+ |
| Maven | 3.6+ |

## 数据库初始化

1. 创建数据库：

```sql
CREATE DATABASE `ry-vue` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 按顺序导入 SQL 脚本：

```bash
# 1. 主表结构 + 初始数据
mysql -u root -p ry-vue < sql/ry_20260321.sql

# 2. 定时任务表（可选）
mysql -u root -p ry-vue < sql/quartz.sql

# 3. 额外种子数据（演示用，为各表补充 10+ 条记录）
mysql -u root -p ry-vue < sql/ry_20260424_seed_extra_data.sql
```

> 默认管理员账号：`admin`，密码：`admin123`

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

### 修改 Redis 连接（如非默认）

编辑 `ruoyi-admin/src/main/resources/application.yml` 中 `spring.data.redis` 部分，确认 `host` 和 `port` 正确。

### 启动后端

```bash
# 确保 Redis 已启动
redis-server

# 构建并启动（端口 8080）
mvn clean package -DskipTests
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

或使用 Maven 直接运行：

```bash
mvn -pl ruoyi-admin spring-boot:run
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

## 项目结构

```
├── ruoyi-admin        # 启动模块，含主入口和配置文件
├── ruoyi-framework    # 框架核心：安全认证、Redis、JWT
├── ruoyi-system       # 系统业务：用户、角色、菜单、部门等
├── ruoyi-common       # 通用工具类
├── ruoyi-quartz       # 定时任务
├── ruoyi-generator    # 代码生成器
├── ruoyi-ui           # Vue 前端
└── sql/               # 数据库脚本
```
