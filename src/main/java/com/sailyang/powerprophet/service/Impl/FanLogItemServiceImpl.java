package com.sailyang.powerprophet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailyang.powerprophet.dao.FanLogItemDao;
import com.sailyang.powerprophet.pojo.FanDataLogItem;
import com.sailyang.powerprophet.pojo.FanLogItem;
import com.sailyang.powerprophet.pojo.UserPair;
import com.sailyang.powerprophet.service.FanLogItemService;
import com.sailyang.powerprophet.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FanLogItemServiceImpl extends ServiceImpl<FanLogItemDao, FanLogItem> implements FanLogItemService {
    @Autowired
    private FanLogItemDao fanLogItemDao;

    @Override
    public boolean addLogItem(Integer fanId, Integer userId) {
        FanLogItem fanLogItem = new FanLogItem();
        fanLogItem.setFanId(fanId);fanLogItem.setUserId(userId);
        fanLogItem.setStatus("no");
        fanLogItem.setStartTime(TimeUtils.getNowTime());
        return fanLogItemDao.insert(fanLogItem) > 0;
    }

    @Override
    public Boolean deleteLogItem(Integer fanId, Integer userId,String status) {
        LambdaQueryWrapper<FanLogItem> fanLogItemLambdaQueryWrapper = Wrappers.lambdaQuery();
        fanLogItemLambdaQueryWrapper.eq(FanLogItem::getFanId,fanId).eq(FanLogItem::getUserId,userId).eq(FanLogItem::getStatus,status);
        return fanLogItemDao.delete(fanLogItemLambdaQueryWrapper) > 0;
    }

    @Override
    public boolean updateLogItemByMulti(Integer fanId, Integer userId, String status, Timestamp timestamp) {
        return fanLogItemDao.updateLogItemByMulti(fanId,userId,status,timestamp) > 0;
    }

    @Override
    public IPage<FanLogItem> getPage(Page<FanLogItem> page, LambdaQueryWrapper<FanLogItem> logItemLambdaQueryWrapper) {
        return fanLogItemDao.selectPage(page, logItemLambdaQueryWrapper);
    }

    @Override
    public List<UserPair> getUserNamesById(List<Integer> idList) {
        return fanLogItemDao.selectUserNamesById(idList);
    }

    @Override
    public boolean deleteById(Integer id) {
        return fanLogItemDao.deleteById(id) > 0;
    }
}
