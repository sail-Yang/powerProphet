package com.sailyang.powerprophet.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInfo implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String code;
}
