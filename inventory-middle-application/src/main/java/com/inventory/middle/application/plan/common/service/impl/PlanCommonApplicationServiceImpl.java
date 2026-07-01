package com.inventory.middle.application.plan.common.service.impl;

import com.inventory.middle.application.plan.common.service.PlanCommonApplicationService;
import com.inventory.middle.client.plan.dto.participant.ParticipantMenuDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantUser;
import com.inventory.middle.domain.plan.common.bo.PlanProductBO;
import com.inventory.middle.infra.plan.stub.PlanParticipantStub;
import com.inventory.middle.infra.plan.stub.PlanProductStub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class PlanCommonApplicationServiceImpl implements PlanCommonApplicationService {

    @Resource
    private PlanProductStub planProductStub;

    @Resource
    private PlanParticipantStub planParticipantStub;

    @Override
    public PlanProductBO queryMaterialByCode(String materialCode, String tenantId) {
        return planProductStub.queryMaterialByCode(materialCode, tenantId);
    }

    @Override
    public List<ParticipantUser> fuzzyQueryUserInfo(String keywords) {
        return planParticipantStub.fuzzyQueryUserInfo(keywords);
    }

    @Override
    public List<ParticipantMenuDTO> getMenuAndFunc(String tenantId, String userId) {
        return planParticipantStub.getMenuAndFunc(tenantId, userId);
    }
}
