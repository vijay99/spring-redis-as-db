package com.xyz.repo;

import com.xyz.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {
    public static final String HASH_KEY = "Product";

    @Autowired
    @Qualifier(value = "myRedisTemplate")
    private RedisTemplate template;

    public Product save(Product product) {
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public Product findById(long id) {
        System.out.println("call findById() method from DB");
        return (Product) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProductById(long id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "Product removed";
    }

}
