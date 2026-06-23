package com.inventory.middle.infra.plan.stub;

import com.inventory.middle.client.plan.dto.participant.ParticipantMenuDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantUser;
import com.inventory.middle.domain.service.external.ParticipantCenterRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class PlanParticipantStub {

    @Resource
    private ParticipantCenterRemoteService participantCenterRemoteService;

    public String getCompanyName(String tenantId, String userId, String companyCode) {
        try {
            String name = participantCenterRemoteService.getCompanyName(tenantId, userId, companyCode);
            if (name != null && !name.equals(companyCode)) return name;
        } catch (Exception e) {
            log.warn("getCompanyName fallback, companyCode={}", companyCode, e);
        }
        return companyCode;
    }

    public String getUserName(String userId) {
        return userId;
    }

    public List<ParticipantUser> fuzzyQueryUserInfo(String keywords) {
        try {
            List<ParticipantUser> users = participantCenterRemoteService.fuzzyQueryUserInfo(keywords);
            if (users != null && !users.isEmpty()) return users;
        } catch (Exception e) {
            log.warn("fuzzyQueryUserInfo fallback, keywords={}", keywords, e);
        }
        return java.util.Collections.emptyList();
    }

    public List<ParticipantMenuDTO> getMenuAndFunc(String tenantId, String userId) {
        try {
            List<ParticipantMenuDTO> menus = participantCenterRemoteService.getMenuAndFunc(tenantId, userId);
            if (menus != null && !menus.isEmpty()) return menus;
        } catch (Exception e) {
            log.warn("getMenuAndFunc fallback, tenantId={}, userId={}", tenantId, userId, e);
        }
        return java.util.Collections.emptyList();
    }
}
