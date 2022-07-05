package com.maizi.demo.mapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizi.demo.enums.SexEnum;
import com.maizi.demo.pojo.Product;
import com.maizi.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class UserMapperTest {


    @Autowired
    private UserMapper userMapper;


    @Test
    public void testSelectList() {
        //selectList()根据MP内置的条件构造器查询一个list集合，null表示没有条件，即查询所有
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testSexEnum() {
        User user = new User();
        user.setName("Enum");
        //设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
        user.setAge(20);
        user.setSex(SexEnum.MALE);
        //INSERT INTO t_user ( username, age, sex ) VALUES ( ?, ?, ? )
        //Parameters: Enum(String), 20(Integer), 1(Integer)
        userMapper.insert(user);
    }


}