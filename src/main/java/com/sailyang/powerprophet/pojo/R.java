package com.sailyang.powerprophet.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*统一后端传送给前端的数据格式，例如：
*{
*   "flag": true,
*   "data":{
*       "id":1,
*       "name":"type":"hello"
*   }
* } */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {
    private Integer code;
    private String msg;
    private Object data;
}
