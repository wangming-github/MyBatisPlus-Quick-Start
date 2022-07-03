package com.maizi.demo.mapper;


import com.maizi.demo.entity.User;
import com.maizi.demo.mapper.UserMapper;
import com.maizi.demo.service.UserService;
import org.junit.jupiter.api.Test;
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
    public void testInsert() {
        //@AllArgsConstructor
        User user = new User(null, "张三", 23, "zhangsan@atguigu.com");
        //INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        int result = userMapper.insert(user);
        System.out.println("受影响行数:" + result);
        //1475754982694199298
        System.out.println("id自动获取:" + user.getId());
    }

    @Test
    public void testDelete() {

        //通过id删除用户信息
        //DELETE FROM user WHERE id=?
        int result = userMapper.deleteById(1492767055210991617L);
        System.out.println("result:" + result);

        //根据map集合中所设置的条件删除用户信息
        //DELETE FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 23);
        int result1 = userMapper.deleteByMap(map);
        System.out.println("result:" + result1);

        //通过多个id实现批量删除
        //DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> list = Arrays.asList(1L, 2L, 3L);
        int result2 = userMapper.deleteBatchIds(list);
        System.out.println("result:" + result2);
    }

    @Test
    public void testUpdate() {
        //修改用户信息
        //UPDATE user SET name=?, email=? WHERE id=?
        User user = new User();
        user.setId(4L);
        user.setName("李四");
        user.setEmail("lisi@atguigu.com");
        int result = userMapper.updateById(user);
        System.out.println("result:" + result);
    }

    @Test
    public void testSelect() {
        //通过id查询用户信息
        //SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(1L);
        System.out.println(user);

        //根据多个id查询多个用户信息
        //SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<Long> list = Arrays.asList(1L, 2L, 3L);
        List<User> users = userMapper.selectBatchIds(list);
        users.forEach(System.out::println);

        //根据map集合中的条件查询用户信息
        //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 20);
        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);

        //查询所有数据
        //SELECT id,name,age,email FROM user
        List<User> users2 = userMapper.selectList(null);
        users2.forEach(System.out::println);
    }


    @Test
    public void myTestSelect() {
        //查询所有数据
        //SELECT id,name,age,email FROM user
        Map<String, Object> map = userMapper.selectMapById(1543274868257062914L);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
        }
    }


    @Autowired
    private UserService userService;

    @Test
    public void testServiceCount() {
        //查询所有数据
        //SELECT id,name,age,email FROM user
        long count = userService.count();
        System.out.println(count);
    }


    @Test
    public void testSaveBatch() {

        // SQL长度有限制，海量数据插入单条SQL无法实行，
        // 因此MP将批量插入放在了通用Service中实现，而不是通用Mapper
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("ybc" + i);
            user.setAge(20 + i);
            users.add(user);
        }
        //SQL:INSERT INTO t_user ( username, age ) VALUES ( ?, ? )
        userService.saveBatch(users);
    }

}