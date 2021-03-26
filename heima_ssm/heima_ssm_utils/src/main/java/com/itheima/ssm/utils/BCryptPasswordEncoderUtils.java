package com.itheima.ssm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean matches(String raw, String encode){
        return bCryptPasswordEncoder.matches(raw,encode);
    }
    public static void main(String[] args) {
        System.out.println(encodePassword("123"));
    }
}