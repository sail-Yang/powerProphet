package com.sailyang.powerprophet.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sailyang.powerprophet.pojo.FanDataLogItem;

import java.util.List;


public interface FanDataLogItemService extends IService<FanDataLogItem> {
    List<FanDataLogItem> getByUserId(Integer userId);
    boolean deleteByUserId(Integer userId);
    boolean deleteById(Integer id);
    boolean addLogItem(FanDataLogItem logItem);
    boolean saveLogItem(FanDataLogItem logItem);
    IPage<FanDataLogItem> getPage(Page<FanDataLogItem> page, LambdaQueryWrapper<FanDataLogItem>  logItemLambdaQueryWrapper);
    Integer getLogNums(Integer userId);
}
