package com.example.multitenant.config;

import com.example.multitenant.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
class TenantFilter implements Filter {


    @Autowired
    private JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        try {
            if (!shouldNotFilter(req)) {
                validateJwt(req);
            }
            chain.doFilter(request, response);
        } catch (Exception ex) {
            throw ex;
        } finally {
            TenantContext.clear();
        }

    }

    private void validateJwt(HttpServletRequest req) {
        if (!StringUtils.hasText(req.getHeader(HttpHeaders.AUTHORIZATION))) {
            throw new RuntimeException("missing authorization header");
        }

        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        try {
            jwtService.validateToken(authHeader);
            String tenantName = jwtService.getTenantId(authHeader);
            TenantContext.setCurrentTenant(tenantName);
        } catch (Exception e) {
            System.out.println("invalid access...!");
            throw new RuntimeException("un authorized access to application");
        }
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.contains("/auth");
    }
}