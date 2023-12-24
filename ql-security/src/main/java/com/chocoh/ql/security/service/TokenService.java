package com.chocoh.ql.security.service;

import com.alibaba.fastjson.JSON;
import com.chocoh.ql.common.pojo.model.LoginUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.chocoh.ql.common.constant.Constants.*;


/**
 * @author chocoh
 */
@Component
public class TokenService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String SECURITY_KEY = "chocoh";

    /**
     * 获取token
     * @param loginUser 登录用户
     * @return token
     */
    public String createAndSaveToken(LoginUser loginUser) {
        refresh(loginUser);
        return createJwt(loginUser.getId());
    }

    /**
     * 校验token
     * @param token token
     * @return 登录用户
     */
    public LoginUser verifyAndRefreshToken(String token) {
        if (token != null) {
            LoginUser loginUser = getLoginUser(token);
            if (loginUser != null) {
                refresh(loginUser);
            }
            return loginUser;
        } else {
            return null;
        }
    }

    /**
     * 刷新状态
     * @param loginUser 登录用户
     */
    public void refresh(LoginUser loginUser) {
        redisTemplate.opsForValue().set(
                LOGIN_USER_KEY + loginUser.getId(), JSON.toJSONString(loginUser),
                LOGIN_USER_TTL, TimeUnit.MINUTES);
    }

    /**
     * 清除token
     */
    public void clearToken(String token) {
        LoginUser loginUser = getLoginUser(token);
        if (loginUser != null) {
            redisTemplate.delete(LOGIN_USER_KEY + loginUser.getId());
        }
    }

    /**
     * 获取登录用户
     * @param token token
     * @return 登录用户
     */
    public LoginUser getLoginUser(String token) {
        String key = LOGIN_USER_KEY + parseJwt(token.replace("Bearer ", ""));
        return JSON.parseObject(redisTemplate.opsForValue().get(key), LoginUser.class);
    }

    private String createJwt(String subject) {
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, generalKey())
                .compact();
    }

    private String parseJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    private SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECURITY_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
