package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BannerMapper;
import com.example.mall.dao.CategoryMapper;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.domain.entity.Category;
import com.example.mall.domain.entity.Product;
import com.example.mall.domain.vo.BannerVo;
import com.example.mall.domain.vo.ProductVo;
import com.example.mall.service.ProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseResult getProductList(String name ,Integer categoryId,Integer pageNum, Integer pageSize) {

        Page<Product> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Product::getTitle,name).eq(categoryId!=null,Product::getCategoryId,categoryId);
        List<Product> products = productMapper.selectPage(page, queryWrapper).getRecords();
        List<ProductVo> productVos = BeanCopyUtils.copyBeanList(products, ProductVo.class);
        for (ProductVo productVo : productVos){
            productVo.setCategoryName(categoryMapper.selectById(productVo.getCategoryId()).getName());
        }
        return ResponseResult.okResult(productVos);

    }

    @Override
    public ResponseResult getProduct(Integer id) {
        Product product = productMapper.selectById(id);
        if(product == null){
            return ResponseResult.failResult();
        }else {
            ProductVo productVo = BeanCopyUtils.copyBean(product, ProductVo.class);
            productVo.setCategoryName(categoryMapper.selectById(productVo.getCategoryId()).getName());
            return ResponseResult.okResult(productVo);
        }
    }

    @Override
    public ResponseResult insertProduct(Product product) {
        if (product.getCode() == null){
            return ResponseResult.failResult("商品编码不能为空");
        }
        Product prod = productMapper.selectOne(new LambdaQueryWrapper<Product>().eq(Product::getCode, product.getCode()));
        if(prod == null){
            int res = productMapper.insert(product);
            if (res == 1){
                return ResponseResult.okResult();
            }else {
                return ResponseResult.failResult();
            }
        }else {
            return ResponseResult.failResult(501,"商品编码已经存在,新增失败");
        }

    }

    @Override
    public ResponseResult updateProduct(Product product) {
        int res = productMapper.updateById(product);
        if (res == 1){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult deleteProduct(List<Integer> ids) {
        int res = productMapper.deleteBatchIds(ids);
        if (res > 0 ){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.failResult();
        }
    }
}
