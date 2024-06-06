package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
public interface BannerService extends IService<Banner> {

    ResponseResult getBannerList(Integer pageNum, Integer pageSize);

    ResponseResult getBanner(Integer id);

    ResponseResult insertBanner(Banner banner);

    ResponseResult updateBanner(Banner banner);

    ResponseResult deleteBanner(List<Integer> ids);
}
