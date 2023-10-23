package com.tiam.peripheral.config;

import com.tiam.peripheral.enums.AnonymityUrl;
import com.tiam.peripheral.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                .excludePathPatterns(anonymityUrlList()); // 不拦截匿名访问路径
    }

    /**
     * 获取匿名访问路径
     * @return 匿名访问路径列表
     */
    private List<String> anonymityUrlList() {
        return Arrays.stream(AnonymityUrl.values()).map(AnonymityUrl::getUrl).collect(Collectors.toList());
    }
}
