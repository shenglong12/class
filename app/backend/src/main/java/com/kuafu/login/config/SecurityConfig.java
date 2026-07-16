package com.kuafu.login.config;

import com.kuafu.common.util.StringUtils;
import com.kuafu.login.annotation.IgnoreAuth;
import com.kuafu.login.handle.AuthenticationEntryPointImpl;
import com.kuafu.login.handle.JwtAuthenticationTokenFilter;
import com.kuafu.login.handle.LogoutSuccessHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;


@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConditionalOnProperty(prefix = "login", name = "enable")
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();


        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .cors().and()
                .csrf().disable()
                // 禁用HTTP响应标头
                .headers().cacheControl().disable().and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests();
        registry
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/get_mp_url").permitAll()
                .antMatchers("/getLoginUser").permitAll()
                .antMatchers("/system/setting/login").permitAll()
                .antMatchers("/chatbot/**", "/difyConfig/**").permitAll()
                .antMatchers("/common/**").permitAll()
                .antMatchers("/generalOrder/callback/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                .antMatchers("/**/*.png", "/**/*.jpg", "/**/*.svg", "/**/*.ico", "/**/*.ttf").permitAll()
                .antMatchers("/").permitAll();


        handlerMethods.forEach((info, method) -> {
            // 带IgnoreAuth注解的方法直接放行
            if (StringUtils.isNotNull(method.getMethodAnnotation(IgnoreAuth.class))) {
                // 根据请求类型做不同的处理
                info.getMethodsCondition().getMethods().forEach(requestMethod -> {
                    switch (requestMethod) {
                        case GET:
                            // getPatternsCondition得到请求url数组，遍历处理
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                // 放行
                                registry.antMatchers(HttpMethod.GET, pattern).permitAll();
                            });
                            break;
                        case POST:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                registry.antMatchers(HttpMethod.POST, pattern).permitAll();
                            });
                            break;
                        case DELETE:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                registry.antMatchers(HttpMethod.DELETE, pattern).permitAll();
                            });
                            break;
                        case PUT:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                registry.antMatchers(HttpMethod.PUT, pattern).permitAll();
                            });
                            break;
                        default:
                            break;
                    }
                });
            }
        });


        registry.anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();

        // 添加Logout filter
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        // 添加JWT filter
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        Map<String, AuthenticationProvider> providerMap = applicationContext.getBeansOfType(AuthenticationProvider.class);
        for (Map.Entry<String, AuthenticationProvider> entry : providerMap.entrySet()) {
            log.info("=============== loading provider {}", entry.getKey());
            auth.authenticationProvider(entry.getValue());
        }
    }


    @Bean
    public HttpFirewall allowDoubleSlashFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

}
