package com.talentmarket.KmongJpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import com.talentmarket.KmongJpa.auth.BCryptPasswordEncoder;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableRetry
@ServletComponentScan
public class KmongJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmongJpaApplication.class, args);
    }


//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder () {
//        return new BCryptPasswordEncoder();
//    }

}
