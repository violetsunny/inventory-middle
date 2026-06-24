# 迁移计划文档索引

## 迁移项目总览

| 源项目 | 计划文件                                                                 | 状态 |
|--------|----------------------------------------------------------------------|------|
| inventory-center + inventory-center-ext | [inventory-center-migration.md](plans/inventory-center-migration.md) | ✅ 已完成 |
| inventory-center-bff | [inventory-bff-migration.md](plans/inventory-bff-migration.md)       | ✅ 已完成 |
| scm-plan-management + scm-plan-bff | [inventory-scm-plan-migration.md](plans/inventory-scm-plan-migration.md)    | ✅ 代码迁移、全量编译和 mock 验证已完成 |

## 补充计划

| 文件 | 说明 | 状态 |
|------|------|------|
| [remaining-todos.md](plans/remaining-todos.md) | 剩余 TODO：迁移代码缺口已基本清零，当前仅剩 **M8（外部 URL 部署配置）** | 1 项未完成 |

## 当前结论

- `inventory-center` 和 `inventory-center-ext` 已完成迁移。
- `inventory-center-bff` 已完成迁移。
- `scm-plan-management` 和 `scm-plan-bff` 已完成代码迁移，并通过全量编译和 mock 验证。
- 当前剩余项不再是代码迁移缺口，而是部署配置项 `M8`：`application.yml` 中部分 `remote.*.url` 仍需在目标环境提供真实值。

## SQL 脚本

| 文件 | 说明 |
|------|------|
| [docs/sql/inventory.sql](../sql/inventory.sql) | inventory 表 DDL（已有） |
| [docs/sql/plan.sql](../sql/plan.sql) | plan 表 DDL（17 张表 + 5 条序列号配置） |

## 目录结构

```
docs/superpowers/
├── README.md                              # 本文件
└── plans/
    ├── inventory-center-migration.md      # inventory-center 完整迁移计划（已合并原 migration-plan.md + full-migration.md）
    ├── inventory-bff-migration.md        # BFF 迁移计划
    ├── inventory-scm-plan-migration.md   # SCM Plan 迁移计划
    └── remaining-todos.md                 # 剩余 TODO + KDLA 对齐任务
```
