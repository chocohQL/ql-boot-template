package com.chocoh.ql.common.exception.user;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.common.exception.BusinessException;

/**
 * @author chocoh
 */
public class UsernameNotFountException extends BusinessException {
    public UsernameNotFountException() {
        super(ResultCodeEnum.USERNAME_NOT_FOUNT);
    }
}
