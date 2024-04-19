package com.chocoh.ql.framework.controller;

import com.chocoh.ql.common.model.Response;
import com.chocoh.ql.dal.domain.dto.AuthForm;
import com.chocoh.ql.framework.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chocoh
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Response login(@RequestBody AuthForm authForm) {
        return Response.success(loginService.login(authForm.getUsername(), authForm.getPassword()));
    }
}