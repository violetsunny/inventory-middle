package com.inventory.middle.client.dto.material.out;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.client.enums.MaterialAdjustTypeEnum;
import com.inventory.middle.client.enums.MaterialDocCategoryEnum;
import com.inventory.middle.client.enums.MaterialDocRefOrderTypeEnum;
import com.inventory.middle.client.enums.MaterialDocTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 出库生成凭证DTO
 *
 * @author vincent.li
 * @date 2022/1/20 14:30
 */
@Data
public class OutboundMaterialDocDTO extends BaseRequest {
    /**
     * MaterialAdjustTypeEnum 移动类型 eg:OLY201
     * @see MaterialAdjustTypeEnum
     */
    @NotBlank(message = "adjustType 移动类型不能为空")
    private String adjustType;
    /**
     * MaterialDocTypeEnum 物料凭证类型 库存管理类
     * @see MaterialDocTypeEnum
     */
    @NotNull(message = "materialDocType 物料凭证类型不能为空")
    private Integer materialDocType;
    /**
     * MaterialDocCategoryEnum 出入库类型 出库 入库 出入库 取消
     * @see MaterialDocCategoryEnum
     */
    @NotNull(message = "materialDocCategory 出入库类型不能为空")
    private Integer materialDocCategory;
    /**
     * 操作人Id
     */
    @NotBlank(message = "operator 操作人Id不能为空")
    private String operator;
    /**
     * 租户
     */
    @NotBlank(message = "tenantId 租户不能为空")
    private String tenantId;
    /**
     * 参照单据号
     */
    private String originalNo;
    /**
     * MaterialDocRefOrderTypeEnum 参照单据号类型 采购单 销售单
     * @see MaterialDocRefOrderTypeEnum
     */
    private Integer originalNoType;

    /**
     * 交运单号,目前支撑交运域，后续其他系统对接根据业务要求传
     */
    private String deliverNo;
    /**
     * 凭证日期
     */
    private String publishDate;
    /**
     * 过帐日期
     */
    private String postingDate;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 发货方逻辑仓库code
     */
    @NotBlank(message = "demandLogicalPlantNo 发货方逻辑仓库不能为空")
    private String demandLogicalPlantNo;
    /**
     * 头备注
     */
    private String remark;
    /**
     * 业务唯一号 幂等 （也可以传materialDocId）
     */
    @NotBlank(message = "uniqueNo 业务唯一号不能为空")
    private String uniqueNo;

    /**
     * 是否计算map
     */
    private Boolean calMap;

    /**
     * 出库物料凭证明细
     */
    @Size(min = 1, message = "materialDocumentItems 出库物料凭证明细至少一个")
    private List<OutboundMaterialDocItemDTO> materialDocumentItems;
}
