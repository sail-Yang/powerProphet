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
    private String role;
    @TableField("email")
    private String email;
    @TableField("model")
    private String model;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
