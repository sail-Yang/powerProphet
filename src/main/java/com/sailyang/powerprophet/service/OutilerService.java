package com.sailyang.powerprophet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sailyang.powerprophet.pojo.Outlier;
import com.sailyang.powerprophet.pojo.PreResult;

import java.sql.Timestamp;
import java.util.List;


public interface OutilerService extends IService<Outlier> {
    List<Outlier> getOutliersByLogIdAndPeriod(Integer logId, Timestamp begtime, Timestamp endtime);
    List<Outlier> getOutliersByLogId(Integer logId);
    boolean addOutlier(Outlier outlier);
    boolean deleteByLogId(Integer logId);
    boolean addOutliers(Integer logId,Integer fanId, Integer useId,List<PreResult> preResultList);

}
