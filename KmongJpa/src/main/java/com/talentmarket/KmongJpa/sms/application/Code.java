package com.talentmarket.KmongJpa.sms.application;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
public class Code {
    @Setter
    private Long id;
    private String code;

    public Code () {
        this.id = id;
        this.code = createSmsKey();
    }

    public boolean sameId(Long id) {
        return this.id==id;
    }

    private String createSmsKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 5; i++) { // 인증코드 5자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

}
