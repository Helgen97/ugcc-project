package com.project.ugcc.security.configuration;

import com.project.ugcc.security.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/public/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/a-panel").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/styles/**", "/scripts/**", "/imgs/**").permitAll()
                .and()
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/a-panel", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("/login?logout=true").permitAll());

        return http.build();
    }
}
