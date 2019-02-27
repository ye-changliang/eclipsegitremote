package com.tydic.bigdata.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {"com.tydic.bigdata.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryOracle")
public class MybatisDataSourceConfig {

    private final DataSource oracleDataSource;

    @Autowired
    public MybatisDataSourceConfig(@Qualifier("secondaryDataSource") DataSource oracleDataSource) {
        this.oracleDataSource = oracleDataSource;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryOracle() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(oracleDataSource);
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateOracle() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryOracle());
        return template;
    }
}
