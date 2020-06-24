package com.zetton.thymeleaf.aop;

import com.zetton.thymeleaf.common.retenion.DataSource;
import com.zetton.thymeleaf.config.DataSourceContextHolder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1) //需要加入切面排序
public class DynamicDataSourceAspect {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 切入点只对@Service注解的类上的@DataSource方法生效
     * @param DataSource
     */
    @Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(DataSource)" )
    public void dynamicDataSourcePointCut(DataSource DataSource){}

    @Before(value = "dynamicDataSourcePointCut(dataSource)")
    public void switchDataSource(DataSource dataSource) {
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    /**
     * 切点执行完后 切换成主数据库
     * @param dataSource
     */
    @After(value="dynamicDataSourcePointCut(dataSource)")
    public void after(DataSource dataSource){
        DataSourceContextHolder.cleanDataSource();
    }
}