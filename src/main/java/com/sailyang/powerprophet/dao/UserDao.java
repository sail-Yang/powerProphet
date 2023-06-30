package com.sailyang.powerprophet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sailyang.powerprophet.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("select * from users where username = #{username}")
    User selectByUserName(@Param("username") String userName);

    @Select("select * from users where email = #{email}")
    User selectByEmail(@Param("email") String email);

    @Select("select username from users")
    List<String> selectUserNames();

    @Select("select * from users where id = #{id}")
    User selectById(@Param("id") Integer id);
}
