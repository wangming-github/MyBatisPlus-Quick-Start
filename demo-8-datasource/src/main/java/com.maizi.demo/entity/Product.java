package com.maizi.demo.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author maizi
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version  //标识乐观锁版本号字段
    private Integer version;
}