package com.maizi.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maizi
 */
@SpringBootApplication
@MapperScan("com.maizi.demo.mapper")
public class MybatisPlusApplication03 {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication03.class, args);
    }
}