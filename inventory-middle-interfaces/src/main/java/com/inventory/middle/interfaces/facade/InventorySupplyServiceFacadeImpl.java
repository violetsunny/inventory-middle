package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventorySupplyQueryService;
import com.inventory.middle.application.service.InventorySupplyApplicationService;
import com.inventory.middle.client.dto.InventorySupplyDto;
import com.inventory.middle.client.dto.command.InventorySupplyCommand;
import com.inventory.middle.client.dto.query.InventorySupplyPageQuery;
import com.inventory.middle.client.facade.InventorySupplyServiceFacade;
import com.inventory.middle.application.convertor.InventorySupplyDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存-供给FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class InventorySupplyServiceFacadeImpl implements InventorySupplyServiceFacade {

	@Resource
	private InventorySupplyApplicationService inventorysupplyApplicationService;
	@Resource
	private InventorySupplyQueryService inventorysupplyQueryService;
	@Resource
	private InventorySupplyDtoConvertor  inventorysupplyDtoConvertor;


	/**
	 * 库存-供给分页查询
	 */
	@Override
	public PageResponse<InventorySupplyDto> page(InventorySupplyPageQuery inventorysupplyPageQuery) {
		return inventorysupplyQueryService.queryPage(inventorysupplyPageQuery);
	}

	/**
	 * 库存-供给list查询
	 */
	@Override
	public MultiResponse<InventorySupplyDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存-供给信息
	 */
	@Override
	public SingleResponse<InventorySupplyDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorysupplyQueryService.findById(id));
	}

	/**
	 * 保存库存-供给
	 */
	@Override
	public SingleResponse<Boolean> save(InventorySupplyCommand inventorysupplyCommand) {
		return SingleResponse.buildSuccess(inventorysupplyApplicationService.add(inventorysupplyCommand));

	}

	/**
	 * 修改库存-供给
	 */
	@Override
	public SingleResponse<Boolean> update( InventorySupplyCommand inventorysupplyCommand) {
		return SingleResponse.buildSuccess(inventorysupplyApplicationService.update(inventorysupplyCommand));
	}

	/**
	 * 删除库存-供给
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorysupplyApplicationService.deleteBatch(ids));
	}

}

