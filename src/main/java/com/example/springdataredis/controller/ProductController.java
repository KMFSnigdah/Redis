package com.example.springdataredis.controller;

import com.example.springdataredis.entity.Product;
import com.example.springdataredis.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {

    @Autowired
    private ProductDao dao;


    @PostMapping("/create")
    public Product save(@RequestBody Product product){
        return dao.save(product);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return dao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result.quantity > 11")
    public Product findProductById(@PathVariable long id){
        return dao.findById(id);
    }

    @DeleteMapping("/remove/{id}")
    @CacheEvict(key = "#id", value = "Product")
    public String remove(@PathVariable long id){
        return dao.deleteProduct(id);
    }
}
