package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryDemand;
import com.inventory.middle.domain.model.types.InventoryDemandId;
import com.inventory.middle.domain.repository.InventoryDemandRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryDemandConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryDemandDo;
import com.inventory.middle.infra.persistence.mapper.InventoryDemandMapper;
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
 * 库存-需求Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Repository
public class InventoryDemandRepositoryImpl extends ServiceImpl<InventoryDemandMapper, InventoryDemandDo> implements InventoryDemandRepository, IService<InventoryDemandDo> {

	@Resource
	private InventoryDemandConvertor convertor;

	@Override
	public PageResponse<InventoryDemand> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryDemandDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryDemandDo>(pageQuery).getPage(params),params);
		List<InventoryDemandDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryDemand> dtoList = records.stream().map(convertor::toInventoryDemand).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryDemand findById(InventoryDemandId id) {
		InventoryDemandDo inventorydemandDo = baseMapper.findById(id.get());
		return convertor.toInventoryDemand(inventorydemandDo);
	}

	@Override
	public boolean store(InventoryDemand inventorydemand) {
		InventoryDemandDo inventorydemandDo = convertor.fromInventoryDemand(inventorydemand);
		return this.saveOrUpdate(inventorydemandDo);
	}

	@Override
	public boolean update(InventoryDemand inventorydemand) {
		InventoryDemandDo inventorydemandDo = convertor.fromInventoryDemand(inventorydemand);
		return this.saveOrUpdate(inventorydemandDo);
	}

	@Override
	public boolean delete(List<InventoryDemandId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
