package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryAlertNotification;
import com.inventory.middle.domain.model.types.InventoryAlertNotificationId;
import com.inventory.middle.domain.repository.InventoryAlertNotificationRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryAlertNotificationConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryAlertNotificationDo;
import com.inventory.middle.infra.persistence.mapper.InventoryAlertNotificationMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.infra.dal.mybatis.util.PlusPageQuery;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存报警通知记录Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Repository
public class InventoryAlertNotificationRepositoryImpl extends ServiceImpl<InventoryAlertNotificationMapper, InventoryAlertNotificationDo> implements InventoryAlertNotificationRepository, IService<InventoryAlertNotificationDo> {

	@Resource
	private InventoryAlertNotificationConvertor convertor;

	@Override
	public PageResponse<InventoryAlertNotification> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryAlertNotificationDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryAlertNotificationDo>(pageQuery).getPage(params),params);
		List<InventoryAlertNotificationDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryAlertNotification> dtoList = records.stream().map(convertor::toInventoryAlertNotification).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryAlertNotification findById(InventoryAlertNotificationId id) {
		InventoryAlertNotificationDo inventoryalertnotificationDo = baseMapper.findById(id.get());
		return convertor.toInventoryAlertNotification(inventoryalertnotificationDo);
	}

	@Override
	public boolean store(InventoryAlertNotification inventoryalertnotification) {
		InventoryAlertNotificationDo inventoryalertnotificationDo = convertor.fromInventoryAlertNotification(inventoryalertnotification);
		return this.saveOrUpdate(inventoryalertnotificationDo);
	}

	@Override
	public boolean update(InventoryAlertNotification inventoryalertnotification) {
		InventoryAlertNotificationDo inventoryalertnotificationDo = convertor.fromInventoryAlertNotification(inventoryalertnotification);
		return this.saveOrUpdate(inventoryalertnotificationDo);
	}

	@Override
	public boolean delete(List<InventoryAlertNotificationId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
