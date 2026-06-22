package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.Shipment;
import com.inventory.middle.domain.model.types.ShipmentId;
import com.inventory.middle.domain.repository.ShipmentRepository;
import com.inventory.middle.infra.persistence.convertor.ShipmentConvertor;
import com.inventory.middle.infra.persistence.entity.ShipmentDo;
import com.inventory.middle.infra.persistence.mapper.ShipmentMapper;
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
 * 交运单Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Repository
public class ShipmentRepositoryImpl extends ServiceImpl<ShipmentMapper, ShipmentDo> implements ShipmentRepository, IService<ShipmentDo> {

	@Resource
	private ShipmentConvertor convertor;

	@Override
	public PageResponse<Shipment> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<ShipmentDo> page = baseMapper.queryPage(new PlusPageQuery<ShipmentDo>(pageQuery).getPage(params),params);
		List<ShipmentDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<Shipment> dtoList = records.stream().map(convertor::toShipment).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public Shipment findById(ShipmentId id) {
		ShipmentDo shipmentDo = baseMapper.findById(id.get());
		return convertor.toShipment(shipmentDo);
	}

	@Override
	public boolean store(Shipment shipment) {
		ShipmentDo shipmentDo = convertor.fromShipment(shipment);
		return this.saveOrUpdate(shipmentDo);
	}

	@Override
	public boolean update(Shipment shipment) {
		ShipmentDo shipmentDo = convertor.fromShipment(shipment);
		return this.saveOrUpdate(shipmentDo);
	}

	@Override
	public boolean delete(List<ShipmentId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
