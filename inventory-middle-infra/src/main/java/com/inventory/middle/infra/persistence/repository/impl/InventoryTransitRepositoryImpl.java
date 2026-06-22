package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryTransit;
import com.inventory.middle.domain.model.types.InventoryTransitId;
import com.inventory.middle.domain.repository.InventoryTransitRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryTransitConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryTransitDo;
import com.inventory.middle.infra.persistence.mapper.InventoryTransitMapper;
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
 * 库存-在途Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Repository
public class InventoryTransitRepositoryImpl extends ServiceImpl<InventoryTransitMapper, InventoryTransitDo> implements InventoryTransitRepository, IService<InventoryTransitDo> {

	@Resource
	private InventoryTransitConvertor convertor;

	@Override
	public PageResponse<InventoryTransit> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryTransitDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryTransitDo>(pageQuery).getPage(params),params);
		List<InventoryTransitDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryTransit> dtoList = records.stream().map(convertor::toInventoryTransit).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryTransit findById(InventoryTransitId id) {
		InventoryTransitDo inventorytransitDo = baseMapper.findById(id.get());
		return convertor.toInventoryTransit(inventorytransitDo);
	}

	@Override
	public boolean store(InventoryTransit inventorytransit) {
		InventoryTransitDo inventorytransitDo = convertor.fromInventoryTransit(inventorytransit);
		return this.saveOrUpdate(inventorytransitDo);
	}

	@Override
	public boolean update(InventoryTransit inventorytransit) {
		InventoryTransitDo inventorytransitDo = convertor.fromInventoryTransit(inventorytransit);
		return this.saveOrUpdate(inventorytransitDo);
	}

	@Override
	public boolean delete(List<InventoryTransitId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
