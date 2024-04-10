package com.talentmarket.KmongJpa.auth;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder {

    public String encode(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }



    public boolean matches(final String password, final String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
