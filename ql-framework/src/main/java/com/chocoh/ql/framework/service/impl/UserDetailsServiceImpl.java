package com.chocoh.ql.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chocoh.ql.common.exception.user.UsernameErrorException;
import com.chocoh.ql.common.pojo.entity.User;
import com.chocoh.ql.common.pojo.model.LoginUser;
import com.chocoh.ql.framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Collections;

/**
 * @author chocoh
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null) {
            throw new UsernameErrorException();
        }
        return new LoginUser(user, Collections.singletonList(user.getRole()), user.getId().toString());
    }
}
