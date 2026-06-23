package com.inventory.middle.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongguo
 */

@Data
public class KeyValueDTO implements Serializable {

    private static final long serialVersionUID = 1779475332864841694L;

    private String key;

    private String value;

}
