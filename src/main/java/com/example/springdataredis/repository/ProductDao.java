package com.example.springdataredis.repository;

import com.example.springdataredis.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    public static final String HASH_KEY = "Product";

    @Autowired
    private RedisTemplate redisTemplate;

    //private final RedisTemplate<String, Object> redisTemplate;
    //private final HashOperations<String, Long, Product> hashOperations;

    public Product save(Product product) {
        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
        //hashOperations.put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> findAll(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Product findById(long id){
        return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProduct(long id){
        redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "Product Remove!!";
    }
}
