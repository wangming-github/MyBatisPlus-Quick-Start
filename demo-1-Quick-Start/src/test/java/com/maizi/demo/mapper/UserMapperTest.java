package com.maizi.demo.mapper;


import com.maizi.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
    public void testInsert() {
        User user = new User(null, "张三", 23, "zhangsan@atguigu.com");
        //INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        int result = userMapper.insert(user);
        System.out.println("受影响行数:" + result);
        //1475754982694199298
        System.out.println("id自动获取:" + user.getId());

    }


    @Test
    public void testInsert1() {
        //实现新增用户信息
        //INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        User user = new User();
        //user.setId(100L);
        user.setName("张三");
        user.setAge(23);
        user.setEmail("zhangsan@atguigu.com");
        int result = userMapper.insert(user);
        System.out.println("result:" + result);
        System.out.println("id:" + user.getId());
    }

}
