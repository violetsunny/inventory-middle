package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.LogicalPlantId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 逻辑仓库表领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class LogicalPlant implements Entity<LogicalPlant> {

		private LogicalPlantId id;
		    		
	/**
	 * 创建该逻辑仓库的租户id
	 */
	private String tenantId;
	    		
	/**
	 * 逻辑仓库编码
	 */
	private String logicalPlantNo;
	    		
	/**
	 * 逻辑仓库名称
	 */
	private String logicalPlantName;
	    		
	/**
	 * 逻辑仓库类型
	 */
	private Integer type;
	    		
	/**
	 * 公司主体代码
	 */
	private String companyCode;
	    		
	/**
	 * 该逻辑仓所属的物理仓no
	 */
	private String warehouseNo;
	    		
	/**
	 * 省
	 */
	private String province;
	    		
	/**
	 * 市
	 */
	private String city;
	    		
	/**
	 * 区
	 */
	private String region;
	    		
	/**
	 * 逻辑仓库地址
	 */
	private String address;
	    		
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
    public boolean sameIdentityAs(LogicalPlant other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
