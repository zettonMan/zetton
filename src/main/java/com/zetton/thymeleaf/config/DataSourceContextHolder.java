package com.zetton.thymeleaf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {
    static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    public static void setDataSource(String name){
        logger.info("-------- 设置数据源数据源为 ：{} ", name);
        contextHolder.set(name);
    }

    public static String getDataSource(){
        return contextHolder.get();
    }

    public static void cleanDataSource(){
        contextHolder.remove();
    }
}
