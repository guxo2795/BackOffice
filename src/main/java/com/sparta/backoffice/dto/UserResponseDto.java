package com.sparta.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.backoffice.entity.User;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserResponseDto extends CommonResponseDto{
    private String nickname;
    private Integer age;
    private String email;
    private String userinfo;
    private String userurl;

    public UserResponseDto(User user) {
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.userinfo = user.getUserinfo();
        this.userurl = user.getUserurl();

    }
}
