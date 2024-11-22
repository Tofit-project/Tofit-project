package com.tofit.mvc.model.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.tofit.mvc.model.dao")
@Configuration
public class MybatisConfig {

}
