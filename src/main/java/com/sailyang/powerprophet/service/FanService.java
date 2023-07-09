package com.sailyang.powerprophet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sailyang.powerprophet.pojo.Fan;
import com.sailyang.powerprophet.pojo.FanAndOutliers;

import java.util.List;


public interface FanService extends IService<Fan> {
    Integer getFans();
    List<Fan> getAllFans();
    Boolean updateFanUser(Integer fanId,Integer userId);
    List<FanAndOutliers> getFanAndOutliers(Integer userId);
}
