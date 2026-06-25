package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.service.InventoryCommonApplicationService;
import com.inventory.middle.client.plan.dto.participant.ParticipantDeptDTO;
import com.inventory.middle.domain.service.external.ParticipantCenterRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通用功能 ApplicationService 实现（M15：下沉外部服务依赖）
 */
@Service
@Slf4j
public class InventoryCommonApplicationServiceImpl implements InventoryCommonApplicationService {

    @Resource
    private ParticipantCenterRemoteService participantCenterRemoteService;

    @Override
    public List<ParticipantDeptDTO> getTenantDeptTree(String tenantId, String userId) {
        return participantCenterRemoteService.getTenantDeptTree(tenantId, userId);
    }
}
