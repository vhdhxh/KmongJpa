package com.talentmarket.KmongJpa.global.config;

import com.talentmarket.KmongJpa.global.HttpLoggingFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<HttpLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<HttpLoggingFilter>();
        filterRegistrationBean.setFilter(new HttpLoggingFilter()); // 여기서 만든 필터 클래스  등록
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }







    }

