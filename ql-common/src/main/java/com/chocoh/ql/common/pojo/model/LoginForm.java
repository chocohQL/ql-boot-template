package com.chocoh.ql.common.pojo.model;

import lombok.Data;

/**
 * @author chocoh
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String captcha;
}
