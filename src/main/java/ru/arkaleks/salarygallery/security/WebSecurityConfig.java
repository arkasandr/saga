package ru.arkaleks.salarygallery.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.*;
import ru.arkaleks.salarygallery.controller.impl.PaySlipService;
import ru.arkaleks.salarygallery.service.UserService;

import javax.sql.DataSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String KEY = "saga";

    @Autowired
    UserService userService;

    @Autowired
    private SagaAuthenticationProvider sagaProvider;

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(sagaProvider)
                .authenticationProvider(new RememberMeAuthenticationProvider(KEY));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**/*.css")
                .antMatchers("/**/*.js")
                .antMatchers("/**/*.png")
                .antMatchers("/**/*.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(customLoginFilter(), RememberMeAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/index.html", "/login*", "/error.html",
                        "/registrationstart.html", "/registrationstart/continue")
                .permitAll()
                .antMatchers("/registrationend.html", "/editor.html")
                .hasRole("USER")
                .antMatchers("/resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/clogout.html")
                .logoutSuccessUrl("/index.html")
                .deleteCookies("remember-me-saga")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(myHttp403ForbiddenEntryPoint())
                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices());
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint myHttp403ForbiddenEntryPoint() {
        return new MyHttp403ForbiddenEntryPoint();
    }


    @Bean
    public AbstractRememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices(KEY, userService);
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setCookieName("remember-me-saga");
        rememberMeServices.setTokenValiditySeconds(1209600);
        return rememberMeServices;
    }

    @Bean
    public CustomLoginFilter customLoginFilter() throws Exception {
        CustomLoginFilter filter = new CustomLoginFilter("/login");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAllowSessionCreation(false);
        filter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler(rememberMeServices()));
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


