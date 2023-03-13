package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.ShipmentQueryService;
import com.inventory.middle.application.service.ShipmentApplicationService;
import com.inventory.middle.client.dto.ShipmentDto;
import com.inventory.middle.client.dto.command.ShipmentCommand;
import com.inventory.middle.client.dto.query.ShipmentPageQuery;
import com.inventory.middle.client.facade.ShipmentServiceFacade;
import com.inventory.middle.application.convertor.ShipmentDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 交运单FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:28
 */
@Component
@Slf4j
@CatchAndLog
public class ShipmentServiceFacadeImpl implements ShipmentServiceFacade {

	@Resource
	private ShipmentApplicationService shipmentApplicationService;
	@Resource
	private ShipmentQueryService shipmentQueryService;
	@Resource
	private ShipmentDtoConvertor  shipmentDtoConvertor;


	/**
	 * 交运单分页查询
	 */
	@Override
	public PageResponse<ShipmentDto> page(ShipmentPageQuery shipmentPageQuery) {
		return shipmentQueryService.queryPage(shipmentPageQuery);
	}

	/**
	 * 交运单list查询
	 */
	@Override
	public MultiResponse<ShipmentDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 交运单信息
	 */
	@Override
	public SingleResponse<ShipmentDto> findById(Long id) {
		return SingleResponse.buildSuccess(shipmentQueryService.findById(id));
	}

	/**
	 * 保存交运单
	 */
	@Override
	public SingleResponse<Boolean> save(ShipmentCommand shipmentCommand) {
		return SingleResponse.buildSuccess(shipmentApplicationService.add(shipmentCommand));

	}

	/**
	 * 修改交运单
	 */
	@Override
	public SingleResponse<Boolean> update( ShipmentCommand shipmentCommand) {
		return SingleResponse.buildSuccess(shipmentApplicationService.update(shipmentCommand));
	}

	/**
	 * 删除交运单
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(shipmentApplicationService.deleteBatch(ids));
	}

}

