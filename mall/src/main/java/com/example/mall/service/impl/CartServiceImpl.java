package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.CartMapper;
import com.example.mall.dao.ProductMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Cart;
import com.example.mall.domain.entity.Product;
import com.example.mall.domain.vo.CartVo;
import com.example.mall.service.CartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponseResult getCartList(String userId, Integer pageNum, Integer pageSize) {
        Page<Cart> cartPage= new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<Cart>().eq(Cart::getUserId, userId);
        List<Cart> carts = cartMapper.selectPage(cartPage, queryWrapper).getRecords();
        System.out.println(carts);
        List<CartVo> cartVos = BeanCopyUtils.copyBeanList(carts, CartVo.class);
        for (CartVo cartVo :cartVos){
            Product product = productMapper.selectById(1);
            cartVo.setProdName(product.getTitle());
            cartVo.setPrice(product.getPrice());
            cartVo.setImg(product.getImg());
        }
        return ResponseResult.okResult(cartVos);
    }

    @Override
    public ResponseResult getCart(Integer id) {
        Cart cart = cartMapper.selectById(id);
        if ( cart != null){
            CartVo cartVo = BeanCopyUtils.copyBean(cart, CartVo.class);
            cartVo.setProdName(productMapper.selectById(cartVo.getProdId()).getTitle());
            return ResponseResult.okResult(cartVo);
        }
        else {
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult insertOrUpdate(Cart cart) {
        if (cart.getUserId() == null || cart.getProdId() == null){
            return ResponseResult.failResult("商品id和用户id不能为空");
        }
        Cart cart1 = cartMapper.selectOne(new LambdaQueryWrapper<Cart>().eq(Cart::getUserId, cart.getUserId()).eq(Cart::getProdId, cart.getProdId()));
        if (cart1 == null){
            int res = cartMapper.insert(cart);
            if (res == 1){
                return ResponseResult.okResult("新增一条购物车记录");
            }
            else {
                return ResponseResult.okResult("新增失败");
            }
        }
        else {
            cart1.setNum(cart1.getNum()+cart.getNum());
            int res = cartMapper.updateById(cart1);
            if (res == 1){
                return ResponseResult.okResult("更新一条购物车记录");
            }
            else {
                return ResponseResult.okResult("更新失败");
            }
        }

    }

    @Override
    public ResponseResult updateCart(Cart cart) {
        if (cart.getId() == null){
            return ResponseResult.failResult("购物车id不能为空");
        }
        int res = cartMapper.updateById(cart);
        if (res == 1){
            return ResponseResult.okResult();
        }
        else {
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult deleteCart(List<Integer> ids) {
        int res = cartMapper.deleteBatchIds(ids);
        if (res == ids.size()){
            return ResponseResult.okResult("删除成功");
        }
        else {
            return ResponseResult.okResult("删除失败");
        }
    }
}
