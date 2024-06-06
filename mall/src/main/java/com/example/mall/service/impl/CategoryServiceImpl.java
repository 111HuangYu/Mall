package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.CategoryMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Category ;
import com.example.mall.domain.vo.CategoryVo;
import com.example.mall.service.CategoryService;
import com.example.mall.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList(Integer pageNum, Integer pageSize) {
        Page<Category > categoryPage= new Page<>(pageNum,pageSize);
        List<Category > categorys = categoryMapper.selectPage(categoryPage, null).getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categorys, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getCategory (Integer id) {
        Category  category  = categoryMapper.selectById(id);
        if ( category  != null){
            CategoryVo categoryVo = BeanCopyUtils.copyBean(category , CategoryVo.class);
            return ResponseResult.okResult(categoryVo);
        }
        else {
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult insertCategory (Category category ) {
        int res = categoryMapper.insert(category );
        if (res == 1){
            return ResponseResult.okResult();
        }
        else {
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult updateCategory (Category category ) {
        int res = categoryMapper.updateById(category );
        if (res == 1){
            return ResponseResult.okResult();
        }
        else {
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult deleteCategory (List<Integer> ids) {
        int res = categoryMapper.deleteBatchIds(ids);
        if (res == ids.size()){
            return ResponseResult.okResult("删除成功");
        }
        else {
            return ResponseResult.okResult("删除失败");
        }
    }
}
