package com.talentmarket.KmongJpa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.    @EnableWebSecurity 애너테이션은 Spring Security와 함께 웹 애플리케이션에서 보안 구성을 활성화하기 위해 사용된다.
public class SecurityConfig  {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c ->c.disable());
        // 인가(접근권한) 설정
        http.authorizeRequests()
                .anyRequest().permitAll()  //이외의 url은 허가한다
                .and()
                .formLogin(f-> f.loginPage("/loginForm")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/").permitAll())
                .logout(l->l.logoutUrl("/logout").logoutSuccessUrl("/logoutsucsess")
                        .deleteCookies("SESSION"))


                ;


                                    //Tip. 구글로그인 완료후 (엑세스토큰+ 사용자프로필정보 를 받는다)


        return http.build();
    }


//    @Bean  //해당메서드의 리턴되는 오브젝트를 빈으로 등록해준다.
//    public BCryptPasswordEncoder encodePwd() { //회원가입 비밀번호 암호화를 위해 빈으로 등록
//        return new BCryptPasswordEncoder();
//    } 순환참조 에러가 발생하여 kmongAplication 으로 옮김
}