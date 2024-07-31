package com.talentmarket.KmongJpa.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.talentmarket.KmongJpa.Dto.UserResponse;
import com.talentmarket.KmongJpa.auth.UserDto;
import com.talentmarket.KmongJpa.sms.application.SmsMessageDto;
import com.talentmarket.KmongJpa.global.config.RedisConfig;
import com.talentmarket.KmongJpa.sms.application.SmsService;
import com.talentmarket.KmongJpa.global.exception.CustomException;
import com.talentmarket.KmongJpa.global.exception.ErrorCode;
import com.talentmarket.KmongJpa.user.application.dto.FindPasswordRequest;
import com.talentmarket.KmongJpa.user.application.dto.RegisterRequest;
import com.talentmarket.KmongJpa.user.domain.Users;
import com.talentmarket.KmongJpa.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.talentmarket.KmongJpa.auth.BCryptPasswordEncoder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SmsService smsService;
    private final RedisConfig redisConfig;
    private final SimpleMailMessageService simpleMailMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;
    //회원가입
@Transactional
    public Long Register(RegisterRequest request) {

    userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
        throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
    });
//        userRepository.findByEmail(request.getEmail());
        String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());
      Users user = Users.createUsers(request,encodePassword);
//    simpleMailMessageService.sendEmail(request.getEmail()); //메일 전송
    Long id = userRepository.save(user).getId();
    applicationEventPublisher.publishEvent(new MailEvent(user.getEmail()));
    trowException();
    return id;
    }
    public void trowException() {
    throw new CustomException(ErrorCode.CODE_NOT_MATCH);
    }

     //회원탈퇴
    @Transactional
    public void Withdrawal(Users users , HttpServletRequest httpServletRequest){
        Users.checkUserSession(users);

//        if(userRepository.findById(principalDetails.getDto().getId()).isEmpty()){
//            throw new CustomException(ErrorCode.USER_WITHDRAWLED);
//        };
        userRepository.delete(users);
       HttpSession httpSession = httpServletRequest.getSession();
       //세션 저장소에있는 세션데이터를 삭제
       httpSession.invalidate();
    }

    //회원 비밀번호찾기
    public void sendSms(FindPasswordRequest findPasswordRequest) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
    //아이디, 닉네임, 핸드폰 번호를 받아 모두 일치하면 문자를 보낸다

       Users user = userRepository.findByEmailAndNameAndPhone( findPasswordRequest.getEmail(),  findPasswordRequest.getName() , findPasswordRequest.getPhone()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        SmsMessageDto smsMessageDto = new SmsMessageDto(findPasswordRequest.getPhone());

       smsService.sendSms(smsMessageDto);


    }

    //인증번호를 받고 redis 에 저장된 인증번호와 비교후 맞다면 비밀번호를 변경한다.
    public void checkCode(String code) {
        String phone = redisConfig.getData(code);
        if(phone==null) {
            throw new CustomException(ErrorCode.CODE_NOT_MATCH);
        }
    }

    public UserResponse getUserInfo(UserDto reqUser) {
    Users user = userRepository.findByEmail(reqUser.userEmail()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
    UserResponse response = user.getResponse();
      return response;
    }
}



