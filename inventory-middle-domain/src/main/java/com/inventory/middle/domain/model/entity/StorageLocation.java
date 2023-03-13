package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.StorageLocationId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存储地点表领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class StorageLocation implements Entity<StorageLocation> {

		private StorageLocationId id;
		    		
	/**
	 * 创建该存储地点的租户id
	 */
	private String tenantId;
	    		
	/**
	 * 存储地点编码
	 */
	private String storageLocationNo;
	    		
	/**
	 * 存储地点名称
	 */
	private String storageLocationName;
	    		
	/**
	 * 逻辑仓库no
	 */
	private Long logicalPlantNo;
	    		
	/**
	 * 存储地点类型 0-普通存储地点；1-客户寄售；2-待回收包装；3-委外加工物资；4-供应商寄售；5-待退回包装；6-销售订单
	 */
	private Integer locationType;
	    		
	/**
	 * 描述
	 */
	private String description;
	    		
	/**
	 * 存储地点位置
	 */
	private String position;
	    		
	/**
	 * 创建人
	 */
	private String creatorId;
	    		
	/**
	 * 更新人
	 */
	private String updatorId;
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	    		
	/**
	 * 删除标识
	 */
	private Integer deleted;
	    
    @Override
    public boolean sameIdentityAs(StorageLocation other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
