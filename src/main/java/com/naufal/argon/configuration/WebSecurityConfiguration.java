package com.naufal.argon.configuration;

import com.naufal.argon.configuration.handler.CustomAuthenticationSuccessHandler;
import com.naufal.argon.service.UserSecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSecurityService userService;

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .anyRequest().authenticated()
        .and().formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/sign-in")
            .defaultSuccessUrl("/dashboard")
            .successHandler(myAuthenticationSuccessHandler())
            .failureUrl("/login?error=true")
        .and().logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true")
            .deleteCookies("JSESSIONID");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html/**",
            "/webjars/**",
            "/user/**",
            "/register/**",
            "/login",
            "/assets/**"
        );
    }

    // @Override
    // protected void configure(final HttpSecurity http) throws Exception {
    //     http.csrf().disable().authorizeRequests()
    //     .antMatchers("/dashboard/**").hasRole("ADMIN")
    //     .antMatchers("/anonymous*").anonymous()
    //     .antMatchers("/login*").permitAll()
    //     .antMatchers("/sign-up*").permitAll()
    //     .antMatchers("/assets/**").permitAll()
    //     .antMatchers("/privacy-policy*").permitAll()
    //     .anyRequest().authenticated().and().formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
    //     .successHandler(myAuthenticationSuccessHandler()).failureUrl("/login?error=true").and().logout()
    //     .logoutUrl("/perform_logout").logoutSuccessUrl("/login?logout=true").deleteCookies("JSESSIONID");
    // }
}
