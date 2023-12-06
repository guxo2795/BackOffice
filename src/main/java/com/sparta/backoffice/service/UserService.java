package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.PwdResponseDto;
import com.sparta.backoffice.dto.PwdUpdateRequestDto;
import com.sparta.backoffice.dto.UserRequestDto;
import com.sparta.backoffice.dto.UserResponseDto;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.repository.UserRepository;
import com.sparta.backoffice.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private User user;

    // 회원가입
    public void signup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        String nickname = userRequestDto.getNickname();
        Integer age = userRequestDto.getAge();
        String email = userRequestDto.getEmail();
        String userinfo = userRequestDto.getUserinfo();
        String userurl = userRequestDto.getUserurl();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 username입니다.");
        }

        User user = new User(username, password, nickname, age, email, userinfo, userurl);
        userRepository.save(user);
    }


    public void login(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional//비밀번호 변경
    public PwdResponseDto updatePwd(PwdUpdateRequestDto pwdUpdateRequestDto, UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        String newPwd = passwordEncoder.encode(pwdUpdateRequestDto.getPassword());
        user.updatePwd(newPwd);
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
}