package com.inventory.middle.infra.plan.aop.extension;

import com.inventory.middle.client.plan.dto.ExternalMaterialSupport;

/**
 * 外部物料编码填充处理器
 *
 * @author Danny.Lee
 * @date 2022/5/12
 */
public interface ExternalMaterialFillingProcessor extends ExtensionProcessor {

    /**
     * 为模型填充外部物料编码
     *
     * @param model 待填充模型
     */
    void apply(ExternalMaterialSupport model);
}
