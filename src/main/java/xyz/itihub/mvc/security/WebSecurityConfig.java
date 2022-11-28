package xyz.itihub.mvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import xyz.itihub.mvc.security.filter.CustomAuthenticationFilter;
import xyz.itihub.mvc.security.handler.CustomFailureHandler;
import xyz.itihub.mvc.security.handler.CustomSuccessHandler;
import xyz.itihub.mvc.security.provider.CustomAdminAuthenticationProvider;
import xyz.itihub.mvc.security.provider.SudoAuthenticationProvider;
import xyz.itihub.mvc.service.UserService;

/**
 * 安全框架的使用
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public WebSecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    /**
     * 密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 定义自定义身份验证提供者
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        // 设置获取用户细节服务
        auth.setUserDetailsService(userService);
        // 设置密码加密器
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    /**
     * 配置
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 添加自定义admin身份验证提供者
        auth.authenticationProvider(new CustomAdminAuthenticationProvider());
        // 添加自定义身份验证提供者到框架调用链上
        auth.authenticationProvider(authenticationProvider())
                // 添加自定义sudo身份验证提供者
                .authenticationProvider(new SudoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 对请求进行鉴权
        http.authorizeRequests()
                // 匹配指定路径进行放行
                .antMatchers("/registration").permitAll()
                // 除匹配路径外其他请求路径需要鉴权
                .anyRequest().authenticated()
                .and()
                // 使用表单登录
                .formLogin()
                .loginPage("/login").permitAll() // 设置登录页面以及该页面无需鉴权
                .defaultSuccessUrl("/") // 成功响应跳转URL
                // 设置自定义登录成功/失败处理器
                .successHandler(new CustomSuccessHandler())
                .failureHandler(new CustomFailureHandler())
                .failureForwardUrl("/login?error=true") // 失败响应跳转URL
                .and()
                // 加入自定义认证过滤器 放在UsernamePasswordAuthenticationFilter过滤器之前
                .addFilterBefore(new CustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 登出删除Session
                .logout().invalidateHttpSession(true).clearAuthentication(true)
                // 设置登出页面路径以及该页面无需鉴权
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // 设置登出成功跳转页面
                .logoutSuccessUrl("/login?logout")
        ;

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静态资源访问路径绕过安全框架的配置
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/webjars/**")
                .antMatchers("/image/**")
                .antMatchers("/api/**")
                // h2 的页面控制台路径
                .antMatchers("/console/**");
    }
}
