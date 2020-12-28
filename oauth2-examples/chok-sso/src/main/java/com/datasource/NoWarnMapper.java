package com.datasource;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 去除"No MyBatis mapper was found in '[com]' package. " 警告
 * @author rico.fung
 *
 */
//@org.apache.ibatis.annotations.Mapper
//@Qualifier("sqlSessionFactoryMybatisOracle")
public interface NoWarnMapper
{

}
