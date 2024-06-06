package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Cart;
import com.example.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping("/list")
    public ResponseResult getCartList(@RequestParam String userId,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        System.out.println("121321");
        System.out.println(userId);
        return cartService.getCartList(userId,pageNum,pageSize);
    }
    @GetMapping("/admin/{id}")
    public ResponseResult getCart(@PathVariable Integer id){

        return cartService.getCart(id);
    }

    @PostMapping("/admin/insertOrUpdate")
    public ResponseResult insertOrUpdate(@RequestBody Cart cart){
        return cartService.insertOrUpdate(cart);
    }

    @PostMapping("/admin/update")
    public ResponseResult updateCart(@RequestBody Cart cart){
        return cartService.updateCart(cart);
    }

    @PostMapping("/admin/delete")
    public ResponseResult deleteCart(@RequestBody List<Integer> ids){
        return cartService.deleteCart(ids);
    }
}
