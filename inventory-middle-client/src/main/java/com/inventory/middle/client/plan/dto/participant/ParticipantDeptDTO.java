package com.inventory.middle.client.plan.dto.participant;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 参与者部门
 *
 * @author dongguo.tao
 */
@Data
public class ParticipantDeptDTO implements Serializable {

    private static final long serialVersionUID = -9172333615589191194L;

    private String id;
    private String participantId;
    private String groupName;
    private int isFictitious;
    private String createTime;
    private String updateTime;
    private String pid;
    private int level;
    private String deptType;
    private String remarks;
    private String pathCode;
    private String pathName;
    private int isDel;
    private String tenantId;
    private String leaderId;
    private boolean checked;
    private String typeCode;
    private String typeName;
    private List<ParticipantDeptDTO> children;
}
