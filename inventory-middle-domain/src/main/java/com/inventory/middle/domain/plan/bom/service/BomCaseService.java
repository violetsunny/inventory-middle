package com.inventory.middle.domain.plan.bom.service;

import com.inventory.middle.domain.plan.bom.bo.*;

import java.util.List;

/**
 * BOM 服务接口
 * <p>
 * 迁移自 com.enn.plan.management.core.bom.service.BomCaseService
 * </p>
 *
 * @author migrated from scm-plan-management
 */
public interface BomCaseService {

    /**
     * 生成bom树
     *
     * @param requestBO
     * @return
     */
    List<BomTreeBO> renderBomTree(BomTreeRenderRequestBO requestBO);

    /**
     * 创建BOM
     *
     * @param requestBO
     * @return
     */
    String create(BomCaseConfigurationBO requestBO);

    /**
     * 更新BOM
     *
     * @param requestBO
     * @return
     */
    String update(BomCaseConfigurationBO requestBO);

    /**
     * BOM单及母件详情
     *
     * @param reqBO
     * @return
     */
    BomCaseDetailBO queryBomCaseDetail(BomCaseDetailReqBO reqBO);

    /**
     * 查询子件详情(不分页)
     *
     * @param reqBO
     * @return
     */
    List<BomNodeBO> queryChildrenDetail(BomCaseDetailReqBO reqBO);
}
