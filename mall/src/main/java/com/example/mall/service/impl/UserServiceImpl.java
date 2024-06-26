package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.UserMapper;
import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.User;
import com.example.mall.domain.vo.UserVo;
import com.example.mall.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper   userMapper;

    @Override
    public User login(String phone, String password) {
        String passwordMD5 = MD5Util.MD5Encode(password,"UTF-8");
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("phone",phone)
//                .eq("password",passwordMD5 );
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone)
                .eq(User::getPassword,passwordMD5);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public boolean userExit(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        boolean result = userMapper.exists(queryWrapper);
        return result;
    }

    @Override
    public int register(String phone, String password) {
        User user = new User();
        user.setPhone(phone);
        user.setPassword(MD5Util.MD5Encode(password, "UTF-8"));
        user.setNickName("用户" + phone);
        return userMapper.insert(user);
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum,pageSize);
        List<User> users = userMapper.selectPage(page, null).getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
        return ResponseResult.okResult(userVos);
    }

    @Override
    public ResponseResult getUser(Integer id) {
        User user = userMapper.selectById(id);
        if(user == null){
            return ResponseResult.failResult();
        }else {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            return ResponseResult.okResult(userVo);
        }
    }

    @Override
    public ResponseResult updateUser(User user) {
        int result = userMapper.updateById(user);
        if(result == 1){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.failResult();
        }
    }

    @Override
    public ResponseResult deleteUser(List<Integer> ids) {
        int result = userMapper.deleteBatchIds(ids);
        if(result > 0){
            return ResponseResult.okResult();
        }else {
            return ResponseResult.failResult();
        }
    }
}
