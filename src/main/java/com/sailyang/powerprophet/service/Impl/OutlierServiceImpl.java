package com.sailyang.powerprophet.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailyang.powerprophet.dao.OutlierDao;
import com.sailyang.powerprophet.dao.UserDao;
import com.sailyang.powerprophet.pojo.Outlier;
import com.sailyang.powerprophet.pojo.PreResult;
import com.sailyang.powerprophet.pojo.User;
import com.sailyang.powerprophet.service.OutilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
public class OutlierServiceImpl extends ServiceImpl<OutlierDao, Outlier> implements OutilerService {
    @Autowired
    private OutlierDao outlierDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean addOutlier(Outlier outlier) {
        return this.save(outlier);
    }

    @Override
    public List<Outlier> getOutliersByLogIdAndPeriod(Integer logId, Timestamp begtime, Timestamp endtime) {
        return outlierDao.selectOutliersByLogIdAndPeriod(begtime,endtime,logId);
    }

    @Override
    public List<Outlier> getOutliersByLogId(Integer logId) {
        return outlierDao.selectOutliersByLogId(logId);
    }

    @Override
    public boolean deleteByLogId(Integer logId) {
        return outlierDao.deleteByLogId(logId) > 0;
    }

    @Override
    public boolean addOutliers(Integer logId,Integer fanId, Integer useId,List<PreResult> preResultList){
        User user = userDao.selectById(useId);
        int min_right = user.getMinRight();
        int max_right = user.getMaxRight();
        List<PreResult> subPreResultList = new LinkedList<>();
        subPreResultList.addAll(preResultList);
        subPreResultList.removeIf(element -> element.getYd15() <= max_right && element.getYd15() >= min_right);
        List<Outlier> outlierList = new LinkedList<>();
        for(PreResult e : subPreResultList) {
            outlierList.add(new Outlier(logId,e.getPower(),e.getYd15(),e.getDatatime()));
        }
        return  outlierDao.addOutliers(logId,fanId,outlierList) > 0;
    }
}
