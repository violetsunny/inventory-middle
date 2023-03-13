package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryPlan;
import com.inventory.middle.domain.model.types.InventoryPlanId;
import com.inventory.middle.domain.repository.InventoryPlanRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryPlanConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryPlanDo;
import com.inventory.middle.infra.persistence.mapper.InventoryPlanMapper;
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
 * 库存-计划Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Repository
public class InventoryPlanRepositoryImpl extends ServiceImpl<InventoryPlanMapper, InventoryPlanDo> implements InventoryPlanRepository, IService<InventoryPlanDo> {

	@Resource
	private InventoryPlanConvertor convertor;

	@Override
	public PageResponse<InventoryPlan> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryPlanDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryPlanDo>(pageQuery).getPage(params),params);
		List<InventoryPlanDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryPlan> dtoList = records.stream().map(convertor::toInventoryPlan).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryPlan findById(InventoryPlanId id) {
		InventoryPlanDo inventoryplanDo = baseMapper.findById(id.get());
		return convertor.toInventoryPlan(inventoryplanDo);
	}

	@Override
	public boolean store(InventoryPlan inventoryplan) {
		InventoryPlanDo inventoryplanDo = convertor.fromInventoryPlan(inventoryplan);
		return this.saveOrUpdate(inventoryplanDo);
	}

	@Override
	public boolean update(InventoryPlan inventoryplan) {
		InventoryPlanDo inventoryplanDo = convertor.fromInventoryPlan(inventoryplan);
		return this.saveOrUpdate(inventoryplanDo);
	}

	@Override
	public boolean delete(List<InventoryPlanId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
