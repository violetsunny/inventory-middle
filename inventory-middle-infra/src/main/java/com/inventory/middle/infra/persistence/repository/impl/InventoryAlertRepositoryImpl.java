package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryAlert;
import com.inventory.middle.domain.model.types.InventoryAlertId;
import com.inventory.middle.domain.repository.InventoryAlertRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryAlertConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryAlertDo;
import com.inventory.middle.infra.persistence.mapper.InventoryAlertMapper;
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
 * 库存报警日志Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Repository
public class InventoryAlertRepositoryImpl extends ServiceImpl<InventoryAlertMapper, InventoryAlertDo> implements InventoryAlertRepository, IService<InventoryAlertDo> {

	@Resource
	private InventoryAlertConvertor convertor;

	@Override
	public PageResponse<InventoryAlert> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryAlertDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryAlertDo>(pageQuery).getPage(params),params);
		List<InventoryAlertDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryAlert> dtoList = records.stream().map(convertor::toInventoryAlert).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryAlert findById(InventoryAlertId id) {
		InventoryAlertDo inventoryalertDo = baseMapper.findById(id.get());
		return convertor.toInventoryAlert(inventoryalertDo);
	}

	@Override
	public boolean store(InventoryAlert inventoryalert) {
		InventoryAlertDo inventoryalertDo = convertor.fromInventoryAlert(inventoryalert);
		return this.saveOrUpdate(inventoryalertDo);
	}

	@Override
	public boolean update(InventoryAlert inventoryalert) {
		InventoryAlertDo inventoryalertDo = convertor.fromInventoryAlert(inventoryalert);
		return this.saveOrUpdate(inventoryalertDo);
	}

	@Override
	public boolean delete(List<InventoryAlertId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
