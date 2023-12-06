package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.*;
import com.sparta.backoffice.jwt.JwtUtil;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.signup(userRequestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto("중복된 username입니다.", HttpStatus.BAD_REQUEST.value()));
        }


        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        try {
            userService.login(userRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDto.getUsername()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/checkpwd")
    public ResponseEntity<CommonResponseDto> checkPwd(@RequestBody PwdCheckRequestDto pwdCheckRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            userService.checkPwd(pwdCheckRequestDto,userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("정보 수정페이지로 가기", HttpStatus.OK.value()));
    }

    //비밀번호 변경
    @PostMapping("/updatepwd")
    public ResponseEntity<CommonResponseDto> updatePwd(@RequestBody PwdUpdateRequestDto pwdUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        PwdResponseDto ResponseDto;
        try {
            ResponseDto =  userService.updatePwd(pwdUpdateRequestDto,userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("비밀번호 수정완료", HttpStatus.OK.value()));
        //ResponseEntity.status(HttpStatus.OK.value()).body(ResponseDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<CommonResponseDto> profile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.viewProfile(userDetails);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(userResponseDto);
    }

    @PatchMapping("/profile/update")
    public ResponseEntity<CommonResponseDto> update(@RequestBody UserUpdateRequestDto userRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.updateProfile(userRequestDto, userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(userResponseDto);
    }



}
