package com.sailyang.powerprophet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sailyang.powerprophet.pojo.Fan;
import com.sailyang.powerprophet.pojo.FanAndOutliers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FanDao extends BaseMapper<Fan> {
    @Select("SELECT f.id,f.name,users.username,f.nums,f.user_id,COUNT(l.nums) as outliers FROM fans f LEFT JOIN log  l ON l.fan_id = f.id AND l.user_id = #{userid} AND  DATE_SUB(CURDATE(), INTERVAL 7 DAY)<=DATE(l.date) left join users on f.user_id = users.id GROUP BY id")
    List<FanAndOutliers> selectFansAndOutliers(@Param("userid")Integer userId);

    @Select("SELECT COUNT(DISTINCT id) FROM fans")
    int selectFans();

    @Update("UPDATE fans SET user_id = #{userid} WHERE id = #{fanid} ")
    int updateUserId(@Param("fanid")Integer fanId, @Param("userid")Integer userId);
}
