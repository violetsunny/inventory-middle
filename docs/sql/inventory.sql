CREATE TABLE `warehouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '创建该物理仓库的租户id',
  `warehouse_no` varchar(64) NOT NULL DEFAULT '' COMMENT '物理仓库编码',
  `warehouse_name` varchar(256) NOT NULL DEFAULT '' COMMENT '物理仓库名称',
  `warehouse_type` tinyint(8) NOT NULL DEFAULT '1' COMMENT '物理仓类型(1-真实,0-虚拟)',
  `owner_name` varchar(256) NOT NULL DEFAULT '' COMMENT '责任人名称',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '仓库电话',
  `address` varchar(256) NOT NULL DEFAULT '' COMMENT '仓库地址',
  `province` varchar(256) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(256) NOT NULL DEFAULT '' COMMENT '市',
  `region` varchar(256) NOT NULL DEFAULT '' COMMENT '区',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '备注',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_warehouseNo` (`warehouse_no`),
  KEY `uk_tenantId_warehouseName` (`tenant_id`,`warehouse_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物理仓库表';

CREATE TABLE `logical_plant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '创建该逻辑仓库的租户id',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓库编码',
  `logical_plant_name` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓库名称',
  `type` tinyint(8) NOT NULL DEFAULT '0' COMMENT '逻辑仓库类型',
  `company_code` varchar(64) NOT NULL DEFAULT '' COMMENT '公司主体代码',
  `warehouse_no` varchar(64) DEFAULT NULL COMMENT '该逻辑仓所属的物理仓no',
  `province` varchar(256) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(256) NOT NULL DEFAULT '' COMMENT '市',
  `region` varchar(256) NOT NULL DEFAULT '' COMMENT '区',
  `address` varchar(256) NOT NULL DEFAULT '' COMMENT '逻辑仓库地址',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_logicalPlantNo` (`logical_plant_no`),
  UNIQUE KEY `uk_tenantId_logicalPlantName` (`tenant_id`,`logical_plant_name`),
  KEY `idx_companyCode` (`company_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='逻辑仓库表';

CREATE TABLE `storage_location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '创建该存储地点的租户id',
  `storage_location_no` varchar(64) NOT NULL DEFAULT '' COMMENT '存储地点编码',
  `storage_location_name` varchar(256) NOT NULL DEFAULT '' COMMENT '存储地点名称',
  `logical_plant_no` bigint(20) NOT NULL COMMENT '逻辑仓库no',
  `location_type` tinyint(8) NOT NULL DEFAULT '0' COMMENT '存储地点类型 0-普通存储地点；1-客户寄售；2-待回收包装；3-委外加工物资；4-供应商寄售；5-待退回包装；6-销售订单',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `position` varchar(256) NOT NULL DEFAULT '' COMMENT '存储地点位置',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_storageLocationNo` (`storage_location_no`),
  UNIQUE KEY `uk_tenantId_logicalPlant_description` (`tenant_id`,`logical_plant_no`,`storage_location_name`),
  KEY `idx_logicalPlant_type` (`logical_plant_no`,`location_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储地点表';

CREATE TABLE `code_generator_cfg` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键(自增处理)',
`code` varchar(64) NOT NULL COMMENT '流水号唯一code',
`name` varchar(64) NOT NULL DEFAULT '' COMMENT '流水号名称',
`epoch` bigint(20) DEFAULT '0' COMMENT '当前编号池的纪元',
`max_value` varchar(32) NOT NULL DEFAULT '' COMMENT '最大流水号',
`rule` text COMMENT '流水号规则',
`is_cache` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否缓存 0：否  ， 1：是',
`cache_num` int(11) NOT NULL DEFAULT '0' COMMENT '缓存数目',
`env_value` varchar(32) NOT NULL DEFAULT 'DEV' COMMENT '环境变量值',
`is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0有效 1无效',
`remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_key` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流水号配置表';

INSERT INTO code_generator_cfg(code, rule, is_cache, cache_num) VALUES
('WAREHOUSE_NO',      'WH+yyyyMMdd+6',   1, 100),
('LOGICAL_PLANT_NO',  'LP+yyyyMMdd+6',   1, 100),
('MONITOR_RULE_NO',   'MR+yyyyMMdd+6',   1, 100),
('MATERIAL_DOC_NO',   'INV+yyyyMMdd+6',  1, 100),
('MATERIAL_BATCH_NO', 'MB+yyyyMMdd+6',   1, 100),
('MAP_CODE',          'MAP+yyyyMMdd+6',  1, 100),
('MAP_SUB_CODE',      'MAPS+yyyyMMdd+6', 1, 100);
-- STORAGE_LOCATION_NO 按实际 locationTypeMark 值各插一行，如：
-- ('STORAGE_LOCATION_NO_RACK', 'RACK+yyyyMMdd+6', 1, 100)


CREATE TABLE `inventory_demand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '需求id',
  `material_code` varchar(64) NOT NULL COMMENT '物料code',
  `batch_no` varchar(64) NOT NULL COMMENT '批次号',
  `batch_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '批次出库时间',
  `owner` varchar(32) NOT NULL COMMENT '库存所有者',
  `logical_plant_no` varchar(64) NOT NULL COMMENT '所在仓库',
  `storage_location_no` varchar(64) NOT NULL COMMENT '存储地点',
  `unrestricted` decimal(20,4) DEFAULT NULL COMMENT '正品数量',
  `damaged` decimal(20,4) DEFAULT NULL COMMENT '次品数量',
  `inspection` decimal(20,4) DEFAULT NULL COMMENT '质检数量',
  `uom` varchar(32) NOT NULL DEFAULT '' COMMENT '计量单位',
  `cancel_date` datetime NOT NULL COMMENT '需求取消日期',
  `deadline_date` datetime NOT NULL COMMENT '需求过期日期',
  `currency` varchar(32) NOT NULL DEFAULT '' COMMENT '货币单位',
  `demand_type` tinyint(8) NOT NULL DEFAULT '1' COMMENT '需求类型1-立即出库，2-在途转出库，3-计划转出库',
  `demand_price` decimal(20,4) DEFAULT NULL COMMENT '出库价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` varchar(32) NOT NULL COMMENT '创建人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updator_id` varchar(32) NOT NULL COMMENT '修改人id',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `tenant_id` varchar(32) NOT NULL COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `idx_tid_mcode_bno_lgicno_lcno` (`tenant_id`,`material_code`,`batch_no`,`logical_plant_no`,`storage_location_no`) COMMENT '物料租户联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存-需求';

CREATE TABLE `inventory_supply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '供应id',
  `material_code` varchar(64) NOT NULL COMMENT '物料code',
  `batch_no` varchar(64) NOT NULL COMMENT '批次号',
  `batch_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '批次入库时间',
  `owner` varchar(32) NOT NULL COMMENT '库存所有者',
  `logical_plant_no` varchar(64) NOT NULL COMMENT '所在仓库',
  `storage_location_no` varchar(64) NOT NULL COMMENT '存储地点',
  `unrestricted` decimal(20,4) DEFAULT NULL COMMENT '良品数量',
  `damaged` decimal(20,4) DEFAULT NULL COMMENT '次品数量',
  `inspection` decimal(20,4) DEFAULT NULL COMMENT '质检数量',
  `uom` varchar(32) NOT NULL DEFAULT '' COMMENT '计量单位',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `available_date` datetime DEFAULT NULL COMMENT '最早可用日期',
  `due_date` datetime DEFAULT NULL COMMENT '过期日期',
  `batch_price` decimal(20,4) DEFAULT NULL COMMENT '批次价格',
  `currency` varchar(32) DEFAULT '' COMMENT '货币单位',
  `supply_type` tinyint(8) NOT NULL DEFAULT '1' COMMENT '供应类型1-立即入库，2-在途转入库，3-计划转入库',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` varchar(32) NOT NULL COMMENT '创建人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updator_id` varchar(32) NOT NULL COMMENT '修改人id',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `tenant_id` varchar(32) NOT NULL COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `idx_tid_mcode_bno_lgicno_lcno` (`tenant_id`,`material_code`,`batch_no`,`logical_plant_no`,`storage_location_no`) USING BTREE COMMENT '租户物料联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存-供给';

CREATE TABLE `inventory_snapshot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库存快照主键',
  `material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料code',
  `material_category_code` varchar(64) NOT NULL DEFAULT '' COMMENT '物料类目',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓库no',
  `storage_location_no` varchar(64) DEFAULT '' COMMENT '存储地点no',
  `batch_no` varchar(64) NOT NULL DEFAULT '' COMMENT '批次号',
  `batch_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '批次入库时间',
  `batch_price` decimal(20,4) NOT NULL COMMENT '批次价格',
  `currency` varchar(32) NOT NULL DEFAULT '' COMMENT '货币',
  `unrestricted` decimal(20,4) NOT NULL COMMENT '正品库存',
  `damaged` decimal(20,4) NOT NULL COMMENT '次品库存',
  `inspection` decimal(20,4) NOT NULL COMMENT '质检库存',
  `uom` varchar(32) NOT NULL DEFAULT '' COMMENT '计量单位',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `due_date` datetime DEFAULT NULL COMMENT '过期日期',
  `owner` varchar(32) DEFAULT '0' COMMENT '库存所有者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updator_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `tenant_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_material_plant_batchTime` (`material_code`,`logical_plant_no`,`batch_time`),
  KEY `idx_tid_mcode_bno_lgicno_lcno` (`tenant_id`,`material_code`,`batch_no`,`logical_plant_no`,`storage_location_no`) USING BTREE COMMENT '物料租户联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存快照-实时';


CREATE TABLE `inventory_transit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '在途id',
  `material_code` varchar(64) NOT NULL COMMENT '物料code',
  `logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓库no',
  `storage_location_no` varchar(64) DEFAULT '' COMMENT '存储地点no',
  `unrestricted` decimal(20,4) DEFAULT NULL COMMENT '良品数量',
  `damaged` decimal(20,4) DEFAULT NULL COMMENT '次品数量',
  `inspection` decimal(20,4) DEFAULT NULL COMMENT '质检数量',
  `uom` varchar(32) NOT NULL DEFAULT '' COMMENT '计量单位',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `due_date` datetime DEFAULT NULL COMMENT '过期日期',
  `price` decimal(20,4) DEFAULT NULL COMMENT '价格',
  `currency` varchar(32) DEFAULT '' COMMENT '货币单位',
  `transit_type` tinyint(8) DEFAULT '0' COMMENT '在途类型1-入库在途 2-出库在途',
  `delivery_no` varchar(64) NOT NULL COMMENT '交运单编号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_id` varchar(32) NOT NULL COMMENT '创建人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `updator_id` varchar(32) NOT NULL COMMENT '修改人id',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  `tenant_id` varchar(32) NOT NULL COMMENT '租户id',
  PRIMARY KEY (`id`),
  KEY `idx_tid_mcode_bno_lgicno_lcno` (`tenant_id`,`material_code`,`logical_plant_no`,`storage_location_no`) USING BTREE COMMENT '租户物料联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存-在途';

CREATE TABLE `inventory_plan` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '在途id',
`material_code` varchar(64) NOT NULL COMMENT '物料code',
`logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓库no',
`storage_location_no` varchar(64) DEFAULT '' COMMENT '存储地点no',
`unrestricted` decimal(20,4) DEFAULT NULL COMMENT '良品数量',
`damaged` decimal(20,4) DEFAULT NULL COMMENT '次品数量',
`inspection` decimal(20,4) DEFAULT NULL COMMENT '质检数量',
`uom` varchar(32) NOT NULL DEFAULT '' COMMENT '计量单位',
`product_date` datetime DEFAULT NULL COMMENT '生产日期',
`due_date` datetime DEFAULT NULL COMMENT '过期日期',
`price` decimal(20,4) DEFAULT NULL COMMENT '价格',
`currency` varchar(32) DEFAULT '' COMMENT '货币单位',
`plan_type` tinyint(8) DEFAULT '0' COMMENT '在途类型1-计划入库 2-计划出库',
`plan_no` varchar(64) NOT NULL COMMENT '计划编号',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` varchar(32) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
`updator_id` varchar(32) NOT NULL COMMENT '修改人id',
`deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`tenant_id` varchar(32) NOT NULL COMMENT '租户id',
PRIMARY KEY (`id`),
KEY `idx_tid_mcode_bno_lgicno_lcno` (`tenant_id`,`material_code`,`logical_plant_no`,`storage_location_no`) USING BTREE COMMENT '租户物料联合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存-计划';

CREATE TABLE `material_doc_main` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物料凭证Id',
 `material_doc_no` varchar(32) NOT NULL COMMENT '物料凭证编号',
 `doc_category` tinyint(8) NOT NULL COMMENT '凭证类型',
 `doc_group_no` varchar(32) NOT NULL COMMENT '凭证编码组',
 `doc_type` tinyint(8) NOT NULL COMMENT '凭证类别',
 `publish_date` datetime DEFAULT NULL COMMENT '创建日期',
 `posting_date` datetime DEFAULT NULL COMMENT '过账日期',
 `original_no` varchar(32) NOT NULL DEFAULT '' COMMENT '参照业务单据号',
 `original_no_type` int(20) DEFAULT NULL COMMENT '业务单据类型',
 `deliver_no` varchar(64) NOT NULL DEFAULT '' COMMENT '交运单号',
 `owner` varchar(64) DEFAULT NULL COMMENT '库存所有者',
 `adjust_type` varchar(32) NOT NULL COMMENT '移动类型',
 `supply_logical_plant_no` varchar(32) DEFAULT '' COMMENT '收货方逻辑仓库no',
 `demand_logical_plant_no` varchar(32) DEFAULT '' COMMENT '发货方逻辑仓库no',
 `map_code` varchar(32) DEFAULT '' COMMENT 'map流水号',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `creator_id` varchar(32) NOT NULL COMMENT '创建人ID',
 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
 `updator_id` varchar(32) NOT NULL COMMENT '修改人ID',
 `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
 `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户ID',
 `remark` varchar(128) DEFAULT '' COMMENT '备注',
 `unique_no` varchar(128) NOT NULL DEFAULT '' COMMENT '外部业务唯一号',
 `app_key` varchar(128) NOT NULL DEFAULT 'bff' COMMENT '操作应用服务标识',
 PRIMARY KEY (`id`),
 UNIQUE KEY `idx_unq_mdoc_no` (`material_doc_no`) USING BTREE COMMENT '凭证号唯一索引',
 UNIQUE KEY `idx_unique_no` (`unique_no`,`app_key`) USING BTREE COMMENT '业务幂等唯一索引',
 KEY `idx_tid_mcode_slgcno` (`tenant_id`,`doc_category`,`supply_logical_plant_no`) USING BTREE COMMENT '租户凭证分类联合索引',
 KEY `idx_original_no` (`original_no`) USING BTREE COMMENT '原始单据号索引',
 KEY `idx_create_time` (`create_time`) USING BTREE COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证主表';

CREATE TABLE `material_doc_item` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物料凭证itemId',
 `material_doc_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '物料凭证id',
 `material_code` varchar(32) NOT NULL DEFAULT '' COMMENT '物料code',
 `batch_no` varchar(64) NOT NULL DEFAULT '' COMMENT '批次号',
 `item_category` tinyint(8) NOT NULL DEFAULT '0' COMMENT '出入库类型1-入库,2-出库',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `creator_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '创建人id',
 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 `updator_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '修改人id',
 `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
 `tenant_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '租户id',
 PRIMARY KEY (`id`),
 KEY `idx_material_doc_id` (`material_doc_id`) USING BTREE,
 KEY `idx_material_batch` (`material_code`,`batch_no`,`item_category`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证-item';

CREATE TABLE `mdoc_sub_ext` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
`material_doc_id` bigint(20) NOT NULL COMMENT '物料凭证id',
`material_doc_item_id` bigint(20) NOT NULL COMMENT '物料凭证itemId',
`valid_days` int(11) DEFAULT NULL COMMENT '批次有效天数',
`produce_date` datetime DEFAULT NULL COMMENT '生产日期',
`hs_code` varchar(32) DEFAULT NULL COMMENT '海关编码',
`annual_date` datetime DEFAULT NULL COMMENT '下次年检日期yyyy-mm-dd',
`annual_cycle_days` int(10) DEFAULT NULL COMMENT '年检周期天数',
`tenant_id` varchar(32) DEFAULT NULL COMMENT '租户id',
`creator_id` bigint(20) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL COMMENT '修改时间',
`updator_id` bigint(20) NOT NULL COMMENT '修改人id',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`deleted` tinyint(1) NOT NULL COMMENT '删除标识',
PRIMARY KEY (`id`),
KEY `idx_material_doc_id` (`material_doc_id`) USING BTREE,
KEY `idc_material_doc_item_id` (`material_doc_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证-标签-扩展';

CREATE TABLE `mdoc_sub_finance` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
`material_doc_id` bigint(20) NOT NULL COMMENT '物料凭证id',
`material_doc_item_id` bigint(20) NOT NULL COMMENT '物料凭证itemId',
`assert_tag` varchar(32) DEFAULT NULL COMMENT '资产号',
`sub_assert_tag` varchar(32) DEFAULT NULL COMMENT '次级编号',
`profit_center_name` varchar(64) DEFAULT NULL COMMENT '利润中心名称',
`profit_center_code` varchar(32) DEFAULT NULL COMMENT '利润中心',
`cost_center_name` varchar(64) DEFAULT NULL COMMENT '成本中心名称',
`cost_center_code` varchar(32) DEFAULT NULL COMMENT '成本中心',
`product_line` varchar(64) DEFAULT NULL COMMENT '产品线',
`trade_partner` varchar(64) DEFAULT NULL COMMENT '贸易伙伴',
`supply_name` varchar(64) DEFAULT NULL COMMENT '供应商名称',
`supply_code` varchar(32) DEFAULT NULL COMMENT '供应商编码',
`customer_name` varchar(64) DEFAULT NULL COMMENT '客户名称',
`customer_code` varchar(32) DEFAULT NULL COMMENT '客户编码',
`settlement_type` tinyint(8) DEFAULT NULL COMMENT '结算方式',
`marketing_no` varchar(32) DEFAULT NULL COMMENT '营销活动编码',
`budget_no` varchar(32) DEFAULT NULL COMMENT '预算编码',
`internal_order_no` varchar(32) DEFAULT NULL COMMENT '内部订单号',
`remark` varchar(255) DEFAULT NULL COMMENT '备注',
`deleted` tinyint(1) NOT NULL COMMENT '删除标识',
`tenant_id` varchar(32) DEFAULT NULL COMMENT '租户id',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`creator_id` bigint(20) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL COMMENT '修改时间',
`updator_id` bigint(20) NOT NULL COMMENT '修改人id',
PRIMARY KEY (`id`),
KEY `idx_material_doc_id` (`material_doc_id`) USING BTREE,
KEY `idc_material_doc_item_id` (`material_doc_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证-标签-财务';

CREATE TABLE `mdoc_sub_in_out` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '出入库信息主键',
`material_doc_id` bigint(20) NOT NULL COMMENT '物料凭证id',
`material_doc_item_id` bigint(20) NOT NULL COMMENT '物料凭证item',
`demand` varchar(255) DEFAULT '' COMMENT '发货方名称',
`demand_sale_order_no` varchar(64) DEFAULT '' COMMENT '发货销售订单号',
`demand_sale_order_item_no` varchar(64) DEFAULT '' COMMENT '发货销售订单行号',
`demand_storage_location_no` varchar(64) DEFAULT '' COMMENT '发货方库存地点no',
`demand_storage_location_name` varchar(255) DEFAULT '' COMMENT '发货方库存地点名称',
`demand_batch_no` varchar(64) DEFAULT '' COMMENT '发货方批次号',
`demand_stock_type` tinyint(8) DEFAULT NULL COMMENT '发货方库存类型(良品、残次品、质检品)',
`supply` varchar(255) DEFAULT '' COMMENT '收货方名称',
`supply_sale_order_no` varchar(64) DEFAULT '' COMMENT '收货销售订单号',
`supply_sale_order_item_no` varchar(64) DEFAULT '' COMMENT '收货销售订单行号',
`supply_storage_location_no` varchar(64) DEFAULT '' COMMENT '收货方库存地点no',
`supply_storage_location_name` varchar(255) DEFAULT '' COMMENT '收货方库存地点名称',
`supply_batch_no` varchar(64) DEFAULT '' COMMENT '收货方批次号',
`supply_stock_type` tinyint(8) DEFAULT NULL COMMENT '收货方库存类型(良品、残次品、质检品)',
`adjust_type` varchar(64) NOT NULL DEFAULT '' COMMENT '移动类型',
`adjust_reason` varchar(255) DEFAULT '' COMMENT '移动原因',
`io` varchar(8) DEFAULT '' COMMENT '出入库标识',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人id',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`updator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '修改人id',
`deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
PRIMARY KEY (`id`),
KEY `idx_material_doc_id` (`material_doc_id`) USING BTREE,
KEY `idc_material_doc_item_id` (`material_doc_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证子表-出入库信息';

CREATE TABLE `mdoc_sub_map` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
`material_doc_id` bigint(20) NOT NULL COMMENT '物料凭证id',
`material_doc_item_id` bigint(20) NOT NULL COMMENT '物料凭证itemId',
`map_code` varchar(64) DEFAULT NULL COMMENT 'map流水code',
`map_sub_code` varchar(64) DEFAULT '' COMMENT 'map子流水号',
`map_status` tinyint(8) DEFAULT NULL COMMENT 'map流水状态',
`deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`tenant_id` varchar(32) NOT NULL COMMENT '租户id',
`creator_id` bigint(20) NOT NULL COMMENT '创建人id',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
`updator_id` bigint(20) NOT NULL COMMENT '修改人id',
PRIMARY KEY (`id`),
KEY `idx_material_doc_id` (`material_doc_id`) USING BTREE,
KEY `idc_material_doc_item_id` (`material_doc_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证-标签-移动平均价';

CREATE TABLE `mdoc_sub_material` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物料信息主键',
 `material_doc_id` bigint(20) NOT NULL COMMENT '物料凭证ID',
 `material_doc_item_id` bigint(20) NOT NULL COMMENT '物料凭证item',
 `material_code` varchar(32) NOT NULL DEFAULT '' COMMENT '物料code',
 `material_name` varchar(128) DEFAULT '' COMMENT '物料名称',
 `material_category_code` varchar(32) DEFAULT NULL COMMENT '物料品类',
 `material_weight` decimal(10,4) DEFAULT NULL COMMENT '物料重量',
 `weight_unit` varchar(32) DEFAULT NULL COMMENT '物料重量单位',
 `material_volume` decimal(10,4) DEFAULT NULL COMMENT '物料体积',
 `volume_unit` varchar(32) DEFAULT NULL COMMENT '物料体积单位',
 `valuation` varchar(32) DEFAULT NULL COMMENT '评估类',
 `remark1` varchar(255) DEFAULT NULL COMMENT '备注1',
 `remark2` varchar(255) DEFAULT NULL COMMENT '备注2',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `creator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 `updator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人ID',
 `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
 `tenant_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '租户ID',
 PRIMARY KEY (`id`),
 KEY `idx_material_doc_id` (`material_doc_id`) USING BTREE,
 KEY `idc_material_doc_item_id` (`material_doc_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证子表-物料信息';

CREATE TABLE `mdoc_sub_quantity` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数量信息主键',
 `material_doc_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '物料凭证id',
 `material_doc_item_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '物料凭证item',
 `adjust_quantity` decimal(20,4) NOT NULL DEFAULT '0.0000' COMMENT '移动数量',
 `uom` varchar(32) DEFAULT NULL COMMENT '计量单位',
 `price` decimal(20,4) DEFAULT NULL COMMENT '不含税单价',
 `total_price` decimal(20,4) DEFAULT NULL COMMENT '不含税总价',
 `total_price_tax` decimal(20,4) DEFAULT NULL COMMENT '价税总价',
 `tax_code` varchar(32) DEFAULT NULL COMMENT '税码',
 `tax_name` varchar(255) DEFAULT NULL COMMENT '税码名称',
 `tax_rate` decimal(20,4) DEFAULT NULL COMMENT '税率',
 `tax` decimal(20,4) DEFAULT NULL COMMENT '税额',
 `currency` varchar(32) DEFAULT NULL COMMENT '货币',
 `exchange_rate` decimal(20,4) DEFAULT NULL COMMENT '汇率',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `creator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 `updator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人id',
 `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
 `tenant_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '租户id',
 PRIMARY KEY (`id`),
 KEY `idx_material_doc_id` (`material_doc_id`) USING BTREE,
 KEY `idc_material_doc_item_id` (`material_doc_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料凭证子表-数量';

CREATE TABLE `inventory_monitor_rule` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '规则id',
`monitor_rule_code` varchar(64) DEFAULT NULL COMMENT '规则code',
`monitor_send_mode` varchar(32) DEFAULT NULL COMMENT '预警发送方式',
`monitor_send_address` varchar(256) DEFAULT NULL COMMENT '发送地址',
`monitor_type` varchar(32) DEFAULT NULL COMMENT '预警类型',
`monitor_dimension` varchar(32) DEFAULT NULL COMMENT '预警维度',
`monitor_interval` int(11) DEFAULT NULL COMMENT '任务间隔（分钟）',
`monitor_enable_status` varchar(32) NOT NULL COMMENT '规则启用状态',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` varchar(32) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL COMMENT '更新时间',
`updator_id` varchar(32) NOT NULL COMMENT '更新人id',
`deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0-未删除；1-已删除',
`tenant_id` varchar(32) NOT NULL COMMENT '租户id',
PRIMARY KEY (`id`),
UNIQUE KEY `uniq_monitor_code` (`monitor_rule_code`) USING BTREE,
KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存预警规则';

CREATE TABLE `inventory_monitor_rule_line` (
`id` bigint(20) NOT NULL COMMENT '规则明细id',
`monitor_rule_id` bigint(20) NOT NULL COMMENT '预警规则id',
`monitor_dimension` varchar(32) DEFAULT NULL COMMENT '预警维度',
`monitor_object` varchar(32) DEFAULT NULL COMMENT '预警对象',
`monitor_ceil` decimal(20,6) DEFAULT NULL COMMENT '上限',
`monitor_floor` decimal(20,6) DEFAULT NULL COMMENT '下限',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` bigint(20) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL COMMENT '更新时间',
`updator_id` bigint(20) NOT NULL COMMENT '更新人id',
`deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0-未删除；1-已删除',
`tenant_id` varchar(32) NOT NULL COMMENT '租户id',
PRIMARY KEY (`id`),
KEY `idx_rule_line` (`monitor_rule_id`,`monitor_dimension`,`monitor_object`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存预警规则明细';

CREATE TABLE `inventory_alert` (
`id` bigint(20) NOT NULL COMMENT '预警记录id',
`monitor_rule_id` bigint(20) NOT NULL COMMENT '预警规则id',
`material_code` varchar(32) NOT NULL COMMENT '物料编码',
`logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '逻辑仓库no',
`deviate` decimal(20,6) NOT NULL COMMENT '偏差数量',
`action` varchar(64) DEFAULT NULL COMMENT '报警标识',
`alert_date` datetime DEFAULT NULL COMMENT '报警时间',
`status` varchar(32) DEFAULT NULL COMMENT '报警状态（处理状态）',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` varchar(32) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL COMMENT '更新时间',
`updator_id` varchar(32) NOT NULL COMMENT '更新人id',
`deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0-未删除；1-已删除',
`tenant_id` varchar(32) NOT NULL COMMENT '租户id',
PRIMARY KEY (`id`),
KEY `idx_tenant_monitor_id` (`tenant_id`,`monitor_rule_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存报警日志';

CREATE TABLE `inventory_alert_notification` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库存报警通知记录id',
`alert_id` bigint(20) NOT NULL COMMENT '报警日志id',
`notification_mode` varchar(32) NOT NULL COMMENT '通知方式',
`notification_address` varchar(64) DEFAULT NULL COMMENT '通知地址（手机/邮箱）',
`content` varchar(512) DEFAULT NULL COMMENT '通知内容',
`url` varchar(256) DEFAULT NULL COMMENT '通知url地址',
`status` tinyint(4) DEFAULT '0' COMMENT '通知状态（是否发送）',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` varchar(32) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL COMMENT '更新时间',
`updator_id` varchar(32) NOT NULL COMMENT '更新人id',
`deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0-未删除；1-已删除',
PRIMARY KEY (`id`),
KEY `idx_alert_id` (`alert_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存报警通知记录';

CREATE TABLE `inventory_map` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'MAP主键',
 `map_code` varchar(32) NOT NULL COMMENT 'map流水号',
 `map_sub_code` varchar(32) DEFAULT '' COMMENT 'map子流水code',
 `sku_code` varchar(32) NOT NULL COMMENT '物料code',
 `logical_plant_no` varchar(32) NOT NULL COMMENT '逻辑仓',
 `map` decimal(20,4) NOT NULL DEFAULT '0.0000' COMMENT '移动平均价',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `creator_id` varchar(32) NOT NULL COMMENT '创建人ID',
 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 `updator_id` varchar(32) NOT NULL COMMENT '修改人ID',
 `deleted` tinyint(1) NOT NULL COMMENT '删除标识',
 `tenant_id` varchar(32) NOT NULL COMMENT '租户ID',
 PRIMARY KEY (`id`),
 KEY `idx_map_code` (`map_code`,`map_sub_code`) USING BTREE,
 KEY `idx_sku_logical` (`tenant_id`,`sku_code`,`logical_plant_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='移动平均价';

CREATE TABLE `inventory_map_his` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'MAP历史记录主键',
`map_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'map主键',
`map_code` varchar(32) NOT NULL COMMENT 'map流水号',
`map_sub_code` varchar(32) NOT NULL DEFAULT '' COMMENT 'map子流水code',
`sku_code` varchar(32) NOT NULL COMMENT '物料code',
`logical_plant_no` varchar(32) NOT NULL COMMENT '逻辑仓',
`sku_price_total` decimal(20,4) DEFAULT NULL COMMENT '价格总和',
`sku_stock_total` decimal(20,4) DEFAULT NULL COMMENT '数量总和',
`map` decimal(20,4) NOT NULL COMMENT '移动平均价',
`currency` varchar(8) NOT NULL COMMENT '货币单位',
`exchange_rate` decimal(10,4) DEFAULT NULL COMMENT '汇率',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` varchar(32) NOT NULL COMMENT '创建人ID',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`updator_id` varchar(32) NOT NULL COMMENT '修改人ID',
`deleted` tinyint(1) NOT NULL COMMENT '删除标识',
`tenant_id` varchar(32) NOT NULL COMMENT '租户ID',
PRIMARY KEY (`id`),
KEY `idx_map_code` (`map_code`,`map_sub_code`) USING BTREE,
KEY `idx_map_id` (`map_id`) USING BTREE,
KEY `idx_sku_logical` (`tenant_id`,`sku_code`,`logical_plant_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='移动平均价历史记录';

CREATE TABLE `shipment` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交运单明细id',
`shipment_no` varchar(64) NOT NULL COMMENT '交运单号',
`shipment_type` varchar(16) NOT NULL COMMENT '交运单类型',
`shipment_status` varchar(16) NOT NULL COMMENT '交运单状态',
`instruction_type` varchar(16) DEFAULT NULL COMMENT 'wms指令类型',
`pick_date` datetime DEFAULT NULL COMMENT '拣货日期',
`pick_status` varchar(32) DEFAULT NULL COMMENT '拣配状态',
`transport_route` varchar(32) DEFAULT NULL COMMENT '运输线路',
`transport_no` varchar(32) DEFAULT NULL COMMENT '运输单据号',
`transport_carrier` varchar(64) DEFAULT NULL COMMENT '运输主体',
`shipment_point` varchar(32) DEFAULT NULL COMMENT '装运点',
`transport_mode` varchar(32) DEFAULT NULL COMMENT '运输方式',
`delivery_name` varchar(64) DEFAULT NULL COMMENT '发货方名称',
`delivery_phone` varchar(64) NOT NULL COMMENT '发货方手机',
`delivery_province` varchar(64) NOT NULL COMMENT '发货地址（省）',
`delivery_city` varchar(64) NOT NULL COMMENT '发货地址（市）',
`delivery_county` varchar(64) NOT NULL COMMENT '发货地址（区）',
`delivery_address_line` varchar(64) NOT NULL COMMENT '发货地址（详细地址）',
`receiving_name` varchar(64) DEFAULT NULL COMMENT '收货放名称',
`receiving_phone` varchar(64) NOT NULL COMMENT '收货方手机',
`receiving_province` varchar(64) NOT NULL COMMENT '收货地址（省）',
`receiving_city` varchar(64) NOT NULL COMMENT '收货地址（市）',
`receiving_county` varchar(64) NOT NULL COMMENT '收货地址（区）',
`receiving_address_line` varchar(64) NOT NULL COMMENT '收货地址（详细地址）',
`planned_delivery_date` datetime DEFAULT NULL COMMENT '计划发货日期',
`planned_receiving_date` datetime DEFAULT NULL COMMENT '计划收货日期',
`actual_delivery_date` datetime DEFAULT NULL COMMENT '实际发货日期',
`actual_receiving_date` datetime DEFAULT NULL COMMENT '实际收货日期',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`creator_id` varchar(32) NOT NULL COMMENT '创建人id',
`update_time` datetime NOT NULL COMMENT '修改时间',
`updator_id` varchar(32) NOT NULL COMMENT '修改人id',
`deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
`tenant_id` varchar(32) NOT NULL COMMENT '租户id',
PRIMARY KEY (`id`),
UNIQUE KEY `idx_unq_sp_no` (`shipment_no`) USING BTREE COMMENT '交运单唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交运单';

CREATE TABLE `shipment_line` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交运单明细id',
 `source_order_no` varchar(32) DEFAULT NULL COMMENT '来源单据编号',
 `source_order_type` varchar(16) DEFAULT NULL COMMENT '来源单号类型',
 `shipment_id` bigint(20) NOT NULL COMMENT '交运单id',
 `shipment_no` varchar(64) NOT NULL COMMENT '交运单号',
 `material_code` varchar(32) NOT NULL COMMENT '物料编码',
 `external_code` varchar(32) DEFAULT NULL COMMENT '外部编码',
 `quantity` decimal(20,6) NOT NULL COMMENT '交运数量',
 `actual_quantity` decimal(20,6) DEFAULT NULL COMMENT '实际交运数量',
 `invoiced_quantity` decimal(20,6) DEFAULT NULL COMMENT '已开票数量',
 `cost_center_code` varchar(32) DEFAULT NULL COMMENT '成本中心',
 `accounting_code` varchar(32) DEFAULT NULL COMMENT '会计科目',
 `receiving_logical_plant_no` varchar(64) DEFAULT NULL COMMENT '收货逻辑仓库no',
 `receiving_warehouse_no` varchar(64) DEFAULT NULL COMMENT '收货物理仓库no',
 `receiving_storage_location_no` varchar(64) DEFAULT NULL COMMENT '收货存储位置no',
 `receiving_batch_no` varchar(64) DEFAULT NULL COMMENT '收货批次号',
 `receiving_company_code` varchar(32) DEFAULT NULL COMMENT '收货公司主体',
 `delivery_logical_plant_no` varchar(64) DEFAULT NULL COMMENT '发货逻辑仓库no',
 `delivery_warehouse_no` varchar(64) DEFAULT NULL COMMENT '发货物理仓库no',
 `delivery_storage_location_no` varchar(64) DEFAULT NULL COMMENT '发货存储位置no',
 `delivery_batch_no` varchar(64) DEFAULT NULL COMMENT '发货批次号',
 `delivery_company_code` varchar(32) DEFAULT NULL COMMENT '发货公司主体',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` datetime NOT NULL COMMENT '修改时间',
 `creator_id` varchar(32) NOT NULL COMMENT '创建人id',
 `updator_id` varchar(32) NOT NULL COMMENT '修改人id',
 `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
 `tenant_id` varchar(32) NOT NULL COMMENT '租户id',
 PRIMARY KEY (`id`),
 KEY `idx_shipment_id` (`shipment_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交运单明细';

-- -------------------------------------------------------
-- product-center 本地沉淀：SKU 批次数据
-- -------------------------------------------------------
CREATE TABLE `product_sku_batch` (
  `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sku_code`    varchar(64)  NOT NULL DEFAULT '' COMMENT 'SKU 编码',
  `batch_code`  varchar(64)  NOT NULL DEFAULT '' COMMENT '批次编码',
  `tenant_id`   varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `ext`         text                  DEFAULT NULL COMMENT '扩展信息（JSON）',
  `updator_id`  varchar(32)  NOT NULL DEFAULT '' COMMENT '操作人',
  `deleted`     tinyint(1)   NOT NULL DEFAULT '0' COMMENT '逻辑删除（0=正常,1=已删除）',
  `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sku_batch_tenant` (`sku_code`, `batch_code`, `tenant_id`),
  KEY `idx_tenant_sku` (`tenant_id`, `sku_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SKU 批次数据（product-center 本地沉淀）';

-- -------------------------------------------------------
-- 流转码：申请单
-- -------------------------------------------------------
CREATE TABLE `code_apply_order` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`        varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `channel`          tinyint(8)   NOT NULL DEFAULT '0' COMMENT '渠道',
  `order_no`         varchar(64)  NOT NULL DEFAULT '' COMMENT '申请单号',
  `owner_id`         varchar(32)  NOT NULL DEFAULT '' COMMENT '申请人',
  `original_distributor_id` varchar(32) NOT NULL DEFAULT '' COMMENT '原经销商 ID',
  `invitee_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '被邀请人 ID',
  `invitee_logical_plant_no` varchar(64) NOT NULL DEFAULT '' COMMENT '被邀请人逻辑仓编码',
  `apply_time`       datetime              DEFAULT NULL COMMENT '申请时间',
  `apply_reason`     varchar(512) NOT NULL DEFAULT '' COMMENT '申请原因',
  `approval_status`  tinyint(8)   NOT NULL DEFAULT '0' COMMENT '审批状态',
  `approval_time`    datetime              DEFAULT NULL COMMENT '审批时间',
  `extend_param`     varchar(1024) NOT NULL DEFAULT '' COMMENT '扩展参数',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_orderNo` (`tenant_id`, `order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流转码申请单';

-- -------------------------------------------------------
-- 流转码：申请单明细
-- -------------------------------------------------------
CREATE TABLE `code_apply_order_detail` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_order_id`   bigint(20)   NOT NULL DEFAULT '0' COMMENT '申请单 ID',
  `code`             varchar(64)  NOT NULL DEFAULT '' COMMENT '流转码',
  `inner_code`       varchar(64)  NOT NULL DEFAULT '' COMMENT '内码',
  `material_code`    varchar(64)  NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_name`    varchar(256) NOT NULL DEFAULT '' COMMENT '物料名称',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_applyOrderId` (`apply_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流转码申请单明细';

-- -------------------------------------------------------
-- 流转码：审批记录
-- -------------------------------------------------------
CREATE TABLE `code_apply_approval_record` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`        varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `apply_order_id`   bigint(20)   NOT NULL DEFAULT '0' COMMENT '申请单 ID',
  `approval_status`  tinyint(8)   NOT NULL DEFAULT '0' COMMENT '审批状态',
  `approval_time`    datetime              DEFAULT NULL COMMENT '审批时间',
  `approval_reason`  varchar(512) NOT NULL DEFAULT '' COMMENT '审批原因',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_applyOrderId` (`apply_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流转码审批记录';

-- -------------------------------------------------------
-- 流转码：码记录
-- -------------------------------------------------------
CREATE TABLE `code_record` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_no`      varchar(64)  NOT NULL DEFAULT '' COMMENT '业务单号',
  `source_no`        varchar(64)  NOT NULL DEFAULT '' COMMENT '来源单号',
  `inner_code`       varchar(64)  NOT NULL DEFAULT '' COMMENT '内码',
  `type`             varchar(32)  NOT NULL DEFAULT '' COMMENT '类型',
  `code`             varchar(64)  NOT NULL DEFAULT '' COMMENT '流转码',
  `publisher`        varchar(64)  NOT NULL DEFAULT '' COMMENT '发布者',
  `pre_owner`        varchar(64)  NOT NULL DEFAULT '' COMMENT '前所有者',
  `current_owner`    varchar(64)  NOT NULL DEFAULT '' COMMENT '当前所有者',
  `status`           varchar(32)  NOT NULL DEFAULT '' COMMENT '状态',
  `extend_field1`    varchar(256) NOT NULL DEFAULT '' COMMENT '扩展字段1',
  `extend_field2`    varchar(256) NOT NULL DEFAULT '' COMMENT '扩展字段2',
  `extend_param`     varchar(1024) NOT NULL DEFAULT '' COMMENT '扩展参数',
  `active_time`      datetime              DEFAULT NULL COMMENT '激活时间',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_businessNo` (`business_no`),
  KEY `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流转码记录';

-- -------------------------------------------------------
-- 文件导入：主记录
-- -------------------------------------------------------
CREATE TABLE `file_import_record` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`        varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `business_type`    varchar(64)  NOT NULL DEFAULT '' COMMENT '业务类型',
  `file_name`        varchar(256) NOT NULL DEFAULT '' COMMENT '文件名',
  `file_url`         varchar(512) NOT NULL DEFAULT '' COMMENT '文件URL',
  `result_url`       varchar(512) NOT NULL DEFAULT '' COMMENT '结果文件URL',
  `process_status`   varchar(32)  NOT NULL DEFAULT '' COMMENT '处理状态',
  `process_message`  varchar(1024) NOT NULL DEFAULT '' COMMENT '处理消息',
  `ext_field`        varchar(512) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `ext_info`         text                  DEFAULT NULL COMMENT '扩展信息',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `creator`          varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `updator`          varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人名称',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_businessType` (`tenant_id`, `business_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件导入记录';

-- -------------------------------------------------------
-- 文件导入：行记录
-- -------------------------------------------------------
CREATE TABLE `file_import_line_record` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`        varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `file_record_id`   bigint(20)   NOT NULL DEFAULT '0' COMMENT '文件记录 ID',
  `process_status`   varchar(32)  NOT NULL DEFAULT '' COMMENT '处理状态',
  `process_message`  varchar(1024) NOT NULL DEFAULT '' COMMENT '处理消息',
  `line_detail`      text                  DEFAULT NULL COMMENT '行明细',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `creator`          varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `updator`          varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人名称',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_fileRecordId` (`file_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件导入行记录';

-- -------------------------------------------------------
-- 库存日志
-- -------------------------------------------------------
CREATE TABLE `inventory_log` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`        varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `request_id`       varchar(64)  NOT NULL DEFAULT '' COMMENT '请求 ID',
  `log_module`       tinyint(8)   NOT NULL DEFAULT '0' COMMENT '日志模块',
  `action`           tinyint(8)   NOT NULL DEFAULT '0' COMMENT '操作动作',
  `payload`          text                  DEFAULT NULL COMMENT '日志载荷',
  `report_time`      datetime              DEFAULT NULL COMMENT '上报时间',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_requestId` (`tenant_id`, `request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存日志';

-- -------------------------------------------------------
-- 库存物料
-- -------------------------------------------------------
CREATE TABLE `inventory_material` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`        varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `material_code`    varchar(64)  NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_name`    varchar(256) NOT NULL DEFAULT '' COMMENT '物料名称',
  `out_material_code` varchar(64) NOT NULL DEFAULT '' COMMENT '外部物料编码',
  `unit_id`          bigint(20)   NOT NULL DEFAULT '0' COMMENT '计量单位 ID',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_materialCode` (`tenant_id`, `material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存物料';

-- -------------------------------------------------------
-- 物料逻辑仓绑定关系
-- -------------------------------------------------------
CREATE TABLE `material_logical_plant_ref` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`        varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `material_code`    varchar(64)  NOT NULL DEFAULT '' COMMENT '物料编码',
  `logical_plant_id` bigint(20)   NOT NULL DEFAULT '0' COMMENT '逻辑仓 ID',
  `logical_plant_no` varchar(64)  NOT NULL DEFAULT '' COMMENT '逻辑仓编码',
  `creator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`       varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`          tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_materialCode` (`tenant_id`, `material_code`),
  KEY `idx_tenant_logicalPlantNo` (`tenant_id`, `logical_plant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料逻辑仓绑定关系';

-- -------------------------------------------------------
-- 备件：交货单
-- -------------------------------------------------------
CREATE TABLE `sp_delivery_order` (
  `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`          varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `delivery_order_no`  varchar(64)  NOT NULL DEFAULT '' COMMENT '交货单号',
  `delivery_type`      varchar(32)  NOT NULL DEFAULT '' COMMENT '交货类型',
  `crm_order_no`       varchar(64)  NOT NULL DEFAULT '' COMMENT 'CRM 订单号',
  `out_order_no`       varchar(64)  NOT NULL DEFAULT '' COMMENT '外部订单号',
  `delivery_time`      varchar(32)  NOT NULL DEFAULT '' COMMENT '交货时间',
  `distributor_id`     varchar(32)  NOT NULL DEFAULT '' COMMENT '经销商 ID',
  `distributor_tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '经销商租户 ID',
  `distributor_name`   varchar(128) NOT NULL DEFAULT '' COMMENT '经销商名称',
  `distributor_type`   varchar(32)  NOT NULL DEFAULT '' COMMENT '经销商类型',
  `origin_system`      varchar(32)  NOT NULL DEFAULT '' COMMENT '来源系统',
  `state`              tinyint(8)   NOT NULL DEFAULT '0' COMMENT '状态',
  `creator_id`         varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`         varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`            tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_deliveryOrderNo` (`tenant_id`, `delivery_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='备件交货单';

-- -------------------------------------------------------
-- 备件：交货单明细
-- -------------------------------------------------------
CREATE TABLE `sp_delivery_order_detail` (
  `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id`          varchar(32)  NOT NULL DEFAULT '' COMMENT '租户 ID',
  `manufacturer_code`  varchar(64)  NOT NULL DEFAULT '' COMMENT '制造商编码',
  `manufacturer_name`  varchar(128) NOT NULL DEFAULT '' COMMENT '制造商名称',
  `delivery_order_id`  bigint(20)   NOT NULL DEFAULT '0' COMMENT '交货单 ID',
  `item_no`            varchar(64)  NOT NULL DEFAULT '' COMMENT '行号',
  `material_code`      varchar(64)  NOT NULL DEFAULT '' COMMENT '物料编码',
  `material_name`      varchar(256) NOT NULL DEFAULT '' COMMENT '物料名称',
  `out_material_code`  varchar(64)  NOT NULL DEFAULT '' COMMENT '外部物料编码',
  `sn_code`            varchar(64)  NOT NULL DEFAULT '' COMMENT 'SN 编码',
  `sn_name`            varchar(128) NOT NULL DEFAULT '' COMMENT 'SN 名称',
  `category_code`      varchar(64)  NOT NULL DEFAULT '' COMMENT '类别编码',
  `category_name`      varchar(128) NOT NULL DEFAULT '' COMMENT '类别名称',
  `quantity`           decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '数量',
  `identify_code`      varchar(64)  NOT NULL DEFAULT '' COMMENT '认证码',
  `unit_id`            bigint(20)   NOT NULL DEFAULT '0' COMMENT '计量单位 ID',
  `unit`               varchar(32)  NOT NULL DEFAULT '' COMMENT '计量单位',
  `out_unit`           varchar(32)  NOT NULL DEFAULT '' COMMENT '外部单位',
  `stock_type`         tinyint(8)   NOT NULL DEFAULT '0' COMMENT '库存类型',
  `price`              decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '单价',
  `total_price`        decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '总价',
  `currency`           varchar(16)  NOT NULL DEFAULT '' COMMENT '币种',
  `total_price_tax`    decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '含税总价',
  `tax_code_name`      varchar(64)  NOT NULL DEFAULT '' COMMENT '税码名称',
  `tax_code`           varchar(32)  NOT NULL DEFAULT '' COMMENT '税码',
  `tax_rate`           decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '税率',
  `tax`                decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '税额',
  `exchange_rate`      decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '汇率',
  `supply_code`        varchar(64)  NOT NULL DEFAULT '' COMMENT '供应商编码',
  `supply_name`        varchar(128) NOT NULL DEFAULT '' COMMENT '供应商名称',
  `customer`           varchar(128) NOT NULL DEFAULT '' COMMENT '客户',
  `customer_code`      varchar(64)  NOT NULL DEFAULT '' COMMENT '客户编码',
  `state`              tinyint(8)   NOT NULL DEFAULT '0' COMMENT '状态',
  `creator_id`         varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
  `updator_id`         varchar(32)  NOT NULL DEFAULT '' COMMENT '更新人',
  `create_time`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`            tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_deliveryOrderId` (`delivery_order_id`),
  KEY `idx_tenant_materialCode` (`tenant_id`, `material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='备件交货单明细';

-- -------------------------------------------------------
-- 计量单位
-- -------------------------------------------------------
CREATE TABLE `unit` (
  `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `unit`             varchar(32)  NOT NULL DEFAULT '' COMMENT '单位',
  `unit_name`        varchar(64)  NOT NULL DEFAULT '' COMMENT '单位名称',
  `description`      varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `biz_code`         varchar(64)  NOT NULL DEFAULT '' COMMENT '业务编码',
  `creator`          varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier`         varchar(64)  NOT NULL DEFAULT '' COMMENT '修改人',
  `del`              tinyint(1)   NOT NULL DEFAULT '0' COMMENT '删除标识',
  `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `enable`           tinyint(1)   NOT NULL DEFAULT '1' COMMENT '启用标识',
  `accuracy`         varchar(32)  NOT NULL DEFAULT '' COMMENT '精度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计量单位';