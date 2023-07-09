package com.sailyang.powerprophet.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sailyang.powerprophet.pojo.*;
import com.sailyang.powerprophet.service.FanLogItemService;
import com.sailyang.powerprophet.service.FanService;
import com.sailyang.powerprophet.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/power/fix")
public class FanController {
    @Autowired
    private UserService userService;
    @Autowired
    private FanService fanService;
    @Autowired
    private FanLogItemService fanLogItemService;

    @CrossOrigin
    @PostMapping(value = "/list")
    @ResponseBody
    public R query(@RequestBody FanLogQuery logQuery){
        Map<String,Object> responseData = new HashMap<>();
        LambdaQueryWrapper<FanLogItem> logItemLambdaQueryWrapper = Wrappers.lambdaQuery();
        logItemLambdaQueryWrapper.eq(FanLogItem::getFanId,logQuery.getFanid())
                .eq(FanLogItem::getStatus,logQuery.getStatus());
        if(!"".equals(logQuery.getUsername()) && logQuery.getUsername() != null){
            User user = userService.getByUserName(logQuery.getUsername());
            if(user == null){
                return  new R(-1,"获取错误,该用户不存在",null);
            }
            Integer userId = user.getId();
            logItemLambdaQueryWrapper.eq(FanLogItem::getUserId,userId);
        }
        logItemLambdaQueryWrapper.orderByDesc(FanLogItem::getId);
        Page<FanLogItem> logItemPage = new Page<>(logQuery.getPage(),logQuery.getLimit());
        IPage<FanLogItem> logItemIPage = fanLogItemService.getPage(logItemPage, logItemLambdaQueryWrapper);
        List<FanLogItem> fanLogItemList = logItemIPage.getRecords();
        List<FanLogResult> fanLogResultList = new ArrayList<>();

        if(fanLogItemList.size()==0){
            responseData.put("items", fanLogResultList);
            responseData.put("total",0);
            return new R(20000,"获取成功",responseData);
        }
        List<UserPair> pairList = fanLogItemService.getUserNamesById(fanLogItemList.stream().map(FanLogItem::getUserId).collect(Collectors.toList()));
        Map<Integer,String> userMap = new HashMap<Integer,String>();

        for(UserPair u : pairList){
            userMap.put(u.getId(),u.getUsername());
        }
        for(int i=0 ;i< fanLogItemList.size();i++){
            FanLogResult fanLogResult = new FanLogResult();
            BeanUtils.copyProperties(fanLogItemList.get(i), fanLogResult);
            fanLogResult.setUserName(userMap.get(fanLogItemList.get(i).getUserId()));
            fanLogResultList.add(fanLogResult);
        }
        long totalItems = logItemIPage.getTotal();
        responseData.put("items",fanLogResultList);
        responseData.put("total",totalItems);
        return new R(20000,"获取成功",responseData);
    }

    @CrossOrigin
    @PostMapping(value = "/del")
    @ResponseBody
    public R deleteById(@RequestParam(value = "id") Integer logId){
        boolean flag = fanLogItemService.deleteById(logId);
        return new R(flag ? 20000 : -1,"delete logitem success",null);
    }
}
