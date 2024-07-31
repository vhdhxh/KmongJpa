package com.talentmarket.KmongJpa.user.application;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MailEventListener {
    private final MailSender mailSender;

    public MailEventListener(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMail(MailEvent mailEvent) {
        SimpleMailMessage msg = new SimpleMailMessage();
        // 받는 사람 이메일
        msg.setTo(mailEvent.getEmail());
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

