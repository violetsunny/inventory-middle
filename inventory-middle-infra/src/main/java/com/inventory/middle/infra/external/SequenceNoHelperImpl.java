package com.inventory.middle.infra.external;

import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.service.external.SequenceNoHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.kdla.framework.supplement.cache.sequence.SequenceNoGenerator;

import javax.annotation.Resource;

/**
 * 序列号生成实现 — 委托给 KDLA SequenceNoGenerator。
 *
 * <p>规则配置在 DB 的 code_generator_cfg 表，每个 code key 对应一类业务号段：
 * <pre>
 *   WAREHOUSE_NO                  仓库编号         WH+yyyyMMdd+6
 *   LOGICAL_PLANT_NO              逻辑工厂编号     LP+yyyyMMdd+6
 *   STORAGE_LOCATION_NO_{TYPE}    库位编号（动态） {type}+yyyyMMdd+6
 *   MONITOR_RULE_NO               监控规则编号     MR+yyyyMMdd+6
 *   MATERIAL_DOC_NO               物料凭证号       INV+yyyyMMdd+6
 *   MATERIAL_BATCH_NO             物料批次号       MB+yyyyMMdd+6
 *   MAP_CODE                      货位码           MAP+yyyyMMdd+6
 *   MAP_SUB_CODE                  货位子码         MAPS+yyyyMMdd+6
 * </pre>
 *
 * <p>建表 DDL 与初始化 INSERT 见 docs/superpowers/plans/remaining-todos.md 任务 L6。
 */
@Component
@Slf4j
public class SequenceNoHelperImpl implements SequenceNoHelper {

    // code_generator_cfg.code 列的规则键常量
    private static final String CODE_WAREHOUSE_NO       = "WAREHOUSE_NO";
    private static final String CODE_LOGICAL_PLANT_NO   = "LOGICAL_PLANT_NO";
    private static final String CODE_MONITOR_RULE_NO    = "MONITOR_RULE_NO";
    private static final String CODE_MATERIAL_DOC_NO    = "MATERIAL_DOC_NO";
    private static final String CODE_MATERIAL_BATCH_NO  = "MATERIAL_BATCH_NO";
    private static final String CODE_MAP_CODE           = "MAP_CODE";
    private static final String CODE_MAP_SUB_CODE       = "MAP_SUB_CODE";

    @Resource
    private SequenceNoGenerator sequenceNoGenerator;

    @Override
    public String generateWarehouseNo() {
        return doGenerate(CODE_WAREHOUSE_NO);
    }

    @Override
    public String generateLogicalPlantNo() {
        return doGenerate(CODE_LOGICAL_PLANT_NO);
    }

    /**
     * 库位编号前缀由 locationTypeMark 决定，每种类型独立计数。
     * code_generator_cfg 表中对应 code = "STORAGE_LOCATION_NO_" + locationTypeMark（大写）。
     */
    @Override
    public String generateStorageLocationNo(String locationTypeMark) {
        if (StringUtils.isBlank(locationTypeMark)) {
            throw new BusinessException("locationTypeMark不能为空");
        }
        String code = "STORAGE_LOCATION_NO_" + locationTypeMark.toUpperCase();
        return doGenerate(code);
    }

    @Override
    public String generateMonitorRuleNo() {
        return doGenerate(CODE_MONITOR_RULE_NO);
    }

    @Override
    public String generateMaterialDocNo() {
        return doGenerate(CODE_MATERIAL_DOC_NO);
    }

    @Override
    public String generateMaterialBatchNo() {
        return doGenerate(CODE_MATERIAL_BATCH_NO);
    }

    @Override
    public String generateMapCode() {
        return doGenerate(CODE_MAP_CODE);
    }

    @Override
    public String generateMapSubCode() {
        return doGenerate(CODE_MAP_SUB_CODE);
    }

    /**
     * 统一委托入口：调用 KDLA SequenceNoGenerator.getMaxNo(code)。
     * 内部使用 Redisson 分布式锁 + DB 流水号规则 + 本地缓存，生产级安全。
     */
    private String doGenerate(String code) {
        try {
            return sequenceNoGenerator.getMaxNo(code);
        } catch (Exception e) {
            log.error("SequenceNoHelperImpl.doGenerate failed, code={}", code, e);
            throw new BusinessException("生成序列号失败, code=" + code, e);
        }
    }
}
