package com.chocoh.ql.security.hanlder;

import com.chocoh.ql.common.constant.HttpStatus;
import com.chocoh.ql.common.utils.ServletUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chocoh
 * @description 身份验证失败处理器
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String MSG = "未授权";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
            ServletUtil.outputResponse(HttpStatus.UNAUTHORIZED, MSG);
    }
}
