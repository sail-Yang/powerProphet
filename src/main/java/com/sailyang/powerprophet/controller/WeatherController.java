package com.sailyang.powerprophet.controller;

import com.sailyang.powerprophet.pojo.FanData;
import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.service.FanDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power/weather")
public class WeatherController {
    @Autowired
    private FanDataService fanDataService;

    @CrossOrigin
    @GetMapping(value = "/period")
    @ResponseBody
    public R getWeatherByPeriod(@RequestParam(value = "bgtime") Timestamp beginTime, @RequestParam(value ="edtime") Timestamp endTime,@RequestParam(value = "fanid") Integer fanId){
        List<FanData> fanDataList = fanDataService.getByFanIdAndPeriod(beginTime,endTime,fanId);
        if(fanDataList != null &&  fanDataList.size() != 0) {
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("fanDataList", fanDataList);
            return new R(20000,"获取成功",responseData);
        }else{
            return  new R(-1,"获取错误,该记录不存在",null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/realtime")
    @ResponseBody
    public R getWeatherByRealTime(@RequestParam(value = "fanid") Integer fanId){
        Timestamp beginTime = Timestamp.valueOf("2021-10-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2021-10-01 23:45:00");
        List<FanData> fanDataList = fanDataService.getByFanIdAndPeriod(beginTime,endTime,fanId);
        if(fanDataList != null &&  fanDataList.size() != 0) {
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("fanDataList", fanDataList);
            return new R(20000,"获取成功",responseData);
        }else{
            return  new R(-1,"获取错误,该记录不存在",null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/periodtype")
    @ResponseBody
    public R getWeatherByPeriodAndType(@RequestParam(value = "bgtime") Timestamp beginTime, @RequestParam(value ="edtime") Timestamp endTime,@RequestParam(value = "fanid") Integer fanId, @RequestParam(value ="type") String type){
        if("wind".equals(type)){
            type = "windspeedAndws";
        }
        List<FanData> fanDataList = fanDataService.getByFanIdAndPeriodAndType(beginTime,endTime,fanId,type);
        if(fanDataList != null &&  fanDataList.size() != 0) {
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("fanDataList", fanDataList);
            return new R(20000,"获取成功",responseData);
        }else{
            return  new R(-1,"获取错误,该记录不存在",null);
        }
    }

}
