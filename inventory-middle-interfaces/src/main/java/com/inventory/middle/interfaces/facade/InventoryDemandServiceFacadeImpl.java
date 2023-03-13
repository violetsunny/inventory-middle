package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryDemandQueryService;
import com.inventory.middle.application.service.InventoryDemandApplicationService;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.command.InventoryDemandCommand;
import com.inventory.middle.client.dto.query.InventoryDemandPageQuery;
import com.inventory.middle.client.facade.InventoryDemandServiceFacade;
import com.inventory.middle.application.convertor.InventoryDemandDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存-需求FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryDemandServiceFacadeImpl implements InventoryDemandServiceFacade {

	@Resource
	private InventoryDemandApplicationService inventorydemandApplicationService;
	@Resource
	private InventoryDemandQueryService inventorydemandQueryService;
	@Resource
	private InventoryDemandDtoConvertor  inventorydemandDtoConvertor;


	/**
	 * 库存-需求分页查询
	 */
	@Override
	public PageResponse<InventoryDemandDto> page(InventoryDemandPageQuery inventorydemandPageQuery) {
		return inventorydemandQueryService.queryPage(inventorydemandPageQuery);
	}

	/**
	 * 库存-需求list查询
	 */
	@Override
	public MultiResponse<InventoryDemandDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存-需求信息
	 */
	@Override
	public SingleResponse<InventoryDemandDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorydemandQueryService.findById(id));
	}

	/**
	 * 保存库存-需求
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryDemandCommand inventorydemandCommand) {
		return SingleResponse.buildSuccess(inventorydemandApplicationService.add(inventorydemandCommand));

	}

	/**
	 * 修改库存-需求
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryDemandCommand inventorydemandCommand) {
		return SingleResponse.buildSuccess(inventorydemandApplicationService.update(inventorydemandCommand));
	}

	/**
	 * 删除库存-需求
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorydemandApplicationService.deleteBatch(ids));
	}

}

