package com.maizi.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.maizi.demo.enums.SexEnum;
import lombok.Data;

/**
 * @author maizi
 */
@Data
public class User {
    @TableId("id")
    private Long uid;
    @TableField("user_name")
    private String name;
    private SexEnum sex;
    private Integer age;
    private String email;
    @TableLogic
    private Integer isDeleted;

    @Override
    public String toString() {
        return "User{" + "uid=" + uid + ", name='" + name + '\'' + ", sex=" + sex.getSexName() + ", age=" + age + ", email='" + email + '\'' + ", isDeleted=" + isDeleted + '}';
    }
}
