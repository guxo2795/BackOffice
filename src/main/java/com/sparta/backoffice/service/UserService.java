package com.sparta.backoffice.service;


import com.sparta.backoffice.dto.*;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.entity.UserRoleEnum;
import com.sparta.backoffice.repository.UserRepository;
import com.sparta.backoffice.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private User user;
    private String pwd, pwdcheck;


    // 회원가입
    public void signup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        UserRoleEnum role = userRequestDto.getRole();

        String nickname = userRequestDto.getNickname();
        Integer age = userRequestDto.getAge();
        String email = userRequestDto.getEmail();
        String userinfo = userRequestDto.getUserinfo();
        String userurl = userRequestDto.getUserurl();

        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 username입니다.");
        }
        User user = new User(username, password, nickname, role, age, email, userinfo, userurl);
        userRepository.save(user);
    }

    // 로그인
    public void login(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    public void checkPwd(PwdCheckRequestDto pwdCheckRequestDto, UserDetailsImpl userDetails){
        pwd = pwdCheckRequestDto.getPassword();
        pwdcheck = userDetails.getUser().getPassword();
        if(!passwordEncoder.matches(pwd, pwdcheck)){
            System.out.println(pwdcheck);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional//비밀번호 변경
    public PwdResponseDto updatePwd(PwdUpdateRequestDto pwdUpdateRequestDto, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        String newPwd = passwordEncoder.encode(pwdUpdateRequestDto.getPassword());
        user.updatePwd(newPwd);

        // 현재 비밀번호를 최근 사용한 비밀번호 목록에 추가
//        user.getRecentHashedPasswords().add(newPwd);
////
////        // 비밀번호를 암호화하여 저장
////        user.setPassword(passwordEncoder.encode(newPwd));
//
//        // 최근 사용한 비밀번호 갯수 제한
//        int maxRecentPasswords = 3;
//        List<String> recentPasswords = user.getRecentHashedPasswords();
//        if (recentPasswords.size() > maxRecentPasswords) {
//            recentPasswords.subList(0, recentPasswords.size() - maxRecentPasswords).clear();
//        }

        if(!passwordEncoder.matches(pwd,pwdcheck)){
            System.out.println(pwdcheck);
            throw new IllegalArgumentException("비밀번호 확인을 해주세요");
        }
        userRepository.save(user);
        return new PwdResponseDto(user);
    }

    //프로필
    public UserResponseDto viewProfile(UserDetailsImpl userDetails) {
        if (userDetails != null) {
            user = userDetails.getUser();
        } else {
            throw new NullPointerException("로그인 된 회원이 아닙니다.");
        }
        return new UserResponseDto(user);
    }

    @Transactional//프로필 수정
    public UserResponseDto updateProfile(UserUpdateRequestDto userUpdateRequestDto, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        user.updateUser(userUpdateRequestDto);
        userRepository.save(user);
        return new UserResponseDto(user);
    }
}
