package com.inventory.middle.infra.plan.stub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 参与者中心 Stub
 * 用于获取公司名称等参与者信息
 * TODO: 接入真实的参与者中心服务（通过 Feign 或 RPC）
 */
@Slf4j
@Component
public class PlanParticipantStub {

    /**
     * 获取公司名称
     *
     * @param tenantId    租户ID
     * @param userId      用户ID
     * @param companyCode 公司编码
     * @return 公司名称
     */
    public String getCompanyName(String tenantId, String userId, String companyCode) {
        log.warn("PlanParticipantStub.getCompanyName: stub implementation, companyCode={}", companyCode);
        return companyCode;
    }

    public String getUserName(String userId) {
        log.warn("PlanParticipantStub.getUserName: stub implementation, userId={}", userId);
        return userId;
    }
}
