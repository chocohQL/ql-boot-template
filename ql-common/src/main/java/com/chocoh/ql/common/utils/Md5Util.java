package com.chocoh.ql.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author chocoh
 */
public class Md5Util {
    private static final String SALT = "0123456789chocoh";

    public static String encrypt(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            password = password + SALT;
            byte[] bytes = md5.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(hex);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}