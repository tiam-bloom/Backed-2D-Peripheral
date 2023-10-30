package com.tiam.peripheral.interceptor;

import com.tiam.peripheral.exception.BizException;
import com.tiam.peripheral.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;
import java.util.Objects;

/**
 * @author Tiam
 * @date 2023/10/23 16:23
 * @description 这里抛出的异常可以被@ControllerAdvice注释的异常处理类捕获吗？
 * 答案是Filter不会，而Interceptor可以，
 * Filter是基于Servlet框架编写的，Interceptor是基于SpringMVC框架的，SpingMVC不会对Filter中的异常统一处理
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
        log.debug("请求路径：{}", request.getRequestURI());
        // 校验accessToken, 合法accessToken == 已登录
        String accessToken = request.getHeader("Authorization");
        if (Objects.isNull(accessToken)) {
            throw new BizException("accessToken不能为空");
        }
        // 解析accessToken, fixme 解析失败处理, 过期处理
        Claims claims = null;
        try {
            claims = TokenUtil.parsePayload(accessToken);
        } catch (Exception e) {
            throw new BizException("accessToken不合法, " + e.getMessage());
        }
        String name = claims.get("username", String.class);
        String role = claims.get("role", String.class);
        // todo 用户是否存在
        // 签发日期不同, 始终为false
//        if (!TokenUtil.verifyToken(accessToken).test(name, role)) {
//            throw new BizException("accessToken不正确");
//        }
        // 放行
        return true;
    }
}
