package com.sparta.backoffice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserRequestDto {

    @Pattern(regexp = "[a-z0-9]{4,10}$")
    private String username;
    @Pattern(regexp = "[A-Za-z\\d~!@#$%^&*()+|=]{8,15}$")
    private String password;
    private String nickname;
    private Integer age;
    private String email;
    private String userinfo;
    private String userurl;
}
