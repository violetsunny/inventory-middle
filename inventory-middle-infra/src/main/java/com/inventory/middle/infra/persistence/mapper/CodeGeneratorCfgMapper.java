package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.CodeGeneratorCfgDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 流水号配置表Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:24
 */
@Mapper
public interface CodeGeneratorCfgMapper extends BaseMapper<CodeGeneratorCfgDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<CodeGeneratorCfgDo> queryPage(IPage<CodeGeneratorCfgDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    CodeGeneratorCfgDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<CodeGeneratorCfgDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    CodeGeneratorCfgDo findById(@Param("id") Long id);
}