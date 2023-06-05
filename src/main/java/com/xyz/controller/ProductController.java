package com.xyz.controller;

import com.xyz.entity.Product;
import com.xyz.repo.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {

    @Autowired
    private ProductDao productDao;


    @PostMapping("/create")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        Product response = productDao.save(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.ok(productDao.findAll());
    }

    @DeleteMapping("/delete/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public ResponseEntity<String> deleteProductById(@PathVariable ( name = "id") long id){
        String response=productDao.deleteProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getProduct/{id}")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 10")
    public Product findProductById(@PathVariable long id){
        return productDao.findById(id);
    }

}
