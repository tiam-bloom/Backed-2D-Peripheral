package com.tiam.peripheral.config;

import com.tiam.peripheral.enums.AnonymityUrlEnum;
import com.tiam.peripheral.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Tiam
 * @date 2023/10/23 17:26
 * @description
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//拦截所有的路径
                .excludePathPatterns(AnonymityUrlEnum.anonymityUrlList()); // 不拦截匿名访问路径
    }
}
