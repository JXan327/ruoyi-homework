# Repository Guidelines

## 项目结构与模块划分
本仓库是多模块的 RuoYi-Vue 项目。后端模块位于仓库根目录：`ruoyi-admin` 是 Spring Boot 启动模块，`ruoyi-framework` 负责 Web、安全与配置，`ruoyi-system` 放业务服务与持久层，`ruoyi-common` 放通用工具，`ruoyi-quartz` 与 `ruoyi-generator` 分别负责定时任务和代码生成。前端位于 `ruoyi-ui/src`，其中 `src/views` 是页面，`src/components` 是复用组件，`src/api` 是接口封装，`src/utils` 是公共方法。数据库脚本放在 `sql/`，项目文档和本地资料放在 `doc/`。

## 构建、测试与开发命令
- `mvn clean package -DskipTests`：在仓库根目录构建全部后端模块。
- `mvn -pl ruoyi-admin spring-boot:run`：本地启动后端主服务。
- `cd ruoyi-ui; npm install`：首次拉起前端或依赖失效时安装依赖。
- `cd ruoyi-ui; npm run dev`：启动 Vue 2 前端开发环境。
- `cd ruoyi-ui; npm run build:prod`：构建前端生产包。
- `cd ruoyi-ui; npm run preview`：本地预览前端构建结果。

## 编码风格与命名约定
后端按 `controller`、`service`、`mapper`、`domain` 分层，避免跨层直接耦合。Java 使用 4 空格缩进，类名使用 `UpperCamelCase`，字段和方法使用 `lowerCamelCase`，包名保持在 `com.ruoyi.*` 下。前端遵循 `ruoyi-ui/.editorconfig`：2 空格缩进、UTF-8、LF 换行、去除行尾空格。可复用 Vue 组件使用 `PascalCase` 文件名；页面目录、接口模块按业务域命名，如 `system`、`monitor`、`tool`。

## 测试要求
当前仓库未见完整的一方测试体系，提交前至少做针对性构建验证。后端改动优先执行 `mvn -pl ruoyi-admin -am test`；若相关模块没有测试，则至少保证 `mvn clean package -DskipTests` 可通过。前端改动提交前执行 `cd ruoyi-ui; npm run build:prod`。若新增 Java 测试，文件名使用 `*Test`，放在 `src/test/java`。

## 提交与合并请求规范
当前目录未包含可用的 Git 历史，提交信息请采用“模块前缀 + 动词短语”的格式，例如：`ruoyi-ui: 修复任务列表筛选`、`ruoyi-admin: 新增场景接口`。一次提交只处理一类变更。PR 需要说明变更范围、影响模块、是否涉及数据库或脚本调整、手工验证步骤；界面改动请附截图；有需求单或缺陷单时请关联。

## 安全与配置提示
不要提交真实账号、密码、令牌或环境专属地址。修改前检查 `application*.yml`、`.env.*` 和 `sql/` 下脚本，避免把本地配置带入仓库。除非任务明确要求，否则不要提交 `target/`、`dist/`、`node_modules/` 等构建产物或依赖目录。
