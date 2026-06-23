package com.inventory.middle.client.plan.dto;

/**
 * 外部物料编码支撑
 *
 * @author Danny.Lee
 * @date 2022/5/12
 */
public interface ExternalMaterialSupport {

    /** 获取物料编码 */
    String getMaterialCode();

    /** 返回外部物料编码 */
    String getExternalMaterialCode();

    /** 设置物料编码 */
    void setMaterialCode(String materialCode);

    /** 设置外部物料编码 */
    void setExternalMaterialCode(String externalMaterialCode);

    /** 租户id */
    String getTenantId();
}
