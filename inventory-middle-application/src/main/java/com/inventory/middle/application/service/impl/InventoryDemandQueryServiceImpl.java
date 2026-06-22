package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryDemandId;
import com.inventory.middle.domain.model.entity.InventoryDemand;
import com.inventory.middle.domain.repository.InventoryDemandRepository;
import com.inventory.middle.application.service.InventoryDemandQueryService;
import com.inventory.middle.application.convertor.InventoryDemandDtoConvertor;
import com.inventory.middle.client.dto.InventoryDemandDto;
import com.inventory.middle.client.dto.query.InventoryDemandPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存-需求QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventoryDemandQueryServiceImpl implements InventoryDemandQueryService {

	@Resource
	private InventoryDemandRepository inventorydemandRepository;
	@Resource
	private InventoryDemandDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryDemandDto> queryPage(InventoryDemandPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryDemand> page = inventorydemandRepository.queryPage(pageQuery, params);
		List<InventoryDemandDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryDemand).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryDemandDto findById(Long id) {
		return dtoConvertor.fromInventoryDemand(inventorydemandRepository.findById(new InventoryDemandId(id)));
	}

	@Override
	public java.util.Map<java.time.LocalDate, java.math.BigDecimal> queryDemandByDay(
			com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayQueryBO query) {
		java.util.List<com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayRespBO> list =
				inventorydemandRepository.queryDemandByDay(query);
		java.util.Map<java.time.LocalDate, java.math.BigDecimal> result = new java.util.LinkedHashMap<>();
		if (list != null) {
			for (com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayRespBO item : list) {
				if (item.getDeadlineDate() != null) {
					java.time.LocalDate date = item.getDeadlineDate().toInstant()
							.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
					result.put(date, item.getUnrestricted() != null ? item.getUnrestricted() : java.math.BigDecimal.ZERO);
				}
			}
		}
		return result;
	}

	@Override
	public java.math.BigDecimal queryOverdueDemandTotal(
			com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayQueryBO query) {
		java.util.List<com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayRespBO> list =
				inventorydemandRepository.queryDemandByDay(query);
		java.math.BigDecimal total = java.math.BigDecimal.ZERO;
		if (list != null) {
			for (com.inventory.middle.domain.model.bo.inventory.InventoryDemandByDayRespBO item : list) {
				if (item.getUnrestricted() != null) {
					total = total.add(item.getUnrestricted());
				}
			}
		}
		return total;
	}

}

