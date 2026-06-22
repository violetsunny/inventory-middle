package com.inventory.middle.domain.factory;


import com.inventory.middle.domain.model.entity.LogicalPlant;
import org.springframework.stereotype.Component;

/**
 * 逻辑仓库表Factory
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Component
public class LogicalPlantFactory {

	public LogicalPlant createLogicalPlant() {
		return new LogicalPlant();
	}

}