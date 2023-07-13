package com.sailyang.powerprophet.utils;

import com.sailyang.powerprophet.pojo.PreResult;

import java.util.List;

public class PredictUtils {
    public static List<PreResult> joinPreResult(List<PreResult> preResultList, List<PreResult> prePowerList){
        int a = 0, b = 0;
        while(b < prePowerList.size() && a < preResultList.size()){
            if(prePowerList.get(b).getDatatime().equals(preResultList.get(a).getDatatime())){
                preResultList.get(a).setYd15(prePowerList.get(b).getYd15());
                a++;b++;
            }else{
                a++;
            }
        }
        return preResultList;
    }
}
