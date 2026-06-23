package com.inventory.middle.domain.model.bo.sparepart;

import lombok.Data;

import java.io.Serializable;
@Data
public class PageSparePartReqBO implements Serializable {

    String tenantId;

    String materialCode;

    Integer pageNum;

    Integer pageSize;
}
