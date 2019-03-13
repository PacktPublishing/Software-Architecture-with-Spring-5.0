package com.packtpub.bankingapplication.security.filter

import com.packtpub.bankingapplication.security.service.JwtService
import com.packtpub.bankingapplication.security.domain.JwtUser
import io.jsonwebtoken.JwtException
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebFilter(urlPatterns = "/api/secure/*")
class JwtFilter implements Filter {

    @Autowired
    private JwtService jwtService;

    String authHeader = "x-auth-token";

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request
        final HttpServletResponse httpResponse = (HttpServletResponse) response

        final String authHeaderVal = httpRequest.getHeader(authHeader)
        if (null == authHeaderVal) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
            return
        }

        try {
            JwtUser jwtUser = jwtService.getUser(authHeaderVal)
            httpRequest.setAttribute("jwtUser", jwtUser)
        }
        catch (JwtException e) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
            return
        }

        chain.doFilter(httpRequest, httpResponse)
    }

    @Override
    void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    void destroy() {}


}
