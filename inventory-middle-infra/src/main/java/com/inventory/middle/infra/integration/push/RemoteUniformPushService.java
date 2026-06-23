package com.inventory.middle.infra.integration.push;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 远程统一推送服务（通过 OpenFeign / RestTemplate HTTP 调用）
 * <p>
 * 迁移自: com.enn.inventory.center.integration.push.RemoteUniformPushService
 * <p>
 * 原 Dubbo @DubboReference(version = "1.0.0") UniformPushClient
 * 已替换为 HTTP REST 调用方式
 */
@Slf4j
@Service
public class RemoteUniformPushService {

    @Resource
    private RestTemplate restTemplate;

    @Value("${remote.uniformPush.url:}")
    private String uniformPushUrl;

    @Value("${remote.uniformPush.tenantId:}")
    private String tenantId;

    @Value("${remote.uniformPush.templateCode:}")
    private String templateCode;

    /**
     * 统一推送
     * 原方法: uniformPushClient.push(pushRequestDTO)
     * 现通过 HTTP POST 调用统一推送服务
     */
    public boolean push(Object pushRequestDTO) {
        log.info("RemoteUniformPushService.push request={}", JSON.toJSONString(pushRequestDTO));
        if (uniformPushUrl == null || uniformPushUrl.isEmpty()) {
            log.warn("RemoteUniformPushService.push - uniformPushUrl not configured, skip push");
            return false;
        }
        try {
            Map<String, Object> request = new HashMap<>();
            if (pushRequestDTO instanceof Map) {
                request.putAll((Map) pushRequestDTO);
            } else {
                request.put("data", pushRequestDTO);
            }
            request.put("tenantId", tenantId);
            request.put("templateCode", templateCode);
            String result = restTemplate.postForObject(uniformPushUrl, request, String.class);
            log.info("RemoteUniformPushService.push response={}", result);
            return true;
        } catch (Exception e) {
            log.error("RemoteUniformPushService.push failed", e);
            return false;
        }
    }
}
