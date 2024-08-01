package com.chocoh.ql.common.enums;

import com.chocoh.ql.common.constant.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chocoh
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(HttpStatus.SUCCESS, "操作成功"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "参数列表错误"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "未授权"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "权限不足"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "资源未找到"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "不允许的http方法"),
    ERROR(HttpStatus.ERROR, "系统内部错误"),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "接口未实现"),

    NOT_TOKEN(1001, "未能读取到有效 token"),
    INVALID_TOKEN(1002, "token 无效"),
    TOKEN_TIMEOUT(1003, "token 已过期"),
    NO_PREFIX(1004, "未按照指定前缀提交 token"),
    NO_LOGIN(1005, "当前会话未登录"),

    USERNAME_NOT_FOUNT(1101, "用户名不存在"),
    USERNAME_EXIST(1102, "用户名已被使用"),
    PASSWORD_ERROR(1103, "密码错误"),

    MAX_UPLOAD_SIZE_EXCEEDED(2011, "文件过大");

    private final int code;
    private final String msg;
}
