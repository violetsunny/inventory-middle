# 迁移计划文档索引

## 迁移项目总览

| 源项目 | 计划文件                                                                 | 状态 |
|--------|----------------------------------------------------------------------|------|
| inventory-center + inventory-center-ext | [inventory-center-migration.md](plans/inventory-center-migration.md) | ✅ 已完成 |
| inventory-center-bff | [inventory-bff-migration.md](plans/inventory-bff-migration.md)       | ✅ 主体已完成（R1-R12 全部完成），缺口见 remaining-todos.md |
| scm-plan-management + scm-plan-bff | [inventory-scm-plan-migration.md](plans/inventory-scm-plan-migration.md)    | ✅ 代码迁移+编译已完成，冒烟验证待完成（组 O 步骤 2-4） |

## 补充计划

| 文件 | 说明 | 状态 |
|------|------|------|
| [remaining-todos.md](plans/remaining-todos.md) | 剩余 TODO 实现计划（A-K / L1-L6 / P1-P10 / R1-R12 / N1-N5 全部完成）；BFF 缺口（O1-O5/V1-V4）待跟进 | BFF 缺口 + SCM 冒烟验证待完成 |

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
