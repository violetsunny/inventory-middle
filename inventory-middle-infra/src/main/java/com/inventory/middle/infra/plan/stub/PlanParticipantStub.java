package com.inventory.middle.infra.plan.stub;

import com.inventory.middle.client.plan.dto.participant.ParticipantMenuDTO;
import com.inventory.middle.client.plan.dto.participant.ParticipantUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 参与者中心 Stub
 * 用于获取公司名称等参与者信息
 * TODO: 接入真实的参与者中心服务（通过 Feign 或 RPC）
 */
@Slf4j
@Component
public class PlanParticipantStub {

    public String getCompanyName(String tenantId, String userId, String companyCode) {
        log.warn("PlanParticipantStub.getCompanyName: stub implementation, companyCode={}", companyCode);
        return companyCode;
    }

    public String getUserName(String userId) {
        log.warn("PlanParticipantStub.getUserName: stub implementation, userId={}", userId);
        return userId;
    }

    /**
     * 模糊查询用户信息
     * TODO: 待接入 ParticipantCenter - fuzzyQueryUserInfo
     */
    public List<ParticipantUser> fuzzyQueryUserInfo(String keywords) {
        log.warn("PlanParticipantStub.fuzzyQueryUserInfo: stub, keywords={}", keywords);
        return Collections.emptyList();
    }

    /**
     * 查询菜单权限
     * TODO: 待接入 ParticipantCenter - getMenuAndFunc
     */
    public List<ParticipantMenuDTO> getMenuAndFunc(String tenantId, String userId) {
        log.warn("PlanParticipantStub.getMenuAndFunc: stub, tenantId={}, userId={}", tenantId, userId);
        return Collections.emptyList();
    }
}

