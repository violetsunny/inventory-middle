package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.ShipmentLine;
import com.inventory.middle.domain.model.types.ShipmentLineId;
import com.inventory.middle.domain.repository.ShipmentLineRepository;
import com.inventory.middle.infra.persistence.convertor.ShipmentLineConvertor;
import com.inventory.middle.infra.persistence.entity.ShipmentLineDo;
import com.inventory.middle.infra.persistence.mapper.ShipmentLineMapper;
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
 * 交运单明细Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:25
 */
@Repository
public class ShipmentLineRepositoryImpl extends ServiceImpl<ShipmentLineMapper, ShipmentLineDo> implements ShipmentLineRepository, IService<ShipmentLineDo> {

	@Resource
	private ShipmentLineConvertor convertor;

	@Override
	public PageResponse<ShipmentLine> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<ShipmentLineDo> page = baseMapper.queryPage(new PlusPageQuery<ShipmentLineDo>(pageQuery).getPage(params),params);
		List<ShipmentLineDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<ShipmentLine> dtoList = records.stream().map(convertor::toShipmentLine).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public ShipmentLine findById(ShipmentLineId id) {
		ShipmentLineDo shipmentlineDo = baseMapper.findById(id.get());
		return convertor.toShipmentLine(shipmentlineDo);
	}

	@Override
	public boolean store(ShipmentLine shipmentline) {
		ShipmentLineDo shipmentlineDo = convertor.fromShipmentLine(shipmentline);
		return this.saveOrUpdate(shipmentlineDo);
	}

	@Override
	public boolean update(ShipmentLine shipmentline) {
		ShipmentLineDo shipmentlineDo = convertor.fromShipmentLine(shipmentline);
		return this.saveOrUpdate(shipmentlineDo);
	}

	@Override
	public boolean delete(List<ShipmentLineId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
