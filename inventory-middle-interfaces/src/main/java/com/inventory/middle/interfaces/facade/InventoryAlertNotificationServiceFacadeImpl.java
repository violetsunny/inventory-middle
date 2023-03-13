package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.InventoryAlertNotificationQueryService;
import com.inventory.middle.application.service.InventoryAlertNotificationApplicationService;
import com.inventory.middle.client.dto.InventoryAlertNotificationDto;
import com.inventory.middle.client.dto.command.InventoryAlertNotificationCommand;
import com.inventory.middle.client.dto.query.InventoryAlertNotificationPageQuery;
import com.inventory.middle.client.facade.InventoryAlertNotificationServiceFacade;
import com.inventory.middle.application.convertor.InventoryAlertNotificationDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 库存报警通知记录FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Component
@Slf4j
@CatchAndLog
public class InventoryAlertNotificationServiceFacadeImpl implements InventoryAlertNotificationServiceFacade {

	@Resource
	private InventoryAlertNotificationApplicationService inventoryalertnotificationApplicationService;
	@Resource
	private InventoryAlertNotificationQueryService inventoryalertnotificationQueryService;
	@Resource
	private InventoryAlertNotificationDtoConvertor  inventoryalertnotificationDtoConvertor;


	/**
	 * 库存报警通知记录分页查询
	 */
	@Override
	public PageResponse<InventoryAlertNotificationDto> page(InventoryAlertNotificationPageQuery inventoryalertnotificationPageQuery) {
		return inventoryalertnotificationQueryService.queryPage(inventoryalertnotificationPageQuery);
	}

	/**
	 * 库存报警通知记录list查询
	 */
	@Override
	public MultiResponse<InventoryAlertNotificationDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 库存报警通知记录信息
	 */
	@Override
	public SingleResponse<InventoryAlertNotificationDto> findById(Long id) {
		return SingleResponse.buildSuccess(inventoryalertnotificationQueryService.findById(id));
	}

	/**
	 * 保存库存报警通知记录
	 */
	@Override
	public SingleResponse<Boolean> save(InventoryAlertNotificationCommand inventoryalertnotificationCommand) {
		return SingleResponse.buildSuccess(inventoryalertnotificationApplicationService.add(inventoryalertnotificationCommand));

	}

	/**
	 * 修改库存报警通知记录
	 */
	@Override
	public SingleResponse<Boolean> update( InventoryAlertNotificationCommand inventoryalertnotificationCommand) {
		return SingleResponse.buildSuccess(inventoryalertnotificationApplicationService.update(inventoryalertnotificationCommand));
	}

	/**
	 * 删除库存报警通知记录
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(inventoryalertnotificationApplicationService.deleteBatch(ids));
	}

}

