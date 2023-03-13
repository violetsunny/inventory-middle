package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.WarehouseQueryService;
import com.inventory.middle.application.service.WarehouseApplicationService;
import com.inventory.middle.client.dto.WarehouseDto;
import com.inventory.middle.client.dto.command.WarehouseCommand;
import com.inventory.middle.client.dto.query.WarehousePageQuery;
import com.inventory.middle.client.facade.WarehouseServiceFacade;
import com.inventory.middle.application.convertor.WarehouseDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 物理仓库表FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Component
@Slf4j
@CatchAndLog
public class WarehouseServiceFacadeImpl implements WarehouseServiceFacade {

	@Resource
	private WarehouseApplicationService warehouseApplicationService;
	@Resource
	private WarehouseQueryService warehouseQueryService;
	@Resource
	private WarehouseDtoConvertor  warehouseDtoConvertor;


	/**
	 * 物理仓库表分页查询
	 */
	@Override
	public PageResponse<WarehouseDto> page(WarehousePageQuery warehousePageQuery) {
		return warehouseQueryService.queryPage(warehousePageQuery);
	}

	/**
	 * 物理仓库表list查询
	 */
	@Override
	public MultiResponse<WarehouseDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 物理仓库表信息
	 */
	@Override
	public SingleResponse<WarehouseDto> findById(Long id) {
		return SingleResponse.buildSuccess(warehouseQueryService.findById(id));
	}

	/**
	 * 保存物理仓库表
	 */
	@Override
	public SingleResponse<Boolean> save(WarehouseCommand warehouseCommand) {
		return SingleResponse.buildSuccess(warehouseApplicationService.add(warehouseCommand));

	}

	/**
	 * 修改物理仓库表
	 */
	@Override
	public SingleResponse<Boolean> update( WarehouseCommand warehouseCommand) {
		return SingleResponse.buildSuccess(warehouseApplicationService.update(warehouseCommand));
	}

	/**
	 * 删除物理仓库表
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(warehouseApplicationService.deleteBatch(ids));
	}

}

