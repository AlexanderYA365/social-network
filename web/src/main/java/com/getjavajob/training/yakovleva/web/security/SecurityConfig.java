package com.getjavajob.training.yakovleva.web.security;

import com.getjavajob.training.yakovleva.service.DetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);
    private final DetailsService detailsService;
    private final String[] PAGES_FOR_ALL = {"/account-message",
            "/account-write-message",
            "/account-friends",
            "/create-test-men-account",
            "/create-test-women-account",
            "/create-test-friend",
            "/create-test-group",
            "/create-test-group-members",
            "/account-find-group",
            "/edit-account-settings",
            "/my-account",
            "/add-friend-account",
            "/show-friend",
            "/create-group",
            "/create-group",
            "/show-group",
            "/account-group"};

    private final AuthenticationConfiguration authConfiguration;

    @Autowired
    public SecurityConfig(DetailsService detailsService, AuthenticationConfiguration authConfiguration) {
        this.detailsService = detailsService;
        this.authConfiguration = authConfiguration;
        logger.info("SecurityConfig - {}", detailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("securityFilterChain(http)");
        http.authorizeRequests()
                .antMatchers(PAGES_FOR_ALL).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/index").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/registration-account", "/resources/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/index.jsp").permitAll()
                .failureUrl("/index.jsp?error=true")
                .defaultSuccessUrl("/main").permitAll()
                .and()
                .logout()
                .logoutUrl("/account-logout")
                .logoutSuccessUrl("/").permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key("remember-me")
                .tokenValiditySeconds(86400)
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(5);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        logger.info("daoAuthenticationProvider()");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(detailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}