package com.devStack.utill;

import org.mindrot.BCrypt;

public class PasswordConfig {
    public String encrypt(String text){
        return BCrypt.hashpw(text,BCrypt.gensalt(10));
    }

    public boolean decrypt(String rawPwd, String encryptPed){
        return BCrypt.checkpw(rawPwd,encryptPed);
    }
}
