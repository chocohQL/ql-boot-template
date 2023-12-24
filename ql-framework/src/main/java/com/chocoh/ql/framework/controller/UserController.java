package com.chocoh.ql.framework.controller;

import com.chocoh.ql.common.pojo.model.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chocoh
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/me")
    public Response me() {
        // TODO test
        return Response.success();
    }
}
