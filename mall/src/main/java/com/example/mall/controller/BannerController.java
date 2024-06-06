package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.Banner;
import com.example.mall.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @GetMapping("/list")
    public ResponseResult gerBannerList(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "5") Integer pageSize){
        return bannerService.getBannerList(pageNum,pageSize);
    }
    @GetMapping("/admin/{id}")
    public ResponseResult getBanner(@PathVariable Integer id){
        return bannerService.getBanner(id);
    }
    @PostMapping("/admin/insert")
    public ResponseResult insertBanner(@RequestBody Banner banner){
        return bannerService.insertBanner(banner);
    }
    @PostMapping("/admin/update")
    public ResponseResult updateBanner(@RequestBody Banner banner){
        return bannerService.updateBanner(banner);
    }
    @PostMapping("/admin/delete")
    public ResponseResult deleteBanner(@RequestBody List<Integer> ids){
        return bannerService.deleteBanner(ids);
    }
}
