package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author peisheng.wang
 * @description 库存
 * @date 2021-06-16
 */
@Data
public class PagedMaterialDocumentReqDTO extends BaseRequest {

    private static final long serialVersionUID = 1L;

    private String companyCode;

    private String materialDocGroup;

    private String logicalPlantNo;

    private String materialCode;

    private String outMaterialCode;

    private String materialDocType;

    private String materialDocNo;

    private String referBizNo;

    private String maxMaterialDocDate;

    private String maxAccountsPostingDate;

    private String minMaterialDocDate;

    private String minAccountsPostingDate;

    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;

    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;

    @NotNull(message = "租户id不能为空")
    @Size(max = 40, message = "租户id过长，最大40")
    private String tenantId;


}
