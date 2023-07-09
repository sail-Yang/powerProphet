package com.sailyang.powerprophet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sailyang.powerprophet.pojo.FanLogItem;
import com.sailyang.powerprophet.pojo.UserPair;

import java.sql.Timestamp;
import java.util.List;

public interface FanLogItemService extends IService<FanLogItem> {
    boolean addLogItem(Integer fanId, Integer userId);

    Boolean deleteLogItem(Integer fanId, Integer userId, String status);

    IPage<FanLogItem> getPage(Page<FanLogItem> page, LambdaQueryWrapper<FanLogItem> logItemLambdaQueryWrapper);

    List<UserPair> getUserNamesById(List<Integer> idList);

    boolean deleteById(Integer id);

    boolean updateLogItemByMulti(Integer fanId, Integer userId, String status, Timestamp timestamp);
}
