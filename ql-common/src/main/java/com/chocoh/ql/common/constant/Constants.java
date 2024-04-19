package com.chocoh.ql.common.constant;

/**
 * @author chocoh
 */
public class Constants {
    /**
     * redis:TTL
     */
    public static final Integer LOGIN_USER_TTL = 1000 * 60 * 30;
    public static final Integer CACHE_NULL_TTL = 2;
    public static final Integer REFRESH_TOKEN_TTL = LOGIN_USER_TTL / 2;
    /**
     * redis:KEY
     */
    public static final String REDIS_KEY_PREFIX = "ql:";
    public static final String LOGIN_USER_KEY = REDIS_KEY_PREFIX + "login:";
    public static final String LIMIT_BUCKET_KEY = REDIS_KEY_PREFIX + "limit:";
    /**
     * constant
     */
    public static final String EMAIL_SUBJECT = "【QL】邮箱验证";
    public static final String EMAIL_RESET_PASS = "【QL】重置密码";
    public static final String HEADER_TOKEN = "Authorization";
    /**
     * pattern
     */
    public static final String PATTERN_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
}
