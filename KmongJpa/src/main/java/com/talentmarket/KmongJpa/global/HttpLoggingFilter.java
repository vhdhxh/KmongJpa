package com.talentmarket.KmongJpa.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Order(Integer.MIN_VALUE)
public class HttpLoggingFilter extends OncePerRequestFilter {
    private static final String HTTP_LOG_FORMAT = """
                                    
            request:
                requestURI: {} {}
                QueryString: {}
                Authorization: {}
                Body: {}
                handler: {}
            ================
            response:
                statusCode: {}
                Body: {}
            ================
                duration: {}
                    """;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest =  request;

        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        String queryString = httpRequest.getQueryString();
        String header = httpRequest.getHeader("Authorization");

        HttpServletResponse httpResponse = response;

        ObjectMapper objectMapper = new ObjectMapper();

        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(httpResponse);

        filterChain.doFilter(cachingRequest, cachingResponse);

        Long entTime = System.currentTimeMillis();
        String duration = String.valueOf(entTime - startTime) + "ms";
        System.out.println(cachingResponse.getContentType());
        if(cachingResponse.getContentType().equals("text/html;charset=UTF-8")){
            log.info(HTTP_LOG_FORMAT,method , uri ,queryString , header, new String(cachingRequest.getContentAsByteArray()),cachingResponse.getStatus(),"html",duration);
            cachingResponse.copyBodyToResponse();
            return;

        }
        log.info(HTTP_LOG_FORMAT,method , uri ,queryString , header, new String(cachingRequest.getContentAsByteArray()), MDC.get("handler"),cachingResponse.getStatus(),objectMapper.readTree(cachingResponse.getContentAsByteArray()),duration);
        cachingResponse.copyBodyToResponse();


    }



}
