package com.inventory.middle.client.dto.material;

import com.inventory.middle.client.dto.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class MaterialDocumentResDTO extends BaseRequest {
    /**
     * 物料凭证请求ID
     */
    private String materialDocId;
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
    private String operator;
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
     * 参照单据号类型 采购单 销售单 物料凭证单 物料冲销单
     */
    private String originalNoTypeDesc;
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
    private String supplyLogicalPlantNo;

    /**
     * 发货方逻辑仓库code
     */
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
     * 业务唯一号 幂等
     */
    private String uniqueNo;
    /**
     * 物料凭证明细
     */
    private List<MaterialDocumentItemResDTO> materialDocumentItems;

}
