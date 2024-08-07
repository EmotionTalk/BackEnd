package com.tutorial.backend.config;

import com.tutorial.backend.entity.type.Authority;
import com.tutorial.backend.handler.MyAuthenticationSuccessHandler;
import com.tutorial.backend.jwt.JwtAccessDeniedHandler;
import com.tutorial.backend.jwt.JwtAuthenticationEntryPoint;
import com.tutorial.backend.jwt.TokenProvider;
import com.tutorial.backend.service.MemberDetailService;
import com.tutorial.backend.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String AUTH_PATH = "/auth/**";
    private static final String OAUTH_PATH = "/oauth/**";
    private static final String MEMBER_PATH = "/member/**";
    private static final String ADMIN_PATH = "/admin/**";
    private static final String WEBSOCKET_PATH = "/ws/**";
    private static final String WEBSOCKET_PUB_PATH = "/pub/**";
    private static final String WEBSOCKET_SUB_PATH ="/sub/**";
    private static final String FILE_PATH = "/file/**";
    private static final String FILES_PATH = "/files/**";

    private final OAuthService oAuthService;
    private final MemberDetailService memberDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 설정 Disable
        http
                .userDetailsService(memberDetailService)
                .cors().and() // WebMvcConfigurer에서 설정한 CORS를 사용
                .csrf().disable()

                // Exception Handling 설정
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않도록 Stateless 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, 회원가입 API는 토큰 없이 접근 가능하도록 permitAll 설정
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_PATH).permitAll()
                .antMatchers(OAUTH_PATH).permitAll()
                .antMatchers(WEBSOCKET_PATH).permitAll()
                .antMatchers(FILE_PATH).permitAll()
                .antMatchers(FILES_PATH).permitAll()
                .antMatchers(MEMBER_PATH).hasRole(Authority.USER.name())
                .antMatchers(ADMIN_PATH).hasRole(Authority.ADMIN.name())
                .anyRequest().authenticated() // 나머지 API는 인증 필요

                // JWT 필터 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                // OAuth2 로그인 설정
                .and()
                .oauth2Login()
                .successHandler(new MyAuthenticationSuccessHandler())
                .userInfoEndpoint()
                .userService(oAuthService);

        return http.build();
    }
}
