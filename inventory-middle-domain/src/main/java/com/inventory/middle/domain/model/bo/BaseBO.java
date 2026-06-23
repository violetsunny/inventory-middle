package com.inventory.middle.domain.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @date 2021/6/11
 */
@Data
public class BaseBO implements Serializable {

    private static final long serialVersionUID = -513849425959045111L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private String creatorId;


    /**
     * 更新人ID
     */
    private String updatorId;

    /**
     * 删除状态
     */
    private Integer deleted;


}
