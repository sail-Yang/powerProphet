package com.sailyang.powerprophet.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogQuery implements Serializable {
    private Integer page;
    private Integer limit;
    private String type;
    private String model;
    private Integer fanid;
    private String username;

}
