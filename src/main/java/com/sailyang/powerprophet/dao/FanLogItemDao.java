package com.sailyang.powerprophet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sailyang.powerprophet.pojo.FanDataLogItem;
import com.sailyang.powerprophet.pojo.FanLogItem;
import com.sailyang.powerprophet.pojo.UserPair;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface FanLogItemDao extends BaseMapper<FanLogItem> {
    @Select("select * from fanlog where user_id = #{userid}")
    List<FanDataLogItem> selectItemsByUserId(@Param("userid")Integer userId);

    @Update("UPDATE fanlog SET status='yes', endtime = #{endtime} where fan_id = #{fanid} AND user_id = #{userid}")
    int updateLogItemByMulti(@Param("fanid") Integer fanId, @Param("userid") Integer userId,@Param("status") String status, @Param("endtime") Timestamp endTime);

    @Delete("DELETE FROM fanlog where user_id=#{userid}")
    int deleteByUserId(@Param("userid")Integer userId);

    @Delete("DELETE FROM fanlog where id=#{id}")
    int deleteById(@Param("id")Integer id);

    @Select("SELECT COUNT(*) FROM fanlog WHERE user_id=#{userid} ")
    int selectLogNums(@Param("userid")Integer userId);

    @Select({"<script>",
            "SELECT id,username FROM users WHERE id IN",
            "<foreach collection='list' item= 'item' index ='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    List<UserPair> selectUserNamesById(@Param("list") List<Integer> list);
}
