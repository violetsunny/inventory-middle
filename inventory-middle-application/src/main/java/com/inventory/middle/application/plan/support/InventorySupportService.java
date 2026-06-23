package com.inventory.middle.application.plan.support;

import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
import com.inventory.middle.client.plan.dto.inventory.request.MaterialPlanInventoryRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 支撑域-库存域服务接口
 * TODO: 待实现，依赖 inventory-middle 已有 inventory 查询服务
 *
 * @author Danny.Lee (migrated from scm-plan-management)
 * @date 2022/4/12
 */
public interface InventorySupportService {

    /**
     * 查询租户下仓库列表
     *
     * @param tenant 租户
     * @return 仓库列表
     */
    List<InvPlantBO> listPlants(String tenant);

    /**
     * 根据仓库编码查询仓库
     *
     * @param plantCode 仓库编码
     * @param tenant    租户
     * @return 仓库
     */
    InvPlantBO findPlant(String plantCode, String tenant);

    /**
     * 查询物料库存
     *
     * @param materialCode 物料编码
     * @param plantCode    仓库编码
     * @param tenant       租户
     * @return 物料库存
     */
    BigDecimal queryInventory(String materialCode, String plantCode, String tenant);

    /**
     * 查询逾期计划库存
     *
     * @param request 物料计划库存查询请求
     * @return 逾期计划库存
     */
    BigDecimal queryOverduePlanInventory(MaterialPlanInventoryRequest request);

    /**
     * 查询计划库存
     *
     * @param request 物料计划库存查询请求
     * @return 计划日期-计划库存
     */
    Map<LocalDate, BigDecimal> queryPlanInventory(MaterialPlanInventoryRequest request);

    /**
     * 查询仓库所属物料集合
     *
     * @param tenant     租户
     * @param plantCodes 仓库列表
     * @return 仓库-物料集合
     */
    Map<String, List<String>> queryMaterialsByPlantCodes(String tenant, List<String> plantCodes);
}
