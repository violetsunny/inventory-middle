package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.Warehouse;
import com.inventory.middle.domain.model.types.WarehouseId;
import com.inventory.middle.domain.repository.WarehouseRepository;
import com.inventory.middle.infra.persistence.convertor.WarehouseConvertor;
import com.inventory.middle.infra.persistence.entity.WarehouseDo;
import com.inventory.middle.infra.persistence.mapper.WarehouseMapper;
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
 * 物理仓库表Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Repository
public class WarehouseRepositoryImpl extends ServiceImpl<WarehouseMapper, WarehouseDo> implements WarehouseRepository, IService<WarehouseDo> {

	@Resource
	private WarehouseConvertor convertor;

	@Override
	public PageResponse<Warehouse> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<WarehouseDo> page = baseMapper.queryPage(new PlusPageQuery<WarehouseDo>(pageQuery).getPage(params),params);
		List<WarehouseDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<Warehouse> dtoList = records.stream().map(convertor::toWarehouse).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public Warehouse findById(WarehouseId id) {
		WarehouseDo warehouseDo = baseMapper.findById(id.get());
		return convertor.toWarehouse(warehouseDo);
	}

	@Override
	public boolean store(Warehouse warehouse) {
		WarehouseDo warehouseDo = convertor.fromWarehouse(warehouse);
		return this.saveOrUpdate(warehouseDo);
	}

	@Override
	public boolean update(Warehouse warehouse) {
		WarehouseDo warehouseDo = convertor.fromWarehouse(warehouse);
		return this.saveOrUpdate(warehouseDo);
	}

	@Override
	public boolean delete(List<WarehouseId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
