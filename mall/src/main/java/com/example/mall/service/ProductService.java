package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.domain.entity.Product;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
public interface ProductService extends IService<Product> {
    ResponseResult getProductList(String name ,Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseResult getProduct(Integer id);

    ResponseResult insertProduct(Product banner);

    ResponseResult updateProduct(Product banner);

    ResponseResult deleteProduct(List<Integer> ids);
}
