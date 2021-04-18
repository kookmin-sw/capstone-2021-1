package com.kookmin.pm.support.config;

import com.kookmin.pm.module.member.domain.MemberRole;
import com.kookmin.pm.support.util.JwtTokenProvider;
import com.kookmin.pm.web.filter.CustomResponseFilter;
import com.kookmin.pm.web.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    private final CustomResponseFilter customResponseFilter;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(customResponseFilter, SecurityContextPersistenceFilter.class);
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.httpBasic().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //초기 세팅
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/**/signin", "/**/signin/**", "/**/signup", "/social/**").permitAll()
                .antMatchers("/**/all").permitAll()
                .mvcMatchers(HttpMethod.GET, "/**/search").permitAll()
                .mvcMatchers(HttpMethod.GET, "/crew/detail/*").permitAll()
                .mvcMatchers(HttpMethod.GET, "/matching/detail/*").permitAll()
                .mvcMatchers(HttpMethod.GET, "/league/detail/*").permitAll()
                .mvcMatchers(HttpMethod.GET, "/league/*/match-up").permitAll()
                .anyRequest().hasRole(MemberRole.USER.toString());
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
