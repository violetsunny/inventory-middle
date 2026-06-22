package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MdocSubQuantityId;
import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import com.inventory.middle.domain.repository.MdocSubQuantityRepository;
import com.inventory.middle.application.service.MdocSubQuantityQueryService;
import com.inventory.middle.application.convertor.MdocSubQuantityDtoConvertor;
import com.inventory.middle.client.dto.MdocSubQuantityDto;
import com.inventory.middle.client.dto.query.MdocSubQuantityPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证子表-数量QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubQuantityQueryServiceImpl implements MdocSubQuantityQueryService {

	@Resource
	private MdocSubQuantityRepository mdocsubquantityRepository;
	@Resource
	private MdocSubQuantityDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MdocSubQuantityDto> queryPage(MdocSubQuantityPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MdocSubQuantity> page = mdocsubquantityRepository.queryPage(pageQuery, params);
		List<MdocSubQuantityDto> dtoList = page.getData().stream().map(dtoConvertor::fromMdocSubQuantity).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MdocSubQuantityDto findById(Long id) {
		return dtoConvertor.fromMdocSubQuantity(mdocsubquantityRepository.findById(new MdocSubQuantityId(id)));
	}

}

