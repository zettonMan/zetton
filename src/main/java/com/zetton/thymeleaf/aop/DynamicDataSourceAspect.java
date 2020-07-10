package com.zetton.thymeleaf.aop;

import com.zetton.thymeleaf.common.enums.DataSourceEnum;
import com.zetton.thymeleaf.common.retenion.DataSource;
import com.zetton.thymeleaf.config.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
// @Order(1) //切面排序
public class DynamicDataSourceAspect {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 切入点只对@Service注解的类上的@DataSource方法生效
     *
     */
    @Pointcut(value="@annotation(com.zetton.thymeleaf.common.retenion.DataSource)" )
    public void dynamicDataSourcePointCut(){}

    @Before("dynamicDataSourcePointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        if (dataSource != null) {
            DataSourceContextHolder.setDataSource(dataSource.value().getValue());
            logger.debug("设置数据源为：" + dataSource.value().getValue());
        } else {
            DataSourceContextHolder.setDataSource(DataSourceEnum.MASTER.getValue());
            logger.debug("设置数据源为：master");
        }
    }

    @After("dynamicDataSourcePointCut()")
    public void doAfter(){
        DataSourceContextHolder.cleanDataSource();
    }
}