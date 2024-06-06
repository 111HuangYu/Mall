package com.example.mall.controller;

import com.example.mall.domain.ResponseResult;
import com.example.mall.domain.entity.User;
import com.example.mall.domain.vo.UserVo;
import com.example.mall.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WH
 * @since 2023-06-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(String phone, String password, String verifyCode, HttpSession session) {
        if (phone.isEmpty() || password.isEmpty()) {
            return ResponseResult.failResult("手机号或密码不能为空");
        }
        if (verifyCode.isEmpty()) {
            return ResponseResult.failResult("验证码不能为空");
        }
        //11位手机号
        if (!phone.matches("^\\d{11}$")) {
            return ResponseResult.failResult("请输入正确的手机号");
        }
        String captchaCode = session.getAttribute("verifyCode").toString();
        if (!verifyCode.toLowerCase().equals(captchaCode)) {
            return ResponseResult.failResult("验证码错误");
        }
        User user = userService.login(phone, password);
        if (user != null) {
            //用户登录后，往session里保存用户Id，用来后续判断用户是否已登录
            session.setAttribute("userId", user.getId());
            //使用UserVo重新封装user
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            return ResponseResult.okResult(userVo);
        } else {
            return ResponseResult.failResult("登录失败");
        }
    }
    @PostMapping("/reg")
    public ResponseResult register(String phone, String password,String password2, String verifyCode,HttpSession session){
        if (phone.isEmpty() || password.isEmpty()) {
            return ResponseResult.failResult("手机号或密码不能为空");
        }
        if (verifyCode.isEmpty()) {
            return ResponseResult.failResult("验证码不能为空");
        }
        //11位手机号
        if (!phone.matches("^\\d{11}$")) {
            return ResponseResult.failResult("请输入正确的手机号");
        }
        if (!password.equals(password2) ){
            return ResponseResult.failResult("确认密码不一致");
        }
        String captchaCode = session.getAttribute("verifyCode").toString();
        if (!verifyCode.toLowerCase().equals(captchaCode)) {
            return ResponseResult.failResult("验证码错误");
        }
        if(userService.userExit(phone)){
            return ResponseResult.failResult("手机号已注册");
        }
        int result =  userService.register(phone,password);
        if(result == 1 ){
            return ResponseResult.okResult();
        }
        else {
            return ResponseResult.failResult("注册失败");
        }
    }
    @GetMapping("/logout")
    public ResponseResult logout(HttpSession session){
        session.removeAttribute("userId");
        return ResponseResult.okResult();
    }
    @GetMapping("/isLogin")
    public ResponseResult isLogin(HttpSession session){
        Object userId = session.getAttribute("userId");
        if(userId == null){
            return ResponseResult.failResult("未登录");
        }else {
            return this.getUser(Integer.valueOf(userId.toString()));
        }
     }
    @GetMapping("/admin/list")
    public ResponseResult getUserList(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "5") Integer pageSize){
        return userService.getUserList(pageNum,pageSize);
    }
    @GetMapping("/admin/{id}")
    public ResponseResult getUser(@PathVariable("id") Integer id){
        return userService.getUser(id);
    }
    @PostMapping("/admin/update")
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @PostMapping("/admin/delete")
    public ResponseResult deleteUser(@RequestBody List<Integer> ids){
        return userService.deleteUser(ids);
    }
}

