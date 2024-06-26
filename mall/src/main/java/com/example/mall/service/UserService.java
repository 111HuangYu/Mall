package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
public interface UserService extends IService<User> {

    User login(String phone, String password);

    boolean userExit(String phone);

    int register(String phone, String password);

    ResponseResult getUserList(Integer pageNum, Integer pageSize);

    ResponseResult getUser(Integer id);

    ResponseResult updateUser(User user);

    ResponseResult deleteUser(List<Integer> ids);
}
