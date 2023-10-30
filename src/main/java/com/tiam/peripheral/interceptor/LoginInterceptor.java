package com.tiam.peripheral.interceptor;

import com.tiam.peripheral.entity.Role;
import com.tiam.peripheral.entity.User;
import com.tiam.peripheral.exception.BizException;
import com.tiam.peripheral.mapper.RoleMapper;
import com.tiam.peripheral.mapper.UserMapper;
import com.tiam.peripheral.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

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
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

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
        // todo 调整异常抛出消息
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
        // 用户是否存在
        if(!isExistsUser(claims)){
            throw new BizException("用户不存在");
        }
        // todo 验证角色权限?

        // 签发日期不同, 始终为false
//        if (!TokenUtil.verifyToken(accessToken).test(name, role)) {
//            throw new BizException("accessToken不正确");
//        }
        // 放行
        return true;
    }

    private boolean isExistsUser(Claims claims){
        String username = claims.get("username", String.class);
        String roleName = claims.get("role", String.class);
        // 用户是否存在
        User user = userMapper.findUserByUsername(username);
        if(Objects.isNull(user)){
            return false;
        }
        Role role = roleMapper.selectById(user.getRoleId());
        return Objects.nonNull(role) && StringUtils.equals(roleName, role.getRoleName());
    }
}
