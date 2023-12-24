package com.chocoh.ql.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chocoh.ql.common.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chocoh
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
