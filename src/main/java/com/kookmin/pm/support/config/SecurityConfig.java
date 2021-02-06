package com.kookmin.pm.support.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();

        //restful 이므로 필요없음, 인증,인가는 jwt 방식으로 대체할 예정
        http.csrf().disable();

        //마찬가지로 restful이므로 stateless하게 감
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //초기 세팅
        http.authorizeRequests()
                .anyRequest().permitAll();

        //TODO::AWS에 배포하기전에 CORS 세팅 추가해놔야함
    }
}
