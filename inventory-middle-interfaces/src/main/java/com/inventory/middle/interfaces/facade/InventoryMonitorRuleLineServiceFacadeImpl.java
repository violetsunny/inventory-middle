package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryMonitorRuleLineQueryService;
import com.inventory.middle.application.service.InventoryMonitorRuleLineApplicationService;
import com.inventory.middle.client.dto.InventoryMonitorRuleLineDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import com.inventory.middle.client.dto.query.InventoryMonitorRuleLinePageQuery;
import com.inventory.middle.client.facade.InventoryMonitorRuleLineServiceFacade;
import com.inventory.middle.application.convertor.InventoryMonitorRuleLineDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存预警规则明细FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryMonitorRuleLineServiceFacadeImpl implements InventoryMonitorRuleLineServiceFacade {

	@Resource
	private InventoryMonitorRuleLineApplicationService inventorymonitorrulelineApplicationService;
	@Resource
	private InventoryMonitorRuleLineQueryService inventorymonitorrulelineQueryService;
	@Resource
	private InventoryMonitorRuleLineDtoConvertor  inventorymonitorrulelineDtoConvertor;


	/**
	 * 库存预警规则明细分页查询
	 */
	@Override
	public PageResponse<InventoryMonitorRuleLineDto> page(InventoryMonitorRuleLinePageQuery inventorymonitorrulelinePageQuery) {
		return inventorymonitorrulelineQueryService.queryPage(inventorymonitorrulelinePageQuery);
	}

	/**
	 * 库存预警规则明细list查询
	 */
	@Override
	public MultiResponse<InventoryMonitorRuleLineDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存预警规则明细信息
	 */
	@Override
	public SingleResponse<InventoryMonitorRuleLineDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorymonitorrulelineQueryService.findById(id));
	}

	/**
	 * 保存库存预警规则明细
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
		return SingleResponse.buildSuccess(inventorymonitorrulelineApplicationService.add(inventorymonitorrulelineCommand));

	}

	/**
	 * 修改库存预警规则明细
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryMonitorRuleLineCommand inventorymonitorrulelineCommand) {
		return SingleResponse.buildSuccess(inventorymonitorrulelineApplicationService.update(inventorymonitorrulelineCommand));
	}

	/**
	 * 删除库存预警规则明细
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorymonitorrulelineApplicationService.deleteBatch(ids));
	}

}

