package com.talentmarket.KmongJpa.global.config;

import com.talentmarket.KmongJpa.auth.util.AuthPrincipalArgumentResolver;
import com.talentmarket.KmongJpa.auth.util.AuthenticationHandlerInterceptor;
//import com.talentmarket.KmongJpa.global.HttpLoggingInterceptor;
import com.talentmarket.KmongJpa.global.HttpLoggingInterceptor;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
   private final UserRepository userRepository;
   private String potoAdd = "file:C:\\Users\\윤민수\\Downloads\\kmongimage\\";
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthPrincipalArgumentResolver(userRepository));
    }

    public void addInterceptors(InterceptorRegistry registry) {
registry.addInterceptor(new AuthenticationHandlerInterceptor())
        .addPathPatterns("/test2") //로그인 체크 인터셉터를 포함할 url
        .excludePathPatterns();//포함하지 않을 url

        registry.addInterceptor(new HttpLoggingInterceptor())
                .addPathPatterns("/*");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Custom-Header")
                .allowCredentials(true)
                .maxAge(3600);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/user/**")
                .addResourceLocations(potoAdd);
    }





}
