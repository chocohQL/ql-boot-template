package com.chocoh.ql.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chocoh.ql.common.exception.user.UsernameNotFountException;
import com.chocoh.ql.dal.domain.dto.LoginUser;
import com.chocoh.ql.dal.domain.entity.User;
import com.chocoh.ql.dal.mapper.UserMapper;
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
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null) {
            throw new UsernameNotFountException();
        }
        return new LoginUser(user, Collections.singletonList(user.getRole()), user.getId().toString());
    }
}
