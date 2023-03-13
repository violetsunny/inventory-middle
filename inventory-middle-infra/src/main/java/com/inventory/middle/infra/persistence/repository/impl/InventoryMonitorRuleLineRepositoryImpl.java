package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventoryMonitorRuleLine;
import com.inventory.middle.domain.model.types.InventoryMonitorRuleLineId;
import com.inventory.middle.domain.repository.InventoryMonitorRuleLineRepository;
import com.inventory.middle.infra.persistence.convertor.InventoryMonitorRuleLineConvertor;
import com.inventory.middle.infra.persistence.entity.InventoryMonitorRuleLineDo;
import com.inventory.middle.infra.persistence.mapper.InventoryMonitorRuleLineMapper;
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
 * 库存预警规则明细Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:09
 */
@Repository
public class InventoryMonitorRuleLineRepositoryImpl extends ServiceImpl<InventoryMonitorRuleLineMapper, InventoryMonitorRuleLineDo> implements InventoryMonitorRuleLineRepository, IService<InventoryMonitorRuleLineDo> {

	@Resource
	private InventoryMonitorRuleLineConvertor convertor;

	@Override
	public PageResponse<InventoryMonitorRuleLine> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventoryMonitorRuleLineDo> page = baseMapper.queryPage(new PlusPageQuery<InventoryMonitorRuleLineDo>(pageQuery).getPage(params),params);
		List<InventoryMonitorRuleLineDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventoryMonitorRuleLine> dtoList = records.stream().map(convertor::toInventoryMonitorRuleLine).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventoryMonitorRuleLine findById(InventoryMonitorRuleLineId id) {
		InventoryMonitorRuleLineDo inventorymonitorrulelineDo = baseMapper.findById(id.get());
		return convertor.toInventoryMonitorRuleLine(inventorymonitorrulelineDo);
	}

	@Override
	public boolean store(InventoryMonitorRuleLine inventorymonitorruleline) {
		InventoryMonitorRuleLineDo inventorymonitorrulelineDo = convertor.fromInventoryMonitorRuleLine(inventorymonitorruleline);
		return this.saveOrUpdate(inventorymonitorrulelineDo);
	}

	@Override
	public boolean update(InventoryMonitorRuleLine inventorymonitorruleline) {
		InventoryMonitorRuleLineDo inventorymonitorrulelineDo = convertor.fromInventoryMonitorRuleLine(inventorymonitorruleline);
		return this.saveOrUpdate(inventorymonitorrulelineDo);
	}

	@Override
	public boolean delete(List<InventoryMonitorRuleLineId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
