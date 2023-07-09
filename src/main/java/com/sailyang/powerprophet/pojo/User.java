package com.sailyang.powerprophet.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("roles")
    private String roles;
    @TableField("email")
    private String email;
    @TableField("model")
    private String model;
    @TableField("min_right")
    private Integer minRight;
    @TableField("max_right")
    private Integer maxRight;
    @TableField("visit_nums")
    private Integer visitNums;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
