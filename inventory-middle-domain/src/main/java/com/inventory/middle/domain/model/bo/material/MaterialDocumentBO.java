package com.inventory.middle.domain.model.bo.material;

import com.inventory.middle.domain.common.annotation.EnumValidator;
import com.inventory.middle.domain.common.annotation.MaterialAdjustTypeValid;
import com.inventory.middle.domain.common.annotation.MaterialCategoryValid;
import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import com.inventory.middle.domain.model.enums.MaterialDocCategoryEnum;
import com.inventory.middle.domain.model.enums.MaterialDocTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
public class MaterialDocumentBO implements Serializable {
    /**
     * 物料凭证 预生成。 乐乐和前端约定，冲销的时候传入的是新生成的ID
     */
    private Long materialDocId;
    /**
     * 临时存放，因为冲销的时候既要用新的ID也要用老的ID
     */
    private Long tempMaterialDocId;
    /**
     * 物料凭证编号
     */
    private String materialDocNo;
    /**
     * 物料凭证组 0QC
     */
    private String materialDocGroup;
    /**
     * 物料凭证组描述 0QC-期初切换相关
     */
    private String materialDocGroupDesc;
    /**
     * 物料凭证类型 库存管理类
     */
    @NotNull(message = "物料凭证类型不能为空")
    @EnumValidator(enumClass = MaterialDocTypeEnum.class, checkMethod = "checkByCode", message = "物料凭证类型取值不正确")
    private Integer materialDocType;
    /**
     * 物料凭证类型
     */
    private String materialDocTypeDesc;
    /**
     * 出入库类型 出库 入库 出入库 取消
     */
    @NotNull(message = "出入库类型不能为空")
    @EnumValidator(enumClass = MaterialDocCategoryEnum.class, checkMethod = "checkByCode", message = "出入库类型取值不正确")
    private Integer materialDocCategory;
    /**
     * 出入库类型
     */
    private String materialDocCategoryDesc;
    /**
     * 业务事项 - 期初导入，调拨
     */
    private String businessType;
    /**
     * 业务事项名称 - 期初导入，调拨
     */
    private String businessTypeDesc;
    /**
     * token
     */
    private String token;
    /**
     * 操作人
     */
    @NotBlank(message = "操作人Id不能为空")
    private String operator;
    /**
     * 租户
     */
    @NotBlank(message = "租户Id不能为空")
    private String tenantId;
    /**
     * 参照单据号
     */
    @MaterialCategoryValid(match = {MaterialDocCategoryEnum.CANCEL}, message = "参照单据号不能为空")
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.RWW101, MaterialAdjustTypeEnum.RWW201}, message = "参照单据号不能为空")
    private String originalNo;
    /**
     *  MaterialDocRefOrderTypeEnum 参照单据号类型 采购单 销售单 物料凭证单 物料冲销单
     */
    @MaterialCategoryValid(match = {MaterialDocCategoryEnum.CANCEL}, message = "参照单据号类型不为空")
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.RWW101, MaterialAdjustTypeEnum.RWW201}, message = "参照单据号类型不为空")
    private Integer originalNoType;

    /**
     * 参照单据号类型 采购单 销售单 物料凭证单 物料冲销单
     */
    private String originalNoTypeDesc;

    /**
     * 交运单号,目前支撑交运域，后续其他系统对接根据业务要求传
     */
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.DB401, MaterialAdjustTypeEnum.DB405, MaterialAdjustTypeEnum.DB403,
        MaterialAdjustTypeEnum.DB407}, message = "交运单号不能为空")
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
     * 子流程是否有问题
     */
    private String subProcesses;

    /**
     * 相关财务凭证
     */
    private String financialDocNo;

    /**
     * 移动类型 eg:CG101
     */
    @NotBlank(message = "移动类型不能为空")
    @EnumValidator(enumClass = MaterialAdjustTypeEnum.class, checkMethod = "checkByCode", message = "移动类型取值不正确")
    private String adjustType;
    /**
     * 移动类型描述 eg:CG101
     */
    private String adjustTypeDesc;
    /**
     * 出入库标识 +-
     */
    private String io;

    /**
     * 收货方逻辑仓库code
     */
    @MaterialCategoryValid(match = {MaterialDocCategoryEnum.IN, MaterialDocCategoryEnum.INOUT}, message = "收货方逻辑仓库不能为空")
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.DB401, MaterialAdjustTypeEnum.DB405}, message = "收货方逻辑仓库不能为空")
    private String supplyLogicalPlantNo;

    /**
     * 发货方逻辑仓库code
     */
    @MaterialCategoryValid(match = {MaterialDocCategoryEnum.OUT, MaterialDocCategoryEnum.INOUT}, message = "发货方逻辑仓库不能为空")
    @MaterialAdjustTypeValid(match = {MaterialAdjustTypeEnum.DB403, MaterialAdjustTypeEnum.DB407}, message = "发货方逻辑仓库不能为空")
    private String demandLogicalPlantNo;

    /**
     * map流水code
     */
    private String mapCode;

    /**
     * 头备注
     */
    private String remark;

    /**
     * 是否计算map
     */
    private Boolean calMap;

    /**
     * 业务唯一号 幂等
     */
    @NotBlank(message = "业务唯一号不能为空")
    @Size(max = 64, message = "业务唯一号不能超过64位")
    private String uniqueNo;

    /**
     * 操作应用服务标识
     */
    private String appKey;

    /**
     * 是否需要批次同步 0-不同步 1-同步给产品中心 2-同步给库存物料中心
     */
    private Integer needBatchSys;

    /**
     * 物料凭证明细
     */
    @Size(min = 1, message = "物料凭证明细至少一个")
    @Valid
    private List<MaterialDocumentItemBO> materialDocumentItems;

    public Boolean isCalMap() {
        if(this.calMap == null) {
            //相同逻辑仓库是不用计算MAP的 && 根据移动类型来
            this.calMap =
                    !StringUtils.equals(this.getDemandLogicalPlantNo(), this.getSupplyLogicalPlantNo()) && Objects
                            .requireNonNull(MaterialAdjustTypeEnum.tansfer(this.getAdjustType()))
                            .isCalMAP();
        }
        return this.calMap;
    }

}
