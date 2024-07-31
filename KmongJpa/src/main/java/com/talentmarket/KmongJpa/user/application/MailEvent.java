package com.talentmarket.KmongJpa.user.application;

import lombok.Getter;

@Getter
public class MailEvent {
    private final String email;
    public MailEvent(String email) {
        this.email = email;
    }


}
