package com.gdyzy.rind.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdyzy.rind.entity.User;
import com.gdyzy.rind.mapper.UserMapper;
import com.gdyzy.rind.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
