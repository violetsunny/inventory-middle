package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryAlertQueryService;
import com.inventory.middle.application.service.InventoryAlertApplicationService;
import com.inventory.middle.client.dto.InventoryAlertDto;
import com.inventory.middle.client.dto.command.InventoryAlertCommand;
import com.inventory.middle.client.dto.query.InventoryAlertPageQuery;
import com.inventory.middle.client.facade.InventoryAlertServiceFacade;
import com.inventory.middle.application.convertor.InventoryAlertDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存报警日志FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryAlertServiceFacadeImpl implements InventoryAlertServiceFacade {

	@Resource
	private InventoryAlertApplicationService inventoryalertApplicationService;
	@Resource
	private InventoryAlertQueryService inventoryalertQueryService;
	@Resource
	private InventoryAlertDtoConvertor  inventoryalertDtoConvertor;


	/**
	 * 库存报警日志分页查询
	 */
	@Override
	public PageResponse<InventoryAlertDto> page(InventoryAlertPageQuery inventoryalertPageQuery) {
		return inventoryalertQueryService.queryPage(inventoryalertPageQuery);
	}

	/**
	 * 库存报警日志list查询
	 */
	@Override
	public MultiResponse<InventoryAlertDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存报警日志信息
	 */
	@Override
	public SingleResponse<InventoryAlertDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventoryalertQueryService.findById(id));
	}

	/**
	 * 保存库存报警日志
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryAlertCommand inventoryalertCommand) {
		return SingleResponse.buildSuccess(inventoryalertApplicationService.add(inventoryalertCommand));

	}

	/**
	 * 修改库存报警日志
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryAlertCommand inventoryalertCommand) {
		return SingleResponse.buildSuccess(inventoryalertApplicationService.update(inventoryalertCommand));
	}

	/**
	 * 删除库存报警日志
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventoryalertApplicationService.deleteBatch(ids));
	}

}

