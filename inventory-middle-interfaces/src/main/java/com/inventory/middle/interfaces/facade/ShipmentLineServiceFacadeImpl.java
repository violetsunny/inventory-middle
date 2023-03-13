package com.inventory.middle.interfaces.facade;


import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.application.service.ShipmentLineQueryService;
import com.inventory.middle.application.service.ShipmentLineApplicationService;
import com.inventory.middle.client.dto.ShipmentLineDto;
import com.inventory.middle.client.dto.command.ShipmentLineCommand;
import com.inventory.middle.client.dto.query.ShipmentLinePageQuery;
import com.inventory.middle.client.facade.ShipmentLineServiceFacade;
import com.inventory.middle.application.convertor.ShipmentLineDtoConvertor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

/**
 * 交运单明细FacadeImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:28
 */
@Component
@Slf4j
@CatchAndLog
public class ShipmentLineServiceFacadeImpl implements ShipmentLineServiceFacade {

	@Resource
	private ShipmentLineApplicationService shipmentlineApplicationService;
	@Resource
	private ShipmentLineQueryService shipmentlineQueryService;
	@Resource
	private ShipmentLineDtoConvertor  shipmentlineDtoConvertor;


	/**
	 * 交运单明细分页查询
	 */
	@Override
	public PageResponse<ShipmentLineDto> page(ShipmentLinePageQuery shipmentlinePageQuery) {
		return shipmentlineQueryService.queryPage(shipmentlinePageQuery);
	}

	/**
	 * 交运单明细list查询
	 */
	@Override
	public MultiResponse<ShipmentLineDto> list() {
		//TODO list query
		return MultiResponse.buildSuccess(null);
	}

	/**
	 * 交运单明细信息
	 */
	@Override
	public SingleResponse<ShipmentLineDto> findById(Long id) {
		return SingleResponse.buildSuccess(shipmentlineQueryService.findById(id));
	}

	/**
	 * 保存交运单明细
	 */
	@Override
	public SingleResponse<Boolean> save(ShipmentLineCommand shipmentlineCommand) {
		return SingleResponse.buildSuccess(shipmentlineApplicationService.add(shipmentlineCommand));

	}

	/**
	 * 修改交运单明细
	 */
	@Override
	public SingleResponse<Boolean> update( ShipmentLineCommand shipmentlineCommand) {
		return SingleResponse.buildSuccess(shipmentlineApplicationService.update(shipmentlineCommand));
	}

	/**
	 * 删除交运单明细
	 */
	@Override
	public SingleResponse<Boolean> delete(List<Long> ids) {
		return SingleResponse.buildSuccess(shipmentlineApplicationService.deleteBatch(ids));
	}

}

