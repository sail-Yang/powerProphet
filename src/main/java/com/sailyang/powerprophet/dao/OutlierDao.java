package com.sailyang.powerprophet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sailyang.powerprophet.pojo.Outlier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface OutlierDao extends BaseMapper<Outlier> {

    @Select("select * from outliers where log_id = #{logid} AND date >= #{bgtime} AND date <= #{edtime}")
    List<Outlier> selectOutliersByLogIdAndPeriod(@Param("bgtime") Timestamp begintime, @Param("edtime") Timestamp endtime, @Param("logid") Integer logId);

    @Select("select * from outliers where log_id = #{logid}")
    List<Outlier> selectOutliersByLogId(@Param("logid") Integer logId);

    @Update({"<script>",
            "<foreach collection='list' item= 'item' index ='index' separator=';'>",
            "insert into outliers(log_id,date,yd15,power) values(#{logid},#{item.date},#{item.yd15},#{item.power})",
            "on duplicate key update power = #{item.power},yd15 = #{item.yd15}",
            "</foreach>",
            "</script>"})
    int addOutliers(@Param("logid")Integer logId,@Param("fanid")Integer fanId,@Param("list") List<Outlier> outlierListList);

    @Select("DELETE FROM outliers where log_id=#{logid}")
    int deleteByLogId(@Param("logid")Integer logId);
}
