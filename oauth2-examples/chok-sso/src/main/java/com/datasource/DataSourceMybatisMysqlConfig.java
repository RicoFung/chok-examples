package com.datasource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:datasource-mybatis-mysql-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class DataSourceMybatisMysqlConfig 
{
    @Value("${datasource.mybatis.mysql.url}")
    private String url;
    @Value("${datasource.mybatis.mysql.username}")
    private String user;
    @Value("${datasource.mybatis.mysql.password}")
    private String password;
    @Value("${datasource.mybatis.mysql.driver-class-name}")
    private String driverClass;
    @Value("${datasource.mybatis.mysql.filters}")
    private String filters;
    @Value("${datasource.mybatis.mysql.initialSize}")
    private int initialSize;
    @Value("${datasource.mybatis.mysql.maxActive}")
    private int maxActive;
    @Value("${datasource.mybatis.mysql.minIdle}")
    private int minIdle;
    @Value("${datasource.mybatis.mysql.maxWait}")
    private int maxWait;
    @Value("${datasource.mybatis.mysql.mapper-location}")
    private String mapperLocation;
    @Value("${mybatis.config-location}")
    private String mybatisConfigLocation;
    
    @Primary
    @Bean(name = "dataSourceMybatisMysql")
    public DataSource dataSource() throws SQLException 
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        
        // 配置防御SQL注入攻击,使用缺省配置的WallFilter
        dataSource.setFilters(filters);
        // 自定义 filters
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(wallFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }
 
    @Bean(name = "sqlSessionFactoryMybatisMysql")
    @DependsOn({ "dataSourceMybatisMysql" })
    public SqlSessionFactory sqlSessionFactory() throws Exception 
    {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(mybatisConfigLocation));
        return sessionFactory.getObject();
    }

	@Bean(name = "sqlSessionTemplateMybatisMysql")
	@DependsOn({ "sqlSessionFactoryMybatisMysql" })
	public SqlSessionTemplate sqlSessionTemplate() throws Exception 
	{
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	@Primary
	@Bean(name = "transactionManagerMybatisMysql")
	@DependsOn({ "dataSourceMybatisMysql" })
	public DataSourceTransactionManager transactionManager() throws SQLException 
	{
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean(name = "transactionInterceptorMybatisMysql")
	@DependsOn({ "transactionManagerMybatisMysql" })
	public TransactionInterceptor transactionInterceptor() throws Throwable
	{
		Properties prop = new Properties();
		prop.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
		prop.setProperty("del*", "PROPAGATION_REQUIRED,-Exception");
		prop.setProperty("upd*", "PROPAGATION_REQUIRED,-Exception");
		prop.setProperty("get*", "PROPAGATION_NEVER,readOnly");
		prop.setProperty("query*", "PROPAGATION_NEVER,readOnly");
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(transactionManager());
		ti.setTransactionAttributes(prop);
		return ti;
	}

	@Bean(name = "beanNameAutoProxyCreatorMybatisMysql")
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() throws Throwable
	{
		BeanNameAutoProxyCreator bpc = new BeanNameAutoProxyCreator();
		bpc.setProxyTargetClass(true);
		bpc.setBeanNames("*Service");
		bpc.setInterceptorNames("transactionInterceptorMybatisMysql");
		return bpc;
	}
	
	@Bean(name = "wallConfigMybatisMysql")
	public WallConfig wallConfig()
	{
		WallConfig wc = new WallConfig();
		wc.setMultiStatementAllow(true); // 允许同时执行多条sql
		return wc;
	}
	
	@Bean(name = "wallFilterMybatisMysql")
	public WallFilter wallFilter()
	{
		WallFilter wf = new WallFilter();
//		wf.setDbType("mysql"); // 指定dbType
		wf.setConfig(wallConfig()); // 读取自定义wall-config
		wf.setLogViolation(true); // 允许 对被认为是攻击的SQL进行LOG.error输出
		wf.setThrowException(false); // 禁止 对被认为是攻击的SQL抛出SQLExcepton
		return wf;
	}
}