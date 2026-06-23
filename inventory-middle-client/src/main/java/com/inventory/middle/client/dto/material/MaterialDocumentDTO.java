package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import com.inventory.middle.client.enums.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MaterialDocumentDTO extends BaseRequest {
    /**
     * 物料凭证编号
     */
    private String materialDocNo;
    /**
     * @see MaterialDocGroupEnum 物料凭证组 0QC
     */
    private String materialDocGroup;
    /**
     * @see MaterialDocTypeEnum 物料凭证类型 库存管理类
     */
    private Integer materialDocType;
    /**
     * @see MaterialDocCategoryEnum 出入库类型 出库 入库 出入库 取消
     */
    private Integer materialDocCategory;
    /**
     * @see BusinessTypeEnum 业务事项 - 期初导入，调拨
     */
    private String businessType;
    /**
     * token
     */
    private String token;
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
     * @see MaterialDocRefOrderTypeEnum 参照单据号类型 采购单 销售单 物料凭证单 物料冲销单
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
     * @see MaterialAdjustTypeEnum 移动类型 eg:CG101
     */
    @NotBlank(message = "adjustType 移动类型不能为空")
    private String adjustType;
    /**
     * 出入库标识 +-
     */
    private String io;
    /**
     * 收货方逻辑仓库code
     */
    private String supplyLogicalPlantNo;

    /**
     * 发货方逻辑仓库code
     */
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
     * 物料凭证明细
     */
    @Size(min = 1, message = "materialDocumentItems 物料凭证明细至少一个")
    private List<MaterialDocumentItemDTO> materialDocumentItems;

}
