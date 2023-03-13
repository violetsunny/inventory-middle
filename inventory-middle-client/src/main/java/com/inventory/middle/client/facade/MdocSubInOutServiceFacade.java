package com.inventory.middle.client.facade;

import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import com.inventory.middle.client.dto.MdocSubInOutDto;
import com.inventory.middle.client.dto.command.MdocSubInOutCommand;
import com.inventory.middle.client.dto.query.MdocSubInOutPageQuery;

import java.util.List;

/**
 * 物料凭证子表-出入库信息Facade
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:30
 */
public interface MdocSubInOutServiceFacade {

    /**
     * 分页查询
     *
     * @param mdocsubinoutPageQuery
     * @return
     */
    PageResponse<MdocSubInOutDto> page(MdocSubInOutPageQuery mdocsubinoutPageQuery);

    /**
     * 物料凭证子表-出入库信息list查询
     */
    MultiResponse<MdocSubInOutDto> list();

    /**
     * 通过ID获取物料凭证子表-出入库信息
     *
     * @param id
     * @return
     */
    SingleResponse<MdocSubInOutDto> findById(Long id);

    /**
     * 保存
     *
     * @param mdocsubinoutCommand
     */
    SingleResponse<Boolean> save(MdocSubInOutCommand mdocsubinoutCommand);

    /**
     * 更新
     *
     * @param mdocsubinoutCommand
     */
    SingleResponse<Boolean> update( MdocSubInOutCommand mdocsubinoutCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    SingleResponse<Boolean> delete(List<Long> ids);

}
