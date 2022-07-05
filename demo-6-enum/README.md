### 通用枚举

表中的有些字段值是固定的，例如性别(男或女)，此时我们可以使用MyBatis-Plus的通用枚举 来实现

#### 数据库表添加字段sex

```sql
ALTER TABLE t2_user
    ADD COLUMN sex INT(11) AFTER user_name;
```



#### 创建通用枚举类型

![image-20220705171416182](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w634hmahj22100f6760.jpg)

#### **配置扫描通用枚举**

![image-20220705171025939](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w5z57djgj21f405kmye.jpg)

#### POJO

![image-20220705171115740](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w6002doij220e0jwgo7.jpg)

#### 测试

![image-20220705171326591](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w629mih2j21xm0dg0ui.jpg)