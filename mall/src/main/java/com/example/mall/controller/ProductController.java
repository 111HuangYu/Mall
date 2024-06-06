package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.domain.entity.Product;
import com.example.mall.service.BannerService;
import com.example.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/list")
    public ResponseResult gerProductList(@RequestParam(required = false) String name,@RequestParam(required = false) Integer categoryId,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        return productService.getProductList(name, categoryId, pageNum, pageSize);
    }
    @GetMapping("/admin/{id}")
    public ResponseResult getProduct(@PathVariable Integer id){
        return productService.getProduct(id);
    }
    @PostMapping("/admin/insert")
    public ResponseResult insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }
    @PostMapping("/admin/update")
    public ResponseResult updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }
    @PostMapping("/admin/delete")
    public ResponseResult deleteProduct(@RequestBody List<Integer> ids){
        return productService.deleteProduct(ids);
    }
}
