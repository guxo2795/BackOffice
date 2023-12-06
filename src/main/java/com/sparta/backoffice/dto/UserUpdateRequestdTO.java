package com.sparta.backoffice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestdTO {
     String nickname;
     Integer age;
     String email;
     String userinfo;
     String userurl;
}
