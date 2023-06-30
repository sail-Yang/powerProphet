package com.sailyang.powerprophet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sailyang.powerprophet.pojo.FanDataLogItem;
import com.sailyang.powerprophet.pojo.Outlier;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface FanDataLogItemDao extends BaseMapper<FanDataLogItem> {
    @Select("select * from log where user_id = #{userid}")
    List<FanDataLogItem> selectItemsByUserId(@Param("userid")Integer userId);

    @Update("insert into log(user_id,date,type,starttime,endtime,fan_id,status,model) values(#{item.userid},#{item.date},#{item.type},#{item.starttime},#{item.endtime},#{item.fanid},#{item.status},#{item.model})")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int addLogItem(@Param("item")FanDataLogItem fanDataLogItem);

    @Delete("DELETE FROM log where user_id=#{userid}")
    int deleteByUserId(@Param("userid")Integer userId);

    @Delete("DELETE FROM log where id=#{id}")
    int deleteById(@Param("id")Integer id);

    @Select("SELECT COUNT(*) FROM log WHERE user_id=#{userid} ")
    int selectLogNums(@Param("userid")Integer userId);
}
