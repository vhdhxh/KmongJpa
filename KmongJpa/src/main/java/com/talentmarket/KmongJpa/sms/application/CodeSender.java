package com.talentmarket.KmongJpa.sms.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CodeSender {
    private final ListCodeRepository listCodeRepository;
    private final SmsService smsService;
    public void send() {

    }
    public Code saveCode() {
        Code code = new Code();
        listCodeRepository.addCode(code);
        return code;
    }
    public Code findCode(Long id) {
      return listCodeRepository.findById(id);
    }

}
