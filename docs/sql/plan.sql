-- ============================================================
-- SCM Plan 模块 DDL（迁移自 scm-plan-management）
-- 所有表使用 pl_ 前缀，与 inventory-middle 现有表并列
-- ============================================================

-- -------------------------------------------------------
-- 1. 计划方案表
-- -------------------------------------------------------
CREATE TABLE `pl_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_code` varchar(64) NOT NULL DEFAULT '' COMMENT '方案编码',
  `plan_desc` varchar(512) NOT NULL DEFAULT '' COMMENT '方案描述',
  `plan_type` int(11) NOT NULL DEFAULT '0' COMMENT '方案类型',
  `cover_logical_plant` varchar(2048) NOT NULL DEFAULT '' COMMENT '覆盖逻辑仓（JSON数组）',
  `cover_material_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '覆盖物料类型（0-全部/1-指定）',
  `plan_horizon` int(11) NOT NULL DEFAULT '0' COMMENT '计划展望期(天)',
  `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `demand_plan_file` varchar(2048) DEFAULT '' COMMENT '需求计划文件（JSON）',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0-已失效/1-已生效）',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `plan_calculate_params` text COMMENT '计算参数（JSON）',
  `plan_delivery_params` text COMMENT '投放参数（JSON）',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人',
  `related_bom` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否关联bom(0:不关联,1:关联)',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plan_code` (`plan_code`),
  KEY `idx_tenant_status` (`tenant_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划方案表';

-- -------------------------------------------------------
-- 2. 计划物料清单表
-- -------------------------------------------------------
CREATE TABLE `pl_plan_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '计划方案ID',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '计划物料的使用场景标识',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户Id',
  PRIMARY KEY (`id`),
  KEY `idx_source_id` (`source_id`),
  KEY `idx_material_plant` (`material_code`, `logical_plant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划物料清单表';

-- -------------------------------------------------------
-- 3. 计划物料参数表
-- -------------------------------------------------------
CREATE TABLE `pl_plan_material_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `plan_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '计划类型（0:采购,1:调拨,2:生产）',
  `demand_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '物料需求类型',
  `demand_strategy_code` varchar(64) NOT NULL DEFAULT '' COMMENT '需求策略编码',
  `vendor_lead_time` int(11) NOT NULL DEFAULT '0' COMMENT '物料提前期',
  `planning_time_fence` int(11) NOT NULL DEFAULT '0' COMMENT '计划时界',
  `demand_time_fence` int(11) NOT NULL DEFAULT '0' COMMENT '需求时界',
  `order_quantity` bigint(20) NOT NULL DEFAULT '0' COMMENT '订货批量',
  `min_order_quantity` bigint(20) NOT NULL DEFAULT '0' COMMENT '最小订货批量',
  `order_cycle_time` int(11) NOT NULL DEFAULT '0' COMMENT '订货周期',
  `safety_stock_cal_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '安全库存计算方式',
  `safety_stock` bigint(20) NOT NULL DEFAULT '0' COMMENT '安全库存',
  `safety_stock_factors` varchar(1024) NOT NULL DEFAULT '' COMMENT '安全库存公式计算参数',
  `loss_rate` int(11) NOT NULL DEFAULT '0' COMMENT '损耗率',
  `finished_rate` int(11) NOT NULL DEFAULT '0' COMMENT '成品率',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人姓名',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户Id',
  `material_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '物料描述',
  `transfer_logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '调拨逻辑仓',
  PRIMARY KEY (`id`),
  KEY `idx_material_plant` (`material_code`, `logical_plant_no`),
  KEY `idx_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划物料参数表';

-- -------------------------------------------------------
-- 4. 计划实例表
-- -------------------------------------------------------
CREATE TABLE `pl_plan_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '方案id',
  `plan_code` varchar(64) NOT NULL DEFAULT '' COMMENT '方案编码',
  `plan_version` varchar(64) NOT NULL DEFAULT '' COMMENT '计划版本号',
  `plan_type` int(11) NOT NULL DEFAULT '0' COMMENT '方案类型',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '计划执行结果（0-未开始/1-执行中/2-已失败/3-已完结）',
  `cal_start_time` datetime DEFAULT NULL COMMENT '计划执行开始时间',
  `cal_end_time` datetime DEFAULT NULL COMMENT '计划执行结束时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_plan_code` (`plan_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划实例表';

-- -------------------------------------------------------
-- 5. 物料计划实例表
-- -------------------------------------------------------
CREATE TABLE `pl_material_plan_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `instance_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '实例id',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '物料描述',
  `material_level` varchar(64) NOT NULL DEFAULT '' COMMENT '物料层级',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `demand_strategy_code` varchar(64) NOT NULL DEFAULT '' COMMENT '需求响应策略编码',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '物料计划状态',
  `root_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '根节点id',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父节点id',
  `ext_attrs` text COMMENT '物料计划扩展属性',
  `gene_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '产出类型',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `idx_instance_id` (`instance_id`),
  KEY `idx_material_plant` (`material_code`, `logical_plant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料计划实例表';

-- -------------------------------------------------------
-- 6. 物料计划详情表
-- -------------------------------------------------------
CREATE TABLE `pl_material_plan_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_instance_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '计划实例详情id',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '物料描述',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `plan_date` datetime DEFAULT NULL COMMENT '计划日期',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `plan_detail` text COMMENT '计划详情（JSON）',
  PRIMARY KEY (`id`),
  KEY `idx_material_instance_id` (`material_instance_id`),
  KEY `idx_material_plant_date` (`material_code`, `logical_plant_no`, `plan_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料计划详情表';

-- -------------------------------------------------------
-- 7. 计划订单表
-- -------------------------------------------------------
CREATE TABLE `pl_plan_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单编号',
  `plan_code` varchar(64) NOT NULL DEFAULT '' COMMENT '方案编码',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '物料描述',
  `external_material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '外部物料编码',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `plan_type` int(11) NOT NULL DEFAULT '0' COMMENT '计划类型',
  `create_type` int(11) NOT NULL DEFAULT '0' COMMENT '创建类型',
  `transfer_logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '调拨逻辑仓',
  `forecast_inventory` int(11) NOT NULL DEFAULT '0' COMMENT '预测库存',
  `plan_order_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '计划订单量',
  `unit` varchar(32) NOT NULL DEFAULT '' COMMENT '单位',
  `issue_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '下发量',
  `confirm_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '确认量',
  `finish_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '完成量',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `plan_issue_time` datetime DEFAULT NULL COMMENT '计划下发时间',
  `real_issue_time` datetime DEFAULT NULL COMMENT '实际下发时间',
  `plan_receiving_time` datetime DEFAULT NULL COMMENT '计划收货时间',
  `real_receiving_time` datetime DEFAULT NULL COMMENT '实际收货时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `planner_name` varchar(64) NOT NULL DEFAULT '' COMMENT '计划员名称',
  `planner_id` varchar(32) NOT NULL DEFAULT '' COMMENT '计划员id',
  `demand_info` varchar(1024) NOT NULL DEFAULT '' COMMENT '需求信息',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `ext_attrs` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展属性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_plan_code` (`plan_code`),
  KEY `idx_material_plant` (`material_code`, `logical_plant_no`),
  KEY `idx_tenant_status` (`tenant_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划订单表';

-- -------------------------------------------------------
-- 8. 计划订单下发详情表
-- -------------------------------------------------------
CREATE TABLE `pl_plan_order_issue_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `issue_no` varchar(64) NOT NULL DEFAULT '' COMMENT '下发编号',
  `plan_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '计划订单id',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `issue_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '下发量',
  `finish_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '完成量',
  `current_status` varchar(32) NOT NULL DEFAULT '' COMMENT '当前状态',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `operator_name` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人',
  PRIMARY KEY (`id`),
  KEY `idx_plan_order_id` (`plan_order_id`),
  KEY `idx_issue_no` (`issue_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划订单下发详情表';

-- -------------------------------------------------------
-- 9. 计划订单回执表
-- -------------------------------------------------------
CREATE TABLE `pl_plan_order_receipt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_no` varchar(64) NOT NULL DEFAULT '' COMMENT '业务编号',
  `source_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '来源类型',
  `source_no` varchar(64) NOT NULL DEFAULT '' COMMENT '来源单号',
  `real_receiving_time` datetime DEFAULT NULL COMMENT '实际收货时间',
  `finish_quantity` int(11) NOT NULL DEFAULT '0' COMMENT '完成量',
  `current_status` varchar(32) NOT NULL DEFAULT '' COMMENT '当前状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `original_info` text COMMENT '原始信息（JSON）',
  PRIMARY KEY (`id`),
  KEY `idx_biz_no` (`biz_no`),
  KEY `idx_source` (`source_type`, `source_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划订单回执表';

-- -------------------------------------------------------
-- 10. 需求计划表
-- -------------------------------------------------------
CREATE TABLE `pl_demand_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_code` varchar(64) NOT NULL DEFAULT '' COMMENT '公司主体',
  `company_name` varchar(256) NOT NULL DEFAULT '' COMMENT '公司名字',
  `plan_version` varchar(64) NOT NULL DEFAULT '' COMMENT '需求计划版本号',
  `demand_source_type` int(11) NOT NULL DEFAULT '0' COMMENT '1:手工导入 2:系统对接',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `warehouse_no` varchar(64) NOT NULL DEFAULT '' COMMENT '物理仓编码',
  `warehouse_name` varchar(256) NOT NULL DEFAULT '' COMMENT '物理仓名称',
  `aggregation_period` int(11) NOT NULL DEFAULT '0' COMMENT '需求汇总周期 1:日,2:周,3:月',
  `demand_horizon_begin_time` datetime DEFAULT NULL COMMENT '需求展望期开始时间',
  `demand_horizon_end_time` datetime DEFAULT NULL COMMENT '需求展望期结束时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0-已失效/1-已生效）',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `demand_type` int(11) NOT NULL DEFAULT '0' COMMENT '1:预测',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_status` (`tenant_id`, `status`),
  KEY `idx_logical_plant` (`logical_plant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='需求计划表';

-- -------------------------------------------------------
-- 11. 需求计划物料表
-- -------------------------------------------------------
CREATE TABLE `pl_demand_plan_material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `demand_plan_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '需求计划主键,关联pl_demand_plan表',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '物料名称',
  `material_unit` varchar(32) NOT NULL DEFAULT '' COMMENT '单位',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0-已失效/1-已生效/2-已剔除）',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `idx_demand_plan_id` (`demand_plan_id`),
  KEY `idx_material_plant` (`material_code`, `logical_plant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='需求计划物料表';

-- -------------------------------------------------------
-- 12. 需求计划物料详情表
-- -------------------------------------------------------
CREATE TABLE `pl_demand_plan_material_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '物料描述',
  `material_unit` varchar(32) NOT NULL DEFAULT '' COMMENT '物料单位',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `plan_date` datetime DEFAULT NULL COMMENT '计划日期',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '数量',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '数据类型 1:预测需求 2:订单需求 3:冲销',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `idx_material_plant_date` (`material_code`, `logical_plant_no`, `plan_date`),
  KEY `idx_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='需求计划物料详情表';

-- -------------------------------------------------------
-- 13. 需求计划物料周期表
-- -------------------------------------------------------
CREATE TABLE `pl_demand_plan_material_period` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `demand_plan_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '需求计划主键',
  `demand_plan_material_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '需求计划物料主键,关联pl_demand_plan_material表',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `plan_period` varchar(128) NOT NULL DEFAULT '' COMMENT '计划日期描述,格式如2021/9/28-2021/10/3',
  `plan_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '预测需求数量',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0-已失效/1-已生效/2-已剔除）',
  `demand_type` int(11) NOT NULL DEFAULT '0' COMMENT '1:预测 2:项目订单',
  `ext_info` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展信息json',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_demand_plan_id` (`demand_plan_id`),
  KEY `idx_demand_plan_material_id` (`demand_plan_material_id`),
  KEY `idx_material_plant` (`material_code`, `logical_plant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='需求计划物料周期表';

-- -------------------------------------------------------
-- 14. 供需来源表
-- -------------------------------------------------------
CREATE TABLE `pl_demand_supply_source` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `plan_date` datetime DEFAULT NULL COMMENT '日期',
  `source_type` int(11) NOT NULL DEFAULT '0' COMMENT '来源类型（2:客户独立需求,3:计划独立需求,4:BOM相关需求,5:计划订单,6:采购申请,7:采购订单,8:交运单）',
  `biz_type` int(11) NOT NULL DEFAULT '0' COMMENT '供需类型（1:需求,2:供给）',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '数量',
  `voucher_no` varchar(64) NOT NULL DEFAULT '' COMMENT '单据',
  `document_no` varchar(64) NOT NULL DEFAULT '' COMMENT '行号',
  `business_id_ref` varchar(64) NOT NULL DEFAULT '' COMMENT '关联业务id',
  `exception_code` varchar(64) NOT NULL DEFAULT '' COMMENT '异常码',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_material_plant_date` (`material_code`, `logical_plant_no`, `plan_date`),
  KEY `idx_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供需来源表';

-- -------------------------------------------------------
-- 15. 订单需求表
-- -------------------------------------------------------
CREATE TABLE `pl_order_demand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(64) NOT NULL DEFAULT '' COMMENT '订单号',
  `order_type` int(11) NOT NULL DEFAULT '0' COMMENT '来源类型,1:项目订单',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `plan_date` datetime DEFAULT NULL COMMENT '计划日期',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '计划数量',
  `ext_info` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展信息',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_material_plant_date` (`material_code`, `logical_plant_no`, `plan_date`),
  KEY `idx_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单需求表';

-- -------------------------------------------------------
-- 16. BOM方案表
-- -------------------------------------------------------
CREATE TABLE `pl_bom_case` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT 'BOM编码',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT 'BOM名称',
  `company_code` varchar(64) NOT NULL DEFAULT '' COMMENT '公司主体代码',
  `company_name` varchar(256) NOT NULL DEFAULT '' COMMENT '公司主体名称',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT 'BOM类型',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `create_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人姓名',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_tenant_status` (`tenant_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM方案表';

-- -------------------------------------------------------
-- 17. BOM节点表
-- -------------------------------------------------------
CREATE TABLE `pl_bom_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bom_case_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'BOM方案id',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_desc` varchar(256) NOT NULL DEFAULT '' COMMENT '物料描述',
  `material_unit` varchar(32) NOT NULL DEFAULT '' COMMENT '物料单位',
  `material_attr` varchar(256) NOT NULL DEFAULT '' COMMENT '物料属性',
  `material_spec` varchar(256) NOT NULL DEFAULT '' COMMENT '物料规格',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓名称',
  `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '数量',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '节点类型',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `idx_bom_case_id` (`bom_case_id`),
  KEY `idx_material_code` (`material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM节点表';

-- -------------------------------------------------------
-- 流水号配置（plan 模块专属 code）
-- -------------------------------------------------------
INSERT INTO code_generator_cfg(code, name, rule, is_cache, cache_num) VALUES
('PLAN_CODE',              '计划方案编码',   'PL+yyyyMMdd+6',    1, 100),
('PLAN_ORDER_NO',          '计划订单编号',   'PO+yyyyMMdd+6',    1, 100),
('PLAN_ORDER_ISSUE_NO',    '计划订单下发编号','PI+yyyyMMdd+6',   1, 100),
('BOM_CASE_CODE',          'BOM方案编码',    'BOM+yyyyMMdd+6',   1, 100),
('DEMAND_PLAN_VERSION',    '需求计划版本号',  'DP+yyyyMMdd+6',   1, 100);
