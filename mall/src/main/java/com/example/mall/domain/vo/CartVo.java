package com.example.mall.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartVo {
    private Integer id;

    /**
     * 商品id
     */
    private Integer  prodId;

    private String  prodName;
    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品价格
     */
    private BigDecimal price;
    private String img;


}
