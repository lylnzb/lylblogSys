package com.lylblog.framework.config;

import com.lylblog.framework.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 过滤器
 * @author lyl
 * @Date 2020年08月11日
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
     * @param reg
     */
    @Override
    public void addViewControllers(ViewControllerRegistry reg) {
        reg.addViewController("/").setViewName("index");//默认访问页面
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE);//最先执行过滤
        super.addViewControllers(reg);
    }

    /**
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /** 头像上传路径 */
        registry.addResourceHandler("/profile/**").addResourceLocations("file:" + LylBlogConfig.getProfile());
        /** 音乐上传路径 */
        registry.addResourceHandler("/musicfile/**").addResourceLocations("file:" + LylBlogConfig.getMusicfile());
        /** 文章上传路径 */
        registry.addResourceHandler("/articlefile/**").addResourceLocations("file:" + LylBlogConfig.getArticlefile());
        /** 百度编辑器文件保存路径 */
        registry.addResourceHandler("/ueditorfile/**").addResourceLocations("file:" + LylBlogConfig.getUeditorfile());
        /** swagger配置 */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
    }

    @Bean
    public LoginInterceptor loginInterceptor(){return new LoginInterceptor();}

}
