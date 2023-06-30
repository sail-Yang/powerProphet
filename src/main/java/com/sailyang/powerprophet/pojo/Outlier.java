package com.sailyang.powerprophet.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Outlier {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("log_id")
    private Integer logId;
    @TableField("power")
    private Float power;
    @TableField("yd15")
    private Float yd15;
    @TableField("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp date;

    public Outlier(Integer logId, Float power, Float yd15, Timestamp date) {
        this.logId = logId;
        this.power = power;
        this.yd15 = yd15;
        this.date = date;
    }
}
