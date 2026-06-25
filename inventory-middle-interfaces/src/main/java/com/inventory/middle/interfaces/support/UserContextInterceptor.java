package com.inventory.middle.interfaces.support;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 用户上下文拦截器。
 * <p>
 * 优先从 X-Tenant-Id / X-User-Id / X-Username 三个明文 header 读取上下文；
 * 若这三个 header 为空，则尝试从 ennUnifiedAuthorization JWT header 中解析，
 * 以兼容 inventory-center-bff / scm-plan-bff 前端的登录态传递方式。
 * </p>
 */
@Slf4j
@Component
public class UserContextInterceptor implements HandlerInterceptor {

    private static final String HEADER_UNIFIED_AUTH = "ennUnifiedAuthorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader("X-Tenant-Id");
        String userId   = request.getHeader("X-User-Id");
        String username = request.getHeader("X-Username");

        // Fallback: parse ennUnifiedAuthorization JWT when explicit headers are absent
        if (StringUtils.isBlank(tenantId) && StringUtils.isBlank(userId)) {
            String token = request.getHeader(HEADER_UNIFIED_AUTH);
            if (StringUtils.isNotBlank(token)) {
                try {
                    JSONObject payload = parseJwtPayload(token);
                    if (payload != null) {
                        tenantId = payload.getString("tenantId");
                        userId   = payload.getString("userId");
                        username = payload.getString("username");
                    }
                } catch (Exception e) {
                    log.warn("UserContextInterceptor: failed to parse ennUnifiedAuthorization token, fallback to empty context. error={}", e.getMessage());
                }
            }
        }

        UserContextHolder.set(UserContext.builder()
                .tenantId(tenantId)
                .userId(userId)
                .username(username)
                .build());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object h, Exception ex) {
        UserContextHolder.clear();
    }

    /**
     * 解析 JWT payload 段（第二段），返回 JSON 对象。
     * JWT 格式：header.payload.signature，各段使用 Base64URL 编码。
     */
    private JSONObject parseJwtPayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            return null;
        }
        // Base64URL decode: replace '-' with '+', '_' with '/'
        String base64Payload = parts[1]
                .replace('-', '+')
                .replace('_', '/');
        // Add padding if needed
        int mod = base64Payload.length() % 4;
        if (mod == 2) {
            base64Payload += "==";
        } else if (mod == 3) {
            base64Payload += "=";
        }
        byte[] decoded = Base64.getDecoder().decode(base64Payload);
        String payloadJson = new String(decoded, StandardCharsets.UTF_8);
        return JSONObject.parseObject(payloadJson);
    }
}
