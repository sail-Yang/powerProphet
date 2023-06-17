package com.sailyang.powerprophet.controller;
import com.sailyang.powerprophet.pojo.PreResult;
import com.sailyang.powerprophet.pojo.R;
import com.sailyang.powerprophet.service.FanDataService;
import com.sailyang.powerprophet.utils.PredictUtils;
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

@RestController
@RequestMapping("/power/predict")
public class PredictController {
    @Autowired
    private FanDataService fanDataService;
    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin
    @GetMapping(value = "/realtime")
    @ResponseBody
    public R getRealTimePower(@RequestParam(value = "fanid") Integer fanId, @RequestParam(value = "model") String model){
        String url = "http://localhost:5000/realtime";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("fanid", fanId).queryParam("model", model);
        ResponseEntity<String> results = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, null, String.class);
        if(results.getStatusCode().is2xxSuccessful()){
            List<PreResult> preResultList = databinds(results.getBody());
            List<PreResult> prePowerList = fanDataService.getPrePowerByFanIdAndPeriod(preResultList.get(0).getDatatime(),preResultList.get(preResultList.size() - 1).getDatatime(),fanId);
            List<PreResult> resultList = PredictUtils.joinPreResult(preResultList, prePowerList);
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("fanDataList",resultList);
            boolean res = fanDataService.updates(1,preResultList);
            if(!res){
                return new R(-1, "预测失败", null);
            }else{
                return new R(20000, "预测成功", responseData);
            }
        }else{
            return new R(-1, "预测失败", null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/period")
    @ResponseBody
    public R getPeriodPower(@RequestParam(value = "fanid") Integer fanId,@RequestParam(value = "bgtime") Timestamp beginTime, @RequestParam(value ="edtime") Timestamp endTime,Integer hours, @RequestParam(value = "model") String model ){
        String url = "http://localhost:5000/period";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("fanid", fanId)
                .queryParam("bgtime", beginTime)
                .queryParam("edtime", endTime)
                .queryParam("hours", hours)
                .queryParam("model", model);
        ResponseEntity<String> results = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, null, String.class);
        if(results.getStatusCode().is2xxSuccessful()){
//            System.out.println(results.getBody());
            List<PreResult> preResultList = databinds(results.getBody());
            if(preResultList == null){
                return new R(-1, "数据集不足，预测失败", null);
            }
            List<PreResult> prePowerList = fanDataService.getPrePowerByFanIdAndPeriod(preResultList.get(0).getDatatime(),preResultList.get(preResultList.size() - 1).getDatatime(),fanId);
            List<PreResult> resultList = PredictUtils.joinPreResult(preResultList, prePowerList);
            Map<String,Object> responseData = new HashMap<>();
            responseData.put("fanDataList",resultList);
            return new R(20000, "预测成功", responseData);
        }else{
            return new R(-1, "预测失败", null);
        }
    }

}
