package com.tydic.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author tao
 * @date 2018/08/28
 * tomcat8 以下 删除tomcat7\apache-tomcat-7.0.88\lib 下的el-api
 * 项目支持
 * java8
 * spring  security （权限管理 已配置所有请求不认证 忽略）
 * spring data jpa（hibernate） https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
 * spring mvc
 * thymeleaf  https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
 * bootstrap  https://v3.bootcss.com/getting-started/#examples  https://v3.bootcss.com/css/
 * jquery
 * <p>
 * user包下是使用例子
 * domain 下是实体
 * repository dao层
 * service
 * controller
 */
@SpringBootApplication
public class BigDataApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BigDataApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BigDataApplication.class);
    }
}
