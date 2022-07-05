package com.maizi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.maizi.demo.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author maizi
 * @description 针对表【t2_user】的数据库操作Mapper
 * @createDate 2022-07-05 18:20:05
 * @Entity com.maizi.demo.pojo.User
 */
public interface UserMapper extends BaseMapper<User> {


    List<User> selectAllByEmail(@Param("email") String email);

    public int insertAll(User user);
}




