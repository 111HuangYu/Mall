package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Category;
import com.example.mall.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/list")
    public ResponseResult getCategoryList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){
        return categoryService.getCategoryList(pageNum,pageSize);
    }
    @GetMapping("/admin/{id}")
    public ResponseResult getCategory (@PathVariable Integer id){
        return categoryService.getCategory (id);
    }

    @PostMapping("/admin/insert")
    public ResponseResult insertCategory (@RequestBody Category  category ){
        return categoryService.insertCategory (category );
    }

    @PostMapping("/admin/update")
    public ResponseResult updateCategory (@RequestBody Category  category ){
        return categoryService.updateCategory (category );
    }

    @PostMapping("/admin/delete")
    public ResponseResult deleteCategory (@RequestBody List<Integer> ids){
        return categoryService.deleteCategory (ids);
    }

}
