package com.inventory.middle.domain.common.constants;

public class RedisConstant {

    private RedisConstant() {
    }

    public static final String SEQUENCE_NO_KEY = "enn::inventory::sequenceNo::";
    public static final String MATERIAL_NO_KEY = "enn::inventory::materialNo::";
    public static final String MATERIAL_INBOUND_KEY = "enn::inventory::inbound::";
    public static final String MATERIAL_REVERSE_KEY = "enn::inventory::reverse::";
    public static final String MATERIAL_NO_OUTBOUND_KEY = "enn::inventory::materialNo::outbound::";
    public static final String TRANSFER_TRANSIT_KEY = "enn::inventory::transit::";
    public static final String INVENTORY_SNOWFLAKE = "enn::inventory::snowflake::";
    public static final long WAITE_TIME = 1_000;
    public static final long LEASE_TIME = 10_000;
}
