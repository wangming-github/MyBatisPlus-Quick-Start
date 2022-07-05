package com.maizi.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.demo.entity.User;
import com.maizi.demo.mapper.UserMapper;
import com.maizi.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author maizi
 */
@DS("master") //指定所操作的数据
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
