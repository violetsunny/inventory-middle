package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.CodeGeneratorCfg;
import com.inventory.middle.domain.model.types.CodeGeneratorCfgId;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * 流水号配置表Repository
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:32
 */
public interface CodeGeneratorCfgRepository {

    /**
    * 分页查询
    *
    * @param pageQuery
    * @param params
    * @return
    */
    PageResponse<CodeGeneratorCfg> queryPage(PageQuery pageQuery, Map<String, Object> params);

    /**
     * 通过ID获取流水号配置表
     *
     * @param id
     * @return
     */
     CodeGeneratorCfg findById(CodeGeneratorCfgId id);

    /**
     * 保存
     *
     * @param codegeneratorcfg
     */
    boolean store(CodeGeneratorCfg codegeneratorcfg);

    /**
     * 更新
     *
     * @param codegeneratorcfg
     */
    boolean update(CodeGeneratorCfg codegeneratorcfg);

    /**
     * 删除
     *
     * @param ids
     */
    boolean delete(List<CodeGeneratorCfgId> ids);
}
