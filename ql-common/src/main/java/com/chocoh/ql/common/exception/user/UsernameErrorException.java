package com.chocoh.ql.common.exception.user;

import com.chocoh.ql.common.exception.BaseException;

/**
 * @author chocoh
 */
public class UsernameErrorException extends BaseException {
    public UsernameErrorException() {
        super("用户名错误");
    }
}
