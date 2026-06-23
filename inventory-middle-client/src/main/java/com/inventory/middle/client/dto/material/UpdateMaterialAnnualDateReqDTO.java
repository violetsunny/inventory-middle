package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/9/3 16:35
 */
@Data
public class UpdateMaterialAnnualDateReqDTO extends BaseRequest {

    private String materialCode;

    private String batchNo;

    private String token;

    private String tenantId;

    private String materialDocNo;

}