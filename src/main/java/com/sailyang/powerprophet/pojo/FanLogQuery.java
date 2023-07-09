package com.sailyang.powerprophet.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FanLogQuery {
    private Integer page;
    private Integer limit;
    private String status;
    private Integer fanid;
    private String username;
}
