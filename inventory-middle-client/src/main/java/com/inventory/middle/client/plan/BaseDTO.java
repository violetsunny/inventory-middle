package com.inventory.middle.client.plan;
import lombok.Data;
import java.io.Serializable;
@Data
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 5280140882584262133L;
    private String token;
    private String userId;
    private String userName;
    private String tenantId;
}
