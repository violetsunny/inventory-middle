package com.inventory.middle.domain.service.outbound;

import com.inventory.middle.domain.model.enums.MaterialAdjustTypeEnum;
import com.inventory.middle.domain.model.enums.MaterialDocCategoryEnum;
import top.kdla.framework.validator.BaseAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author holmes
 * @Classname MaterialDocBaseService
 * @Description 基类
 * @Date 2021/6/23 19:22
 */
@Slf4j
@Service
public class MaterialDocBaseService {


    /**
     * @param materialAdjustTypeEnum
     * @param servicePrefix
     * @param serviceSuffix
     * @return
     */
    public String buildClassNameOutBound(MaterialAdjustTypeEnum materialAdjustTypeEnum, String servicePrefix, String serviceSuffix) {
        BaseAssert.notNull(materialAdjustTypeEnum, "can not match MaterialAdjustTypeEnum ");

        String name = materialAdjustTypeEnum.name();
        return underlineToCamel(servicePrefix + name + serviceSuffix);
    }

    /**
     * @param materialAdjustTypeEnum
     * @param servicePrefix
     * @param serviceSuffix
     * @return
     */
    public String buildClassNameReverseWithAdjustType(MaterialAdjustTypeEnum materialAdjustTypeEnum, String servicePrefix, String serviceSuffix) {
        BaseAssert.notNull(materialAdjustTypeEnum, "can not match MaterialAdjustTypeEnum ");
        MaterialDocCategoryEnum materialDocCategoryEnum = materialAdjustTypeEnum.getMaterialDocCategoryEnum();
        BaseAssert.notNull(materialDocCategoryEnum, "can not match materialDocCategoryEnum,materialAdjustTypeEnum =" + materialAdjustTypeEnum.name());
        String name = materialAdjustTypeEnum.name();
        return underlineToCamel(servicePrefix + materialDocCategoryEnum.name().toLowerCase() + "_" + name + serviceSuffix);
    }

    /**
     * @param materialAdjustTypeEnum
     * @param servicePrefix
     * @param serviceSuffix
     * @return
     */
    public String buildClassNameReverseDefault(MaterialAdjustTypeEnum materialAdjustTypeEnum, String servicePrefix, String serviceSuffix) {
        BaseAssert.notNull(materialAdjustTypeEnum, "can not match MaterialAdjustTypeEnum ");
        MaterialDocCategoryEnum materialDocCategoryEnum = materialAdjustTypeEnum.getMaterialDocCategoryEnum();
        BaseAssert.notNull(materialDocCategoryEnum, "can not match materialDocCategoryEnum,materialAdjustTypeEnum =" + materialAdjustTypeEnum.name());
        String name = materialAdjustTypeEnum.name();
        return underlineToCamel(servicePrefix + materialDocCategoryEnum.name().toLowerCase() +"_default"+ serviceSuffix);
    }

    /**
     * 转换成驼峰写法
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        String sourceStr = param.toLowerCase().trim();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = sourceStr.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(sourceStr.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
