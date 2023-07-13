package com.sailyang.powerprophet.controller;

import com.sailyang.powerprophet.pojo.*;
import com.sailyang.powerprophet.service.FanDataService;
import com.sailyang.powerprophet.service.FanLogItemService;
import com.sailyang.powerprophet.service.FanService;
import com.sailyang.powerprophet.service.UserService;
import com.sailyang.powerprophet.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power/fandata")
public class FanDataController {
    @Autowired
    private FanDataService fanDataService;
    @Autowired
    private UserService userService;
    @Autowired
    private FanService fanService;

    @Autowired
    private FanLogItemService fanLogItemService;

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
    @GetMapping(value = "/fannums")
    @ResponseBody
    public R getFanNums(){
        Integer fans = fanService.getFans();
        if(fans == 0){
            return new R(-1,"获取错误,无记录",null);
        }
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("fans",fans);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @GetMapping(value = "/fans")
    @ResponseBody
    public R getFans(@RequestParam(value = "username") String userName){
        User user = userService.getByUserName(userName);
        if(user == null){
            return  new R(-1,"获取错误,该用户不存在",null);
        }
        List<FanAndOutliers> fanAndOutliersList = fanService.getFanAndOutliers(user.getId());
        Map<String,Object> responseData = new HashMap<>();
        responseData.put("fanlist",fanAndOutliersList);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @PostMapping(value = "/fanuser")
    @ResponseBody
    public R updateFanUser(@RequestParam(value = "fanid") Integer fanId, @RequestParam(value = "username") String userName){
        Map<String,Object> responseData = new HashMap<>();
        if(!"cancel".equals(userName)){
            User user = userService.getByUserName(userName);
            if(user == null){
                return  new R(-1,"获取错误,该用户不存在",null);
            }
            boolean flag1 = fanService.updateFanUser(fanId, user.getId());
            if(!flag1){
                return  new R(-1,"更新维修人员失败",null);
            }
            boolean flag2 = fanLogItemService.addLogItem(fanId,user.getId());
            if(!flag2){
                return  new R(-1,"添加维修日志日志失败",null);
            }
            responseData.put("id",user.getId());
            return new R(20000,"成功",responseData);
        }else{
            Fan fan = fanService.getById(fanId);
            boolean flag1 = fanService.updateFanUser(fanId, 0);
            if(!flag1){
                return  new R(-1,"取消维修人员失败",null);
            }
            boolean flag2 = fanLogItemService.updateLogItemByMulti(fanId,fan.getUserId(),"yes", TimeUtils.getNowTime());
            if(!flag2){
                return  new R(-1,"更新维修日志失败",null);
            }
            return new R(20000,"成功",null);
        }
    }

    @CrossOrigin
    @GetMapping(value="/getavg")
    @ResponseBody
    public R getAvgPower() {
        Map<String,Object> responseData = new HashMap<>();
        Timestamp bgTime = Timestamp.valueOf("2021-09-30 00:00:00");
        Timestamp edTime = Timestamp.valueOf("2021-09-30 23:45:00");
        Float avgPower = fanDataService.getAvgPower(bgTime,edTime);
        avgPower = (float)(Math.round(avgPower*100))/100;
        responseData.put("avg",avgPower);
        return new R(20000,"成功",responseData);
    }
}
