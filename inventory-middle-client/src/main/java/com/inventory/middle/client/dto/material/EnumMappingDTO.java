/**
 * OYO.com Inc.
 * Copyright (c) 2017-2019 All Rights Reserved.
 */
package com.inventory.middle.client.dto.material;

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
public class EnumMappingDTO implements Serializable {

    private String key;

    private String value;
}
