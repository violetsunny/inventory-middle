package com.inventory.middle.application.plan.common.service;

import com.inventory.middle.client.plan.dto.participant.ParticipantMenuDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantUser;
import com.inventory.middle.domain.plan.common.bo.PlanProductBO;

import java.util.List;

public interface PlanCommonApplicationService {

    PlanProductBO queryMaterialByCode(String materialCode, String tenantId);

    List<ParticipantUser> fuzzyQueryUserInfo(String keywords);

    List<ParticipantMenuDTO> getMenuAndFunc(String tenantId, String userId);
}
