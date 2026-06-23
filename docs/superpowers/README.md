# 迁移计划文档索引

## 迁移项目总览

| 源项目 | 计划文件                                                                 | 状态 |
|--------|----------------------------------------------------------------------|------|
| inventory-center + inventory-center-ext | [inventory-center-migration.md](plans/inventory-center-migration.md) | ✅ 已完成 |
| inventory-center-bff | [inventory-bff-migration.md](plans/inventory-bff-migration.md)       | ✅ 已完成（可能遗漏 3 个 Controller，见 remaining-todos X1-X3） |
| scm-plan-management + scm-plan-bff | [inventory-scm-plan-migration.md](plans/inventory-scm-plan-migration.md)    | ✅ 代码迁移+编译+mock验证已完成；功能缺口见 remaining-todos H1-H11 |

## 补充计划

| 文件 | 说明 | 状态 |
|------|------|------|
| [remaining-todos.md](plans/remaining-todos.md) | 剩余 TODO：已完成 A-K/L/P/R/N/O/V 全系列；**未完成 H1-H11（高）/ M1-M8（中）/ X1-X3（待确认）/ L1-L3（低）** | 24 项未完成 |

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
