package com.joinme.configuration;

import com.joinme.configuration.service.UserDetailServiceImpl;
import com.joinme.configuration.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    CustomizeAccessDeniedHandler accessDeniedHandler;

    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    CustomizeAccessDecisionManager accessDecisionManager;

    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomizeAbstractSecurityInterceptor securityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();
        http.authorizeRequests().
                antMatchers(
                        "/webjars/**",
                        "/resources/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/user/create")
                .permitAll().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);
                        o.setSecurityMetadataSource(securityMetadataSource);
                        return o;
                    }
                }).
                and().logout().
                permitAll().
                logoutSuccessHandler(logoutSuccessHandler).
                deleteCookies("JSESSIONID").
                and().formLogin().
                permitAll().
                successHandler(authenticationSuccessHandler).
                failureHandler(authenticationFailureHandler).
                and().exceptionHandling().
                accessDeniedHandler(accessDeniedHandler).
                authenticationEntryPoint(authenticationEntryPoint).
                and().sessionManagement().
                maximumSessions(1).
                expiredSessionStrategy(sessionInformationExpiredStrategy);
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
    }
}
