package org.moonsong.booknet.infrastructure;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;

    public LoginInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPreFlight(request)) {
            return true;
        }

        String token = AuthorizationExtractor.extractAccessToken(request);
        jwtUtils.validateToken(token);
        return true;
    }

    private boolean isPreFlight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.toString().equals(request.getMethod());
    }
}
