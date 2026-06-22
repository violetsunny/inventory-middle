package com.inventory.middle.application.service.impl;


import cn.hutool.core.bean.BeanUtil;
import top.kdla.framework.dto.PageResponse;

import com.inventory.middle.domain.model.types.MdocSubExtId;
import com.inventory.middle.domain.model.entity.MdocSubExt;
import com.inventory.middle.domain.repository.MdocSubExtRepository;
import com.inventory.middle.application.service.MdocSubExtQueryService;
import com.inventory.middle.application.convertor.MdocSubExtDtoConvertor;
import com.inventory.middle.client.dto.MdocSubExtDto;
import com.inventory.middle.client.dto.query.MdocSubExtPageQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料凭证-标签-扩展QueryServiceImpl
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
@Service
@Slf4j
public class MdocSubExtQueryServiceImpl implements MdocSubExtQueryService {

	@Resource
	private MdocSubExtRepository mdocsubextRepository;
	@Resource
	private MdocSubExtDtoConvertor dtoConvertor;


	@Override
	public PageResponse<MdocSubExtDto> queryPage(MdocSubExtPageQuery pageQuery) {
		Map<String, Object> params = BeanUtil.beanToMap(pageQuery);
		PageResponse<MdocSubExt> page = mdocsubextRepository.queryPage(pageQuery, params);
		List<MdocSubExtDto> dtoList = page.getData().stream().map(dtoConvertor::fromMdocSubExt).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotalCount(), page.getPageSize(), page.getPageNum());
	}

	@Override
	public MdocSubExtDto findById(Long id) {
		return dtoConvertor.fromMdocSubExt(mdocsubextRepository.findById(new MdocSubExtId(id)));
	}

}

