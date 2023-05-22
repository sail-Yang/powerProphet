package com.sailyang.powerprophet.ServiceTests;

import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    void testGetById(){
        System.out.println(userService.getById(3));
    }

    @Test
    void testAdd(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        userService.save(user);
    }

    @Test
    void testUpdate(){

    }

    @Test
    void testDelete(){
        userService.delete(5);
    }

    @Test
    void testGetAll(){
        userService.getAll();
    }

}
