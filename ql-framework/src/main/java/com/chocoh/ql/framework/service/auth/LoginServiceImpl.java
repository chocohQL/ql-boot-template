package com.chocoh.ql.framework.service.auth;

import com.chocoh.ql.common.exception.GlobalException;
import com.chocoh.ql.common.exception.user.PasswordErrorException;
import com.chocoh.ql.common.exception.user.UsernameErrorException;
import com.chocoh.ql.common.pojo.model.LoginUser;
import com.chocoh.ql.framework.service.LoginService;
import com.chocoh.ql.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author chocoh
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Override
    public String login(String username, String password) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            return tokenService.createAndSaveToken(loginUser);
        } catch (PasswordErrorException | UsernameErrorException e) {
            throw new GlobalException(e.getDefaultMessage());
        }
    }
}
