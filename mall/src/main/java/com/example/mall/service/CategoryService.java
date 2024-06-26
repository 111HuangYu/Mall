package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Category;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList(Integer pageNum, Integer pageSize);

    ResponseResult getCategory(Integer id);

    ResponseResult insertCategory(Category category);

    ResponseResult updateCategory(Category category);

    ResponseResult deleteCategory (List<Integer> ids);
}
