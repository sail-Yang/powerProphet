package com.sailyang.powerprophet.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FanAndOutliers {
    private Integer id;
    private String name;
    private Integer nums;
    private Integer outliers;
}
