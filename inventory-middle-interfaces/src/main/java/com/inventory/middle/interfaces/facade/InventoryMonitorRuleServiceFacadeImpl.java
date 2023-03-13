package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryMonitorRuleQueryService;
import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.client.dto.InventoryMonitorRuleDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import com.inventory.middle.client.dto.query.InventoryMonitorRulePageQuery;
import com.inventory.middle.client.facade.InventoryMonitorRuleServiceFacade;
import com.inventory.middle.application.convertor.InventoryMonitorRuleDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存预警规则FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryMonitorRuleServiceFacadeImpl implements InventoryMonitorRuleServiceFacade {

	@Resource
	private InventoryMonitorRuleApplicationService inventorymonitorruleApplicationService;
	@Resource
	private InventoryMonitorRuleQueryService inventorymonitorruleQueryService;
	@Resource
	private InventoryMonitorRuleDtoConvertor  inventorymonitorruleDtoConvertor;


	/**
	 * 库存预警规则分页查询
	 */
	@Override
	public PageResponse<InventoryMonitorRuleDto> page(InventoryMonitorRulePageQuery inventorymonitorrulePageQuery) {
		return inventorymonitorruleQueryService.queryPage(inventorymonitorrulePageQuery);
	}

	/**
	 * 库存预警规则list查询
	 */
	@Override
	public MultiResponse<InventoryMonitorRuleDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存预警规则信息
	 */
	@Override
	public SingleResponse<InventoryMonitorRuleDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorymonitorruleQueryService.findById(id));
	}

	/**
	 * 保存库存预警规则
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryMonitorRuleCommand inventorymonitorruleCommand) {
		return SingleResponse.buildSuccess(inventorymonitorruleApplicationService.add(inventorymonitorruleCommand));

	}

	/**
	 * 修改库存预警规则
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryMonitorRuleCommand inventorymonitorruleCommand) {
		return SingleResponse.buildSuccess(inventorymonitorruleApplicationService.update(inventorymonitorruleCommand));
	}

	/**
	 * 删除库存预警规则
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorymonitorruleApplicationService.deleteBatch(ids));
	}

}

