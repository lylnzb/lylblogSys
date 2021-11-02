package com.lylblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.lylblog.project.**.mapper")
public class LylBlogSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(LylBlogSysApplication.class, args);
    }

}
