package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.infra.persistence.convertor.InventorySnapshotConvertor;
import com.inventory.middle.infra.persistence.entity.InventorySnapshotDo;
import com.inventory.middle.infra.persistence.mapper.InventorySnapshotMapper;
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
 * 库存快照-实时Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:23
 */
@Repository
public class InventorySnapshotRepositoryImpl extends ServiceImpl<InventorySnapshotMapper, InventorySnapshotDo> implements InventorySnapshotRepository, IService<InventorySnapshotDo> {

	@Resource
	private InventorySnapshotConvertor convertor;

	@Override
	public PageResponse<InventorySnapshot> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<InventorySnapshotDo> page = baseMapper.queryPage(new PlusPageQuery<InventorySnapshotDo>(pageQuery).getPage(params),params);
		List<InventorySnapshotDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<InventorySnapshot> dtoList = records.stream().map(convertor::toInventorySnapshot).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public InventorySnapshot findById(InventorySnapshotId id) {
		InventorySnapshotDo inventorysnapshotDo = baseMapper.findById(id.get());
		return convertor.toInventorySnapshot(inventorysnapshotDo);
	}

	@Override
	public boolean store(InventorySnapshot inventorysnapshot) {
		InventorySnapshotDo inventorysnapshotDo = convertor.fromInventorySnapshot(inventorysnapshot);
		return this.saveOrUpdate(inventorysnapshotDo);
	}

	@Override
	public boolean update(InventorySnapshot inventorysnapshot) {
		InventorySnapshotDo inventorysnapshotDo = convertor.fromInventorySnapshot(inventorysnapshot);
		return this.saveOrUpdate(inventorysnapshotDo);
	}

	@Override
	public boolean delete(List<InventorySnapshotId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
