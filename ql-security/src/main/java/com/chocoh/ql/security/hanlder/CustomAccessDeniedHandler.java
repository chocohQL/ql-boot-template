package com.chocoh.ql.security.hanlder;

import com.chocoh.ql.common.constant.HttpStatus;
import com.chocoh.ql.common.utils.ServletUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chocoh
 * @description 权限不足处理器
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final String MSG = "权限不足";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException{
        ServletUtil.outputResponse(HttpStatus.FORBIDDEN, MSG);
    }
}
