package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.MaterialDocMainId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料凭证主表领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MaterialDocMain implements Entity<MaterialDocMain> {

		private MaterialDocMainId id;
		    		
	/**
	 * 物料凭证编号
	 */
	private String materialDocNo;
	    		
	/**
	 * 凭证类型
	 */
	private Integer docCategory;
	    		
	/**
	 * 凭证编码组
	 */
	private String docGroupNo;
	    		
	/**
	 * 凭证类别
	 */
	private Integer docType;
	    		
	/**
	 * 创建日期
	 */
	private LocalDateTime publishDate;
	    		
	/**
	 * 过账日期
	 */
	private LocalDateTime postingDate;
	    		
	/**
	 * 参照业务单据号
	 */
	private String originalNo;
	    		
	/**
	 * 业务单据类型
	 */
	private Integer originalNoType;
	    		
	/**
	 * 交运单号
	 */
	private String deliverNo;
	    		
	/**
	 * 库存所有者
	 */
	private String owner;
	    		
	/**
	 * 移动类型
	 */
	private String adjustType;
	    		
	/**
	 * 收货方逻辑仓库no
	 */
	private String supplyLogicalPlantNo;
	    		
	/**
	 * 发货方逻辑仓库no
	 */
	private String demandLogicalPlantNo;
	    		
	/**
	 * map流水号
	 */
	private String mapCode;
	    		
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
	    		
	/**
	 * 备注
	 */
	private String remark;
	    		
	/**
	 * 外部业务唯一号
	 */
	private String uniqueNo;
	    		
	/**
	 * 操作应用服务标识
	 */
	private String appKey;
	    
    @Override
    public boolean sameIdentityAs(MaterialDocMain other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
