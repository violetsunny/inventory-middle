package com.inventory.middle.client.dto.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @author holmes
 * @Classname DomainMaterialDocRespDTO
 * @Description DomainMaterialDocRespDTO
 * @Date 2021/8/23 18:07
 */
@Data
public class DomainMaterialDocRespDTO implements Serializable {

    /**
     * 物料凭证号
     */
    private String materialDocNo;

    /**
     * 多个批次号=','号隔开
     */
    private String batchNo;

    /**
     * 物料凭证号-全量=','号隔开
     */
    private String fullMaterialDocNo;

}
