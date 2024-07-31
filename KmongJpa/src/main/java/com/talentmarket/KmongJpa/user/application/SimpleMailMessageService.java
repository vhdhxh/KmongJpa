package com.talentmarket.KmongJpa.user.application;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailMessageService {

    private final MailSender mailSender;

    public SimpleMailMessageService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String emailAddress) {

        SimpleMailMessage msg = new SimpleMailMessage();
        // 받는 사람 이메일
        msg.setTo(emailAddress);
        // 이메일 제목
        msg.setSubject("회원가입 테스트 이메일 입니다.");
        // 이메일 내용
        msg.setText("테스트 내용입니다.");

        try {
            // 메일 보내기
            this.mailSender.send(msg);
            System.out.println("이메일 전송 성공!");
        } catch (MailException e) {
            throw e;
        }
    }
}