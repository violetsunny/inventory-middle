package com.inventory.middle.application.plan.plan.calculate.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 转换服务支持类
 *
 * @author Danny.Lee
 * @date 2021/10/9
 */
@Component
@Named("ConvertHelper")
public class ConvertHelper {

    @Named("indicatorsToJson")
    public static String indicatorsToJson(Map<String, BigDecimal> mapToJson) {
        return JSON.toJSONString(mapToJson);
    }

    @Named("jsonToIndicators")
    public static Map<String, BigDecimal> jsonToIndicators(String json) {
        return JSONObject.parseObject(json, new TypeReference<Map<String, BigDecimal>>() {
        });
    }

    @Named("jsonToObject")
    public static JSONObject jsonToObject(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSONObject.parseObject(json);
    }


    @Named("objectToJson")
    public static String jsonToObject(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        return jsonObject.toJSONString();
    }
}
