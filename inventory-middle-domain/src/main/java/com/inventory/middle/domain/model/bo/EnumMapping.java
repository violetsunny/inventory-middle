/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.domain.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kanglele
 * @version $Id: EnumMapping, v 0.1 2019-11-04 18:31 Exp $
 */
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class EnumMapping implements Serializable {

    private String key;

    private String value;
}
