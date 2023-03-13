package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.ShipmentLineId;
import com.inventory.middle.domain.model.entity.ShipmentLine;
import com.inventory.middle.domain.repository.ShipmentLineRepository;
import com.inventory.middle.application.service.ShipmentLineQueryService;
import com.inventory.middle.application.convertor.ShipmentLineDtoConvertor;
import com.inventory.middle.client.dto.ShipmentLineDto;
import com.inventory.middle.client.dto.query.ShipmentLinePageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 交运单明细QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:33
 */
@Service
@Slf4j
public class ShipmentLineQueryServiceImpl implements ShipmentLineQueryService {

	@Resource
	private ShipmentLineRepository shipmentlineRepository;
	@Resource
	private ShipmentLineDtoConvertor dtoConvertor;


	@Override
	public PageResponse<ShipmentLineDto> queryPage(ShipmentLinePageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<ShipmentLine> page = shipmentlineRepository.queryPage(pageQuery, params);
		List<ShipmentLineDto> dtoList = page.getData().stream().map(dtoConvertor::fromShipmentLine).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public ShipmentLineDto findById(Long id) {
		return dtoConvertor.fromShipmentLine(shipmentlineRepository.findById(new ShipmentLineId(id)));
	}

}

