package com.chocoh.ql.security.hanlder;

import com.chocoh.ql.common.constant.Constants;
import com.chocoh.ql.common.constant.HttpStatus;
import com.chocoh.ql.common.utils.ServletUtil;
import com.chocoh.ql.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chocoh
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String token = request.getHeader(Constants.HEADER_TOKEN);
        tokenService.clearToken(token);
        ServletUtil.outputResponse(HttpStatus.SUCCESS, "退出登录成功");
    }
}
