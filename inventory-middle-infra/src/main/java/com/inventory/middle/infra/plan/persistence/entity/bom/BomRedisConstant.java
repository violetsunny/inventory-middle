package com.inventory.middle.infra.plan.persistence.entity.bom;

/**
 * BOM Redis key 常量
 */
public class BomRedisConstant {

    public static final String NULL_STRING = "NULL";

    public static final long ONE_DAY = 86400L;

    /**
     * 母件缓存 key（查询物料对应母件节点信息）
     */
    public static String getBomParentInfoKey(String tenantId, String logicalPlantNo, String materialCode) {
        return "bom:parent:info:" + tenantId + ":" + logicalPlantNo + ":" + materialCode;
    }

    /**
     * 子件列表缓存 key（母件对应的所有子件）
     */
    public static String getBomChildrenKey(String tenantId, String logicalPlantNo, String materialCode) {
        return "bom:children:" + tenantId + ":" + logicalPlantNo + ":" + materialCode;
    }

    /**
     * 反查母件 key（子件materialCode -> 母件节点）
     */
    public static String getBomParentKey(String tenantId, String logicalPlantNo, String materialCode) {
        return "bom:parent:" + tenantId + ":" + logicalPlantNo + ":" + materialCode;
    }
}
