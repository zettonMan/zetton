package com.zetton.thymeleaf.vo;

import cn.hutool.json.JSONSupport;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * API接口的基础返回类
 *
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class BaseRes<T> extends JSONSupport {
    /**
     * 是否成功
     */
    private Integer resCode;

    /**
     * 说明
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;
}