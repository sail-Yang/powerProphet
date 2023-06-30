package com.sailyang.powerprophet.controller;

import com.sailyang.powerprophet.pojo.FanData;
import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.service.FanDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power/fandata")
public class FanDataController {
    @Autowired
    private FanDataService fanDataService;

    @CrossOrigin
    @PostMapping(value = "/getbyfanid")
    @ResponseBody
    public R getByFanId(@RequestParam(value = "fanid") Integer fanId){
        Map<String,Object> responseData = new HashMap<>();
        List<FanData> fanDataList = fanDataService.getListByFanId(fanId,100);
        responseData.put("list",fanDataList);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @GetMapping(value = "/getbytime")
    @ResponseBody
    public R getByTime(@RequestParam(value = "time") Timestamp time, @RequestParam(value = "fanid") Integer fanId){
        Map<String,Object> responseData = new HashMap<>();
        FanData fandata = fanDataService.getByFanIdAndTime(time,fanId);
        responseData.put("fandata",fandata);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @GetMapping(value = "/period")
    @ResponseBody
    public R getPowerByPeriod(@RequestParam(value = "bgtime") Timestamp beginTime, @RequestParam(value ="edtime") Timestamp endTime,@RequestParam(value = "fanid") Integer fanId){
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
    @GetMapping(value = "/datas")
    @ResponseBody
    public R getDatas(){
        Integer datas = fanDataService.getDatas();
        if(datas == 0){
            return new R(-1,"获取错误,无记录",null);
        }
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("datas", datas);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @GetMapping(value = "/fans")
    @ResponseBody
    public R getFans(){
        Integer fans = fanDataService.getFans();
        if(fans == 0){
            return new R(-1,"获取错误,无记录",null);
        }
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("fans",fans);
        return new R(20000,"获取成功",responseData);
    }
}
