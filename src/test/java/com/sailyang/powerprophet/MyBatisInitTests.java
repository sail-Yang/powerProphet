package com.sailyang.powerprophet;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sailyang.powerprophet.dao.UserDao;
import com.sailyang.powerprophet.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisInitTests {
    @Autowired
    private UserDao userDao;

    @Test
    void getAllUsers(){
        List<User> users = userDao.selectList(null);
        System.out.println(users);
    }

    @Test
    void inserUser(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        int result1 = userDao.insert(user);
        System.out.println("return result ，1代表成功:"+result1);
    }

    @Test
    void getPage(){
        IPage page = new Page(1,5);
        userDao.selectPage(page,null);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        System.out.println(page.getRecords());
    }
}
