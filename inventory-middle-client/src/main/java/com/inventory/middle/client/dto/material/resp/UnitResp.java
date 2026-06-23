package com.inventory.middle.client.dto.material.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 单位DTO
 *
 * @author vincent.li
 * @date 2022/5/7 11:08
 */
@Data
public class UnitResp implements Serializable {
    /**
     * 单位id
     */
    private Long id;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 描述
     */
    private String description;

    /**
     * 业务编码
     */
    private String bizCode;
}
