package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryAlertId;
import com.inventory.middle.domain.model.entity.InventoryAlert;
import com.inventory.middle.domain.repository.InventoryAlertRepository;
import com.inventory.middle.application.service.InventoryAlertQueryService;
import com.inventory.middle.application.convertor.InventoryAlertDtoConvertor;
import com.inventory.middle.client.dto.InventoryAlertDto;
import com.inventory.middle.client.dto.query.InventoryAlertPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存报警日志QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:29
 */
@Service
@Slf4j
public class InventoryAlertQueryServiceImpl implements InventoryAlertQueryService {

	@Resource
	private InventoryAlertRepository inventoryalertRepository;
	@Resource
	private InventoryAlertDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryAlertDto> queryPage(InventoryAlertPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryAlert> page = inventoryalertRepository.queryPage(pageQuery, params);
		List<InventoryAlertDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryAlert).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryAlertDto findById(Long id) {
		return dtoConvertor.fromInventoryAlert(inventoryalertRepository.findById(new InventoryAlertId(id)));
	}

}

