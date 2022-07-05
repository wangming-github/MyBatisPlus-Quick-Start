package com.maizi.demo.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maizi.demo.entity.User;
import com.maizi.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


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
    public void test01() {
        //查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息
        // SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE
        // is_deleted = 0 AND(username LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "a").between("age", 20, 30).isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test02() {
        //按年龄降序查询用户，如果年龄相同则按id升序排列
        //SELECT id,username AS name,age,email,is_deleted FROM t_user
        // WHERE is_deleted=0 ORDER BY age DESC,id ASC
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test03() {
        //删除email为空的用户
        //DELETE FROM t_user WHERE (email IS NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        //条件构造器也可以构建删除语句的条件
        int result = userMapper.delete(queryWrapper);
        System.out.println("受影响的行数:" + result);
    }

    @Test
    public void test04() {
        //将(年龄大于20并且用户名中包含有a)或邮箱为null的用户信息修改
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //UPDATE t_user SET age=?, email=? WHERE (username LIKE ? AND age > ? OR email IS NULL)
        queryWrapper.like("user_name", "a").gt("age", 20).or().isNull("email");
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("受影响的行数:" + result);
    }


    @Test
    public void test04_1() {
        //将用户名中包含有a并且(年龄大于20或邮箱为null)的用户信息修改
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // UPDATE t_user SET age=?, email=? WHERE (username LIKE ? AND (age > ? OR email IS NULL))
        // lambda表达式内的逻辑优先运算
        queryWrapper.like("user_name", "a").and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("受影响的行数:" + result);
    }


    @Test
    public void test05() {
        //查询用户信息的username和age字段
        //SELECT username,age FROM t_user
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name", "age");
        //selectMaps()返回Map集合列表，通常配合select()使用，避免User对象中没有被查询到的列值为null
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    @Test
    public void test06() {
        //查询id小于等于3的用户信息
        //SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (id IN (select id from t_user where id <= 3))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from t_user where id <= 3");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test07() {
        //将(年龄大于20或邮箱为null)并且用户名中包含有a的用户信息修改 组装set子句以及修改条件 lambda表达式内的逻辑优先运算
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("age", 18).set("email", "user@atguigu.com").like("user_name", "a").and(i -> i.gt("age", 20).or().isNull("email"));
        //这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
        // UPDATE t2_user SET age=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        //User user = new User(); //user.setName("张三");
        //int result = userMapper.update(user, updateWrapper);
        //UPDATE t_user SET age=?,email=? WHERE (username LIKE ? AND (age > ? OR  email IS NULL))
        int result = userMapper.update(null, updateWrapper);
        System.out.println(result);
    }

    @Test
    public void test08() {
        //定义查询条件，有可能为null(用户未输入或未选择)
        //SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (age >=? AND age <= ?)
        String username = null;
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        //StringUtils.isNotBlank()判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("user_name", "a");
        }
        if (ageBegin != null) {
            queryWrapper.ge("age", ageBegin);
        }
        if (ageEnd != null) {
            queryWrapper.le("age", ageEnd);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void test08UseCondition() {
        //定义查询条件，有可能为null(用户未输入或未选择)
        String username = null;
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        //StringUtils.isNotBlank()判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), "user_name", "a").ge(ageBegin != null, "age", ageBegin).le(ageEnd != null, "age", ageEnd);
        //SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (age >= ? AND age <= ?)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test09() {
        //定义查询条件，有可能为null(用户未输入)
        //SELECT id AS uid,user_name AS name,age,email,is_deleted FROM t2_user WHERE is_deleted=0 AND (user_name LIKE ? AND age >= ? AND age <= ?)
        String username = "a";
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        //避免使用字符串表示字段，防止运行时错误
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), User::getName, username).ge(ageBegin != null, User::getAge, ageBegin).le(ageEnd != null, User::getAge, ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test10() {
        //组装set子句
        // UPDATE t2_user SET age=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age < ? OR email IS NULL))
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        //lambda 表达式内的逻辑优先运算
        updateWrapper
                .set(User::getAge, 18)
                .set(User::getEmail, "user@atguigu.com")
                .like(User::getName, "a")
                .and(i -> i.lt(User::getAge, 24)
                        .or()
                        .isNull(User::getEmail)
                );
        User user = new User();
        int result = userMapper.update(user, updateWrapper);
        System.out.println("受影响的行数:" + result);
    }
}