package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BannerMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.domain.entity.User;
import com.example.mall.domain.vo.BannerVo;
import com.example.mall.domain.vo.UserVo;
import com.example.mall.service.BannerService;
import com.example.mall.util.BeanCopyUtils;
import com.example.mall.util.MD5Util;
import org.springframework.beans.BeanUtils;
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Override
    public ResponseResult getBannerList(Integer pageNum, Integer pageSize) {

        Page<Banner> page = new Page<>(pageNum,pageSize);
        List<Banner> banners = bannerMapper.selectPage(page, null).getRecords();
        List<BannerVo> bannerVos = BeanCopyUtils.copyBeanList(banners, BannerVo.class);
        return ResponseResult.okResult(bannerVos);

    }

    @Override
    public ResponseResult getBanner(Integer id) {
        Banner banner = bannerMapper.selectById(id);
        if(banner == null){
            return ResponseResult.failResult();
        }else {
            BannerVo bannerVo = BeanCopyUtils.copyBean(banner, BannerVo.class);
            return ResponseResult.okResult(bannerVo);
        }
    }

    @Override
    public ResponseResult insertBanner(Banner banner) {
        int res = bannerMapper.insert(banner);
        if (res == 1){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult updateBanner(Banner banner) {
        int res = bannerMapper.updateById(banner);
        if (res == 1){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult deleteBanner(List<Integer> ids) {
        int res = bannerMapper.deleteBatchIds(ids);
        if (res > 0 ){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.failResult();
        }
    }
}
