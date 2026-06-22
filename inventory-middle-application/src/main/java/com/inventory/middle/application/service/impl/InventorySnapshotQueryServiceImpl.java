package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.application.service.InventorySnapshotQueryService;
import com.inventory.middle.application.convertor.InventorySnapshotDtoConvertor;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存快照-实时QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class InventorySnapshotQueryServiceImpl implements InventorySnapshotQueryService {

	@Resource
	private InventorySnapshotRepository inventorysnapshotRepository;
	@Resource
	private InventorySnapshotDtoConvertor dtoConvertor;


	@Override
	public PageResponse<InventorySnapshotDto> queryPage(InventorySnapshotPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<InventorySnapshot> page = inventorysnapshotRepository.queryPage(pageQuery, params);
		List<InventorySnapshotDto> dtoList = page.getData().stream().map(dtoConvertor::fromInventorySnapshot).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public InventorySnapshotDto findById(Long id) {
		return dtoConvertor.fromInventorySnapshot(inventorysnapshotRepository.findById(new InventorySnapshotId(id)));
	}


        @Override
        public top.kdla.framework.dto.PageResponse<com.inventory.middle.client.dto.InventorySnapshotDto> pageListCityGas(com.inventory.middle.client.dto.query.InventorySnapshotPageQuery pageQuery) {
                // 城燃无独立字段过滤，复用标准分页
                return queryPage(pageQuery);
        }

        @Override
        public top.kdla.framework.dto.PageResponse<com.inventory.middle.client.dto.InventorySnapshotDto> queryByBatchNo(com.inventory.middle.client.dto.query.InventorySnapshotPageQuery pageQuery) {
                java.util.Map<String, Object> params = cn.hutool.core.bean.BeanUtil.beanToMap(pageQuery);
                top.kdla.framework.dto.PageResponse<com.inventory.middle.domain.model.entity.InventorySnapshot> page =
                        inventorysnapshotRepository.queryPage(pageQuery, params);
                java.util.List<com.inventory.middle.client.dto.InventorySnapshotDto> dtoList = page.getData().stream()
                        .map(dtoConvertor::fromInventorySnapshot).collect(java.util.stream.Collectors.toList());
                return top.kdla.framework.dto.PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
        }

        @Override
        public top.kdla.framework.dto.PageResponse<com.inventory.middle.client.dto.InventorySnapshotDto> queryCurrentInventory(com.inventory.middle.client.dto.query.InventorySnapshotPageQuery pageQuery) {
                return queryPage(pageQuery);
        }

        @Override
        public java.util.List<com.inventory.middle.client.dto.InventorySnapshotDto> exportList(com.inventory.middle.client.dto.query.InventorySnapshotPageQuery pageQuery) {
                java.util.Map<String, Object> params = cn.hutool.core.bean.BeanUtil.beanToMap(pageQuery);
                java.util.List<com.inventory.middle.domain.model.entity.InventorySnapshot> list =
                        inventorysnapshotRepository.queryList(params);
                return list.stream().map(dtoConvertor::fromInventorySnapshot).collect(java.util.stream.Collectors.toList());
        }

        @Override
        public java.math.BigDecimal queryCurrentInventoryByCondition(String materialCode, String logicalPlantNo, String tenantId) {
                java.util.Map<String, Object> params = new java.util.HashMap<>();
                if (materialCode != null) {
                        params.put("materialCode", materialCode);
                }
                if (logicalPlantNo != null) {
                        params.put("logicalPlantNo", logicalPlantNo);
                }
                if (tenantId != null) {
                        params.put("tenantId", tenantId);
                }
                params.put("deleted", 0);
                java.util.List<com.inventory.middle.domain.model.entity.InventorySnapshot> list =
                        inventorysnapshotRepository.queryList(params);
                if (list == null || list.isEmpty()) {
                        return java.math.BigDecimal.ZERO;
                }
                return list.stream()
                        .map(s -> s.getUnrestricted() != null ? s.getUnrestricted() : java.math.BigDecimal.ZERO)
                        .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        }

}
