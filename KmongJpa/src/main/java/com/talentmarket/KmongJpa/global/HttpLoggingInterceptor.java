package com.talentmarket.KmongJpa.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.MDC;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.*;
import java.nio.charset.Charset;

@Slf4j
@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

    private static final String HTTP_LOG_FORMAT = """
                                    
            request:
                requestURI: {} {}
                QueryString: {}
                Authorization: {}
                Body: {}
                Handler: {}
            ================
            response:
                statusCode: {}
                Body: {}
                    """;


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        MDC.put("handler", String.valueOf(handler));
    }
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
//        String header = request.getHeader("Authorization");
//
//        ContentCachingRequestWrapper cachingRequest1 = (ContentCachingRequestWrapper) request;
//        ContentCachingResponseWrapper cachingResponse1 = (ContentCachingResponseWrapper) response;
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        response.getStatus();
//        System.out.println("===========interceptor==========");
//
//        if(request.getContentType()!="application/json"){
//            log.info(HTTP_LOG_FORMAT,method , uri ,queryString , header ,new String(cachingRequest1.getContentAsByteArray()),handler,response.getStatus(),"http");
//          return;
//        }
//        log.info(HTTP_LOG_FORMAT,method , uri ,queryString , header ,new String(cachingRequest1.getContentAsByteArray()),handler,response.getStatus(),objectMapper.readTree(cachingResponse1.getContentAsByteArray()));
//    }
}
