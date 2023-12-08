package com.sparta.backoffice.dto;

import com.sparta.backoffice.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequestDto {
    private UserRoleEnum role;
}
