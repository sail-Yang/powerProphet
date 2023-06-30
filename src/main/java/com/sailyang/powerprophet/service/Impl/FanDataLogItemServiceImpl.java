package com.sailyang.powerprophet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailyang.powerprophet.dao.FanDataLogItemDao;
import com.sailyang.powerprophet.dao.OutlierDao;
import com.sailyang.powerprophet.dao.UserDao;
import com.sailyang.powerprophet.pojo.FanDataLogItem;
import com.sailyang.powerprophet.pojo.Outlier;
import com.sailyang.powerprophet.pojo.PreResult;
import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.FanDataLogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanDataLogItemServiceImpl extends ServiceImpl<FanDataLogItemDao, FanDataLogItem> implements FanDataLogItemService {
    @Autowired
    private FanDataLogItemDao fanDataLogItemDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private OutlierDao outlierDao;

    @Override
    public List<FanDataLogItem> getByUserId(Integer userId) {
        return fanDataLogItemDao.selectItemsByUserId(userId);
    }


    @Override
    public boolean addLogItem(FanDataLogItem logItem) {
        return this.save(logItem);
    }

    @Override
    public boolean deleteByUserId(Integer userId) {
        return fanDataLogItemDao.deleteByUserId(userId) >= 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            outlierDao.deleteByLogId(id);
        }catch (Exception ignored) {

        }
        return fanDataLogItemDao.deleteById(id) > 0;
    }

    @Override
    public boolean saveLogItem(FanDataLogItem logItem){
        return fanDataLogItemDao.addLogItem(logItem) > 0;
    }

    @Override
    public IPage<FanDataLogItem> getPage(Page<FanDataLogItem> page, LambdaQueryWrapper<FanDataLogItem> logItemLambdaQueryWrapper) {
        return fanDataLogItemDao.selectPage(page, logItemLambdaQueryWrapper);
    }

    @Override
    public Integer getLogNums(Integer userId) {
        return fanDataLogItemDao.selectLogNums(userId);
    }
}
