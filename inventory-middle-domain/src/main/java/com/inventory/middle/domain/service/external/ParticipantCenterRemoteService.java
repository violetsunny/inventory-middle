package com.inventory.middle.domain.service.external;

import com.inventory.middle.client.plan.dto.participant.ParticipantDeptDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantMenuDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantUser;

import java.util.List;

public interface ParticipantCenterRemoteService {

    List<ParticipantUser> fuzzyQueryUserInfo(String keywords);

    List<ParticipantDeptDTO> getTenantDeptTree(String tenantId, String userId);

    String getCompanyName(String tenantId, String userId, String companyCode);

    List<ParticipantMenuDTO> getMenuAndFunc(String tenantId, String userId);
}
