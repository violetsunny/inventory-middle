package com.inventory.middle.domain.model.entity;


import com.inventory.middle.domain.model.types.CodeGeneratorCfgId;
import top.kdla.framework.domain.shared.Entity;
import top.kdla.framework.domain.shared.StateEnum;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流水号配置表领域对象
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class CodeGeneratorCfg implements Entity<CodeGeneratorCfg> {

		private CodeGeneratorCfgId id;
		    		
	/**
	 * 流水号唯一code
	 */
	private String code;
	    		
	/**
	 * 流水号名称
	 */
	private String name;
	    		
	/**
	 * 当前编号池的纪元
	 */
	private Long epoch;
	    		
	/**
	 * 最大流水号
	 */
	private String maxValue;
	    		
	/**
	 * 流水号规则
	 */
	private String rule;
	    		
	/**
	 * 是否缓存 0：否  ， 1：是
	 */
	private Integer isCache;
	    		
	/**
	 * 缓存数目
	 */
	private Integer cacheNum;
	    		
	/**
	 * 环境变量值
	 */
	private String envValue;
	    		
	/**
	 * 状态：0有效 1无效
	 */
	private Integer isDeleted;
	    		
	/**
	 * 备注
	 */
	private String remark;
	    		
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	    		
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	    
    @Override
    public boolean sameIdentityAs(CodeGeneratorCfg other) {
		return other != null && this.id.sameValueAs(other.id);
    }

}
