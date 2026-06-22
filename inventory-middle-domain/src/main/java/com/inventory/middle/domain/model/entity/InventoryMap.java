package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.InventoryMapId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 移动平均价领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class InventoryMap implements Entity<InventoryMap> {

		private InventoryMapId id;
		    		
	/**
	 * map流水号
	 */
	private String mapCode;
	    		
	/**
	 * map子流水code
	 */
	private String mapSubCode;
	    		
	/**
	 * 物料code
	 */
	private String skuCode;
	    		
	/**
	 * 逻辑仓
	 */
	private String logicalPlantNo;
	    		
	/**
	 * 移动平均价
	 */
	private BigDecimal map;
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 创建人ID
	 */
	private String creatorId;
	    		
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	    		
	/**
	 * 修改人ID
	 */
	private String updatorId;
	    		
	/**
	 * 删除标识
	 */
	private Integer deleted;
	    		
	/**
	 * 租户ID
	 */
	private String tenantId;
	    
    @Override
    public boolean sameIdentityAs(InventoryMap other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
