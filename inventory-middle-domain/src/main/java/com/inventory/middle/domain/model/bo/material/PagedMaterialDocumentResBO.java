package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author holmes
 */
@Data
public class PagedMaterialDocumentResBO implements Serializable {

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
    private Integer materialDocType;
    /**
     * 物料凭证类型
     */
    private String materialDocTypeDesc;
    /**
     * 出入库类型 出库 入库 出入库 取消
     */
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
     * 操作人
     */
    private Long operator;
    /**
     * 租户
     */
    private String tenantId;
    /**
     * 参照单据号
     */
    private String originalNo;
    /**
     * 参照单据号类型 采购单 销售单 物料凭证单 物料冲销单
     */
    private Integer originalNoType;
    /**
     * 凭证日期
     */
    private String publishDate;
    /**
     * 过帐日期
     */
    private String postingDate;

    /**
     * 备注
     */
    private String extInfo;

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
    @NotEmpty
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
     * 发货方逻辑仓库code
     */
    private String demandLogicalPlantNo;

    /**
     * 发货方逻辑仓库名称
     */
    private String demandLogicalPlantName;

    /**
     * 发货方物理仓
     */
    private String demandWarehouseName;

    /**
     * 收货方逻辑仓库code
     */
    private String supplyLogicalPlantNo;

    /**
     * 收货方逻辑仓库名称
     */
    private String supplyLogicalPlantName;

    /**
     * 收货方物理仓名称
     */
    private String supplyWarehouseName;

    /**
     * 公司代码
     */
    private String companyCode;

    /**
     * map流水号
     */
    private String mapCode;
}
