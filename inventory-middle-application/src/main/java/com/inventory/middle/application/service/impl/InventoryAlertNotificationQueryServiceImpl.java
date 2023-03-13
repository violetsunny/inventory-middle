package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventoryAlertNotificationId;
import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import com.inventory.middle.domain.repository.InventoryAlertNotificationRepository;
import com.inventory.middle.application.service.InventoryAlertNotificationQueryService;
import com.inventory.middle.application.convertor.InventoryAlertNotificationDtoConvertor;
import com.inventory.middle.client.dto.InventoryAlertNotificationDto;
import com.inventory.middle.client.dto.query.InventoryAlertNotificationPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存报警通知记录QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class InventoryAlertNotificationQueryServiceImpl implements InventoryAlertNotificationQueryService {

	@Resource
	private InventoryAlertNotificationRepository inventoryalertnotificationRepository;
	@Resource
	private InventoryAlertNotificationDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventoryAlertNotificationDto> queryPage(InventoryAlertNotificationPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventoryAlertNotification> page = inventoryalertnotificationRepository.queryPage(pageQuery, params);
		List<InventoryAlertNotificationDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventoryAlertNotification).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventoryAlertNotificationDto findById(Long id) {
		return dtoConvertor.fromInventoryAlertNotification(inventoryalertnotificationRepository.findById(new InventoryAlertNotificationId(id)));
	}

}

