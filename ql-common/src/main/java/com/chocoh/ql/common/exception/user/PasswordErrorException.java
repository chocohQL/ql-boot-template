package com.chocoh.ql.common.exception.user;

import com.chocoh.ql.common.exception.BaseException;

/**
 * @author chocoh
 */
public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {
        super("密码错误");
    }
}
