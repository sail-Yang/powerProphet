package com.sailyang.powerprophet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sailyang.powerprophet.pojo.FanData;
import com.sailyang.powerprophet.pojo.PreResult;

import java.sql.Timestamp;
import java.util.List;

public interface FanDataService extends IService<FanData> {
    Boolean saves(List<FanData> fanDataList);
    Boolean update(FanData fanData);
    Boolean updates(Integer fanId, List<PreResult> preResultList);
    Boolean delete(Integer id);
    Boolean deleteByFanId(Integer fanId);
    FanData getById(Integer id);
    List<FanData> getListByFanId(Integer fanId,Integer nums);
    IPage<FanData> getPage(int currentPage, int pageSize);
    List<String> getFanIds();
    FanData getByFanIdAndTime(Timestamp time, Integer fanId);
    List<FanData> getByFanIdAndPeriod(Timestamp beginTime, Timestamp endTime,Integer fanId);
    List<PreResult> getPrePowerByFanIdAndPeriod(Timestamp beginTime, Timestamp endTime,Integer fanId);
    Integer getDatas();
    Integer getFans();
}
