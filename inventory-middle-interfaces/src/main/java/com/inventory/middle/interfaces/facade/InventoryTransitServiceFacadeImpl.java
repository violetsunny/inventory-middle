package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryTransitQueryService;
import com.inventory.middle.application.service.InventoryTransitApplicationService;
import com.inventory.middle.client.dto.InventoryTransitDto;
import com.inventory.middle.client.dto.command.InventoryTransitCommand;
import com.inventory.middle.client.dto.query.InventoryTransitPageQuery;
import com.inventory.middle.client.facade.InventoryTransitServiceFacade;
import com.inventory.middle.application.convertor.InventoryTransitDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存-在途FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryTransitServiceFacadeImpl implements InventoryTransitServiceFacade {

	@Resource
	private InventoryTransitApplicationService inventorytransitApplicationService;
	@Resource
	private InventoryTransitQueryService inventorytransitQueryService;
	@Resource
	private InventoryTransitDtoConvertor  inventorytransitDtoConvertor;


	/**
	 * 库存-在途分页查询
	 */
	@Override
	public PageResponse<InventoryTransitDto> page(InventoryTransitPageQuery inventorytransitPageQuery) {
		return inventorytransitQueryService.queryPage(inventorytransitPageQuery);
	}

	/**
	 * 库存-在途list查询
	 */
	@Override
	public MultiResponse<InventoryTransitDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存-在途信息
	 */
	@Override
	public SingleResponse<InventoryTransitDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorytransitQueryService.findById(id));
	}

	/**
	 * 保存库存-在途
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryTransitCommand inventorytransitCommand) {
		return SingleResponse.buildSuccess(inventorytransitApplicationService.add(inventorytransitCommand));

	}

	/**
	 * 修改库存-在途
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryTransitCommand inventorytransitCommand) {
		return SingleResponse.buildSuccess(inventorytransitApplicationService.update(inventorytransitCommand));
	}

	/**
	 * 删除库存-在途
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorytransitApplicationService.deleteBatch(ids));
	}

}

