package com.inventory.middle.client.plan.dto.participant;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 参与者菜单
 *
 * @author xiaokang
 */
@Data
public class ParticipantMenuDTO implements Serializable {

    private Integer level;
    private String resDesc;
    private String resId;
    private String resKey;
    private Integer resLabel;
    private String resName;
    private String resType;
    private String rsrv1;
    private String url;
    private List<ParticipantMenuDTO> subMenu;
}
