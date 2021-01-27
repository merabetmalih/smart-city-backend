package com.example.springmvcrest.security;

import com.example.springmvcrest.security.provider.JWTAuthorizationProviderFilter;

import com.example.springmvcrest.security.provider.ProviderDetailService;
import com.example.springmvcrest.security.simple.JWTAuthorizationUserFilter;
import com.example.springmvcrest.security.simple.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.springmvcrest.security.SecurityUtils.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration  {
    @Configuration
    @RequiredArgsConstructor
    @Order(1)
    public static class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final UserDetailService userDetailService;
        private final JwtProvider jwtProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/user/**")
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers(USER_REGISTER_URL)
                    .permitAll()
                    .antMatchers("**")
                    .permitAll()
                    .antMatchers("/v2/api-docs",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**")
                    .permitAll()
                    .antMatchers("**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProvider,USER_AUTH_URL))
                    .addFilter(new JWTAuthorizationUserFilter(authenticationManager(), jwtProvider));
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider1());
        }

        @Bean()
        DaoAuthenticationProvider authenticationProvider1() {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            daoAuthenticationProvider.setUserDetailsService(this.userDetailService);
            return daoAuthenticationProvider;
        }

        @Bean("passwordEncoder1")
        PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }

    }


    @Configuration
    @RequiredArgsConstructor
    @Order(2)
    public static class ProviderSecurityConfiguration extends WebSecurityConfigurerAdapter {
        private final ProviderDetailService providerDetailService;
        private final JwtProvider jwtProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/provider/**")
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers(PROVIDER_REGISTER_URL)
                    .permitAll()
                    .antMatchers("**")
                    .permitAll()
                    .antMatchers("/v2/api-docs",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**")
                    .permitAll()
                    .antMatchers("**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProvider,PROVIDER_AUTH_URL))
                    .addFilter(new JWTAuthorizationProviderFilter(authenticationManager(), jwtProvider));
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider2());
        }

        @Bean()
        DaoAuthenticationProvider authenticationProvider2() {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            daoAuthenticationProvider.setUserDetailsService(this.providerDetailService);
            return daoAuthenticationProvider;
        }

        @Bean("passwordEncoder2")
        PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }
    }
}
