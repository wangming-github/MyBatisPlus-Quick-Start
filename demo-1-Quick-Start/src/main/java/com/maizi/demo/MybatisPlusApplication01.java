package com.maizi.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 注解 @MapperScan(" mapper 接口所在的包")
 *
 * @author maizi
 */
@SpringBootApplication
@MapperScan("com.maizi.demo.mapper")
public class MybatisPlusApplication01 {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication01.class, args);
    }
}