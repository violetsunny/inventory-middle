package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.LogicalPlantId;
import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.repository.LogicalPlantRepository;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.application.convertor.LogicalPlantDtoConvertor;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 逻辑仓库表QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
@Service
@Slf4j
public class LogicalPlantQueryServiceImpl implements LogicalPlantQueryService {

	@Resource
	private LogicalPlantRepository logicalplantRepository;
	@Resource
	private LogicalPlantDtoConvertor dtoConvertor;


	@Override
	public PageResponse<LogicalPlantDto> queryPage(LogicalPlantPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<LogicalPlant> page = logicalplantRepository.queryPage(pageQuery, params);
		List<LogicalPlantDto> dtoList = page.getData().stream().map(dtoConvertor::fromLogicalPlant).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public LogicalPlantDto findById(Long id) {
		return dtoConvertor.fromLogicalPlant(logicalplantRepository.findById(new LogicalPlantId(id)));
	}

}

