package com.inventory.middle.domain.model.entity.inventory;


import com.inventory.middle.domain.model.types.InventoryDemandId;
import top.kdla.framework.domain.shared.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存-需求领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 16:34:30
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class InventoryDemand implements Entity<InventoryDemand> {

		private InventoryDemandId id;
		    		
	/**
	 * 物料code
	 */
	private String materialCode;
	    		
	/**
	 * 批次号
	 */
	private String batchNo;
	    		
	/**
	 * 批次出库时间
	 */
	private LocalDateTime batchTime;
	    		
	/**
	 * 库存所有者
	 */
	private String owner;
	    		
	/**
	 * 所在仓库
	 */
	private String logicalPlantNo;
	    		
	/**
	 * 存储地点
	 */
	private String storageLocationNo;
	    		
	/**
	 * 正品数量
	 */
	private BigDecimal unrestricted;
	    		
	/**
	 * 次品数量
	 */
	private BigDecimal damaged;
	    		
	/**
	 * 质检数量
	 */
	private BigDecimal inspection;
	    		
	/**
	 * 计量单位
	 */
	private String uom;
	    		
	/**
	 * 需求取消日期
	 */
	private LocalDateTime cancelDate;
	    		
	/**
	 * 需求过期日期
	 */
	private LocalDateTime deadlineDate;
	    		
	/**
	 * 货币单位
	 */
	private String currency;
	    		
	/**
	 * 需求类型1-立即出库，2-在途转出库，3-计划转出库
	 */
	private Integer demandType;
	    		
	/**
	 * 出库价
	 */
	private BigDecimal demandPrice;
	    		
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
    public boolean sameIdentityAs(InventoryDemand other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
