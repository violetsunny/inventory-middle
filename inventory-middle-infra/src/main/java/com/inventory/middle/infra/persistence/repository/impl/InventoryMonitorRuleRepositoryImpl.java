package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryMonitorRule;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleId;
import com.inventory.middle.domain.repository.InventoryMonitorRuleRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryMonitorRuleConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryMonitorRuleDo;
import com.inventory.middle.infra.persistence.mapper.InventoryMonitorRuleMapper;
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
 * 库存预警规则Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Repository
public class InventoryMonitorRuleRepositoryImpl extends ServiceImpl<InventoryMonitorRuleMapper, InventoryMonitorRuleDo> implements InventoryMonitorRuleRepository, IService<InventoryMonitorRuleDo> {

	@Resource
	private InventoryMonitorRuleConvertor convertor;

	@Override
	public PageResponse<InventoryMonitorRule> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryMonitorRuleDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryMonitorRuleDo>(pageQuery).getPage(params),params);
		List<InventoryMonitorRuleDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryMonitorRule> dtoList = records.stream().map(convertor::toInventoryMonitorRule).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryMonitorRule findById(InventoryMonitorRuleId id) {
		InventoryMonitorRuleDo inventorymonitorruleDo = baseMapper.findById(id.get());
		return convertor.toInventoryMonitorRule(inventorymonitorruleDo);
	}

	@Override
	public boolean store(InventoryMonitorRule inventorymonitorrule) {
		InventoryMonitorRuleDo inventorymonitorruleDo = convertor.fromInventoryMonitorRule(inventorymonitorrule);
		return this.saveOrUpdate(inventorymonitorruleDo);
	}

	@Override
	public boolean update(InventoryMonitorRule inventorymonitorrule) {
		InventoryMonitorRuleDo inventorymonitorruleDo = convertor.fromInventoryMonitorRule(inventorymonitorrule);
		return this.saveOrUpdate(inventorymonitorruleDo);
	}

	@Override
	public boolean delete(List<InventoryMonitorRuleId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
