# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

这是一个基于若依（RuoYi v3.9.2）框架的快速开发平台，采用前后端分离架构：
- **后端**：Spring Boot 4.0.3 + Spring Security + MyBatis + Redis + JWT（JDK 17）
- **前端**：Vue 2.6.14 + Element UI + Vuex + Vue Router

## 模块架构

```
ruoyi-parent
├── ruoyi-admin       # Spring Boot 启动模块，包含主入口类 RuoYiApplication
├── ruoyi-framework   # 框架核心：Web 配置、安全认证、Redis、JWT、通用切面
├── ruoyi-system      # 系统业务：用户、角色、菜单、部门、岗位、字典等
├── ruoyi-common      # 通用工具：注解、常量、工具类、分页、异常处理
├── ruoyi-quartz      # 定时任务模块
├── ruoyi-generator   # 代码生成器
└── ruoyi-ui          # Vue 前端项目
```

## 开发命令

### 后端构建与运行

```bash
# 构建全部后端模块（跳过测试）
mvn clean package -DskipTests

# 本地启动后端服务（端口 8080）
mvn -pl ruoyi-admin spring-boot:run

# 生产环境启动/停止/重启/状态
./ry.sh start|stop|restart|status
```

### 前端开发

```bash
cd ruoyi-ui

# 安装依赖
npm install

# 启动开发环境（默认代理到 localhost:8080）
npm run dev

# 生产环境构建
npm run build:prod

# 预览生产构建
npm run preview
```

## 技术要点

### 后端分层规范

- **Controller** (`ruoyi-admin` 和各模块的 `controller` 包)：处理 HTTP 请求，参数校验，调用 Service
- **Service** (`service` 包)：业务逻辑，事务管理
- **Mapper** (`mapper` 包)：MyBatis 数据访问层
- **Domain** (`domain` 包)：实体类，DO（数据库对象）和 DTO（数据传输对象）

### 前端目录结构

- `views/`：页面组件，按业务域划分（system、monitor、tool）
- `components/`：可复用组件
- `api/`：接口封装，每个业务模块一个文件
- `utils/`：公共工具方法
- `store/`：Vuex 状态管理
- `router/`：路由配置

### 依赖与配置

- **数据库**：MySQL，配置在 `application-druid.yml`
- **Redis**：localhost:6379，用于缓存和 Token 存储
- **Token**：JWT，Header `Authorization`，30分钟有效期
- **API 文档**：Swagger UI，路径 `/swagger-ui.html`
- **文件上传路径**：`D:/ruoyi/uploadPath`（需根据系统调整）

### 代码生成

使用内置代码生成器可一键生成前后端 CRUD 代码，生成的文件包括：
- 后端：Java、MyBatis XML
- 前端：Vue 页面、API 封装
- 数据库：SQL 脚本

## 数据库脚本

位于 `sql/` 目录，包含初始化脚本和数据表结构。

## 提交规范

采用"模块前缀 + 动词短语"格式，例如：
- `ruoyi-ui: 修复任务列表筛选`
- `ruoyi-admin: 新增场景接口`

一次提交只处理一类变更，PR 需说明变更范围、影响模块和手工验证步骤。
