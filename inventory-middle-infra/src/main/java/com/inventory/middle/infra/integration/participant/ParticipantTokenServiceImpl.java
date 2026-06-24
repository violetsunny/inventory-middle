package com.inventory.middle.infra.integration.participant;

import com.inventory.middle.domain.service.external.ParticipantTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ParticipantTokenServiceImpl implements ParticipantTokenService {

    @Value("${remote.participant.token.value:}")
    private String tokenValue;

    @Value("${remote.participant.token.secret:}")
    private String appSecret;

    @Override
    public String getParticipantToken() {
        if (StringUtils.isBlank(tokenValue)) {
            log.warn("participant-token not configured");
            return "";
        }
        return tokenValue;
    }

    @Override
    public String getAppSecret() {
        if (StringUtils.isBlank(appSecret)) {
            log.warn("participant appSecret not configured");
            return "";
        }
        return appSecret;
    }
}
