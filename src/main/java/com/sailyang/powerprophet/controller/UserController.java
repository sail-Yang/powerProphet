package com.sailyang.powerprophet.controller;


import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/power/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public R getAll(){
        List<User> userList = userService.getAll();
        return new R(userList == null ? -1 : 200,"get user list success",userList);
    }

    @PostMapping
    public R save(@RequestBody User user){
        Boolean flag = userService.save(user);
        return new R(flag ? 200 : -1,"save user success",null);
    }

    @PutMapping
    public R update(@RequestBody User user){
        Boolean flag = userService.update(user);
        return new R(flag ? 200 : -1,"update user success",null);
    }

    @DeleteMapping("")
    public R delete(@RequestParam(value = "id") Integer id){
        Boolean flag = userService.delete(id);
        return new R(flag ? 200 : -1,"delete user success",null);
    }

    @GetMapping(value = "/getById")
    public R getById(@RequestParam(value = "id") Integer id){
        User user = userService.getById(id);
        return new R(user != null ? 200 : -1,"get user success",user);
    }

}
