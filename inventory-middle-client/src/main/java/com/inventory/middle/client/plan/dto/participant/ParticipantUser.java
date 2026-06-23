package com.inventory.middle.client.plan.dto.participant;

import lombok.Data;

/**
 * 参与者用户
 */
@Data
public class ParticipantUser {

    private String id;
    private String email;
    private String loginName;
    private String mobile;
    private String nickname;
    private Integer type;
}
