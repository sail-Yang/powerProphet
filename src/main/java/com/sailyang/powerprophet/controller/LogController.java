package com.sailyang.powerprophet.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sailyang.powerprophet.pojo.*;
import com.sailyang.powerprophet.service.FanDataLogItemService;
import com.sailyang.powerprophet.service.OutilerService;
import com.sailyang.powerprophet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power/log")
public class LogController {
    @Autowired
    private OutilerService outilerService;

    @Autowired
    private FanDataLogItemService fanDataLogItemService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping(value = "/list")
    @ResponseBody
    public R query(@RequestBody LogQuery logQuery){
        Map<String,Object> responseData = new HashMap<>();
        User user = userService.getByUserName(logQuery.getUsername());
        if(user == null){
            return  new R(-1,"获取错误,该用户不存在",null);
        }
        Integer userId = user.getId();
        LambdaQueryWrapper<FanDataLogItem> logItemLambdaQueryWrapper = Wrappers.lambdaQuery();
        logItemLambdaQueryWrapper.eq(FanDataLogItem::getModel, logQuery.getModel())
                .eq(FanDataLogItem::getFanId,logQuery.getFanid())
                .eq(FanDataLogItem::getType,logQuery.getType())
                .eq(FanDataLogItem::getUserId,userId)
                .orderByDesc(FanDataLogItem::getId);
        Page<FanDataLogItem> logItemPage = new Page<>(logQuery.getPage(),logQuery.getLimit());
        IPage<FanDataLogItem> logItemIPage = fanDataLogItemService.getPage(logItemPage, logItemLambdaQueryWrapper);
        List<FanDataLogItem> fanDataLogItemList = logItemIPage.getRecords();
        long totalItems = logItemIPage.getTotal();
        responseData.put("items",fanDataLogItemList);
        responseData.put("total",totalItems);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @PostMapping(value = "/getbyuserid")
    @ResponseBody
    public R getByFanId(@RequestParam(value = "userid") Integer userId){
        Map<String,Object> responseData = new HashMap<>();
        List<FanDataLogItem> logItem = fanDataLogItemService.getByUserId(userId);
        responseData.put("items",logItem);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @PostMapping(value = "/add")
    public R addItem(@RequestBody FanDataLogItem logItem){
        boolean flag = fanDataLogItemService.save(logItem);
        return new R(flag ? 20000 : -1,"save logitem success",null);
    }

    @CrossOrigin
    @PostMapping(value = "/deleteall")
    public R deleteByUserId(@RequestParam(value = "userid") Integer userId){
        boolean flag = fanDataLogItemService.deleteByUserId(userId);
        return new R(flag ? 20000 : -1,"delete all logitem success",null);
    }

    @CrossOrigin
    @PostMapping(value = "/del")
    public R deleteById(@RequestParam(value = "id") Integer logId){
        boolean flag = fanDataLogItemService.deleteById(logId);
        return new R(flag ? 20000 : -1,"delete logitem success",null);
    }

    @CrossOrigin
    @GetMapping(value = "/outliers")
    @ResponseBody
    public R getOutliersByLogId(@RequestParam(value = "logid") Integer logId){
        List<Outlier> outlierList = outilerService.getOutliersByLogId(logId);
        if(outlierList != null){
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("outliers",outlierList);
            responseData.put("total",outlierList.size());
            return new R(20000,"获取成功",responseData);
        }else{
            return new R(-1,"获取失败",null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/nums")
    public R getFans(@RequestParam(value="username") String username){
        Map<String,Object> responseData = new HashMap<>();
        User user = userService.getByUserName(username);
        if(user == null){
            return  new R(-1,"获取错误,该用户不存在",null);
        }
        Integer nums = fanDataLogItemService.getLogNums(user.getId());
        responseData.put("nums",nums);
        return new R(20000,"获取成功",responseData);
    }
}
