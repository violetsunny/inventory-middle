package com.inventory.middle.application.plan.demand.service;

import com.inventory.middle.application.plan.demand.bo.*;
import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import java.util.List;

/**
 * @author xiaokang
 */
public interface DemandPlanService {

    /**
     * 创建需求计划
     * @param demandPlanBO
     * @return
     */
    boolean createDemandPlan(DemandPlanBO demandPlanBO);

    boolean updateDemandPlan(DemandPlanBO demandPlanBO);

    /**
     * 导入物料需求计划
     * @param createBO
     * @return
     */
    DemandPlanMaterialBatchCreateResBO createDemandPlanMaterialPeriod(DemandPlanMaterialBatchCreateReqBO createBO);

    /**
     * 创建完整的需求计划
     * @param demandPlanBO
     * @return
     */
    SingleResponse<DemandPlanMaterialBatchCreateResBO> createFullDemandPlan(DemandPlanBO demandPlanBO);

    /**
     * 分页查询
     * @param reqDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<DemandPlanSelectResBO> selectDemandPlanByPage(DemandPlanSelectReqBO reqBo, int pageNum, int pageSize);

    /**
     * 更新状态
     * @param statusBO
     */
    boolean updateDemandPlanStatus(DemandPlanUpdateStatusBO statusBO);


    /**
     * 剔除物料
     * @param reqBO
     * @return
     */
    boolean cancelDemandPlanMaterial(CancelDemandPlanMaterialReqBO reqBO);

    /**
     * 修改物料数量
     * @param reqBO
     * @return
     */
    boolean modifyDemandPlanMaterialAmount(ModifyDemandPlanMaterialAmountReqBO reqBO);

    /**
     * 导出物料模板表头
     * @param demandPlanId
     * @return
     */
    List<String> exportDemandPlanTemplate(Long demandPlanId,String tenantId );

    /**
     * 需求计划文件查询
     * @param reqBO
     * @return
     */
    DemandPlanVersionSelectResBO selectByLogicalPlantNos(DemandPlanVersionSelectReqBO reqBO);

    /**
     * 需求计划详情查询
     * @param reqBO
     * @return
     */
    DemandPlanDetailSelectResBO selectDemandPlanDetail(DemandPlanDetailSelectReqBO reqBO);

    /**
     * 定时任务每日跑一下job
     * @param material 物料信息
     */
    void generateData(MaterialBO material);
}
