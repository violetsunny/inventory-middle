package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryPlanQueryService;
import com.inventory.middle.application.service.InventoryPlanApplicationService;
import com.inventory.middle.client.dto.InventoryPlanDto;
import com.inventory.middle.client.dto.command.InventoryPlanCommand;
import com.inventory.middle.client.dto.query.InventoryPlanPageQuery;
import com.inventory.middle.client.facade.InventoryPlanServiceFacade;
import com.inventory.middle.application.convertor.InventoryPlanDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存-计划FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryPlanServiceFacadeImpl implements InventoryPlanServiceFacade {

	@Resource
	private InventoryPlanApplicationService inventoryplanApplicationService;
	@Resource
	private InventoryPlanQueryService inventoryplanQueryService;
	@Resource
	private InventoryPlanDtoConvertor  inventoryplanDtoConvertor;


	/**
	 * 库存-计划分页查询
	 */
	@Override
	public PageResponse<InventoryPlanDto> page(InventoryPlanPageQuery inventoryplanPageQuery) {
		return inventoryplanQueryService.queryPage(inventoryplanPageQuery);
	}

	/**
	 * 库存-计划list查询
	 */
	@Override
	public MultiResponse<InventoryPlanDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存-计划信息
	 */
	@Override
	public SingleResponse<InventoryPlanDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventoryplanQueryService.findById(id));
	}

	/**
	 * 保存库存-计划
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryPlanCommand inventoryplanCommand) {
		return SingleResponse.buildSuccess(inventoryplanApplicationService.add(inventoryplanCommand));

	}

	/**
	 * 修改库存-计划
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryPlanCommand inventoryplanCommand) {
		return SingleResponse.buildSuccess(inventoryplanApplicationService.update(inventoryplanCommand));
	}

	/**
	 * 删除库存-计划
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventoryplanApplicationService.deleteBatch(ids));
	}

}

