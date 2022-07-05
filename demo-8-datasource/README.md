### **多数据源**

> 适用于多种场景:纯粹多库、 读写分离、 一主多从、 混合模式等 
>
> 目前我们就来模拟一个纯粹多库的一个场景，其他场景类似 
>
> 场景说明:
> 我们创建两个库，分别为:mybatis_plus(以前的库不动)与mybatis_plus_1(新建)，将 mybatis_plus库的product表移动到mybatis_plus_1库，这样每个库一张表，通过一个测试用例 分别获取用户数据与商品数据，如果获取到说明多库模拟成功

#### 1、创建数据库及表

创建数据库mybatis_plus_1和表product

```sql
CREATE DATABASE `mybatisplus-quick-start_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
use `mybatisplus-quick-start_1`;
CREATE TABLE product
(
    id      BIGINT(20)  NOT NULL COMMENT '主键ID',
    name    VARCHAR(30) NULL DEFAULT NULL COMMENT '商品名称',
    price   INT(11)          DEFAULT 0 COMMENT '价格',
    version INT(11)          DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (id)
);
```

添加测试数据

```sql
INSERT INTO product (id, NAME, price) VALUES (1, '外星人笔记本', 100);
```

删除mybatis_plus库product表

```sql
use mybatis_plus;
DROP TABLE IF EXISTS product;
```

#### **2、引入依赖**

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
    <version>3.5.0</version>
</dependency>
```

#### 3、配置多数据源

```yaml
mybatis-plus:
  configuration:
    # 配置MyBatis日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:com/maizi/demo/mapper/xml/*.xml
  global-config:
    db-config:
      #配置MyBatis-Plus操作表的默认前缀
      table-prefix: t2_
      #使用数据库的自增策略，注意，该类型请确保数据库设置了id自增否则无效
      id-type: auto
  #配置类型别名所对应的包
  type-aliases-package: com.maizi.demo.pojo

spring:
  datasource:
    dynamic:
      # 设置默认的数据源或者数据源组,默认值即为master
      primary: master
      # 严格匹配数据源,默认false.true未匹配到指定数据源时抛异常,false使用默认数据源
      strict: false
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/mybatisplus-quick-start?characterEncoding=utf-8&useSSL=false
          username: root
          password: admin123
          driver-class-name: com.mysql.cj.jdbc.Driver
        slave_1:
          url: jdbc:mysql://localhost:3306/mybatisplus-quick-start_1?characterEncoding=utf-8&useSSL=false
          username: root
          password: admin123
          driver-class-name: com.mysql.cj.jdbc.Driver


```

4.在service或者方法中使用注解指定数据源

![image-20220705185244065](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w8xl5fbtj21ka05saav.jpg)

![image-20220705185301873](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w8xvtk7jj21qk06s0tl.jpg)