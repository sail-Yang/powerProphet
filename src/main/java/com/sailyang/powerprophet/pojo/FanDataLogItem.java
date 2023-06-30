package com.sailyang.powerprophet.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("log")
public class FanDataLogItem implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("fan_id")
    private Integer fanId;
    @TableField("type")
    private String type;
    @TableField("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp date;
    @TableField("starttime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp startTime;
    @TableField("endtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp endTime;
    @TableField("status")
    private String status;
    @TableField("model")
    private String model;
    @TableField("nums")
    private Integer nums;

    public FanDataLogItem(Integer userId, Integer fanId, String type, Timestamp date, Timestamp startTime, Timestamp endTime, String status, String model) {
        this.userId = userId;
        this.fanId = fanId;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.model = model;
    }
}
