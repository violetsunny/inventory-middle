package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryMapQueryService;
import com.inventory.middle.application.service.InventoryMapApplicationService;
import com.inventory.middle.client.dto.InventoryMapDto;
import com.inventory.middle.client.dto.command.InventoryMapCommand;
import com.inventory.middle.client.dto.query.InventoryMapPageQuery;
import com.inventory.middle.client.facade.InventoryMapServiceFacade;
import com.inventory.middle.application.convertor.InventoryMapDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 移动平均价FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:28
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryMapServiceFacadeImpl implements InventoryMapServiceFacade {

	@Resource
	private InventoryMapApplicationService inventorymapApplicationService;
	@Resource
	private InventoryMapQueryService inventorymapQueryService;
	@Resource
	private InventoryMapDtoConvertor  inventorymapDtoConvertor;


	/**
	 * 移动平均价分页查询
	 */
	@Override
	public PageResponse<InventoryMapDto> page(InventoryMapPageQuery inventorymapPageQuery) {
		return inventorymapQueryService.queryPage(inventorymapPageQuery);
	}

	/**
	 * 移动平均价list查询
	 */
	@Override
	public MultiResponse<InventoryMapDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 移动平均价信息
	 */
	@Override
	public SingleResponse<InventoryMapDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorymapQueryService.findById(id));
	}

	/**
	 * 保存移动平均价
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryMapCommand inventorymapCommand) {
		return SingleResponse.buildSuccess(inventorymapApplicationService.add(inventorymapCommand));

	}

	/**
	 * 修改移动平均价
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryMapCommand inventorymapCommand) {
		return SingleResponse.buildSuccess(inventorymapApplicationService.update(inventorymapCommand));
	}

	/**
	 * 删除移动平均价
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorymapApplicationService.deleteBatch(ids));
	}

}

