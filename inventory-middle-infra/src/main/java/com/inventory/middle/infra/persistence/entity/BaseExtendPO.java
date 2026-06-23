package com.inventory.middle.infra.persistence.entity;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @AUTHOR yinglong.chen
 * @DATE: 2019/6/14 9:38
 * @Description
 **/
@Data
public abstract class BaseExtendPO extends BasePO implements Serializable {

    private static final long serialVersionUID = 5992999610701002920L;

    /**
     * 扩展属性-map
     */
    private String extendParam;

    /**
     * 设置extendParam特性
     */
    public void setExtendParam(String extendParam) {
        if (StringUtils.isBlank(extendParam)) {
            this.extendParam = StringUtils.EMPTY;
        } else {
            this.extendParam = extendParam;
        }
    }


    public Map<String, String> getExtendParamMap(){
        if (StringUtils.isBlank(this.extendParam)){
            return Maps.newHashMap();
        }
        Gson gson = new Gson();
        Map<String, String> hashMap =  gson.fromJson(this.extendParam, new TypeToken<Map<String, String>>(){}.getType());
        return hashMap;
    }


    /**
     * 获取值
     */
    public String getExtendParamValue(String key) {
        return getExtendParamMap().get(key);
    }


    /**
     * 添加特性相关
     */
    public void addExtendParamMap(String key, String value) {
        Map<String, String> featureMap = this.getExtendParamMap();
        featureMap.put(key, value);
        Gson gson = new Gson();
        this.setExtendParam(gson.toJson(featureMap));
    }

}
