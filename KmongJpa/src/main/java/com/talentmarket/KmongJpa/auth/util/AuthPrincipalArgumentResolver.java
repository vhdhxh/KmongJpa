package com.talentmarket.KmongJpa.auth.util;

import com.talentmarket.KmongJpa.auth.UserDto;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return  parameter.hasParameterAnnotation(AuthPrincipal.class); //AuthPrincipal 클래스로 등록한 애너테이션을 사용하면 true
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
       HttpSession httpSession = httpServletRequest.getSession();
       UserDto userDto = (UserDto) httpSession.getAttribute("user");
      Users user = userRepository // user 엔티티말고 userId만 반환하는것도 고려해야봐야겠다.
              .findByEmail(userDto.userEmail()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        System.out.println("resolveArgument");

return user;

    }
}
