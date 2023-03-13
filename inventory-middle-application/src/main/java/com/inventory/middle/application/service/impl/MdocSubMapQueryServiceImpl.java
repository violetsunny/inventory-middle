package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MdocSubMapId;
import com.inventory.middle.domain.model.entity.MdocSubMap;
import com.inventory.middle.domain.repository.MdocSubMapRepository;
import com.inventory.middle.application.service.MdocSubMapQueryService;
import com.inventory.middle.application.convertor.MdocSubMapDtoConvertor;
import com.inventory.middle.client.dto.MdocSubMapDto;
import com.inventory.middle.client.dto.query.MdocSubMapPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证-标签-移动平均价QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
@Service
@Slf4j
public class MdocSubMapQueryServiceImpl implements MdocSubMapQueryService {

	@Resource
	private MdocSubMapRepository mdocsubmapRepository;
	@Resource
	private MdocSubMapDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MdocSubMapDto> queryPage(MdocSubMapPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MdocSubMap> page = mdocsubmapRepository.queryPage(pageQuery, params);
		List<MdocSubMapDto> dtoList = page.getData().stream().map(dtoConvertor::fromMdocSubMap).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MdocSubMapDto findById(Long id) {
		return dtoConvertor.fromMdocSubMap(mdocsubmapRepository.findById(new MdocSubMapId(id)));
	}

}

