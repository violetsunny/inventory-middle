package com.inventory.middle.application.service;

import com.inventory.middle.client.plan.dto.participant.ParticipantDeptDTO;

import java.util.List;

/**
 * 通用功能 ApplicationService（M15：下沉 InventoryCommonController 的直连外部服务依赖）
 */
public interface InventoryCommonApplicationService {

    /** 查询租户组织树 */
    List<ParticipantDeptDTO> getTenantDeptTree(String tenantId, String userId);
}
