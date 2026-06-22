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
import com.inventory.middle.client.plan.dto.inventory.InvPlantBO;
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


        @Override
        public java.util.List<LogicalPlantDto> list(LogicalPlantPageQuery pageQuery) {
                // 全量查询：强制覆盖 pageSize，防止调用方未传导致默认 10 条截断
                pageQuery.setPageSize(Integer.MAX_VALUE);
                pageQuery.setPageNum(1);
                java.util.Map<String, Object> params = cn.hutool.core.bean.BeanUtil.beanToMap(pageQuery);
                top.kdla.framework.dto.PageResponse<com.inventory.middle.domain.model.entity.LogicalPlant> page =
                        logicalplantRepository.queryPage(pageQuery, params);
                return page.getData().stream().map(dtoConvertor::fromLogicalPlant).collect(java.util.stream.Collectors.toList());
        }

        @Override
        public List<InvPlantBO> list(String tenantId) {
                LogicalPlantPageQuery query = new LogicalPlantPageQuery();
                query.setTenantId(tenantId);
                List<LogicalPlantDto> dtos = list(query);
                return dtos.stream().map(dto -> {
                        InvPlantBO bo = new InvPlantBO();
                        bo.setPlantCode(dto.getLogicalPlantNo());
                        bo.setPlantName(dto.getLogicalPlantName());
                        bo.setTenantId(dto.getTenantId());
                        return bo;
                }).collect(Collectors.toList());
        }

        @Override
        public LogicalPlantDto findByNo(String logicalPlantNo) {
                com.inventory.middle.domain.model.entity.LogicalPlant e = logicalplantRepository.findByLogicalPlantNo(logicalPlantNo);
                return e == null ? null : dtoConvertor.fromLogicalPlant(e);
        }

        @Override
        public LogicalPlantDto findByOutPlantNo(String outPlantNo, String tenantId) {
                com.inventory.middle.domain.model.entity.LogicalPlant e = logicalplantRepository.findByOutPlantNo(outPlantNo, tenantId);
                return e == null ? null : dtoConvertor.fromLogicalPlant(e);
        }

}
