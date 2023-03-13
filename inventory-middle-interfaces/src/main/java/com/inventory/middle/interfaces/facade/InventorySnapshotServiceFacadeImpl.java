package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventorySnapshotQueryService;
import com.inventory.middle.application.service.InventorySnapshotApplicationService;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.command.InventorySnapshotCommand;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;
import com.inventory.middle.client.facade.InventorySnapshotServiceFacade;
import com.inventory.middle.application.convertor.InventorySnapshotDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存快照-实时FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class InventorySnapshotServiceFacadeImpl implements InventorySnapshotServiceFacade {

	@Resource
	private InventorySnapshotApplicationService inventorysnapshotApplicationService;
	@Resource
	private InventorySnapshotQueryService inventorysnapshotQueryService;
	@Resource
	private InventorySnapshotDtoConvertor  inventorysnapshotDtoConvertor;


	/**
	 * 库存快照-实时分页查询
	 */
	@Override
	public PageResponse<InventorySnapshotDto> page(InventorySnapshotPageQuery inventorysnapshotPageQuery) {
		return inventorysnapshotQueryService.queryPage(inventorysnapshotPageQuery);
	}

	/**
	 * 库存快照-实时list查询
	 */
	@Override
	public MultiResponse<InventorySnapshotDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存快照-实时信息
	 */
	@Override
	public SingleResponse<InventorySnapshotDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventorysnapshotQueryService.findById(id));
	}

	/**
	 * 保存库存快照-实时
	 */
	@Override
	public SingleResponse<Boolean> save(InventorySnapshotCommand inventorysnapshotCommand) {
		return SingleResponse.buildSuccess(inventorysnapshotApplicationService.add(inventorysnapshotCommand));

	}

	/**
	 * 修改库存快照-实时
	 */
	@Override
	public SingleResponse<Boolean> update( InventorySnapshotCommand inventorysnapshotCommand) {
		return SingleResponse.buildSuccess(inventorysnapshotApplicationService.update(inventorysnapshotCommand));
	}

	/**
	 * 删除库存快照-实时
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventorysnapshotApplicationService.deleteBatch(ids));
	}

}

