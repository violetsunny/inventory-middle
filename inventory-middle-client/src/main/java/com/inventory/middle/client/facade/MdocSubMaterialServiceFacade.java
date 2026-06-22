package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MdocSubMaterialDto;
import com.inventory.middle.client.dto.command.MdocSubMaterialCommand;
import com.inventory.middle.client.dto.query.MdocSubMaterialPageQuery;

import java.util.List;

/**
 * 物料凭证子表-物料信息Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubMaterialServiceFacade {

    /**
     * 分页查询
     *
     * @param mdocsubmaterialPageQuery
     * @return
     */
    PageResponse<MdocSubMaterialDto> page(MdocSubMaterialPageQuery mdocsubmaterialPageQuery);

    /**
     * 物料凭证子表-物料信息list查询
     */
    MultiResponse<MdocSubMaterialDto> list();

    /**
     * 通过ID获取物料凭证子表-物料信息
     *
     * @param id
     * @return
     */
    SingleResponse<MdocSubMaterialDto> findById(Long id);

    /**
     * 保存
     *
     * @param mdocsubmaterialCommand
     */
    SingleResponse<Boolean> save(MdocSubMaterialCommand mdocsubmaterialCommand);

    /**
     * 更新
     *
     * @param mdocsubmaterialCommand
     */
    SingleResponse<Boolean> update( MdocSubMaterialCommand mdocsubmaterialCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
