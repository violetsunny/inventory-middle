package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryMap;
import com.inventory.middle.domain.model.types.InventoryMapId;
import com.inventory.middle.domain.repository.InventoryMapRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryMapConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryMapDo;
import com.inventory.middle.infra.persistence.mapper.InventoryMapMapper;
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
 * 移动平均价Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Repository
public class InventoryMapRepositoryImpl extends ServiceImpl<InventoryMapMapper, InventoryMapDo> implements InventoryMapRepository, IService<InventoryMapDo> {

	@Resource
	private InventoryMapConvertor convertor;

	@Override
	public PageResponse<InventoryMap> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryMapDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryMapDo>(pageQuery).getPage(params),params);
		List<InventoryMapDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryMap> dtoList = records.stream().map(convertor::toInventoryMap).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryMap findById(InventoryMapId id) {
		InventoryMapDo inventorymapDo = baseMapper.findById(id.get());
		return convertor.toInventoryMap(inventorymapDo);
	}

	@Override
	public boolean store(InventoryMap inventorymap) {
		InventoryMapDo inventorymapDo = convertor.fromInventoryMap(inventorymap);
		return this.saveOrUpdate(inventorymapDo);
	}

	@Override
	public boolean update(InventoryMap inventorymap) {
		InventoryMapDo inventorymapDo = convertor.fromInventoryMap(inventorymap);
		return this.saveOrUpdate(inventorymapDo);
	}

	@Override
	public boolean delete(List<InventoryMapId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
