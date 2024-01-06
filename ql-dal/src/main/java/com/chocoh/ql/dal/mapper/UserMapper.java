package com.chocoh.ql.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chocoh.ql.dal.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chocoh
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
