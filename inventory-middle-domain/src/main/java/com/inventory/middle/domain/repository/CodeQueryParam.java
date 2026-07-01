package com.inventory.middle.domain.repository;

import lombok.Data;

import java.util.List;

/**
 * Code查询参数
 */
@Data
public class CodeQueryParam {

    private String businessNo;

    private String sourceNo;

    private String innerCode;

    private String type;

    private String code;

    private List<String> codeList;

    private List<String> innerCodeList;

    private String publisher;

    private String currentOwner;

    private String status;

    private String extendField1;

    public static CodeQueryParam of() {
        return new CodeQueryParam();
    }

    public CodeQueryParam withBusinessNo(String businessNo) {
        this.businessNo = businessNo;
        return this;
    }

    public CodeQueryParam withPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public CodeQueryParam withCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
        return this;
    }

    public CodeQueryParam withStatus(String status) {
        this.status = status;
        return this;
    }

    public CodeQueryParam withInnerCode(String innerCode) {
        this.innerCode = innerCode;
        return this;
    }

    public CodeQueryParam withCodeList(List<String> codeList) {
        this.codeList = codeList;
        return this;
    }

    public CodeQueryParam withInnerCodeList(List<String> innerCodeList) {
        this.innerCodeList = innerCodeList;
        return this;
    }

    public CodeQueryParam withSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
        return this;
    }
}
