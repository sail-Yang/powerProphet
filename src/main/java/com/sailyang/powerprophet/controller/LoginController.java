package com.sailyang.powerprophet.controller;

import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.UserService;
import com.sailyang.powerprophet.utils.TokenUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sailyang.powerprophet.utils.TokenUtil.createJWT;

@RestController
@RequestMapping("/power/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody
    public R login(@RequestBody User reqUser, HttpSession session){
        String username = reqUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user = userService.getByUserName(username);
        String msg;
        if(user == null){
            msg = "该用户不存在";
            return new R(-1,msg,null);
        }else{
            if(user.getPassword().equals(reqUser.getPassword())) {
                Map<String,Object> responseData = new HashMap<>();
                session.setAttribute("user",user);
                String token = createJWT(user.getUsername(),user.getPassword());
                responseData.put("token",token);
                return new R(20000,"login successful",responseData);
            }else{
                return new R(-1,"密码错误，请重新输入",null);
            }
        }

    }

    @CrossOrigin
    @GetMapping(value = "/info")
    @ResponseBody
    public R getInfo(@RequestParam String token){
        HashMap<String, Object> responseData = new HashMap<>();
        User user = userService.getByUserName(TokenUtil.getUserNameFromToken(token));
        if(user == null){
            return new R(50008,"Login failed, unable to get user details.",responseData);
        }
        responseData.put("roles",user.getRole());
        responseData.put("name",user.getUsername());
        responseData.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return new R(20000,"登录成功",responseData);
    }

    @CrossOrigin
    @PostMapping(value = "/logout")
    @ResponseBody
    public R logout(){
        HashMap<String, Object> responseData = new HashMap<>();
        return new R(20000,"登出成功",responseData);
    }

    @CrossOrigin
    @GetMapping(value = "/getusernames")
    @ResponseBody
    public R getUserNames(){
        List<String> usernames = userService.getUserNames();
        return new R(20000,"获取用户名列表成功",usernames);
    }

    @CrossOrigin
    @PostMapping(value = "/signup")
    @ResponseBody
    public R signUp(@RequestBody User reqUser){
        String username = reqUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user = userService.getByUserName(username);
        if(user == null){
            boolean flag = userService.save(reqUser);
            if(flag){
                return new R(20000,"注册成功",null);
            }else {
                return new R(-1, "注册失败，NetWork Error", null);
            }
        }else{
            return new R(-1,"注册失败，该用户已存在",null);
        }
    }
}
