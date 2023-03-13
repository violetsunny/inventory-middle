package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventorySupply;
import com.inventory.middle.domain.model.types.InventorySupplyId;
import com.inventory.middle.domain.repository.InventorySupplyRepository;
import com.inventory.middle.infra.persistence.convertor.InventorySupplyConvertor;
import com.inventory.middle.infra.persistence.entity.InventorySupplyDo;
import com.inventory.middle.infra.persistence.mapper.InventorySupplyMapper;
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
 * 库存-供给Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Repository
public class InventorySupplyRepositoryImpl extends ServiceImpl<InventorySupplyMapper, InventorySupplyDo> implements InventorySupplyRepository, IService<InventorySupplyDo> {

	@Resource
	private InventorySupplyConvertor convertor;

	@Override
	public PageResponse<InventorySupply> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventorySupplyDo> page = baseMapper.queryPage(new PlusPageQuery<InventorySupplyDo>(pageQuery).getPage(params),params);
		List<InventorySupplyDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventorySupply> dtoList = records.stream().map(convertor::toInventorySupply).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventorySupply findById(InventorySupplyId id) {
		InventorySupplyDo inventorysupplyDo = baseMapper.findById(id.get());
		return convertor.toInventorySupply(inventorysupplyDo);
	}

	@Override
	public boolean store(InventorySupply inventorysupply) {
		InventorySupplyDo inventorysupplyDo = convertor.fromInventorySupply(inventorysupply);
		return this.saveOrUpdate(inventorysupplyDo);
	}

	@Override
	public boolean update(InventorySupply inventorysupply) {
		InventorySupplyDo inventorysupplyDo = convertor.fromInventorySupply(inventorysupply);
		return this.saveOrUpdate(inventorysupplyDo);
	}

	@Override
	public boolean delete(List<InventorySupplyId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
