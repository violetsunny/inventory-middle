package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryMapHisQueryService;
import com.inventory.middle.application.service.InventoryMapHisApplicationService;
import com.inventory.middle.client.dto.InventoryMapHisDto;
import com.inventory.middle.client.dto.command.InventoryMapHisCommand;
import com.inventory.middle.client.dto.query.InventoryMapHisPageQuery;
import com.inventory.middle.client.facade.InventoryMapHisServiceFacade;
import com.inventory.middle.application.convertor.InventoryMapHisDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 移动平均价历史记录FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryMapHisServiceFacadeImpl implements InventoryMapHisServiceFacade {

	@Resource
	private InventoryMapHisApplicationService inventorymaphisApplicationService;
	@Resource
	private InventoryMapHisQueryService inventorymaphisQueryService;
	@Resource
	private InventoryMapHisDtoConvertor  inventorymaphisDtoConvertor;


	/**
	 * 移动平均价历史记录分页查询
	 */
	@Override
	public PageResponse<InventoryMapHisDto> page(InventoryMapHisPageQuery inventorymaphisPageQuery) {
		return inventorymaphisQueryService.queryPage(inventorymaphisPageQuery);
	}

	/**
	 * 移动平均价历史记录list查询
	 */
	@Override
	public MultiResponse<InventoryMapHisDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 移动平均价历史记录信息
	 */
	@Override
	public SingleResponse<InventoryMapHisDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorymaphisQueryService.findById(id));
	}

	/**
	 * 保存移动平均价历史记录
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryMapHisCommand inventorymaphisCommand) {
		return SingleResponse.buildSuccess(inventorymaphisApplicationService.add(inventorymaphisCommand));

	}

	/**
	 * 修改移动平均价历史记录
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryMapHisCommand inventorymaphisCommand) {
		return SingleResponse.buildSuccess(inventorymaphisApplicationService.update(inventorymaphisCommand));
	}

	/**
	 * 删除移动平均价历史记录
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorymaphisApplicationService.deleteBatch(ids));
	}

}

