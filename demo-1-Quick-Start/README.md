### 1.入门案例

#### 1.建库 添加数据

```sql
CREATE DATABASE `mybatisplus-quick-start` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
use `mybatisplus-quick-start`;
CREATE TABLE `user`
(
    `id`    bigint(20) NOT NULL COMMENT '主键ID',
    `name`  varchar(30) DEFAULT NULL COMMENT '姓名',
    `age`   int(11)     DEFAULT NULL COMMENT '年龄',
    `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO user (id, name, age, email)
VALUES (1, 'Jone', 18, 'test1@baomidou.com'),
       (2, 'Jack', 20, 'test2@baomidou.com'),
       (3, 'Tom', 28, 'test3@baomidou.com'),
       (4, 'Sandy', 21, 'test4@baomidou.com'),
       (5, 'Billie', 24, 'test5@baomidou.com');
```

#### 2.添加依赖

```xml
<dependencies>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

#### 3.配置application.yml

```yaml
spring:
  # 配置数据源信息
  datasource:
    # 配置数据源类型
    type: com.zaxxer.hikari.HikariDataSource
    # 配置连接数据库信息
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mybatisplus-quick-start?characterEncoding=utf-8&useSSL=false
    username: root
    password: admin123
```

**注意:**

##### 1、驱动类driver-class-name

spring boot 2.0(内置jdbc5驱动)，驱动类使用:driver-class-name: com.mysql.jdbc.Driver

spring boot 2.1及以上(内置**jdbc8**驱动)，驱动类使用:driver-class-name: com.mysql.**cj**.jdbc.Driver

否则运行测试用例的时候会有 WARN 信息

##### 2、连接地址url

MySQL5.7版本的url:

jdbc:mysql://localhost:3306/mybatisplus-quick-start?characterEncoding=utf-8&useSSL=false

**MySQL8.0**版本的url:

jdbc:mysql://localhost:3306/mybatisplus-quick-start? **serverTimezone=GMT%2B8**&characterEncoding=utf-8&useSSL=false

> 否则运行测试用例报告如下错误:
>
> java.sql.SQLException: The server time zone value 'ÖÐ1ú±ê×1⁄4Ê±1⁄4ä' is unrecognized or represents more



#### 4.启动类

在Spring Boot启动类中添加@MapperScan注解，扫描mapper包

```java
@SpringBootApplication
@MapperScan("com.maizi.demo.mapper")
public class MybatisPlusApplication01 {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication01.class, args);
    }
}
```

#### 5.添加实体

```java

/**
 * @author maizi
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

#### 6.添加mapper

BaseMapper是MyBatis-Plus提供的模板mapper，其中包含了基本的CRUD方法，泛型为操作的实体类型

```java

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizi.demo.entity.User;

/**
 * @author maizi
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
```

> IDEA在 userMapper 处报错，因为找不到注入的对象，因为类是动态创建的，但是程序可以正确 的执行。
>
> 为了避免报错，可以在mapper接口上添加 **@Repository** 注解

#### 7.测试

```java
package com.maizi.demo.mapper;

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
}
```

![image-20220702234724726](https://tva1.sinaimg.cn/large/e6c9d24egy1h3t0lcetjjj21vy08i40q.jpg)

#### 8.yaml配置日志输出

```yam
# 配置日志输出
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

![image-20220702234811405](https://tva1.sinaimg.cn/large/e6c9d24egy1h3t0m495daj21yu0nqdlm.jpg)

### 