package com.sailyang.powerprophet.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sailyang.powerprophet.dao.UserDao;
import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Boolean save(User user){
        return userDao.insert(user) > 0;
    }

    @Override
    public Boolean update(User user){
        return userDao.updateById(user) > 0;
    }

    @Override
    public Boolean delete(Integer id){
        return userDao.deleteById(id) > 0;
    }

    @Override
    public User getById(Integer id){
        return userDao.selectById(id);
    }

    @Override
    public List<User> getAll(){
        return userDao.selectList(null);
    }

    @Override
    public User getByUserName(String userName){ return userDao.selectByUserName(userName);}

    @Override
    public List<String> getUserNames() { return userDao.selectUserNames(); }

    @Override
    public IPage<User> getPage(int currentPage,int pageSize){
        IPage page = new Page(currentPage,pageSize);
        userDao.selectPage(page,null);
        return page;
    }


}
