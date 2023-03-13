package com.inventory.middle.domain.model.entity.material;


import com.inventory.middle.domain.model.types.MdocSubMapId;
import top.kdla.framework.domain.shared.Entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料凭证子表-移动平均价领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 16:34:29
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MdocSubMap implements Entity<MdocSubMap> {

		private MdocSubMapId id;
		    		
	/**
	 * 物料凭证id
	 */
	private Long materialDocId;
	    		
	/**
	 * 物料凭证itemId
	 */
	private Long materialDocItemId;
	    		
	/**
	 * map流水code
	 */
	private String mapCode;
	    		
	/**
	 * map子流水号
	 */
	private String mapSubCode;
	    		
	/**
	 * map流水状态
	 */
	private Integer mapStatus;
	    		
	/**
	 * 删除标识
	 */
	private Integer deleted;
	    		
	/**
	 * 租户id
	 */
	private String tenantId;
	    		
	/**
	 * 创建人id
	 */
	private Long creatorId;
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	    		
	/**
	 * 修改人id
	 */
	private Long updatorId;
	    
    @Override
    public boolean sameIdentityAs(MdocSubMap other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
