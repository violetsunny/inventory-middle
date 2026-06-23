package com.inventory.middle.application.service;


import com.inventory.middle.client.dto.command.MaterialDocMainCommand;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocCancelMessage;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocInMessage;
import com.inventory.middle.domain.service.material.model.MaterialDocInvRes;

import java.util.List;
import top.kdla.framework.dto.SingleResponse;


/**
 * 物料凭证主表ApplicationService
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 18:25:31
 */
public interface MaterialDocMainApplicationService {

    /**
     * 保存
     *
     * @param materialdocmainCommand
     */
    boolean add(MaterialDocMainCommand materialdocmainCommand);

    /**
     * 更新
     *
     * @param materialdocmainCommand
     */
    boolean update(MaterialDocMainCommand materialdocmainCommand);

    /**
     * 批量删除
     *
     * @param ids
     */
    boolean deleteBatch(List<Long> ids);

    /** 入库 */
    SingleResponse<MaterialDocInvRes> createMaterialDocIn(MaterialDocumentBO materialDocument);

    /** 出库 */
    SingleResponse<MaterialDocInvRes> createMaterialDocOut(MaterialDocumentBO materialDocument);

    /** 出入库（同单） */
    SingleResponse<MaterialDocInvRes> createMaterialDocOutIn(MaterialDocumentBO materialDocument);

    /** 撤销 */
    SingleResponse<MaterialDocInvRes> reverseMaterialDoc(MaterialDocumentBO materialDocument);




    /** 从MQ消息创建物料凭证（入库） */
    SingleResponse<MaterialDocInvRes> createMaterialDocFromMessage(MaterialDocInMessage message);

    /** 从MQ消息撤销物料凭证 */
    SingleResponse<MaterialDocInvRes> reverseMaterialDocFromMessage(MaterialDocCancelMessage message);

    /** 生成物料凭证唯一请求 ID */
    String getMaterialDocId();

    /**
     * 校验物料凭证请求参数合法性
     * @return true 合法；抛异常表示非法
     */
    boolean checkMaterialDoc(com.inventory.middle.domain.model.bo.material.MaterialDocumentBO bo);

    /**
     * 更新年检时间
     */
    boolean updateAnnualDate(com.inventory.middle.domain.model.bo.material.UpdateMaterialAnnualDateReqBO bo);

}
