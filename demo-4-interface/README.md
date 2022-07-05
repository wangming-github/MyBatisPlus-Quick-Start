### **条件构造器和常用接口**

#### 1、wapper介绍

![image-20220705004238440](https://tva1.sinaimg.cn/large/e6c9d24egy1h3w91ef801j222y0h8n0v.jpg)

#### 2、QueryWrapper

##### 例1:组装查询条件

查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息

![image-20220705004926621](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vdmfcb51j21xq0dwwi0.jpg)

*SELECT id AS uid,user_name AS name,age,email,is_deleted FROM t2_user WHERE **is_deleted=0** AND (user_name **LIKE** ? AND age **BETWEEN** ? AND ? AND email **IS NOT NULL**)*

![image-20220705005019496](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vdnbxtanj20zm02ojrs.jpg)



##### 例2:组装排序条件

按年龄降序查询用户，如果年龄相同则按id升序排列

![image-20220705005408159](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vdrayowwj21ks0egtbb.jpg)

SELECT id AS uid,user_name AS name,age,email,is_deleted FROM t2_user WHERE **is_deleted=0 ORDER BY age DESC,id ASC**

![image-20220705005550738](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vdtm9ly1j21lu060wfx.jpg)



##### 例3:组装删除条件 ![image-20220705005941011](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vdx2vi47j21i60bkwg5.jpg)

UPDATE t2_user SET **is_deleted=1** WHERE **is_deleted=0 AND (email IS NULL)**



##### 例4-1:条件的优先级 

将**(年龄大于20并且用户名中包含有a)**或邮箱为null的用户信息修改

![image-20220705010421303](https://tva1.sinaimg.cn/large/e6c9d24egy1h3ve1xv3jcj21u60ii0w5.jpg)

```sql
UPDATE t2_user SET age=?, email=? WHERE is_deleted=0 AND  (user_name LIKE ? AND age > ? OR email IS NULL) 
```



##### 例4-2:条件的优先级 

将用户名中包含有a并且**(年龄大于20或邮箱为null)的用户信息修改)**

![image-20220705011025381](https://tva1.sinaimg.cn/large/e6c9d24egy1h3ve89k1g4j223y0maq70.jpg)

##### 例5:组装select子句

查询用户信息的username和age字段
![image-20220705011456762](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vecydzyxj21jw0bq0uu.jpg)

![image-20220705011517583](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vedbgjhij21cs06edga.jpg)



##### 例6:实现子查询：

查询id小于等于3的用户信息

![image-20220705011713908](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vefcuwssj21mk0bqdht.jpg)

```sql
SELECT id AS uid,user_name AS name,age,email,is_deleted FROM t2_user WHERE is_deleted=0 AND (id IN (select id from t_user where id <= 3))
```

![image-20220705011805358](https://tva1.sinaimg.cn/large/e6c9d24egy1h3veg7xkwpj21e803yaas.jpg)



#### 3、UpdateWrapper

![image-20220705013950029](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vf2wl2ggj22ay0omtdy.jpg)

```sql
UPDATE t2_user
SET age=?,
    email=?
WHERE is_deleted = 0
  AND (
            user_name LIKE ?
        AND (
                age > ? OR email IS NULL))
```

![image-20220705014237010](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vf5ttn7qj21q402omxu.jpg)

#### 4、condition

> 在真正开发的过程中，组装条件是常见的功能，而这些条件数据来源于用户输入，是可选的，因 此我们在组装这些条件时，必须先判断用户是否选择了这些条件，若选择则需要组装该条件，若没有选择则一定不能组装，以免影响SQL执行的结果

##### **思路一:**自定义逻辑

定义查询条件，有可能为null(用户未输入或未选择)
![image-20220705012650274](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vepbpc6uj21ys0hm0wo.jpg)

##### **思路二:**condition

上面的实现方案没有问题，但是代码比较复杂，我们可以使用带condition参数的重载方法构建查 询条件，简化代码的编写

![image-20220705012929112](https://tva1.sinaimg.cn/large/e6c9d24egy1h3ves43h0fj21z60iqgpi.jpg)

#### 5、LambdaQueryWrapper

![image-20220705014538434](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vf8wiruvj21yq0iwdjl.jpg)

#### 6、LambdaUpdateWrapper

![image-20220705014904862](https://tva1.sinaimg.cn/large/e6c9d24egy1h3vfchc0jij21xc0l077u.jpg)

```sql
UPDATE t2_user SET age=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age < ? OR email IS NULL))
```

