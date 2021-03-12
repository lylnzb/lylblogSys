package com.lylblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.lylBlog.project.**.mapper")
@ServletComponentScan
public class LylBlogSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(LylBlogSysApplication.class, args);
    }

}
