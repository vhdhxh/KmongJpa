package com.talentmarket.KmongJpa.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.MDC;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.*;
import java.nio.charset.Charset;

@Slf4j
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

//    ContentCachingRequestWrapper cachingRequest;
//    ContentCachingResponseWrapper cachingResponse;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//       String method = request.getMethod();
//       String uri = request.getRequestURI();
//       String queryString = request.getQueryString();
//       String header = request.getHeader("Authorization");
//
//         cachingRequest = new ContentCachingRequestWrapper(request);
//         cachingResponse = new ContentCachingResponseWrapper(response);
//
////        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
////        StringBuilder stringBuilder = new StringBuilder();
////        String line;
////        while ((line = reader.readLine()) != null) {
////            stringBuilder.append(line);
////        }
////        String requestBody = stringBuilder.toString();
//
//        log.info( "request: requestURI: {} {} QueryString: {} Authorization: {} Body: {} Handler: {}",method , uri ,queryString , header ,"requestBody",handler );
//
//        return true;
//    }
//
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String header = request.getHeader("Authorization");


        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        System.out.println(request.getClass());
        HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper)request;
        ContentCachingRequestWrapper cachingRequest2 = (ContentCachingRequestWrapper) httpServletRequestWrapper;
        System.out.println(cachingRequest2.getClass());
        SessionRepositoryFilter sessionRepositoryFilter;

        String requestBody = stringBuilder.toString();
        ContentCachingRequestWrapper cachingRequest1 = (ContentCachingRequestWrapper) request;
        ContentCachingResponseWrapper cachingResponse1 = (ContentCachingResponseWrapper) response;

//        ((ContentCachingRequestWrapper) request).getContentAsString();
//
////         응답 처리 로직 (실제로는 여기에서 응답을 작성합니다)
////         ...
//
//        // 응답 본문을 캐싱합니다.
//        cachingResponse.copyBodyToResponse();
//
//        // 캐싱된 응답 본문을 가져옵니다.
//        String responseBody = new String(cachingResponse.getContentAsByteArray(), cachingResponse.getCharacterEncoding());

        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(response);
        String body = StreamUtils.copyToString(cachingRequest.getInputStream(), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        // 이제 캐싱된 본문을 가져올 수 있음
        String cachedBody = cachingRequest.getContentAsString();
        System.out.println(new String(cachingRequest.getContentAsByteArray()));
        response.getStatus();
        System.out.println("===========interceptor==========");
        MDC.put("handler",String.valueOf(handler));
        log.info(HTTP_LOG_FORMAT,method , uri ,queryString , header ,new String(cachingRequest1.getContentAsByteArray()),handler,response.getStatus(),objectMapper.readTree(cachingResponse1.getContentAsByteArray()));
    }
}
