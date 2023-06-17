package com.sailyang.powerprophet.controller;

import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.pojo.SignUpInfo;
import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.UserService;
import com.sailyang.powerprophet.utils.TokenUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sailyang.powerprophet.utils.TokenUtils.createJWT;

@RestController
@RequestMapping("/power/user")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody
    public R login(@RequestBody User reqUser, HttpSession session){
        String username = reqUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user = userService.getByUserName(username);
        String msg;
        if(user == null){
            msg = "该用户不存在,请先注册";
            return new R(-1,msg,null);
        }else{
            if(user.getPassword().equals(reqUser.getPassword())) {
                Map<String,Object> responseData = new HashMap<>();
                session.setAttribute("user",user);
                String token = createJWT(user.getUsername(),user.getPassword());
                responseData.put("token",token);
                responseData.put("model", user.getModel());
                responseData.put("password",user.getPassword());
                responseData.put("email",user.getEmail());
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
        User user = userService.getByUserName(TokenUtils.getUserNameFromToken(token));
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
    public R signUp(@RequestBody SignUpInfo reqUser){
        String username = reqUser.getUsername();username = HtmlUtils.htmlEscape(username);
        String email = reqUser.getEmail();
        String code = reqUser.getCode();
        String password = reqUser.getPassword();
        if(username == null || email == null || code == null || password == null){
            return new R(-1, "注册失败，表单不全", null);
        }

        User user = userService.getByUserName(username);
        if(user != null){
            return new R(-1,"注册失败，该用户已存在",null);
        }
        user = userService.getByEmail(email);
        if(user != null){
            return new R(-1,"注册失败，该邮箱已存在",null);
        }
        String verCodeFromRedis = (String) redisTemplate.opsForValue().get(email);
        if(code.equals(verCodeFromRedis)){
            User newUser = new User(username,password,email);
            boolean flag = userService.save(newUser);
            if(flag){
                return new R(20000,"注册成功",null);
            }else {
                return new R(-1, "注册失败，数据库错误", null);
            }
        }else {
            return new R(-1, "注册失败，验证码错误", null);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/emaillogin")
    @ResponseBody
    public R emailLogin(@RequestParam(value = "email") String email, @RequestParam(value = "code") String code,  HttpSession session){
        User user = userService.getByEmail(email);
        String msg;
        if(user == null){
            msg = "该用户不存在,请先注册";
            return new R(-1,msg,null);
        }else{
            String verCodeFromRedis = (String) redisTemplate.opsForValue().get(user.getEmail());
            if(code.equals(verCodeFromRedis)){
                Map<String,Object> responseData = new HashMap<>();
                session.setAttribute("user",user);
                String token = createJWT(user.getUsername(),user.getPassword());
                responseData.put("token",token);
                responseData.put("model", user.getModel());
                responseData.put("password",user.getPassword());
                responseData.put("email",user.getEmail());
                return new R(20000,"登录成功",responseData);
            }else{
                return new R(-1,"验证码错误，请重新输入",null);
            }
        }

    }

    @CrossOrigin
    @PostMapping(value = "/update/account")
    @ResponseBody
    public R updateAccount(@RequestBody SignUpInfo reqUser, HttpSession session){
        String username = reqUser.getUsername();
        String password = reqUser.getPassword();
        String email = reqUser.getEmail();
        String emailCode = reqUser.getCode();
        String verCodeFromRedis = (String) redisTemplate.opsForValue().get(email);
        if(!emailCode.equals(verCodeFromRedis)){
            return new R(-1,"验证码错误，更新失败",null);
        }
        username = HtmlUtils.htmlEscape(username);
        User user = userService.getByUserName(username);
        if(user != null){
            user.setEmail(email);
            user.setPassword(password);
            boolean result;
            try {
                result = userService.update(user);
            } catch (Exception e) {
                return new R(-1,"更新失败，该用户名已存在",null);
            }

            if(result) {
                Map<String,Object> responseData = new HashMap<>();
                session.setAttribute("user",user);
                String token = createJWT(user.getUsername(),user.getPassword());
                responseData.put("token",token);
                return new R(20000,"update successful",responseData);
            }else{
                return new R(-1,"更新失败，数据库错误",null);
            }
        }else{
            user = userService.getByEmail(email);
            if(user != null){
                user.setUsername(username);
                user.setPassword(password);
                boolean result;
                try {
                    result = userService.update(user);
                } catch (Exception e) {
                    return new R(-1,"更新失败，该用邮箱已存在",null);
                }
                if(result) {
                    Map<String,Object> responseData = new HashMap<>();
                    String token = createJWT(user.getUsername(),user.getPassword());
                    session.setAttribute("user",user);
                    responseData.put("token",token);
                    return new R(20000,"update successful",responseData);
                }else{
                    return new R(-1,"更新失败，数据库错误",null);
                }
            }else{
                return new R(-1,"该用户不存在",null);
            }
        }

    }

    @CrossOrigin
    @PostMapping(value = "/update/model")
    public R updateModel(@RequestParam("username") String username,@RequestParam("model") String model, HttpSession session){
        User user = userService.getByUserName(HtmlUtils.htmlEscape(username));
        if( user == null){
            return new R(-1,"更新失败，该用户不存在",null);
        }
        user.setModel(model);
        session.setAttribute("user",user);
        boolean result = userService.update(user);
        if(result) {
            return new R(20000,"update successful",null);
        }else{
            return new R(-1,"更新失败，数据库错误",null);
        }
    }
}
