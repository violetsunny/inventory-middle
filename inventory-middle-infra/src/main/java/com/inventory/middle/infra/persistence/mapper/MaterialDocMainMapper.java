package com.inventory.middle.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.inventory.middle.infra.persistence.entity.MaterialDocMainDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.List;

/**
 * 物料凭证主表Mapper
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Mapper
public interface MaterialDocMainMapper extends BaseMapper<MaterialDocMainDo> {

    /**
     * 分页查询
     *
     * @param page
     * @param params
     * @return
     */
    IPage<MaterialDocMainDo> queryPage(IPage<MaterialDocMainDo> page, @Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    MaterialDocMainDo queryEntity(@Param("params") Map<String, Object> params);

    /**
     * 查询
     *
     * @param params
     * @return
     */
    List<MaterialDocMainDo> queryList(@Param("params") Map<String, Object> params);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    MaterialDocMainDo findById(@Param("id") Long id);

    /**
     * 根据物料凭证号查询
     */
    MaterialDocMainDo queryByMaterialDocNo(@Param("materialDocNo") String materialDocNo);

    /**
     * 根据原始单据号和类型查询
     */
    MaterialDocMainDo queryByOriginalNoAndCate(@Param("originalNo") String originalNo,
                                               @Param("materialDocCategory") String materialDocCategory);

    /**
     * 根据原始单号查询
     */
    MaterialDocMainDo queryByMaterialDocByOriginalNo(@Param("originalNo") String originalNo);

    /**
     * 分页查询物料凭证（带参数）
     */
    List<MaterialDocMainDo> pageListByParams(@Param("params") Map<String, Object> params);

    /**
     * 导出列表查询
     */
    List<MaterialDocMainDo> exportListByParams(@Param("params") Map<String, Object> params);

    /**
     * 批量查询物料凭证
     */
    List<MaterialDocMainDo> listMaterialDocByIds(@Param("list") List<Long> materialDocIds);

    /**
     * 查询 uniqueNo 是否存在
     */
    String queryByUniqueNo(@Param("uniqueNo") String uniqueNo, @Param("appKey") String appKey);
}
