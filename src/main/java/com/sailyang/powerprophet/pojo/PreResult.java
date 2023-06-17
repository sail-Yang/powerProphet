package com.sailyang.powerprophet.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("fandata1")
public class PreResult implements Serializable {
    @TableField("datatime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp datatime;
    @TableField("power")
    private Float power;
    @TableField("yd15")
    private Float yd15;
    @TableField("prepower")
    private Float prePower;
}
