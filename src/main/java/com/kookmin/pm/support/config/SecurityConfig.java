package com.kookmin.pm.support.config;

import com.kookmin.pm.support.util.JwtTokenProvider;
import com.kookmin.pm.web.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();

        //jwt 인증 필터 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        //restful 이므로 필요없음, 인증,인가는 jwt 방식으로 대체할 예정
        http.csrf().disable();

        //마찬가지로 restful이므로 stateless하게 감
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //초기 세팅
        http.authorizeRequests()
                .anyRequest().permitAll();

        //TODO::AWS에 배포하기전에 CORS 세팅 추가해놔야함
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
