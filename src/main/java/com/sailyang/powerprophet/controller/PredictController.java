package com.sailyang.powerprophet.controller;
import com.sailyang.powerprophet.pojo.*;
import com.sailyang.powerprophet.service.*;
import com.sailyang.powerprophet.utils.PredictUtils;
import com.sailyang.powerprophet.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sailyang.powerprophet.utils.JsonUtils.databinds;
import static com.sailyang.powerprophet.utils.TokenUtils.createJWT;

@RestController
@RequestMapping("/power/predict")
public class PredictController {
    @Autowired
    private FanDataService fanDataService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private OutilerService outilerService;

    @Autowired
    private FanDataLogItemService fanDataLogItemService;

    @Autowired
    private FanService fanService;
    /*

    * */
    @CrossOrigin
    @GetMapping(value = "/realtime")
    @ResponseBody
    public R getRealTimePower(@RequestParam(value="username") String userName, @RequestParam(value = "fanid") Integer fanId, @RequestParam(value = "model") String model){
        Timestamp date = TimeUtils.getNowTime();
        //判断当前用户是否存在
        User user = userService.getByUserName(userName);
        if(user == null){
            return new R(-1, "预测失败,该用户不存在", null);
        }
        //调用深度学习模型
        String url = "http://118.195.146.68:5001/realtime";
//        String url = "http://localhost:5000/realtime";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("fanid", fanId).queryParam("model", model);
        ResponseEntity<String> results;
        try {
            results = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, null, String.class);
        }catch (Exception e){
            return new R(-1, "预测失败,数据集不足", null);
        }

        if(results.getStatusCode().is2xxSuccessful()){
            //每次预测都更新对应风机的预测总次数
            Fan fan = fanService.getById(fanId);
            fan.setNums(fan.getNums()+1);
            fanService.updateById(fan);
            /*从深度学习框架获取json格式的预测数据*/
            List<PreResult> preResultList = databinds(results.getBody());
            List<PreResult> yd15List = fanDataService.getPrePowerByFanIdAndPeriod(preResultList.get(0).getDatatime(),preResultList.get(preResultList.size() - 1).getDatatime(),fanId);
            List<PreResult> resultList = PredictUtils.joinPreResult(preResultList, yd15List);
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("fanDataList",resultList);
            //这里注释了Insert yd15_Pre
            /* 生成日志记录 */
            FanDataLogItem newItem = new FanDataLogItem(user.getId(),fanId,"real",date,preResultList.get(0).getDatatime(),preResultList.get(preResultList.size() - 1).getDatatime(),"success",user.getModel());
            fanDataLogItemService.save(newItem);
            outilerService.addOutliers(newItem.getId(),fanId,user.getId(),preResultList);
            return new R(20000, "预测成功", responseData);
        }else{
            /* 生成日志记录 */
            fanDataLogItemService.save(new FanDataLogItem(user.getId(), fanId,"real",date,null,null,"fail",user.getModel()));
            return new R(-1, "预测失败", null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/period")
    @ResponseBody
    public R getPeriodPower(@RequestParam(value="username") String userName, @RequestParam(value = "fanid") Integer fanId,@RequestParam(value = "bgtime") Timestamp beginTime, @RequestParam(value ="edtime") Timestamp endTime,Integer hours, @RequestParam(value = "model") String model ){
        Timestamp date = TimeUtils.getNowTime();
        //判断当前用户是否存在
        User user = userService.getByUserName(userName);
        if(user == null){
            return new R(-1, "预测失败,该用户不存在", null);
        }
//        String url = "http://localhost:5000/period";
        String url = "http://118.195.146.68:5001/period";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("fanid", fanId)
                .queryParam("bgtime", beginTime)
                .queryParam("edtime", endTime)
                .queryParam("hours", hours)
                .queryParam("model", model);
        ResponseEntity<String> results;
        try {
            results = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, null, String.class);
        }catch (Exception e){
            return new R(-1, "预测失败,数据集不足", null);
        }
        if(results.getStatusCode().is2xxSuccessful()){
            //每次预测都更新对应风机的预测总次数
            Fan fan = fanService.getById(fanId);
            fan.setNums(fan.getNums()+1);
            fanService.updateById(fan);

            List<PreResult> preResultList = databinds(results.getBody());
            if(preResultList == null){
                fanDataLogItemService.save(new FanDataLogItem(user.getId(),fanId,"period",date,beginTime,endTime,"fail",user.getModel()));
                return new R(-1, "数据集不足，预测失败", null);
            }
            List<PreResult> yd15List = fanDataService.getPrePowerByFanIdAndPeriod(preResultList.get(0).getDatatime(),preResultList.get(preResultList.size() - 1).getDatatime(),fanId);
            List<PreResult> resultList = PredictUtils.joinPreResult(preResultList, yd15List);
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("fanDataList",resultList);
            FanDataLogItem newItem = new FanDataLogItem(user.getId(),fanId,"period",date,beginTime,endTime,"success",user.getModel());
            fanDataLogItemService.save(newItem);
            outilerService.addOutliers(newItem.getId(),fanId,user.getId(),preResultList);
            return new R(20000, "预测成功", responseData);
        }else{
            fanDataLogItemService.save(new FanDataLogItem(user.getId(),fanId,"period",date,beginTime,endTime,"fail",user.getModel()));
            return new R(-1, "预测失败", null);
        }
    }

}
