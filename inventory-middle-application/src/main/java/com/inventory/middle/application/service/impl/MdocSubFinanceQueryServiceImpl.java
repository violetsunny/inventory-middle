package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MdocSubFinanceId;
import com.inventory.middle.domain.model.entity.MdocSubFinance;
import com.inventory.middle.domain.repository.MdocSubFinanceRepository;
import com.inventory.middle.application.service.MdocSubFinanceQueryService;
import com.inventory.middle.application.convertor.MdocSubFinanceDtoConvertor;
import com.inventory.middle.client.dto.MdocSubFinanceDto;
import com.inventory.middle.client.dto.query.MdocSubFinancePageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证-标签-财务QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MdocSubFinanceQueryServiceImpl implements MdocSubFinanceQueryService {

	@Resource
	private MdocSubFinanceRepository mdocsubfinanceRepository;
	@Resource
	private MdocSubFinanceDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MdocSubFinanceDto> queryPage(MdocSubFinancePageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MdocSubFinance> page = mdocsubfinanceRepository.queryPage(pageQuery, params);
		List<MdocSubFinanceDto> dtoList = page.getData().stream().map(dtoConvertor::fromMdocSubFinance).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MdocSubFinanceDto findById(Long id) {
		return dtoConvertor.fromMdocSubFinance(mdocsubfinanceRepository.findById(new MdocSubFinanceId(id)));
	}

}

