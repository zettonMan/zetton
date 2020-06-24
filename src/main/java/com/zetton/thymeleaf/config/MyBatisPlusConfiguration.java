package com.zetton.thymeleaf.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zetton.thymeleaf.common.enums.DataSourceEnum;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * MybatisPlus配置
 *
 * @author xiongneng
 * @since 2017/5/20 21:58
 */
@Configuration
@MapperScan("com.zetton.thymeleaf.mapper")
public class MyBatisPlusConfiguration {

    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource createMasterDataSource(){
        return new DruidDataSource();
    }

    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource createSlaveDataSource(){
        return new DruidDataSource();
    }

    /**
     * 设置动态数据源，通过@Primary 来确定主DataSource
     * @return
     */
    @Bean
    @Primary
    public DataSource createDynamicDataSource(@Qualifier("master") DataSource master, @Qualifier("slave") DataSource slave){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //配置多数据源
        Map<Object, Object> map = new HashMap<>();
        map.put(DataSourceEnum.MASTER.getValue(),master);
        map.put(DataSourceEnum.SLAVE.getValue(),slave);
        dynamicDataSource.setTargetDataSources(map);
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(master);
        return  dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(createDynamicDataSource(createMasterDataSource(),createSlaveDataSource()));
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*/*Mapper.xml"));

        MybatisConfiguration configuration = new MybatisConfiguration();
        //configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        /*sqlSessionFactory.setPlugins(new Interceptor[]{ //PerformanceInterceptor(),OptimisticLockerInterceptor()
                paginationInterceptor() //添加分页功能
        });*/
        //sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }
}
