# INDEX.md — inventory-middle-starter 模块索引

> Starter 层：Spring Boot 启动入口，配置文件。

---

## 包结构

```
com.inventory.middle.starter
└── ProviderApplication.java   # Spring Boot 主类
```

---

## 关键文件

| 文件 | 说明 |
|---|---|
| `ProviderApplication.java` | `@SpringBootApplication`，扫描 `top.kdla.framework` + `com.inventory.middle` |
| `src/main/resources/application.yml` | 全量配置：DB、Redis、RocketMQ、KDLA |
| `src/main/resources/application-dev.yml` | 开发环境覆盖 |

---

## ProviderApplication 特性

- `@EnableFeignClients` — 启用 OpenFeign
- `@EnableScheduling` — 启用定时任务
- 显式扫描包：`top.kdla.framework` + `com.inventory.middle`
- **MapStruct 注解处理器被注释掉**（pom.xml line 64-80）— 可能不生效

---

## 配置速查 (application.yml)

| 配置项 | 默认值 |
|---|---|
| Server port | 8081 |
| Context path | `/inventory` |
| MySQL | `root:root@localhost:3306/inventory` |
| Redis | `localhost:6379` (no password) |
| RocketMQ nameserver | `127.0.0.1:9876` |
| Log level (com.inventory.middle) | DEBUG |
| MyBatis Plus log | INFO |

---

*Last updated: 2026-07-02*
