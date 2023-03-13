package com.inventory.middle.domain.model.entity.material;


import com.inventory.middle.domain.model.types.MdocSubFinanceId;
import top.kdla.framework.domain.shared.Entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料凭证子表-财务领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 16:34:29
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MdocSubFinance implements Entity<MdocSubFinance> {

		private MdocSubFinanceId id;
		    		
	/**
	 * 物料凭证id
	 */
	private Long materialDocId;
	    		
	/**
	 * 物料凭证itemId
	 */
	private Long materialDocItemId;
	    		
	/**
	 * 资产号
	 */
	private String assertTag;
	    		
	/**
	 * 次级编号
	 */
	private String subAssertTag;
	    		
	/**
	 * 利润中心名称
	 */
	private String profitCenterName;
	    		
	/**
	 * 利润中心
	 */
	private String profitCenterCode;
	    		
	/**
	 * 成本中心名称
	 */
	private String costCenterName;
	    		
	/**
	 * 成本中心
	 */
	private String costCenterCode;
	    		
	/**
	 * 产品线
	 */
	private String productLine;
	    		
	/**
	 * 贸易伙伴
	 */
	private String tradePartner;
	    		
	/**
	 * 供应商名称
	 */
	private String supplyName;
	    		
	/**
	 * 供应商编码
	 */
	private String supplyCode;
	    		
	/**
	 * 客户名称
	 */
	private String customerName;
	    		
	/**
	 * 客户编码
	 */
	private String customerCode;
	    		
	/**
	 * 结算方式
	 */
	private Integer settlementType;
	    		
	/**
	 * 营销活动编码
	 */
	private String marketingNo;
	    		
	/**
	 * 预算编码
	 */
	private String budgetNo;
	    		
	/**
	 * 内部订单号
	 */
	private String internalOrderNo;
	    		
	/**
	 * 备注
	 */
	private String remark;
	    		
	/**
	 * 删除标识
	 */
	private Integer deleted;
	    		
	/**
	 * 租户id
	 */
	private String tenantId;
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 创建人id
	 */
	private Long creatorId;
	    		
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	    		
	/**
	 * 修改人id
	 */
	private Long updatorId;
	    
    @Override
    public boolean sameIdentityAs(MdocSubFinance other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
