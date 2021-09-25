package com.sprinkler.kwakkwak.config;

import lombok.RequiredArgsConstructor;
import com.sprinkler.kwakkwak.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // http 관련 인증 설정

        http
                .authorizeRequests() // 접근 인증 설정
                 .antMatchers("/login", "/signup", "/user").permitAll() // 누구나 접근 허용
                 .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
                 .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                 .anyRequest().authenticated() // 나머지 요청은 권한이 있어야 접근 가능
                .and()
                 .formLogin() // 로그인 설정
                 .loginPage("/login") // 로그인 페이지
                 .defaultSuccessUrl("/") // 로그인 성공 후 주소
                .and()
                 .logout() // 로그아웃 설정
                 .logoutSuccessUrl("/login") // 로그아웃 성공 후 주소
                 .invalidateHttpSession(true) // 세션 초기화
    }
    
    @Override
    public void configure(WebSecurity web) { // static 하위 파일 목록(css, js, img) 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/h2-console/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 정보 가져오기
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

