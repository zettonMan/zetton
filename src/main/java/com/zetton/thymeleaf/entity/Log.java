package com.zetton.thymeleaf.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Log {
    private int logid;
    private String msg;
    private String logtime;
}
