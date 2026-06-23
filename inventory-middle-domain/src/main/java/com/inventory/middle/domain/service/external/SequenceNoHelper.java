package com.inventory.middle.domain.service.external;

/** 序列号生成 Helper（domain 层定义，infra 层实现） */
public interface SequenceNoHelper {
    String generateWarehouseNo();
    String generateLogicalPlantNo();
    String generateStorageLocationNo(String locationTypeMark);
    String generateMonitorRuleNo();
    String generateMaterialDocNo();
    String generateMaterialBatchNo();
    String generateMapCode();
    String generateMapSubCode();
}
