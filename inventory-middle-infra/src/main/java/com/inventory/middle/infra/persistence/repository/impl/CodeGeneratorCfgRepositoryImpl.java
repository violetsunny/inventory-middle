package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.CodeGeneratorCfg;
import com.inventory.middle.domain.model.types.CodeGeneratorCfgId;
import com.inventory.middle.domain.repository.CodeGeneratorCfgRepository;
import com.inventory.middle.infra.persistence.convertor.CodeGeneratorCfgConvertor;
import com.inventory.middle.infra.persistence.entity.CodeGeneratorCfgDo;
import com.inventory.middle.infra.persistence.mapper.CodeGeneratorCfgMapper;
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
 * 流水号配置表Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:24
 */
@Repository
public class CodeGeneratorCfgRepositoryImpl extends ServiceImpl<CodeGeneratorCfgMapper, CodeGeneratorCfgDo> implements CodeGeneratorCfgRepository, IService<CodeGeneratorCfgDo> {

	@Resource
	private CodeGeneratorCfgConvertor convertor;

	@Override
	public PageResponse<CodeGeneratorCfg> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<CodeGeneratorCfgDo> page = baseMapper.queryPage(new PlusPageQuery<CodeGeneratorCfgDo>(pageQuery).getPage(params),params);
		List<CodeGeneratorCfgDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<CodeGeneratorCfg> dtoList = records.stream().map(convertor::toCodeGeneratorCfg).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public CodeGeneratorCfg findById(CodeGeneratorCfgId id) {
		CodeGeneratorCfgDo codegeneratorcfgDo = baseMapper.findById(id.get());
		return convertor.toCodeGeneratorCfg(codegeneratorcfgDo);
	}

	@Override
	public boolean store(CodeGeneratorCfg codegeneratorcfg) {
		CodeGeneratorCfgDo codegeneratorcfgDo = convertor.fromCodeGeneratorCfg(codegeneratorcfg);
		return this.saveOrUpdate(codegeneratorcfgDo);
	}

	@Override
	public boolean update(CodeGeneratorCfg codegeneratorcfg) {
		CodeGeneratorCfgDo codegeneratorcfgDo = convertor.fromCodeGeneratorCfg(codegeneratorcfg);
		return this.saveOrUpdate(codegeneratorcfgDo);
	}

	@Override
	public boolean delete(List<CodeGeneratorCfgId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
