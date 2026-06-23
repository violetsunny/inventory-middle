package com.inventory.middle.client.dto;

import java.io.Serializable;

public class HelloDubboDto implements Serializable {

    private String srcData;
    private String resData;

    public String getSrcData() {
        return srcData;
    }

    public void setSrcData(String srcData) {
        this.srcData = srcData;
    }

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }

    @Override
    public String toString() {
        return "HelloDubboDto{" +
                "srcData='" + srcData + '\'' +
                ", resData='" + resData + '\'' +
                '}';
    }
}