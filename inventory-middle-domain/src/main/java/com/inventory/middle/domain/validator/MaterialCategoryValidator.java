/**
 * kanglele Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.validator;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.inventory.middle.domain.model.entity.material.MaterialDocument;
import com.inventory.middle.domain.model.enums.MaterialDocCategoryEnum;
import com.inventory.middle.domain.validator.annotation.MaterialCategoryValid;
import org.apache.commons.lang3.exception.ExceptionUtils;
import top.kdla.framework.exception.BizException;
import top.kdla.framework.validator.ObjectValidator;

/**
 * @author kanglele
 * @version $Id: MaterialCategoryValidator, v 0.1 2021/9/28 16:28 Exp $
 */
public class MaterialCategoryValidator extends ObjectValidator<MaterialDocument> {

    @Override
    public boolean validate(ValidatorContext context, MaterialDocument materialDocument) {
        try {
            MaterialDocCategoryEnum materialDocCategoryEnum =
                MaterialDocCategoryEnum.tansfer(materialDocument.getMaterialDocCategory());
            getValidatorMsg(materialDocument, MaterialCategoryValid.class, materialDocCategoryEnum);
        } catch (BizException e) {
            context.addErrorMsg(ExceptionUtils.getStackTrace(e));
            return false;
        } catch (Exception e) {
            context.addErrorMsg(ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }
}
