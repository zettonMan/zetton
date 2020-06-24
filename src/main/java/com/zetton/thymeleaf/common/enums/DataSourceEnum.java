package com.zetton.thymeleaf.common.enums;

public enum DataSourceEnum {

    MASTER("master"),SLAVE("slave");

    private String value;

    DataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}