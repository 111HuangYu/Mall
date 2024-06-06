package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Cart;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
public interface CartService extends IService<Cart> {

    ResponseResult getCartList(String userId, Integer pageNum, Integer pageSize);

    ResponseResult getCart(Integer id);

    ResponseResult insertOrUpdate(Cart cart);

    ResponseResult updateCart(Cart cart);

    ResponseResult deleteCart(List<Integer> ids);
}
