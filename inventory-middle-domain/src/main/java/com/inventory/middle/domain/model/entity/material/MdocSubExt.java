package com.inventory.middle.domain.model.entity.material;


import com.inventory.middle.domain.model.types.MdocSubExtId;
import top.kdla.framework.domain.shared.Entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料凭证子表-扩展领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 16:34:29
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MdocSubExt implements Entity<MdocSubExt> {

		private MdocSubExtId id;
		    		
	/**
	 * 物料凭证id
	 */
	private Long materialDocId;
	    		
	/**
	 * 物料凭证itemId
	 */
	private Long materialDocItemId;
	    		
	/**
	 * 批次有效天数
	 */
	private Integer validDays;
	    		
	/**
	 * 生产日期
	 */
	private LocalDateTime produceDate;
	    		
	/**
	 * 海关编码
	 */
	private String hsCode;
	    		
	/**
	 * 下次年检日期yyyy-mm-dd
	 */
	private LocalDateTime annualDate;
	    		
	/**
	 * 年检周期天数
	 */
	private Integer annualCycleDays;
	    		
	/**
	 * 租户id
	 */
	private String tenantId;
	    		
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
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 删除标识
	 */
	private Integer deleted;
	    
    @Override
    public boolean sameIdentityAs(MdocSubExt other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
