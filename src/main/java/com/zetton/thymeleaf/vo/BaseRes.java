package com.zetton.thymeleaf.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * API接口的基础返回类
 *
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class BaseRes<T> {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 说明
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;
}