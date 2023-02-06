package com.between.springboot.between.service;

import com.between.springboot.between.entity.Product;

public interface ProductService {

   public Product getProduct(long id);

   public Object[] silimiarIdsList(long id);

}
