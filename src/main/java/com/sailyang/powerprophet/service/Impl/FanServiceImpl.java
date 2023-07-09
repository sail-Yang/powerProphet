package com.sailyang.powerprophet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailyang.powerprophet.dao.FanDao;
import com.sailyang.powerprophet.pojo.Fan;
import com.sailyang.powerprophet.pojo.FanAndOutliers;
import com.sailyang.powerprophet.pojo.FanLogItem;
import com.sailyang.powerprophet.service.FanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanServiceImpl extends ServiceImpl<FanDao, Fan> implements FanService {
    @Autowired
    FanDao fanDao;

    @Override
    public Integer getFans() {
        return fanDao.selectFans();
    }

    @Override
    public List<Fan> getAllFans() {
        QueryWrapper<Fan> query = new QueryWrapper<>();
        return fanDao.selectList(query);
    }

    @Override
    public List<FanAndOutliers> getFanAndOutliers(Integer userId) {
        return fanDao.selectFansAndOutliers(userId);
    }

    @Override
    public Boolean updateFanUser(Integer fanId, Integer userId) {
        return fanDao.updateUserId(fanId, userId) > 0;
    }

}
