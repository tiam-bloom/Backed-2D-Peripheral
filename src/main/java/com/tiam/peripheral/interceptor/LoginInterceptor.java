package com.tiam.peripheral.interceptor;

import com.tiam.peripheral.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author Tiam
 * @date 2023/10/23 16:23
 * @description
 */
@Component
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 登录后(session) 且有 accessToken 的请求才能通过
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录日志
        log.info("请求路径：{}", request.getRequestURI());
        // 校验是否登录
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 校验accessToken
        String accessToken = request.getHeader("Authorization");
        String name = RedisUtil.get(Objects.requireNonNull(accessToken));
        if (StringUtils.equals(name, username)) {
            return true;
        }

        // todo 未登录处理
        // 重定向到未登录接口
        response.sendRedirect("/unLogin");
        return false;
    }
}
