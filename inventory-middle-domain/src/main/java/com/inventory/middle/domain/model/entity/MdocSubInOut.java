package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.MdocSubInOutId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料凭证子表-出入库信息领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MdocSubInOut implements Entity<MdocSubInOut> {

		private MdocSubInOutId id;
		    		
	/**
	 * 物料凭证id
	 */
	private Long materialDocId;
	    		
	/**
	 * 物料凭证item
	 */
	private Long materialDocItemId;
	    		
	/**
	 * 发货方名称
	 */
	private String demand;
	    		
	/**
	 * 发货销售订单号
	 */
	private String demandSaleOrderNo;
	    		
	/**
	 * 发货销售订单行号
	 */
	private String demandSaleOrderItemNo;
	    		
	/**
	 * 发货方库存地点no
	 */
	private String demandStorageLocationNo;
	    		
	/**
	 * 发货方库存地点名称
	 */
	private String demandStorageLocationName;
	    		
	/**
	 * 发货方批次号
	 */
	private String demandBatchNo;
	    		
	/**
	 * 发货方库存类型(良品、残次品、质检品)
	 */
	private Integer demandStockType;
	    		
	/**
	 * 收货方名称
	 */
	private String supply;
	    		
	/**
	 * 收货销售订单号
	 */
	private String supplySaleOrderNo;
	    		
	/**
	 * 收货销售订单行号
	 */
	private String supplySaleOrderItemNo;
	    		
	/**
	 * 收货方库存地点no
	 */
	private String supplyStorageLocationNo;
	    		
	/**
	 * 收货方库存地点名称
	 */
	private String supplyStorageLocationName;
	    		
	/**
	 * 收货方批次号
	 */
	private String supplyBatchNo;
	    		
	/**
	 * 收货方库存类型(良品、残次品、质检品)
	 */
	private Integer supplyStockType;
	    		
	/**
	 * 移动类型
	 */
	private String adjustType;
	    		
	/**
	 * 移动原因
	 */
	private String adjustReason;
	    		
	/**
	 * 出入库标识
	 */
	private String io;
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 创建人id
	 */
	private String creatorId;
	    		
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	    		
	/**
	 * 修改人id
	 */
	private String updatorId;
	    		
	/**
	 * 删除标识
	 */
	private Integer deleted;
	    		
	/**
	 * 租户id
	 */
	private String tenantId;
	    
    @Override
    public boolean sameIdentityAs(MdocSubInOut other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
