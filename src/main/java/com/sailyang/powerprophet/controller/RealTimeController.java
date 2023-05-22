package com.sailyang.powerprophet.controller;

import com.sailyang.powerprophet.pojo.Fan;
import com.sailyang.powerprophet.pojo.FanData;
import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.service.FanDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power/fan")
public class RealTimeController {
    @Autowired
    private FanDataService fanDataService;

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
    public R getRealTimePower(@RequestParam(value = "time") Timestamp time,@RequestParam(value = "fanid") Integer fanId){
        Map<String,Object> responseData = new HashMap<>();
        FanData fandata = fanDataService.getByFanIdAndTime(time,fanId);
        responseData.put("fandata",fandata);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @GetMapping(value = "/init")
    @ResponseBody
    public R getInitialTimePower(@RequestParam(value = "bgtime") Timestamp beginTime, @RequestParam(value ="edtime") Timestamp endTime,@RequestParam(value = "fanid") Integer fanId){
        Map<String,Object> responseData = new HashMap<>();
        List<FanData> fanDataList = fanDataService.getByFanIdAndPeriod(beginTime,endTime,fanId);
        responseData.put("fanDataList", fanDataList);
        return new R(20000,"获取成功",responseData);
    }

}
