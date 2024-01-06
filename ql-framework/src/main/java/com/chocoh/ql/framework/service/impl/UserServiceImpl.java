package com.chocoh.ql.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chocoh.ql.dal.domain.entity.User;
import com.chocoh.ql.dal.mapper.UserMapper;
import com.chocoh.ql.framework.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author chocoh
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
