package com.sparta.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.backoffice.entity.User;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class PwdResponseDto extends CommonResponseDto{
    private String password;

    public PwdResponseDto(User user) {
        this.password = user.getPassword();
    }
}
