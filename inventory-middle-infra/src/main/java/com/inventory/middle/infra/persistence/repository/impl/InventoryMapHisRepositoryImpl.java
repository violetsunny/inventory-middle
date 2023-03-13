package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryMapHis;
import com.inventory.middle.domain.model.types.InventoryMapHisId;
import com.inventory.middle.domain.repository.InventoryMapHisRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryMapHisConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryMapHisDo;
import com.inventory.middle.infra.persistence.mapper.InventoryMapHisMapper;
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
 * 移动平均价历史记录Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Repository
public class InventoryMapHisRepositoryImpl extends ServiceImpl<InventoryMapHisMapper, InventoryMapHisDo> implements InventoryMapHisRepository, IService<InventoryMapHisDo> {

	@Resource
	private InventoryMapHisConvertor convertor;

	@Override
	public PageResponse<InventoryMapHis> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryMapHisDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryMapHisDo>(pageQuery).getPage(params),params);
		List<InventoryMapHisDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryMapHis> dtoList = records.stream().map(convertor::toInventoryMapHis).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryMapHis findById(InventoryMapHisId id) {
		InventoryMapHisDo inventorymaphisDo = baseMapper.findById(id.get());
		return convertor.toInventoryMapHis(inventorymaphisDo);
	}

	@Override
	public boolean store(InventoryMapHis inventorymaphis) {
		InventoryMapHisDo inventorymaphisDo = convertor.fromInventoryMapHis(inventorymaphis);
		return this.saveOrUpdate(inventorymaphisDo);
	}

	@Override
	public boolean update(InventoryMapHis inventorymaphis) {
		InventoryMapHisDo inventorymaphisDo = convertor.fromInventoryMapHis(inventorymaphis);
		return this.saveOrUpdate(inventorymaphisDo);
	}

	@Override
	public boolean delete(List<InventoryMapHisId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
