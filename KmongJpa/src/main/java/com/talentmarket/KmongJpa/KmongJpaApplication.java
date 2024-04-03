package com.talentmarket.KmongJpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import com.talentmarket.KmongJpa.auth.BCryptPasswordEncoder;

@SpringBootApplication
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
