package com.maizi.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.demo.pojo.User;
import com.maizi.demo.service.UserService;
import com.maizi.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author maizi
 * @description 针对表【t2_user】的数据库操作Service实现
 * @createDate 2022-07-05 18:20:05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}




