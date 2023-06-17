package com.sailyang.powerprophet.service;

import com.sailyang.powerprophet.pojo.User;

import java.util.List;

public interface UserService {
    Boolean save(User user);
    Boolean update(User user);
    Boolean delete(Integer id);
    User getById(Integer id);
    User getByUserName(String userName);
    User getByEmail(String email);
    List<User> getAll();
    List<String> getUserNames();
}
