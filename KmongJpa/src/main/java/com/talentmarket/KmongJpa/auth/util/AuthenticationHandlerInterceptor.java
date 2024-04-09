package com.talentmarket.KmongJpa.auth.util;

import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.domain.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationHandlerInterceptor implements HandlerInterceptor {
    // 컨트롤러 실행 전에 preHandle 을 호출하고  preHandle() 이 false 를 반환한다면, 다음 HandlerInterceptor 혹은 컨트롤러를 실행하지 않는다.
    @Override


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("user")==null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }



        return true;
    }


}
