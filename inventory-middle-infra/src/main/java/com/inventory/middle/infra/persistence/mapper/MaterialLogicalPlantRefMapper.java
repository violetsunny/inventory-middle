package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.MaterialLogicalPlantRefDo;
import com.inventory.middle.infra.persistence.entity.PageQueryMaterialPlantRefRequestPO;
import com.inventory.middle.infra.persistence.entity.QueryMaterialLogicalPlantRefPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description material_logical_plant_ref
 * @author dongguo.tao
 * @date 2021-10-13
 */
@Mapper
@Repository
public interface MaterialLogicalPlantRefMapper {

    /**
     * 新增
     * @author dongguo.tao
     * @date 2021/10/13
     * @param materialLogicalPlantRefPO
     * @return
     **/
    int insert(MaterialLogicalPlantRefDo materialLogicalPlantRefPO);

    /**
     * 批量新增
     * @param refPOList
     * @return
     */
    int insertBatch(@Param("list") List<MaterialLogicalPlantRefDo> refPOList);

    /**
     * 逻辑刪除
     * @param id
     * @author dongguo.tao
     * @date 2021/10/13
     * @return
     **/
    int delete(Long id);

    /**
     * 更新
     * @param materialLogicalPlantRefPO
     * @author dongguo.tao
     * @date 2021/10/13
     * @return
     **/
    int update(MaterialLogicalPlantRefDo materialLogicalPlantRefPO);

    /**
     * 查询 根据主键 id 查询
     * @param id
     * @author dongguo.tao
     * @date 2021/10/13
     * @return
     **/
    MaterialLogicalPlantRefDo load(Long id);

    /**
     * 根据条件查询
     * @param queryRefPO
     * @return
     */
    List<MaterialLogicalPlantRefDo> getListByCondition(QueryMaterialLogicalPlantRefPO queryRefPO);

    /**
     * 根据物料编码查询绑定关系
     * @param tenantId
     * @param materialCodes
     * @return
     */
    List<MaterialLogicalPlantRefDo> getListByMaterialCodes(@Param("tenantId")String tenantId,
                                                           @Param("materialCodes")List<String> materialCodes);

    /**
     * 列表查询
     * @param requestPO
     * @return
     */
    List<MaterialLogicalPlantRefDo> listBy(PageQueryMaterialPlantRefRequestPO requestPO);
}
