package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.MaterialDocItemId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料凭证-item领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MaterialDocItem implements Entity<MaterialDocItem> {

		private MaterialDocItemId id;
		    		
	/**
	 * 物料凭证id
	 */
	private Long materialDocId;
	    		
	/**
	 * 物料code
	 */
	private String materialCode;
	    		
	/**
	 * 批次号
	 */
	private String batchNo;
	    		
	/**
	 * 出入库类型1-入库,2-出库
	 */
	private Integer itemCategory;
	    		
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
    public boolean sameIdentityAs(MaterialDocItem other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
