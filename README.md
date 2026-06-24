# inventory-middle

`inventory-middle` 是一个基于 Spring Boot 的库存中台服务，采用多模块分层结构，承载库存、物料凭证、计划、预警、MQ 消费等业务能力。

当前仓库已经完成从历史工程向 `inventory-middle` 的分层迁移，根目录 `README.md` 主要面向本地开发、编译和启动说明。

## 项目结构

本仓库是一个 6 模块 Maven Monorepo：

- `inventory-middle-starter`：Spring Boot 启动模块，服务入口为 `ProviderApplication`
- `inventory-middle-interfaces`：Controller、Facade、MQ Consumer 等接口适配层
- `inventory-middle-application`：应用服务层，负责用例编排和流程协调
- `inventory-middle-domain`：领域逻辑层，包含业务规则、校验、规格和仓储接口
- `inventory-middle-infra`：基础设施层，负责 MyBatis Plus、外部服务调用、MQ Producer、Redis 锁等实现
- `inventory-middle-client`：对外提供的客户端 SDK 模块

主要依赖方向如下：

`starter -> interfaces/application -> domain <- infra`

## 技术栈

- Java 8
- Maven 3.3+
- Spring Boot 2.7.7
- Spring Cloud 2021.0.5
- OpenFeign 3.1.5
- MyBatis Plus 3.5.3
- MySQL 8
- Redis
- RocketMQ 2.2.3
- KDLA Framework 1.0.1-SNAPSHOT
- Lombok
- MapStruct

## 环境要求

本地启动前，请先准备以下环境：

- JDK 1.8
- Maven 3.3 及以上版本
- MySQL，默认连接库为 `inventory`
- Redis，默认地址为 `127.0.0.1:6379`
- RocketMQ，默认 `NameServer` 为 `127.0.0.1:9876`
- 可访问的 `KDLA` 相关依赖仓库或已预装到本地 Maven 仓库

说明：

- 仓库历史上依赖过 Nacos / Apollo 配置中心；当前本地开发以 `inventory-middle-starter/src/main/resources/application.yml` 中的显式配置为主。
- 某些 Plan 模块的 MQ topic、consumer group 和定时任务配置仍保留“以原 Nacos 实际值为准”的说明，联调或部署前需要和目标环境配置保持一致。

## 默认配置

默认配置位于 `inventory-middle-starter/src/main/resources/application.yml`。

关键默认值如下：

- 服务端口：`8081`
- 服务上下文：`/inventory`
- MySQL：`jdbc:mysql://127.0.0.1:3306/inventory`
- MySQL 用户名：`root`
- MySQL 密码：`root`
- Redis：`127.0.0.1:6379`
- RocketMQ NameServer：`127.0.0.1:9876`
- 商品中心默认地址：`${PRODUCT_CENTER_URL:http://localhost:8082/product}`

如果需要对接其他环境，优先通过环境变量或 `application-dev.yml` 进行覆盖，不要直接修改默认配置。

## 构建项目

建议使用 Java 8 执行构建。

完整编译安装：

```bash
mvn clean install -DskipTests
```

仅编译检查：

```bash
mvn clean compile -DskipTests
```

只构建单个模块，例如领域层：

```bash
mvn clean install -DskipTests -pl inventory-middle-domain
```

说明：

- 当前仓库没有完善的自动化测试覆盖。
- 即使执行 `mvn test`，本地若缺少 MySQL、Redis、RocketMQ、Nacos 等依赖环境，也无法完成有效的集成验证。

## 启动服务

服务启动入口：

- 启动类：`com.inventory.middle.starter.ProviderApplication`
- 所在模块：`inventory-middle-starter`

在 IDE 中直接运行 `ProviderApplication` 即可启动服务。

如需通过 Maven 方式启动，可先完成编译后再运行对应启动类。

启动成功后，默认访问地址为：

```text
http://localhost:8081/inventory
```

## 运行时能力说明

应用启动后会自动启用以下能力：

- Spring MVC Web 服务
- MyBatis Mapper 扫描
- OpenFeign 客户端
- 定时任务能力
- AOP 切面代理
- RocketMQ Producer / Consumer
- KDLA 统一异常、日志、链路追踪能力

项目中大量使用以下 KDLA 注解和约定：

- `@CatchAndLog`：统一方法日志和异常处理
- `@StopWatchWrapper`：方法耗时统计
- `@MdcDot`：链路追踪与 `traceId` 注入

## 开发说明

### 1. 接口开发

新增接口时，通常遵循以下分层：

1. 在 `inventory-middle-interfaces` 编写 Controller 或 Facade
2. 调用 `inventory-middle-application` 中的应用服务
3. 由应用层协调领域对象和仓储接口
4. 在 `inventory-middle-infra` 中落地数据库或外部系统调用

### 2. 持久化说明

- MyBatis Mapper XML 路径：`classpath*:/mapper/**/*.xml`
- 实体别名包：`com.inventory.middle.infra.persistence.entity`
- 逻辑删除字段：`deleted`

### 3. 外部系统调用

当前仓库通过 OpenFeign 对接外部服务，相关地址在 `remote.*` 配置下维护，例如：

- `remote.product.center.url`
- `remote.uniformPush.url`
- `remote.spDelivery.url`
- `remote.crm.url`

## 常见问题

### 1. Java 版本不匹配

现象：编译报错，或 Lombok 处理器异常。

处理方式：

- 优先使用 JDK 1.8 编译和启动
- 确认 IDE 和 Maven 使用的是同一套 Java 版本

### 2. 启动时报数据库或 Redis 连接失败

现象：应用在 Spring 容器初始化阶段失败。

处理方式：

- 检查 MySQL 是否已启动
- 检查本地是否存在 `inventory` 数据库
- 检查 Redis 是否已启动并监听 `6379` 端口
- 如本地端口不同，请覆盖 `application.yml` 中对应配置

### 3. RocketMQ 初始化失败

现象：MQ 相关 Bean 初始化异常，或消费者无法订阅。

处理方式：

- 检查 `127.0.0.1:9876` 是否可用
- 核对 topic、group 是否与目标环境一致
- 对于 Plan 相关 Consumer，确认占位配置是否已替换为真实值

### 4. 内部依赖无法下载

现象：Maven 构建时报 `top.kdla.framework` 相关依赖缺失。

处理方式：

- 确认本地 Maven `settings.xml` 已配置可访问的私服
- 或提前将 `KDLA` 组件安装到本地仓库

### 5. MapStruct 相关代码不生效

说明：当前仓库中 `starter` 模块的编译器注解处理器配置处于注释状态，调整映射代码时请先确认是否依赖自动生成实现。

## 关键文件

- `pom.xml`：父工程与版本管理
- `inventory-middle-starter/src/main/java/com/inventory/middle/starter/ProviderApplication.java`：服务启动入口
- `inventory-middle-starter/src/main/resources/application.yml`：核心配置文件
- `docs/sql/`：数据库脚本
- `docs/superpowers/plans/`：迁移背景和实施记录

## 备注

如果只是本地快速启动，最小步骤如下：

1. 准备 JDK 1.8 和 Maven 3.3+
2. 启动 MySQL、Redis、RocketMQ
3. 确认 `KDLA` 相关依赖可下载
4. 执行 `mvn clean install -DskipTests`
5. 在 IDE 中运行 `ProviderApplication`
