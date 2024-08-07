package com.chocoh.ql.common.exception.user;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.common.exception.BusinessException;

/**
 * @author chocoh
 */
public class PasswordErrorException extends BusinessException {
    public PasswordErrorException() {
        super(ResultCodeEnum.PASSWORD_ERROR);
    }
}
