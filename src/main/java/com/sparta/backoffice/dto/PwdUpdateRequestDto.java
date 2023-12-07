package com.sparta.backoffice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PwdUpdateRequestDto {
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 소문자, 대문자, 숫자로만 이루어진 8~15글자로 입력해주세요.")
    String password;
}
