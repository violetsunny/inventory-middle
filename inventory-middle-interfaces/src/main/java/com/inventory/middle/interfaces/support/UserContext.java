package com.inventory.middle.interfaces.support;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserContext {
    private String userId;
    private String tenantId;
    private String username;
}
