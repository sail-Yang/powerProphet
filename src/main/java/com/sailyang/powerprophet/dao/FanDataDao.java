package com.sailyang.powerprophet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sailyang.powerprophet.pojo.Fan;
import com.sailyang.powerprophet.pojo.FanData;
import com.sailyang.powerprophet.pojo.PreResult;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface FanDataDao extends BaseMapper<FanData> {
    @Select("DELETE FROM fandata where fan_id=#{fanid}")
    int deleteByFanId(@Param("fanid")Integer fanId);

    @Select("select distinct fan_id from fandata")
    List<String> selectFanIds();

    @Select("select * from fandata where fan_id = #{fanid} LIMIT #{nums}")
    List<FanData> selectFanDataListByFanId(@Param("fanid") Integer fanId,@Param("nums") Integer nums);

    @Select("select * from fandata where fan_id = #{fanid} AND datatime = #{time}")
    FanData selectByFanIdAndTime(@Param("time")Timestamp time,@Param("fanid") Integer fanId);

    @Select("select * from fandata where fan_id = #{fanid} AND datatime >= #{bgtime} AND datatime <= #{edtime}")
    List<FanData> selectFanDataListByFanIdAndPeriod(@Param("bgtime") Timestamp begintime,@Param("edtime") Timestamp endtime,@Param("fanid") Integer fanId);

    @Select("select datatime,windspeed,ws from fandata where fan_id = #{fanid} AND datatime >= #{bgtime} AND datatime <= #{edtime}")
    List<FanData> selectWindListByFanIdAndPeriodAndType(@Param("bgtime") Timestamp begintime,@Param("edtime") Timestamp endtime,@Param("fanid") Integer fanId);

    @Select("select datatime,${type} from fandata where fan_id = #{fanid} AND datatime >= #{bgtime} AND datatime <= #{edtime}")
    List<FanData> selectFanDataListByFanIdAndPeriodAndType(@Param("bgtime") Timestamp begintime,@Param("edtime") Timestamp endtime,@Param("fanid") Integer fanId,@Param("type") String type);

    @Select("select datatime,yd15 from fandata where fan_id = #{fanid} AND datatime >= #{bgtime} AND datatime <= #{edtime}")
    List<PreResult> selectPrePowerByFanIdAndPeriod(@Param("bgtime") Timestamp begintime,@Param("edtime") Timestamp endtime,@Param("fanid") Integer fanId);

    @Update({"<script>",
            "<foreach collection='list' item= 'item' index ='index' separator=';'>",
            "insert into fandata(power,yd15,datatime,fan_id) values(#{item.power},#{item.yd15},#{item.datatime},#{fanid})",
            "on duplicate key update power = #{item.power},yd15 = #{item.yd15}",
            "</foreach>",
            "</script>"})
    int updatePreResultByFanId(@Param("fanid")Integer fanId,@Param("list") List<PreResult> preResultList);

    @Select("SELECT COUNT(DISTINCT id) FROM fans")
    int selectFans();

    @Select("SELECT COUNT(*) FROM fandata")
    int selectDatas();

    @Select("select AVG(yd15) from fandata WHERE datatime >= #{bgtime} AND datatime <= #{edtime}")
    Float selectAvgPower(@Param("bgtime")Timestamp bgTime, @Param("edtime")Timestamp edTime);
}
