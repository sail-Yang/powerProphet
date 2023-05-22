package com.sailyang.powerprophet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sailyang.powerprophet.pojo.Fan;
import com.sailyang.powerprophet.pojo.FanData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface FanDataDao extends BaseMapper<FanData> {
    @Select("DELETE FROM fandata where fan_id=#{fan_id}")
    int deleteByFanId(@Param("fanid")Integer fanId);

    @Select("select distinct fan_id from fandata")
    List<String> selectFanIds();

    @Select("select * from fandata where fan_id = #{fanid} LIMIT #{nums}")
    List<FanData> selectFanDataListByFanId(@Param("fanid") Integer fanId,@Param("nums") Integer nums);

    @Select("select * from fandata where fan_id = #{fanid} AND datatime = #{time}")
    FanData selectByFanIdAndTime(@Param("time")Timestamp time,@Param("fanid") Integer fanId);

    @Select("select * from fandata where fan_id = #{fanid} AND datatime >= #{bgtime} AND datatime <= #{edtime}")
    List<FanData> selectFanDataListByFanIdAndPeriod(@Param("bgtime") Timestamp begintime,@Param("edtime") Timestamp endtime,@Param("fanid") Integer fanId);
}
