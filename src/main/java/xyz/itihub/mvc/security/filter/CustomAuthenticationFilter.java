package xyz.itihub.mvc.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.itihub.mvc.security.token.CustomAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 自定义认证过滤器
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        HashMap<String, String> customizedDetails = new HashMap<>();
        customizedDetails.put("RemoteAddress", request.getRemoteAddr());
        customizedDetails.put("RiskLevel", "LOW");
        authRequest.setDetails(customizedDetails);
    }

    /**
     * 认证失败方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendRedirect("/login.html?error=true");
    }

    /**
     * 认证方法
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String name = obtainUsername(request);
        String password = obtainPassword(request);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = null;
        if (context.getAuthentication() == null) {
            auth = new CustomAuthenticationToken(name, password);
            setDetails(request, (UsernamePasswordAuthenticationToken) auth);
            return this.getAuthenticationManager().authenticate(auth);
        } else {
            auth = context.getAuthentication();
            return auth;
        }
    }
}
