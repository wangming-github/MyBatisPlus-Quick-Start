package com.maizi.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizi.demo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 注解 @Repository 将类或者接口标示为持久层组件
 * BaseMapper<T> T:操作类的实体类类型
 *
 * @author maizi
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


}