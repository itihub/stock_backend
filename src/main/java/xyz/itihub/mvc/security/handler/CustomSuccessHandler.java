package xyz.itihub.mvc.security.handler;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义成功处理器
 * 适用场景：
 * 如
 *  1. 不通角色登录成功引导到不同页面
 */
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Getter
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("Recorded successful login from host");

        String targetUrl = targetUrl(authentication);
        if (response.isCommitted()) {
            logger.info("Can't redirect");
            return;
        }

        // 发送重定向请求
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String targetUrl(Authentication authentication) {
        String url;

        // 获取角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        // 根据角色不同跳转不同页面
        if (isUserRole(roles)) {
            url = "/subscription";
        } else if (isSudoRole(roles)) {
            url = "/admin";
        } else {
            url = "/";
        }
        return url;
    }

    private boolean isUserRole(List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean isSudoRole(List<String> roles) {
        if (roles.contains("ROLE_SUDO")) {
            return true;
        }
        return false;
    }

}
