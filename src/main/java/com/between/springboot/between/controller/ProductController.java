package com.between.springboot.between.controller;

import com.between.springboot.between.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import com.between.springboot.between.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    public Product getProducts(@PathVariable("id") long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/product/{id}/similarids")
    public List<Product> getSimilarIds(@PathVariable("id") long id) {
        List<Product>  listProductSimilariIds = new ArrayList<>();
        try {

            Object[] silimiarIdsList = productService.silimiarIdsList(id);

            for (Object idSimilarId : silimiarIdsList) {
                Product product = getProducts(idSimilarId.hashCode());
                listProductSimilariIds.add(product);
                listProductSimilariIds.removeAll(Collections.singletonList(null));
            }
            return listProductSimilariIds;

        } catch (final HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
            return listProductSimilariIds;
        }
    }
}
