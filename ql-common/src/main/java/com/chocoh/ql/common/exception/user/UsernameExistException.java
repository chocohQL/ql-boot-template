package com.chocoh.ql.common.exception.user;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.common.exception.BusinessException;

/**
 * @author chocoh
 */
public class UsernameExistException extends BusinessException {
    public UsernameExistException() {
        super(ResultCodeEnum.USERNAME_EXIST);
    }
}
