package com.maizi.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.demo.entity.Product;
import com.maizi.demo.mapper.ProductMapper;
import com.maizi.demo.service.ProductService;
import org.springframework.stereotype.Service;

@DS("slave_1")
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}