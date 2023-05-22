package com.sailyang.powerprophet.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fandata")
public class FanData {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("fan_id")
    private Integer fanId;
    @TableField("datatime")
    private Timestamp datatime;
    @TableField("windspeed")
    private Float windSpeed;
    @TableField("prepower")
    private Float prePower;
    @TableField("winddirection")
    private Integer windDirection;
    @TableField("temperature")
    private Float temperature;
    @TableField("humidity")
    private Integer humidity;
    @TableField("pressure")
    private Integer pressure;

    @TableField("ws")
    private Float ws;

    @TableField("power")
    private Float power;

    @TableField("yd15")
    private Float yd15;

}
