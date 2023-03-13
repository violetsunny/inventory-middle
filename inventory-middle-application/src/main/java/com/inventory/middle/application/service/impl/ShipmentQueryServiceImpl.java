package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.ShipmentId;
import com.inventory.middle.domain.model.entity.Shipment;
import com.inventory.middle.domain.repository.ShipmentRepository;
import com.inventory.middle.application.service.ShipmentQueryService;
import com.inventory.middle.application.convertor.ShipmentDtoConvertor;
import com.inventory.middle.client.dto.ShipmentDto;
import com.inventory.middle.client.dto.query.ShipmentPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 交运单QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class ShipmentQueryServiceImpl implements ShipmentQueryService {

	@Resource
	private ShipmentRepository shipmentRepository;
	@Resource
	private ShipmentDtoConvertor dtoConvertor;


	@Override
	public PageResponse<ShipmentDto> queryPage(ShipmentPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<Shipment> page = shipmentRepository.queryPage(pageQuery, params);
		List<ShipmentDto> dtoList = page.getData().stream().map(dtoConvertor::fromShipment).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public ShipmentDto findById(Long id) {
		return dtoConvertor.fromShipment(shipmentRepository.findById(new ShipmentId(id)));
	}

}

