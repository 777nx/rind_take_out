package com.gdyzy.rind.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdyzy.rind.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
