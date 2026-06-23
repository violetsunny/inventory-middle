package com.inventory.middle.interfaces.support;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader("X-Tenant-Id");
        String userId   = request.getHeader("X-User-Id");
        String username = request.getHeader("X-Username");
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
}
