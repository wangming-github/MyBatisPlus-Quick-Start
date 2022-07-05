### 1、分页插件

> MyBatis Plus自带分页插件，只要简单的配置即可实现分页功能

#### 1.添加配置类

![image-20220705020046888](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vfomx9rxj21l40gctbg.jpg)

测试

![image-20220705020111344](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vfp2xc4yj21y80gitc1.jpg)

输出

![image-20220705020213559](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vfq5tfirj222x0u0q8n.jpg)

### 2、xml自定义分页

#### 1.UserMapper中定义接口方法

![image-20220705023657448](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vgqak9gwj21h80bu40d.jpg)

![image-20220705024206645](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vgvnxyzmj21hu084dh3.jpg)

![image-20220705024136676](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vgvrkidrj21q60fg418.jpg)

![image-20220705024402989](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vgxnx5cij21vs0gkn0m.jpg)



### 3、乐观锁

 新增一个版本号字段 每次修改进行完成进行+1 多人异步同时修改时就会报错 然后重试

##### 场景

一件商品，成本价是80元，售价是100元。老板先是通知小李，说你去把商品价格增加50元。小 李正在玩游戏，耽搁了一个小时。正好一个小时后，老板觉得商品价格增加到150元，价格太 高，可能会影响销量。又通知小王，你把商品价格降低30元。
此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格100元;小王 也在操作，取出的商品价格也是100元。小李将价格加了50元，并将100+50=150元存入了数据 库;小王将商品减了30元，并将100-30=70元存入了数据库。是的，如果没有锁，小李的操作就 完全被小王的覆盖了。
现在商品价格是70元，比成本价低10元。几分钟后，这个商品很快出售了1千多件商品，老板亏1 万多。

乐观锁与悲观锁
上面的故事，如果是乐观锁，小王保存价格前，会检查下价格是否被人修改过了。如果被修改过 了，则重新取出的被修改后的价格，150元，这样他会将120元存入数据库。
如果是悲观锁，小李取出数据后，小王只能等小李操作完之后，才能对价格进行操作，也会保证 最终的价格是120元。

#### 模拟修改冲突

##### 数据库中增加商品表

```sql
CREATE TABLE t2_product
(
    id      BIGINT(20)  NOT NULL COMMENT '主键ID',
    NAME    VARCHAR(30) NULL DEFAULT NULL COMMENT '商品名称',
    price   INT(11)          DEFAULT 0 COMMENT '价格',
    VERSION INT(11)          DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (id)
);
```

##### 添加数据

```sql
INSERT INTO t_product (id, NAME, price) VALUES (1, '外星人笔记本', 100);
```

##### 添加实体

```java
import lombok.Data;
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer version;
}
```

##### 添加mapper

```java
public interface ProductMapper extends BaseMapper<Product> {
}
```

##### **测试**

```java
  @Test
    public void testConcurrentUpdate() {
        //1、小李
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格:" + p1.getPrice());
        //2、小王
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格:" + p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果:" + result1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果:" + result2);
        //最后的结果
        Product p3 = productMapper.selectById(1L);
        //价格覆盖，最后的结果:70
        System.out.println("最后的结果:" + p3.getPrice());
    }
```

##### 乐观锁实现流程

数据库中添加version字段 取出记录时，获取当前version

```sql
SELECT id,`name`,price,`version` FROM product WHERE id=1
```

更新时，version + 1，如果where语句中的version版本不对，则更新失败

```sql
UPDATE product SET price=price+50, `version`=`version` + 1 WHERE id=1 AND `version`=1
```

#### Mybatis-Plus实现乐观锁 

##### 修改实体类

```java
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version
    private Integer version;
}
```

##### 添加乐观锁插件配置

![image-20220705134701387](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w03huk6dj21qe0d6q5k.jpg)

##### 优化流程

![image-20220705135422582](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w0b56shhj21z60tatgf.jpg)

---

