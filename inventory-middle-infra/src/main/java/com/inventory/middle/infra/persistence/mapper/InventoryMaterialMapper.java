package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.InventoryMaterialDo;
import com.inventory.middle.infra.persistence.entity.InventoryMaterialQueryPO;
import com.inventory.middle.infra.persistence.entity.ListMaterialCodeParamPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2022-05-05 16:59:30
 */
@Mapper
public interface InventoryMaterialMapper {

    /**
     * 新增物料
     * @param inventoryMaterialPO
     * @return
     */
    int insert(InventoryMaterialDo inventoryMaterialPO);

    /**
     * 批量新增物料
     * @param list
     * @return
     */
    int insertBatch(List<InventoryMaterialDo> list);

    /**
     * 根据物料编码更新物料
     * @param inventoryMaterialPO
     * @return
     */
    int updateByMaterialCode(InventoryMaterialDo inventoryMaterialPO);

    /**
     * 根据编码查询
     * @param paramPO
     * @return
     */
    List<InventoryMaterialDo> listByMaterialCodeList(ListMaterialCodeParamPO paramPO);

    /**
     * 根据条件查询物料信息
     * @param queryPO
     * @return
     */
    List<InventoryMaterialDo> listByCondition(InventoryMaterialQueryPO queryPO);


}
