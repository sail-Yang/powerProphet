package com.sailyang.powerprophet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailyang.powerprophet.dao.*;
import com.sailyang.powerprophet.pojo.*;
import com.sailyang.powerprophet.service.FanDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class FanDataServiceImpl extends ServiceImpl<FanDataDao,FanData> implements FanDataService {
    @Autowired
    private FanDataDao fanDataDao;

    @Autowired
    private FanDao fanDao;

    @Override
    public Boolean saves(List<FanData> fanDataList){
        return this.saveBatch(fanDataList,1000);
    }

    @Override
    public Boolean update(FanData fanData) {
        return fanDataDao.updateById(fanData) > 0;
    }

    @Override
    public Boolean updates(Integer fanId, List<PreResult> preResultList) {
        return fanDataDao.updatePreResultByFanId(fanId,preResultList) > 0;
    }

    @Override
    public Boolean delete(Integer id) {
        return fanDataDao.deleteById(id) > 0;
    }

    @Override
    public Boolean deleteByFanId(Integer fanId) {
        List<Integer> fanIds = new LinkedList<>();
        fanIds.add(fanId);
        QueryWrapper<FanData> wrapper = new QueryWrapper<>();
        wrapper.in("fan_id",fanIds);
        return fanDataDao.delete(wrapper)>0;
    }

    @Override
    public IPage<FanData> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage,pageSize);
        fanDataDao.selectPage(page,null);
        return page;
    }

    @Override
    public List<FanData> getListByFanId(Integer fanId,Integer nums) {
        return fanDataDao.selectFanDataListByFanId(fanId,nums);
    }

    @Override
    public FanData getById(Integer id) {
        return fanDataDao.selectById(id);
    }

    @Override
    public List<String> getFanIds(){
        return fanDataDao.selectFanIds();
    }

    @Override
    public FanData getByFanIdAndTime(Timestamp time, Integer fanId){
        return fanDataDao.selectByFanIdAndTime(time,fanId);
    }

    @Override
    public List<FanData> getByFanIdAndPeriod(Timestamp beginTime, Timestamp endTime,Integer fanId) {
        return fanDataDao.selectFanDataListByFanIdAndPeriod(beginTime,endTime,fanId);
    }

    @Override
    public List<FanData> getByFanIdAndPeriodAndType(Timestamp beginTime, Timestamp endTime,Integer fanId,String type) {
        if("windspeedAndws".equals(type)){
            return fanDataDao.selectWindListByFanIdAndPeriodAndType(beginTime,endTime,fanId);
        }else{
            return fanDataDao.selectFanDataListByFanIdAndPeriodAndType(beginTime,endTime,fanId,type);
        }
    }

    @Override
    public  List<PreResult> getPrePowerByFanIdAndPeriod(Timestamp beginTime, Timestamp endTime,Integer fanId){
        return fanDataDao.selectPrePowerByFanIdAndPeriod(beginTime,endTime,fanId);
    }

    @Override
    public Integer getDatas() {
        return fanDataDao.selectDatas();
    }

    @Override
    public Float getAvgPower(Timestamp beTime, Timestamp edTime) {
        return fanDataDao.selectAvgPower(beTime, edTime);
    }
}
