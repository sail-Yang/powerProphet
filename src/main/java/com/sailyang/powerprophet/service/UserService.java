package com.sailyang.powerprophet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailyang.powerprophet.pojo.User;

import java.util.List;

public interface UserService {
    Boolean save(User user);
    Boolean update(User user);
    Boolean delete(Integer id);
    User getById(Integer id);
    User getByUserName(String userName);
    List<User> getAll();
    IPage<User> getPage(int currentPage, int pageSize);
    List<String> getUserNames();
}
